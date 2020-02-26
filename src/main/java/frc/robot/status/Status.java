/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.status;

import edu.wpi.first.wpilibj.DigitalOutput;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.status.commands.ToggleFlashlight;

//
// This subsystem should be used for any status output such as lights.
// While the flashlight can be considered OI, Drivetrain, or Shooter, the intent
// is to eventually put more in here like controlling LED status lights.
//

public class Status extends Subsystem {

    private static Status INSTANCE = null;

    private static int FLASHLIGHT_DIO_CHANNEL = 9;

    private DigitalOutput flashlightOutput = null;

    private Status() {
        super();

        // The RIO DIO ports as outputs have a high pullup resistor.
        // The high pullup causes the voltage regulator to be enabled so 
        // here as soon as we initialize the output we set it to false to
        // disable the regulator which turns off the flashlight.
        flashlightOutput = new DigitalOutput(FLASHLIGHT_DIO_CHANNEL);
        flashlightOutput.set(false);
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

    }
}