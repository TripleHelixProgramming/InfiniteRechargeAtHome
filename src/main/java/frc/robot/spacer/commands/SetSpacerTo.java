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
import frc.robot.shooter.Shooter;
import frc.robot.spacer.Spacer;

public class SetSpacerTo extends Command {

  private double SHOOT_SPEED = 0.6;
  private double INTAKE_SPEED = 0.6;

  private double power = 0.0;

  Boolean ballAtShooter = false;
  Boolean ballAtSpacer = false;
  Boolean ballAtSpacerLastTime = false;

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

    ballAtSpacerLastTime = ballAtSpacer;
    ballAtShooter = Magazine.getMagazine().ballAtShooter();
    ballAtSpacer = Magazine.getMagazine().ballAtSpacer();

    switch (action) {
      case SHOOT_NO_LOGIC:
        //  Case for no ball spacing logic when spacer is in SHOOT mode.
        Spacer.getSpacer().setPower(SHOOT_SPEED);
        break;

      case INTAKE_NO_LOGIC:
        //  Case for no ball spacing logic when Spacer is in INTAKE mode.
        Spacer.getSpacer().setPower(INTAKE_SPEED);
        break;

      case SHOOT_ONE:  //SHOOT ONE is same as SHOOT, except case ADVANCE is executed on button release.
      case SHOOT:
        // Case for ball spacing logic when spacer is in SHOOT mode.
        power = 0.0;
        if (Shooter.getShooter().isAtRPM()) {
            power = SHOOT_SPEED;
        } 
        Spacer.getSpacer().setPower(power);
        break;
      
      case ADVANCE:
      case INTAKE:
        // Case for ball spacing logic when spacer is in INTAKE mode.
        power = INTAKE_SPEED;
        if (ballAtShooter) { 
          power = 0.0;
        }
        Spacer.getSpacer().setPower(power);
        break;

      case STOP:
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
