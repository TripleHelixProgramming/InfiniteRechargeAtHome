/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.spacer.commands;

import com.team2363.logger.HelixEvents;

import edu.wpi.first.wpilibj.command.Command;
//import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.spacer.Spacer;
import frc.robot.command_groups.LoadMagazineCG;
import frc.robot.magazine.Magazine;
import frc.robot.magazine.commands.AdvanceMagazine;

public class RunSpacer extends Command {

  private double speed;

  public RunSpacer(double speed) {
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    requires(Spacer.getSpacer());
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    HelixEvents.getInstance().addEvent("SPACER", "Run Spacer");
    Spacer.getSpacer().setVelocity(speed);
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    //SmartDashboard.putNumber("Spacer Velocity", Spacer.getSpacer().getVelocity());
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return Magazine.getMagazine().ballAtSpacer();
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    new AdvanceMagazine(800).start(); //finishes when !ballAtSpacer || ballAtShooter
    new LoadMagazineCG().start();
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    end();
  }
}