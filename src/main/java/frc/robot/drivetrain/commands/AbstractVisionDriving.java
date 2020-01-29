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
    double h1 = 16;
    double h2 = 39;
    double a1 = 0;
    double verticalAngleToTarget = camera.getVerticalDegreesToTarget();
    double distanceToTarget = (h2 - h1) / Math.tan(a1 + verticalAngleToTarget);
    double angleToTarget = camera.getRotationalDegreesToTarget();
    
    controller.setReference(0);
    SmartDashboard.putNumber("Angle to target", verticalAngleToTarget);
    SmartDashboard.putNumber("Distance to target", distanceToTarget);
  }

  @Override
  protected boolean isFinished() {
    return false;
  }

  @Override
  protected void end() {
    notifier.stop();
    HelixEvents.getInstance().addEvent("DRIVETRAIN", "Stopping Vision Driving");
  }

  @Override
  protected void interrupted() {
    end();
  }

  private void calculate() {
    final double output = controller.calculate(camera.getRotationalDegreesToTarget());
    getDrivetrain().setSetpoint(FPS, getThrottle() - output, getThrottle() + output);
  }

  public double angle() {
    return camera.getRotationalDegreesToTarget();
  }
}
