/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.shooter.commands;

import static java.lang.Math.min;

import com.team2363.logger.HelixEvents;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.oi.OI;
import frc.robot.shooter.Shooter;
import frc.robot.shooter.Shooter.ShooterState;

public class Climb extends Command {

  public Climb() {
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    requires(Shooter.getShooter());
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    HelixEvents.getInstance().addEvent("SHOOTER", "Starting Climb");
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    double rpm = min(OI.getOI().getClimberPower(), 0) * Shooter.getShooter().getMAXRPM();
    Shooter.getShooter().setRPM(ShooterState.CLIMB, rpm);
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return OI.getOI().getClimberPower() > 0.5;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    HelixEvents.getInstance().addEvent("SHOOTER", "Ending Climb");
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    HelixEvents.getInstance().addEvent("SHOOTER", "Climb interrupted");
  }
}
