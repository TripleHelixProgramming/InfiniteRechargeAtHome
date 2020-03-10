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
import frc.robot.status.commands.ColorCommand;
import frc.robot.status.commands.RunRainbow;

//
// This subsystem should be used for any status output such as lights.
// While the flashlight can be considered OI, Drivetrain, or Shooter, the intent
// is to eventually put more in here like controlling LED status lights.
//

public class Status extends Subsystem {

    // Run in what state.
    public static final int STATE_RIO_BOOT = 0;
    public static final int STATE_IN_AUTO = 1;
    public static final int STATE_IN_TELEOP = 2;


    private static Status INSTANCE = null;

    // Flashlight uses the DIO port and its +5v power.
    private static int FLASHLIGHT_DIO_CHANNEL = 9;

    // Addressable LEDs use the PWM port and its +6v power.
    private static int ADDRESSABLE_LED_PWM_CHANNEL = 0;
    public static int ADDRESSABLE_LED_COUNT = 60; // accessable to actions

    private DigitalOutput flashlightOutput = null;

    // Addressable LED support.
    private AddressableLED addressableLed = null;

    // The current LED action
    private Action currentAction = null;

    // Timer allows us to do things at specific game time.
    private Timer timer = null;

    // If both are false then the RIO is running but neither init has triggered.
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

        // addressableLed.setData(addressableBuffer);
        addressableLed.start();

        // setColor(102, 46, 145, .1);

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
        currentAction = action;
    }

    public void setBootActions() {

    }

    public void setAutoActions() {

    }

    public void setTeleOpActions() {

        Scheduler.getInstance().add(new TeleCommandGroup());

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

        // Turn off when boot
        setColor(Color.kBlack, 0);

        //setColor(245, 0, 255, 20);

        //setColor(245, 0, 255, 255);
        //setColor(102, 46, 145, 1);

        // Resets the timer so that it represents "time since code init".
        timer.reset();
    }

    // Resets the class state for Auto mode.
    public void resetAuto() {
        // Set state flags.
        inAuto = true;
        inTeleOp = false;

        // Set purple
        setColor(245, 0, 255, 90);

        // Resets the timer so that it represents "time since auto init".
        timer.reset();
    }

    // Resets the class state for TeleOp mode.
    public void resetTeleOp() {
        // Set state flags.
        inAuto = false;
        inTeleOp = true;

        setColor(245, 0, 255, 90);

        // Set the LEDs to TripleHelix Purple.
        // setColor(102, 46, 145, .5);

        // Todd Purple: 255, 0, 220

        timer.reset();
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
        // Run the rainbow pattern by default.
        //setDefaultCommand(new RunRainbow());
    }

    @Override
    public void periodic() {

    }

    // Set a color from the predefined wipilib Color
    // brightness: 0.0 (full off), 1.0 (full on)
    public void setColor(Color color, int brightness) {
        setColor((int) color.red, (int) color.green, (int) color.blue, brightness);
    }

    // Set RGB color values.
    // rgb values are 0 (full off) - 255 (full on)
    // brightness: 0.0 (full off), 1.0 (full on)
    public void setColor(int red, int green, int blue, int brightness) {
        double b = brightness / 255.0;

        red = (int) (red * b);
        green = (int) (green * b);
        blue = (int) (blue * b);

        AddressableLEDBuffer buffer = new AddressableLEDBuffer(ADDRESSABLE_LED_COUNT);
        for (var i = 0; i < buffer.getLength(); i++) {
            // Sets the specified LED to the RGB values for red
            buffer.setRGB(i, red, green, blue);
        }
        addressableLed.setData(buffer);
    }

    private class ActionRunner extends Thread {
        public void run() {
            while (true) {

                if (Status.this.currentAction != null) {
                    Status.this.currentAction.run();
                } else {
                    // Delay 100ms since nothing to do
                    Timer.delay(.100);
                }
            }
        }
    }

    protected class TeleCommandGroup extends CommandGroup {
        public TeleCommandGroup() {
            addSequential(new WaitCommand(10));
            addSequential(new RunRainbow(), 10);
            addSequential(new ColorCommand(0,255,0,100));
        }
    }
}