/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.drivetrain.commands;

import static frc.robot.drivetrain.Drivetrain.getDrivetrain;
import static frc.robot.drivetrain.Drivetrain.CommandUnits.FPS;

import com.team2363.controller.PIDController;
import com.team2363.logger.HelixEvents;

import edu.wpi.first.wpilibj.Notifier;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.drivetrain.Camera;

public abstract class AbstractVisionDriving extends Command {

  private final Camera camera;
  private double angleToTarget, output;

  public AbstractVisionDriving() {
    requires(getDrivetrain());
    camera = getDrivetrain().getFrontCamera();
  }

  public abstract double getThrottle();

  @Override
  protected void initialize() {
    camera.setDockingMode();
  }

  @Override protected void execute() {
    double kP = 0.125; // 0.15
    double kF = 0.65; //.5
    angleToTarget = camera.getRotationalDegreesToTarget();
    output = -angleToTarget * kP - kF*(Math.abs(angleToTarget) / angleToTarget);
    getDrivetrain().setSetpoint(FPS, getThrottle() - output, getThrottle() + output);
  }

  @Override
  protected boolean isFinished() {
    return false;
  }

  @Override
  protected void end() {
    HelixEvents.getInstance().addEvent("DRIVETRAIN", "Stopping Vision Driving");
    camera.setDriverMode();
  }

  @Override
  protected void interrupted() {
    end();
  }
}
