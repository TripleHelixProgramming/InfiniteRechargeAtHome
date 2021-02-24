/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.magazine.commands;

import com.team2363.logger.HelixEvents;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.magazine.Magazine;
import frc.robot.magazine.Magazine.BallHandlingState;
import frc.robot.shooter.Shooter;

public class SetMagazineTo extends Command {

  public BallHandlingState action;

  private double SHOOT_SPEED = 800;
  private double INTAKE_SPEED = 800;

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
    HelixEvents.getInstance().addEvent("MAGAZINE", "SetMagazineTo" + action.toString());
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

    SmartDashboard.putString("Magazine State", action.toString());
    SmartDashboard.putNumber("Magazine Velocity", Magazine.getMagazine().getVelocity());

    switch (action) {

    case SHOOT_NO_LOGIC: // For testing purposes before beam breaks.
      Magazine.getMagazine().setVelocity(SHOOT_SPEED);
      break;

    case INTAKE_NO_LOGIC: // For testing purposes before beam breaks.
      Magazine.getMagazine().setVelocity(INTAKE_SPEED);
      break;
      
    case SHOOT_ONE:  
      // power = 0.0;
      // if (Shooter.getShooter().isAtRPM() && ballAtSpacer) {
      //   power = SHOOT_SPEED;
      // }
      // Magazine.getMagazine().setPower(power);
      // break;

    case SHOOT:
      if (Shooter.getShooter().isAtRPM()) {
        Magazine.getMagazine().setVelocity(SHOOT_SPEED);
      } else {
        Magazine.getMagazine().setPower(0);
      }
      break;

    case ADVANCE:  
      // Called when SHOOT ONE button is released to advance the magazine to next ball.
      // Magazine will run indefinitely, when no balls left in magazine.

      break;

    case INTAKE:
      if (!ballAtShooter && ballAtSpacer) {
        Magazine.getMagazine().setVelocity(SHOOT_SPEED);
      } else {
        Magazine.getMagazine().setPower(0);
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
    HelixEvents.getInstance().addEvent("MAGAZINE", "Interrupted SetMagazineTo" + action.toString());
  }
}
