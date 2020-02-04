/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.shooter.commands;

import edu.wpi.first.wpilibj.command.Command;

import frc.robot.oi.OI;
import frc.robot.shooter.Shooter;

public class TestWithController extends Command {
  public TestWithController() {
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    requires(Shooter.getShooter());
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    int rpm;

    rpm = (int)(OI.getOI().getThrottle() * Shooter.getShooter().MAX_RPM);
    Shooter.getShooter().setRPM(rpm);
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
