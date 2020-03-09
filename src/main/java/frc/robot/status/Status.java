/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.status;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.DigitalOutput;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.util.Color;
import frc.robot.status.commands.RunRainbow;
import frc.robot.status.commands.ToggleFlashlight;

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

    // What to do.
    public static final int ACTION_LED_OFF = 0;
    public static final int ACTION_LED_ON = 1;
    public static final int ACTION_LED_FLASH = 2;
    public static final int ACTION_LED_CLIMB = 3;

    private static Status INSTANCE = null;

    // Flashlight uses the DIO port and its +5v power.
    private static int FLASHLIGHT_DIO_CHANNEL = 9;

    // Addressable LEDs use the PWM port and its +6v power.
    private static int ADDRESSABLE_LED_PWM_CHANNEL = 0;
    public static int ADDRESSABLE_LED_COUNT = 25; // accessable to actions

    private DigitalOutput flashlightOutput = null;

    // Addressable LED support.
    private AddressableLED addressableLed = null;
    private AddressableLEDBuffer addressableBuffer = null;

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
        addressableBuffer = new AddressableLEDBuffer(ADDRESSABLE_LED_COUNT);
        addressableLed.setLength(addressableBuffer.getLength());

        addressableLed.setData(addressableBuffer);
        addressableLed.start();

        //setColor(102, 46, 145, .1);

        // Create the timer and start it.
        // This will start counting when the RIO initializes.
        timer = new Timer();
        timer.start();
    }

    // This will set/send the buffer to the LEDs.
    public synchronized void setLedData(AddressableLEDBuffer buffer) {
        addressableLed.setData(buffer);
    }

    public void setBootActions() {
        // Turn on to purple.
        LedAction action = new LedAction(ACTION_LED_ON, 102, 46, 145, .7);
        scheduleAction(action, STATE_RIO_BOOT, 0);

        // Switch to yellow at 15s before match end.
        action = new LedAction(ACTION_LED_ON, 253, 184, 19, .7);
        scheduleAction(action, STATE_RIO_BOOT, 135);

        // At match end go red.
        action = new LedAction(ACTION_LED_ON, 255, 0, 0, 1);
        scheduleAction(action, STATE_RIO_BOOT, 150);
    }

    public void setAutoActions() {

    }

    public void setTeleOpActions() {
        // Turn on to purple.
        LedAction action = new LedAction(ACTION_LED_ON, 102, 46, 145, .3);
        scheduleAction(action, STATE_RIO_BOOT, 0);

        // Switch to yellow at 15s before match end.
        action = new LedAction(ACTION_LED_ON, 253, 184, 19, .5);
        scheduleAction(action, STATE_RIO_BOOT, 135);

        // At match end go red.
        action = new LedAction(ACTION_LED_ON, 255, 0, 0, .5);
        scheduleAction(action, STATE_RIO_BOOT, 150);
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

        setActionSeconds();
        timer.reset();
    }

    // Resets the class state for Auto mode.
    public void resetAuto() {
        // Set state flags.
        inAuto = true;
        inTeleOp = false;

        // Resets the timer so that it represents "time since auto init".
        setActionSeconds();
        timer.reset();
    }

    // Resets the class state for TeleOp mode.
    public void resetTeleOp() {
        // Set state flags.
        inAuto = false;
        inTeleOp = true;

        // Set the LEDs to TripleHelix Purple.
        setColor(102, 46, 145, .5);

        // Resets the timer so that it represents "time since teleop init".
        setActionSeconds();
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
        setDefaultCommand(new RunRainbow());
    }

    @Override
    public void periodic() {

        // Skip preiodic code below.
        if (true) return;

        // Send rainbow
        //rainbowPeriodic();

        // Send climb
        climbPeriodic();

        // Comment this to use scheduled actions.
        actionSecondsIndex = -1;

        // Only run if there's a next second
        if (actionSecondsIndex > -1) {
            int actionSecond = actionSeconds[actionSecondsIndex];
            int second = (int) timer.get();

            if (second >= actionSecond) {
                performActions(actionSecond);

                if (actionSecondsIndex < actionSeconds.length - 1) {
                    ++actionSecondsIndex;
                } else {
                    actionSecondsIndex = -1;
                }
            }
        } else {
            // Noting else to do so run rainbow.
            //rainbowPeriodic();
        }
    }

    // Set a color from the predefined wipilib Color
    // brightness: 0.0 (full off), 1.0 (full on)
    public void setColor(Color color, double brightness) {
        setColor((int) color.red, (int) color.green, (int) color.blue, brightness);
    }

    // Set RGB color values.
    // rgb values are 0 (full off) - 255 (full on)
    // brightness: 0.0 (full off), 1.0 (full on)
    public void setColor(int red, int green, int blue, double brightness) {
        red = (int) (red * brightness);
        green = (int) (green * brightness);
        blue = (int) (blue * brightness);

        for (var i = 0; i < addressableBuffer.getLength(); i++) {
            // Sets the specified LED to the RGB values for red
            addressableBuffer.setRGB(i, red, green, blue);
        }
        addressableLed.setData(addressableBuffer);
    }

    // Run in periodic to make a rainbow pattern on the led.
    private int rainbowHue = 0;
    private void rainbowPeriodic() {
        // For every pixel
        for (var i = 0; i < addressableBuffer.getLength(); i++) {
            // Calculate the hue - hue is easier for rainbows because the color
            // shape is a circle so only one value needs to precess
            final var hue = (rainbowHue + (i * 180 / addressableBuffer.getLength())) % 180;
            // Set the value
            addressableBuffer.setHSV(i, hue, 255, 128);
        }
        // Increase by to make the rainbow "move"
        rainbowHue += 3;
        // Check bounds
        rainbowHue %= 180;

        addressableLed.setData(addressableBuffer);
    }

    private static final int climbTicksPerStep = 0;
    private int climbTicks = climbTicksPerStep;
    private int climbPerStep = 2; // How many address per step
    private int climbStep = 0;
    private void climbPeriodic() {

        if (climbStep > ADDRESSABLE_LED_COUNT) {

            //return; // to stop at full on/climb
            
            // start over
            climbStep = 0;
        }

        --climbTicks;
        if (climbTicks > 0) {
            // Delaying another tick.
            return;
        }

        // Could use climb step here
        for (var i = 0; i < addressableBuffer.getLength(); ++i) {
            if (i < climbStep) {
                addressableBuffer.setRGB(i, 255, 0, 0);
            } else {
                addressableBuffer.setRGB(i, 0, 0, 0);
            }
        }
        addressableLed.setData(addressableBuffer);

        // Advance to next climb step.
        climbStep += climbPerStep;
        if (climbStep > ADDRESSABLE_LED_COUNT) {
            climbStep = ADDRESSABLE_LED_COUNT;
        } else if (climbStep == ADDRESSABLE_LED_COUNT) {
            // Set to past the count to allow the next iteration to repeat or not.
            climbStep = ADDRESSABLE_LED_COUNT + 1;
        }

        // Reset delay ticks.
        climbTicks = climbTicksPerStep;
    }

    private Map<Integer, ArrayList<Action>> bootActions = new TreeMap<Integer, ArrayList<Action>>();
    private Map<Integer, ArrayList<Action>> autoActions = new TreeMap<Integer, ArrayList<Action>>();
    private Map<Integer, ArrayList<Action>> teleOpActions = new TreeMap<Integer, ArrayList<Action>>();

    public void scheduleAction(Action action, int inState, int afterSeconds) {
        Map<Integer, ArrayList<Action>> map = bootActions;
        switch (inState) {
        case STATE_IN_AUTO:
            map = autoActions;
            break;
        case STATE_IN_TELEOP:
            map = teleOpActions;
            break;
        }

        Integer key = Integer.valueOf(afterSeconds);
        if (map.containsKey(key) == false) {
            map.put(key, new ArrayList<Action>());
        }
        map.get(key).add(action);
    }

    private int actionSecondsIndex = -1;
    private int[] actionSeconds = new int[0];

    private void setActionSeconds() {
        Map<Integer, ArrayList<Action>> map = bootActions;

        if (inAuto == true) {
            map = autoActions;
        } else if (inTeleOp == true) {
            map = teleOpActions;
        }

        // Optimize periodic to not have to troll through the map looking
        // for a close second (since it won't always line up to be a perfect second).
        // This optimization is two parts. The seconds we're interested in
        // and an index into the array of seconds we're interested in.
        actionSeconds = new int[map.keySet().size()];
        Iterator<Integer> iter = map.keySet().iterator();

        int i = 0;
        while (iter.hasNext() == true) {
            actionSeconds[i++] = iter.next().intValue();
        }

        // Default the index to -1 to inform periodic there's nothing to do
        // unless the actionSeconds contains something, then start at index 0.
        actionSecondsIndex = -1;
        if (actionSeconds.length > 0) {
            actionSecondsIndex = 0;
        }
    }

    private void performActions(int atSeconds) {
        Map<Integer, ArrayList<Action>> map = bootActions;

        if (inAuto == true) {
            map = autoActions;
        } else if (inTeleOp == true) {
            map = teleOpActions;
        }

        ArrayList<Action> actions = map.get(Integer.valueOf(atSeconds));
        Iterator<Action> iter = actions.iterator();
        while (iter.hasNext()) {
            Action action = iter.next();

            switch (action.getAction()) {
            case ACTION_LED_OFF:
                setColor(Color.kBlack, 0);
                break;

            case ACTION_LED_ON:
                if (action instanceof LedAction) {
                    LedAction ledAction = (LedAction) action;
                    setColor(ledAction.getRed(), ledAction.getGreen(), ledAction.getBlue(), ledAction.getBrightness());
                } else {
                    setColor(Color.kFuchsia, .5);
                }
            case ACTION_LED_FLASH:
                // TODO: Implement
                break;
            }
        }
    }

    protected class Action {
        private final int action;

        public Action(int action) {
            this.action = action;
        }

        public int getAction() {
            return action;
        }
    }

    public class LedAction extends Action {
        private int red = 0;
        private int green = 0;
        private int blue = 0;
        private double brightness = 0.0;

        private int onTime = 1;
        private int offTime = 1;

        public LedAction(int action) {
            super(action);
        }

        public LedAction(int action, int red, int green, int blue, double brightness) {
            super(action);
            this.red = red;
            this.green = green;
            this.blue = blue;
            this.brightness = brightness;
        }

        public int getRed() {
            return red;
        }

        public int getGreen() {
            return green;
        }

        public int getBlue() {
            return blue;
        }

        public double getBrightness() {
            return brightness;
        }

        public void setFlashIntervals(int onTime, int offTime) {
            this.onTime = onTime;
            this.offTime = offTime;
        }

        public int getOnTime() {
            return onTime;
        }

        public int getOffTime() {
            return offTime;
        }
    }
}