/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.spacer.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.magazine.Magazine;
import frc.robot.magazine.Magazine.BallHandlingState;
import frc.robot.spacer.Spacer;

public class SetSpacerTo extends Command {

  private double SHOOT_SPEED = 0.5;
  private double INTAKE_SPEED = 0.5;

  Boolean magazine_hand_off_complete= false;

  BallHandlingState action;
  public SetSpacerTo(BallHandlingState action) {
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    requires(Spacer.getSpacer());
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
        if (!ballAtSpacer) 
          Spacer.getSpacer().setPower(SHOOT_SPEED);
        else 
          Spacer.getSpacer().setPower(0.0);
        break;
      case INTAKE:
        if (!ballAtShooter && !ballAtSpacer) {
          // No balls are present at the beginning of the magazine and it's not full yet
          Spacer.getSpacer().setPower(INTAKE_SPEED);
          magazine_hand_off_complete = false;
        } else if (!ballAtShooter && ballAtSpacer) {
          // Continue to at this speed until x number of spacer rotations
          // vice time. Then speed goes to 0.0 for this case. Need to figure 
          // out how to calculation rotations that have occurred since 
          // ball at spacer.  And if number of rotations >= certain number
          // then switch speed to 0.0
          Spacer.getSpacer().setPower(INTAKE_SPEED);
        } else {
          Spacer.getSpacer().setPower(0.0);
        }
        break;
      case STOP:
      case FULL:
      default:
        Spacer.getSpacer().setPower(0.0);
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
