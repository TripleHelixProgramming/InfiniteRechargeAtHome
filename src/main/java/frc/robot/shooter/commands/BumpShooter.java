/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.shooter.commands;

import edu.wpi.first.wpilibj.command.Command;

import frc.robot.shooter.Shooter;

public class BumpShooter extends Command {

  private int direction = Shooter.BUMP_UP;

  // Direction should be Shooter.BUMP_* value.
  public BumpShooter(int direction) {
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    requires(Shooter.getShooter());

    this.direction = direction;
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
      // Nothing to run.
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    Shooter.getShooter().setBumpTicks(this.direction);
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    // This command is responsible for just updating a shooter subsystem variable
    // so there's no reason to run more than once.
    return true;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
      // Nothing to do here after bumping up.
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
      // Nothing active in this command that would require it to be interrupted.
  }
}
