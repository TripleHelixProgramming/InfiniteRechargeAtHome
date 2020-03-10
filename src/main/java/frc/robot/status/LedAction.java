/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.status;

import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.Timer;

public class LedAction extends Action {

    // How many times to run, less than 0 will run until the thread dies.
    protected int intervalCount = -1;

    // How long to delay intervals.
    protected double intervalTime = 0.050;

    // Buffer this action uses, and sends to the LEDs.
    protected AddressableLEDBuffer buffer = new AddressableLEDBuffer(Status.ADDRESSABLE_LED_COUNT);

    // Default behavior of this action is to run a rainbow pattern.
    // This member tracks the hue between periodics.
    private int rainbowHue = 0;

    // Default will run a rainbow pattern.
    public LedAction() {

    }

    // Invoke with a specific color.
    public LedAction(int red, int green, int blue, int brightness) {
        double b = brightness / 255;
        red = (int) (red * b);
        green = (int) (green * b);
        blue = (int) (blue * b);

        for (var i = 0; i < buffer.getLength(); i++) {
            buffer.setRGB(i, red, green, blue);
        }

        // One and done.
        intervalCount = 1;
    }

    // Implementations should override the updateBuffer method.
    // This will be invoked every intervalTime seconds and only needs to
    // alter the buffer. The outer run() will handle the intervalTime,
    // intervalCount,
    // and set/sending the buffer to the LEDs.
    protected void updateBuffer() {

        // For every pixel
        for (var i = 0; i < buffer.getLength(); i++) {
        
            // Calculate the hue - hue is easier for rainbows because the color
            // shape is a circle so only one value needs to precess
            final var hue = (rainbowHue + (i * 180 / buffer.getLength())) % 180;

            // Set the value
            buffer.setHSV(i, hue, 255, 128);
        }
        // Increase by to make the rainbow "move"
        rainbowHue += 3;

        // Check bounds
        rainbowHue %= 180;
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    // Expected to run in a thread dedicated for LED stuff.
    @Override
    public void run() {

        while (intervalCount != 0) {
            updateBuffer();

            Status.getStatus().setLedData(buffer);

            --intervalCount;

            Timer.delay(intervalTime);
        }
    }
}
