/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.drivetrain.commands;

import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.drivetrain.Drivetrain;

public class EnableLights extends Command {
  
  String CamName;

  public EnableLights() {
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    Drivetrain.getDrivetrain().getFrontCamera().setDockingMode();
    CamName = Drivetrain.getDrivetrain().getFrontCamera().getCameraName();
    SmartDashboard.putString("Camera Light:", "Initialize");
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return (NetworkTableInstance.getDefault().getTable(CamName).getEntry("ledMode").getDouble(0) == 3.0);
    // return true;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    SmartDashboard.putString("Camera Light:", "Light ON");
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    SmartDashboard.putString("Camera Light:", "Interrupted");
  }
}
