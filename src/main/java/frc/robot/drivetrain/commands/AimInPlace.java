/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.drivetrain.commands;
import frc.robot.drivetrain.Drivetrain;

public class AimInPlace extends AbstractVisionDriving {
 
  @Override
  public double getThrottle() {
    return 0;
  }

  @Override
  protected boolean isFinished() {
    if (Math.abs(Drivetrain.getDrivetrain().getFrontCamera().getRotationalDegreesToTarget()) < 1) {
      return true;
    }
    return false;
  }
}
