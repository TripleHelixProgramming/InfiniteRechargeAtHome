/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.magazine.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.magazine.Magazine;

public class ShootOne extends Command {

  private boolean beamState;
  private boolean lastBeamState;
  private boolean isFinished;
  private Timer clock;

  public ShootOne() {
    requires(Magazine.getMagazine());
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    lastBeamState = Magazine.getMagazine().ballAtShooter();
    clock = new Timer();
    clock.start();
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    beamState = Magazine.getMagazine().ballAtShooter();
    Magazine.getMagazine().setVelocity(600);
    isFinished = beamState && !lastBeamState || clock.get() > 1.5;
    lastBeamState = beamState;
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return isFinished;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    Magazine.getMagazine().setPower(0);
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
  }
}
