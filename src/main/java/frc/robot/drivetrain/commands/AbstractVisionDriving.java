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

  double cameraHeight = 16; // (inches) currently the height on the programming bot
  double middleTargetHeight = 74; // (inches) use 89.75 for actual arena height
  double cameraElevation = 22.9; //(degrees) currently the angle on the programming bot
  double ty = 0; //(degrees) vertical angle between crosshair and center of target

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

    final double angleToTarget = camera.getRotationalDegreesToTarget();
    controller.setReference(getDrivetrain().getHeading() + angleToTarget);
    ty = camera.getVerticalDegreesToTarget();
    SmartDashboard.putNumber("ty", ty);
    SmartDashboard.putNumber("tx", angleToTarget);
    SmartDashboard.putNumber("ground_distance",calculateDistanceToTarget());

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
    end();
  }

  private void calculate() {
    final double output = controller.calculate(camera.getRotationalDegreesToTarget());
    getDrivetrain().setSetpoint(FPS, getThrottle() - output, getThrottle() + output);
  }

  public double angle() {
    return camera.getRotationalDegreesToTarget();
  }

  private double calculateDistanceToTarget() {
    return (middleTargetHeight - cameraHeight)/Math.tan(Math.toRadians(cameraElevation + ty));
    // calculates ground distane from robot to target, only accurate when tx = 0
  }
}
