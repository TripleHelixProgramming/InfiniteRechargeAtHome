/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.magazine.commands;

import com.team2363.logger.HelixEvents;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.magazine.Magazine;
import frc.robot.spacer.Spacer;
import frc.robot.spacer.commands.RunSpacer;

public class AdvanceMagazine extends Command {

  public AdvanceMagazine() {
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    requires(Magazine.getMagazine());
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    if (isFinished()) {
      end();
    } else {
      HelixEvents.getInstance().addEvent("MAGAZINE", "Advance Magazine");
      Magazine.getMagazine().setVelocity(Magazine.getMagazine().getVelocitySP());
    }
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return !Spacer.getSpacer().ballAtSpacer() || Magazine.getMagazine().ballAtShooter();
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    nextStopMagazine().start();
    nextRunSpacer().start();
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
  }

  protected Command nextRunSpacer() {
    return new RunSpacer();
  }

  protected Command nextStopMagazine() {
    return new StopMagazine();
  }
}