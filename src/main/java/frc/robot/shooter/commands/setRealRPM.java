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
import frc.robot.shooter.Shooter;
import frc.robot.shooter.Shooter.ShooterState;

public class setRealRPM extends Command {

  double pos0;
  double deltaPos;
  double targetPos;
  Timer clock;
  double RPM;

  public setRealRPM(double RPM) {
    this.RPM = RPM;
    requires(Shooter.getShooter());
  }

  @Override
  protected void initialize() {
    clock = new Timer();
    clock.start();
    pos0 = Shooter.getShooter().getPosition();
  }

  @Override
  protected void execute() {
    targetPos = clock.get() * RPM / 60.0;
    deltaPos = (Shooter.getShooter().getPosition() - pos0) * 30.0 / 18.0;
    Shooter.getShooter().setRPM(ShooterState.SHOOT, (targetPos - deltaPos) * 25);
    SmartDashboard.putNumber("Target Position", targetPos);
    SmartDashboard.putNumber("clock", clock.get());
    SmartDashboard.putNumber("position", deltaPos);
  }

  @Override
  protected boolean isFinished() {
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
