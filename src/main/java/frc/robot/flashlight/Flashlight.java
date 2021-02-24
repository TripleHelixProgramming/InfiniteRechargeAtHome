/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.flashlight;

import edu.wpi.first.wpilibj.DigitalOutput;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Add your docs here.
 */
public class Flashlight extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.

  DigitalOutput flashlight = new DigitalOutput(5);

  private static Flashlight INSTANCE = null;

  public static Flashlight getFlashlight() {
    if (INSTANCE == null) {
      INSTANCE = new Flashlight();
    }
    return INSTANCE;
  }

  public void flashlightOn() {
    flashlight.set(true);
  }

  public void flashlightOff() {
    flashlight.set(false);
  }

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }
}
