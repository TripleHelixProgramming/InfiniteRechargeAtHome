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
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.util.Color;
import frc.robot.status.commands.ToggleFlashlight;

//
// This subsystem should be used for any status output such as lights.
// While the flashlight can be considered OI, Drivetrain, or Shooter, the intent
// is to eventually put more in here like controlling LED status lights.
//

public class Status extends Subsystem {

    private static Status INSTANCE = null;

    // Flashlight uses the DIO port and its +5v power.
    private static int FLASHLIGHT_DIO_CHANNEL = 9;

    // Addressable LEDs use the PWM port and its +6v power.
    private static int ADDRESSABLE_LED_PWM_CHANNEL = 0;
    private static int ADDRESSABLE_LED_COUNT = 40;

    private DigitalOutput flashlightOutput = null;

    // Addressable LED support.
    private AddressableLED addressableLed = null;
    private AddressableLEDBuffer addressableBuffer = null;

    // Timer allows us to do things at specific game time.
    private Timer timer = null;

    // If both are false then the RIO is running but neither init has triggered.
    private boolean inAuto = false;
    private boolean inTeleop = false;

    private Status() {
        super();

        // The RIO DIO ports as outputs have a high pullup resistor.
        // The high pullup causes the voltage regulator to be enabled so
        // here as soon as we initialize the output we set it to false to
        // disable the regulator which turns off the flashlight.
        flashlightOutput = new DigitalOutput(FLASHLIGHT_DIO_CHANNEL);
        flashlightOutput.set(true);

        // Init the addressable led stuff. Note that the docs indicate
        // setting the length is expensive, so doing so here in a singleton
        // is proably best. Don't attempt to do this during a run.
        // https://docs.wpilib.org/en/latest/docs/software/actuators/addressable-leds.html
        addressableLed = new AddressableLED(ADDRESSABLE_LED_PWM_CHANNEL);
        addressableBuffer = new AddressableLEDBuffer(ADDRESSABLE_LED_COUNT);
        addressableLed.setLength(addressableBuffer.getLength());

        addressableLed.setData(addressableBuffer);
        addressableLed.start();

        setColor(102, 46, 145);

        // Create the timer and start it.
        // This will start counting when the RIO initializes.
        timer = new Timer();
        timer.start();
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

    // Resets the class state for Auto mode.
    public void resetAuto() {
        // Set state flags.
        inAuto = true;
        inTeleop = false;

        // Resets the timer so that it represents "time since auto init".
        timer.reset();
    }

    // Resets the class state for TeleOp mode.
    public void resetTeleOp() {
        // Set state flags.
        inAuto = false;
        inTeleop = true;

        // Set the LEDs to TripleHelix Purple.
        setColor(102, 46, 145);

        // Resets the timer so that it represents "time since teleop init".
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
        // It's not needed but added for completeness.
        setDefaultCommand(new ToggleFlashlight(false));
    }

    @Override
    public void periodic() {

        statusAtSecond((int) timer.get());

        // rainbow();
        // addressableLed.setData(addressableBuffer);
    }

    // Do things based on the time.
    private void statusAtSecond(int seconds) {

        // Somewhere after 120 seconds turn the led team yellow.
        if ((seconds > 120) && (seconds < 130)) {
            setColor(253, 184, 19);
        }
    }

    // Set a color from the predefined wipilib Color
    public void setColor(Color color) {
        setColor((int) color.red, (int) color.green, (int) color.blue);
    }

    // Set RGB color values.
    public void setColor(int red, int green, int blue) {
        for (var i = 0; i < addressableBuffer.getLength(); i++) {
            // Sets the specified LED to the RGB values for red
            addressableBuffer.setRGB(i, red, green, blue);
        }
        addressableLed.setData(addressableBuffer);
    }

    // Used in rainbowPeriodic for tracking hue.
    private int rainbowHue = 0;

    // Run in periodic to make a rainbow pattern on the led.
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
}