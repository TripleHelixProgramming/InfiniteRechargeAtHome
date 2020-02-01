/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.drivetrain.commands;

import edu.wpi.first.wpilibj.command.Command;

public class aimInPlace extends AbstractVisionDriving {
  @Override
  public double getThrottle() {
    return 0;
  }
  @Override
  protected boolean isFinished() {
    if (Math.abs(angle()) < 1) {
      return true;
    }
    return false;
  }
}
