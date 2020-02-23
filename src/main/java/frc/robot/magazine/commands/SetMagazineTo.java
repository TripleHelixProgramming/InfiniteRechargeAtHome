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
import frc.robot.shooter.Shooter;

public class SetMagazineTo extends Command {

  public BallHandlingState action;

  private double SHOOT_SPEED = 0.9;
  private double INTAKE_SPEED = 0.9;

  private double power = 0.0;

  private Boolean ballAtShooterLastTime = false;
  private Boolean ballAtSpacerLastTime = false;
  private Boolean ballAtShooter = false;
  private Boolean ballAtSpacer = false;

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

    ballAtShooterLastTime = ballAtShooter;
    ballAtSpacerLastTime = ballAtSpacer;
    ballAtShooter = Magazine.getMagazine().ballAtShooter();
    ballAtSpacer = Magazine.getMagazine().ballAtSpacer();

    if (ballAtSpacerLastTime && !ballAtSpacer) Magazine.getMagazine().IncreaseBallCount();
    if (Magazine.getMagazine().getBallCount() == 3 && ballAtSpacer) Magazine.getMagazine().setBallCount(4);
    if (action == Magazine.BallHandlingState.SHOOT) Magazine.getMagazine().setBallCount(0);

    switch (action) {

    case SHOOT_NO_LOGIC: // For testing purposes before beam breaks.
      Magazine.getMagazine().setPower(SHOOT_SPEED);
      break;

    case INTAKE_NO_LOGIC: // For testing purposes before beam breaks.
      Magazine.getMagazine().setPower(INTAKE_SPEED);
      break;
      
    case SHOOT_ONE:  
      //SHOOT ONE is same as SHOOT, except case ADVANCE is executed on button release.
    case SHOOT:
      power = 0.0;
      if (Shooter.getShooter().isAtRPM()) {
        power = SHOOT_SPEED;
      }
      Magazine.getMagazine().setPower(power);
      break;

    case ADVANCE:  
      // Called when SHOOT ONE button is released to advance the magazine to next ball.
      // Magazine will run indefinitely, when no balls left in magazine.
      power = 0.0;
      if (!ballAtShooter) {
        power = SHOOT_SPEED;
      }
      Magazine.getMagazine().setPower(power);
      break;

    case INTAKE:
      power = 0.0;
      if (!ballAtShooter && ballAtSpacer) {
        power = INTAKE_SPEED;
      }
      Magazine.getMagazine().setPower(power);
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
