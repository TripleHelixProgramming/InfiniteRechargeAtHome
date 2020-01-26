/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.indexer.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.indexer.Indexer;
import frc.robot.magazine.Magazine;
import frc.robot.magazine.Magazine.BallHandlingState;

public class SetIndexerTo extends Command {

  private double SHOOT_SPEED = 0.4;
  private double INTAKE_SPEED = 0.3;

  BallHandlingState action;
  public SetIndexerTo(BallHandlingState action) {
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    requires(Indexer.getIndexer());
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
        if (Magazine.getMagazine().hasBalls()) {
          Indexer.getIndexer().setPower(SHOOT_SPEED);
        }
        break;
      case INTAKE:
        if (!ballAtShooter && ballAtSpacer) {
          Indexer.getIndexer().setPower(INTAKE_SPEED);
        }
        break;
      case STOP:
        Indexer.getIndexer().setPower(0.0);
        break;
      default:
        Indexer.getIndexer().setPower(0.0);
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
