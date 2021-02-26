/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.shooter.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.magazine.Magazine.BallHandlingState;
import frc.robot.shooter.Position;
import frc.robot.shooter.Shooter;
import frc.robot.shooter.Shooter.ShooterState;

public class setRealRPM extends Command {

  double deltaPos;
  double targetPos;
  Timer clock;
  double RPM;
  double position;

  public setRealRPM(double RPM) {
    this.RPM = RPM;
    requires(Shooter.getShooter());
  }

  @Override
  protected void initialize() {
    clock = new Timer();
    clock.start();
    Shooter.getShooter().resetEncoder();
  }

  @Override
  protected void execute() {
    if (Shooter.getShooter().getRPM() < RPM * 0.9) {
      Shooter.getShooter().resetEncoder();
      clock.reset();
    }
    position = Shooter.getShooter().getPosition();
    targetPos = clock.get() * RPM / 60.0;
    Shooter.getShooter().setRPM(ShooterState.SHOOT, RPM + (targetPos - position) * 25);
    SmartDashboard.putNumber("Target Position", targetPos);
    SmartDashboard.putNumber("clock", clock.get());
    SmartDashboard.putNumber("position", position);
  }

  @Override
  protected boolean isFinished() {
    // boolean isFinished = false;
    return false;
  }

  @Override
  protected void end() {
    Shooter.getShooter().stop();
    clock.stop();
  }

  @Override
  protected void interrupted() {
    end();
  }
}
