/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.magazine.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.magazine.Magazine;
import frc.robot.magazine.Magazine.BallHandlingState;

public class SetMagazineTo extends Command {

  public BallHandlingState action;

  private double SHOOT_SPEED = 0.5;
  private double INTAKE_SPEED = 0.5;

  public SetMagazineTo(BallHandlingState action) {
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    requires(Magazine.getMagazine());
    this.action = action;
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {

    
    Boolean ballAtShooter = Magazine.getMagazine().ballAtShooter();
    Boolean ballAtSpacer = Magazine.getMagazine().ballAtSpacer();

    switch (action) {
      case SHOOT:
          Magazine.getMagazine().setPower(SHOOT_SPEED);
        break;
      case INTAKE:
        if (!ballAtShooter && ballAtSpacer) {
          Magazine.getMagazine().setPower(INTAKE_SPEED);
        } else {
          Magazine.getMagazine().setPower(0.0);
        }
        break;
      case STOP:
      default:
        Magazine.getMagazine().setPower(0.0);
        break;
    }
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