/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.status;

import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.DigitalOutput;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.command.WaitCommand;
import edu.wpi.first.wpilibj.util.Color;
import frc.robot.status.commands.ActionCommand;

//
// This subsystem should be used for any status output such as lights.
// While the flashlight can be considered OI, Drivetrain, or Shooter, the intent
// is to eventually put more in here like controlling LED status lights.
//

// TODO: This class is really dedicated to doing LED stuff with the addressable led class.
// Remove all the other stuff, and rename it as such.

public class Status extends Subsystem {

    private static Status INSTANCE = null;

    // Flashlight uses the DIO port and its +5v power.
    private static int FLASHLIGHT_DIO_CHANNEL = 9;

    // Addressable LEDs use the PWM port and its +6v power.
    private static int ADDRESSABLE_LED_PWM_CHANNEL = 0;
    public static int ADDRESSABLE_LED_COUNT = 60; // accessable to actions

    // The DO object that controls the flashlight.
    private DigitalOutput flashlightOutput = null;

    // Addressable LED support.
    private AddressableLED addressableLed = null;

    // The current LED action
    private Action currentAction = null;

    // Object used for locking operations around the currentAction
    private Object actionLock = new Object();

    // Timer allows us to do things at specific game time.
    private Timer timer = null;

    // If both are false then the RIO is running but neither init has triggered
    // (it's in boot up).
    private boolean inAuto = false;
    private boolean inTeleOp = false;

    private Status() {
        super();

        // The RIO DIO ports as outputs have a high pullup resistor.
        // The high pullup causes the voltage regulator to be enabled so
        // here as soon as we initialize the output we set it to false to
        // disable the regulator which turns off the flashlight.
        flashlightOutput = new DigitalOutput(FLASHLIGHT_DIO_CHANNEL);
        flashlightOutput.set(false);

        // Init the addressable led stuff. Note that the docs indicate
        // setting the length is expensive, so doing so here in a singleton
        // is proably best. Don't attempt to do this during a run.
        // https://docs.wpilib.org/en/latest/docs/software/actuators/addressable-leds.html
        addressableLed = new AddressableLED(ADDRESSABLE_LED_PWM_CHANNEL);
        addressableLed.setLength(ADDRESSABLE_LED_COUNT);

        // Spawn the thread that runs actions to control the LEDs.
        new ActionRunner().start();

        // Initialize to black.
        setColor(Color.kBlack, 0);

        // This will output whatever was set in the last setData call continuously.
        addressableLed.start();

        // Create the timer and start it.
        // This will start counting when the RIO initializes.
        timer = new Timer();
        timer.start();
    }

    // This will set/send the buffer to the LEDs.
    public synchronized void setLedData(AddressableLEDBuffer buffer) {
        addressableLed.setData(buffer);
    }

    public void setAction(Action action) {
        // Before doing anything with an action synchornize around it.
        // This prevents swapping the action while the action runner thread is doing
        // something with it.
        synchronized (actionLock) {
            currentAction = action;
        }
    }

    // Things to do when boot starts.
    // Note: these can't be commands since commands require enablement.
    private void scheduleBootActions() {

        ScannerAction scannerAction = new ScannerAction(245, 0, 255, 90);
        scannerAction.setIntervalTime(0.075);
        scannerAction.setIntervalCount(ADDRESSABLE_LED_COUNT * 5 * 2); // number of lights, how many times to update them, back and fourth
        setAction(scannerAction);

        ChaseAction chaseAction = new ChaseAction(245, 0, 255, 90);
        chaseAction.setIntervalCount(-1);
        //setAction(chaseAction);
    }

    // Things to do when auto resets/inits.
    // This can be scheduled commands, command groups, etc.
    private void scheduleAutoActions() {

        // Power up to purple, and stay on.
        PowerUpAction powerUpAction = new PowerUpAction(245, 0, 255, 90);
        powerUpAction.setIntervalCount(ADDRESSABLE_LED_COUNT);
        setAction(powerUpAction);
    }

    // This is what we want to run when teleop starts.
    // This can be scheduled commands, command groups, etc.
    private void scheduleTeleOpActions() {

        // Power up to purple, and stay on - should be on already in match.
        PowerUpAction powerUpAction = new PowerUpAction(245, 0, 255, 90);
        powerUpAction.setIntervalCount(ADDRESSABLE_LED_COUNT);
        setAction(powerUpAction);

        // Using a command group with sequentials to force timely control of the leds.
        CommandGroup commandGroup = new CommandGroup();

        // With 40s to remain, warning.
        commandGroup.addSequential(new WaitCommand(94));
        LedAction warnAction = new LedAction(255, 127, 0, 127);
        commandGroup.addSequential(new ActionCommand(warnAction));

        // With 15s remain, go nuts to climb.
        commandGroup.addSequential(new WaitCommand(24)); // 15 sec before match end (adding extra since it takes a bit to start an action)

        // Run the rainbow to indicate we need to climb.
        ChaseAction chaseAction = new ChaseAction(255, 127, 0, 90);
        chaseAction.setIntervalCount(-1);
        commandGroup.addSequential(new ActionCommand(chaseAction));

        // Don't do anything for some time.
        commandGroup.addSequential(new WaitCommand(15)); // match end

        // Set red which should indicate match end.
        commandGroup.addSequential(new ActionCommand(new LedAction(255, 0, 0, 127)));

        Scheduler.getInstance().add(commandGroup);
    }

    /**
     * @return the singleton instance of the Status subsystem
     */
    public static Status getStatus() {
        if (INSTANCE == null) {
            INSTANCE = new Status();
        }
        return INSTANCE;
    }

    public void resetBoot() {
        inAuto = false;
        inTeleOp = false;

        // Resets the timer so that it represents "time since code init".
        timer.reset();

        scheduleBootActions();
    }

    // Resets the class state for Auto mode.
    public void resetAuto() {
        // Set state flags.
        inAuto = true;
        inTeleOp = false;

        // Resets the timer so that it represents "time since auto init".
        timer.reset();

        scheduleAutoActions();
    }

    // Resets the class state for TeleOp mode.
    public void resetTeleOp() {
        // Set state flags.
        inAuto = false;
        inTeleOp = true;

        // Resets the timer so that it represents "time since teleOp init".
        timer.reset();

        scheduleTeleOpActions();
    }

    // Determines if the flashlight is on.
    public boolean isFlashlightOn() {
        // The DIO port maps nicely to the state we need.
        return flashlightOutput.get();
    }

    // Specifically sets the flashlight on/true or off/false.
    public void setFlashlightState(boolean state) {
        flashlightOutput.set(state);
    }

    // Toggles the state of the flashlight.
    public void toggleFlashlight() {
        boolean isOn = isFlashlightOn();
        setFlashlightState(!isOn);
    }

    @Override
    public void initDefaultCommand() {
        // No default command - the output buffers are set in the reset* above.
    }

    @Override
    public void periodic() {

        // TODO: Summer project - update the subsystem to look at it's own scheduled
        // list of things to do based on state and what was set in the schedule* methods
        // (internal vs. Command/CommandGroup on that scheduler).
        // See an old commit for this class that had such things.
    }

    // Set a color from the predefined wpilib Color
    // Brightness is on a scale of 0-255
    public void setColor(Color color, int brightness) {
        setColor((int) color.red, (int) color.green, (int) color.blue, brightness);
    }

    // Set RGB color values.
    // RGB values are 0 (full off) - 255 (full on)
    // Brightness is on a scale of 0-255
    public void setColor(int red, int green, int blue, int brightness) {
        double b = brightness / 255.0;

        red = (int) (red * b);
        green = (int) (green * b);
        blue = (int) (blue * b);

        // Create a buffer for all the LEDs, set all of them to the same value, and
        // output the buffer.
        AddressableLEDBuffer buffer = new AddressableLEDBuffer(ADDRESSABLE_LED_COUNT);
        for (var i = 0; i < buffer.getLength(); i++) {
            buffer.setRGB(i, red, green, blue);
        }
        addressableLed.setData(buffer);
    }

    // This is the thread that runs the current action.
    private class ActionRunner extends Thread {

        // The minium amount of time to delay the tread.
        // While the RIO should handle it either way, the delay
        // allows the OS schedular a good slice to do things.
        private static final double MINIMUM_DELAY_SECONDS = 0.010;

        // How long to delay/sleep when there's no action.
        private static final double IDLE_DELAY_SECONDS = 0.250;

        public void run() {

            // The thread should persist while the code does (forever).
            //
            // TODO: A watchdog should probably check to see if the thread died off
            // due to an exception and restart it.
            while (true) {

                synchronized (actionLock) {
                    if (currentAction != null) {

                        //System.out.println("ActionRunner: run");
                        currentAction.run();

                        // If the current action is now done, remove it and loop back around.
                        if (currentAction.isFinished() == true) {
                            //System.out.println("ActionRunner: finished");
                            currentAction = null;
                            continue;
                        }

                        // Delay the amount of time requested by the action.
                        double delay = currentAction.getDelay();
                        if (delay < MINIMUM_DELAY_SECONDS) {
                            delay = MINIMUM_DELAY_SECONDS;
                        }

                        //System.out.println("ActionRunner: delay");
                        Timer.delay(delay);
                        continue;
                    }
                }

                // Nothing to do; delay before looping.
                Timer.delay(IDLE_DELAY_SECONDS);
            }
        }
    }
}