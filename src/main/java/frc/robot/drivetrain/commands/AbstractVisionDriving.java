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

  private final PIDController controller = new PIDController(0.05, 0, 0);
  private final Notifier notifier = new Notifier(this::calculate);
  private final Camera camera;
  private double angleToTarget, output;

  public AbstractVisionDriving() {
    requires(getDrivetrain());
    camera = getDrivetrain().getFrontCamera();
  }

  public abstract double getThrottle();

  @Override
  protected void initialize() {
    notifier.startPeriodic(0.001);
    camera.setDockingMode();
  }

  @Override
  protected void execute() {
    controller.setReference(0);
    SmartDashboard.putNumber("tx", camera.getRotationalDegreesToTarget());
    SmartDashboard.putNumber("ground_distance",camera.calculateDistanceToTarget());
    SmartDashboard.putNumber("calculated RPM", camera.calculateRPM());
  }

  @Override
  protected boolean isFinished() {
    return false;
  }

  @Override
  protected void end() {
    notifier.stop();
    HelixEvents.getInstance().addEvent("DRIVETRAIN", "Stopping Vision Driving");
    getDrivetrain().setPIDFValues();

  }

  @Override
  protected void interrupted() {
    camera.setDriverMode();
    end();
  }

  private void calculate() {
    output = controller.calculate(camera.getRotationalDegreesToTarget());
    getDrivetrain().setSetpoint(FPS, getThrottle() - output, getThrottle() + output);
  }
}
