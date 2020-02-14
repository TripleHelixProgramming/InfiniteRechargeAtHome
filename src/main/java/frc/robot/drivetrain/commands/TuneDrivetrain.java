/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.drivetrain.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.drivetrain.Drivetrain;
import static frc.robot.drivetrain.Drivetrain.CommandUnits.PERCENT_FULLSPEED;

public class TuneDrivetrain extends Command {

  double fps = 0.0;

  public TuneDrivetrain(double fps) {
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    requires(Drivetrain.getDrivetrain());
    this.fps = fps;
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    Drivetrain.getDrivetrain().setSetpoint(PERCENT_FULLSPEED, fps, fps);
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return false;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
  }
}
