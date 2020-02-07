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

  private double SHOOT_SPEED = -0.8;
  private double INTAKE_SPEED = -0.8;

  // From Robot Worksheet
  private double MAGAZINE_ROLLER_DIAMETER = 1.44;
  private double INCHES_PER_MAGZINE_ROLLER_REV = MAGAZINE_ROLLER_DIAMETER * 3.1416; // inches

  // Gear ratio = Stage 1 * Stage 2
  private double GEAR_RATIO = 1 / 4 * 1 / 4;
  private double MAGAZINE_ROLLER_REVS_PER_MOTOR_REV = 1 * GEAR_RATIO;
  private double INCHES_PER_MOTOR_REV = INCHES_PER_MAGZINE_ROLLER_REV * MAGAZINE_ROLLER_REVS_PER_MOTOR_REV;

  private double BALL_CIRCUMFERENCE = 9 * 3.1416;   // ball diameter * pi

  private double power = 0.0;
  private double startPos = 0.0;

  private Boolean ballAtShooterLastTime = false;
  private Boolean ballAtShooter = false;
  private Boolean ballAtSpacer = false;
  private Boolean ballShot = false;

  private int ballsShot = 0;

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
    ballAtShooter = Magazine.getMagazine().ballAtShooter();
    ballAtSpacer = Magazine.getMagazine().ballAtSpacer();

    if (ballAtShooterLastTime && !ballAtShooter) {
      ballsShot++;
      ballShot = true;
      startPos = Magazine.getMagazine().getMotorPosition();
    } else {
      ballShot = false;
    }

    switch (action) {
    case SHOOT_NO_LOGIC: // For testing purposes before beam breaks.
      Magazine.getMagazine().setPower(SHOOT_SPEED);
      break;
    case INTAKE_NO_LOGIC: // For testing purposes before beam breaks.
      Magazine.getMagazine().setPower(INTAKE_SPEED);
      break;
    case SHOOT_ONE:
      // power = SHOOT_SPEED;
      // if (!Shooter.getShooter().isAtRPM()) {
      //     power = 0.0;
      // } else {
      //     if (!ballShot) {
      //       // Roll magazine forware by one ball circumference.
      //       if (((Magazine.getMagazine().getMotorPosition()- startPos) * INCHES_PER_MOTOR_REV) >= BALL_CIRCUMFERENCE) {
      //           // Roller has turn for 1 ball circumference.
      //           ballShot = true;
      //           power = 0.0;
      //       }
      //   }
      // }
      // Magazine.getMagazine().setPower(power);
      break;
    case SHOOT:
      power = 0.0;
      if (Shooter.getShooter().isAtRPM()) {
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
