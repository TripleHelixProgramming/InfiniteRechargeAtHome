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

  double cameraHeight = 16; //random value inches
  double cameraElevation = 0; //random value degrees
  double targetHeight = 81.25; //actual value inches
  double ty = 42; //random value pixels
  double tvert = 42; //random value pixels

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
    ty = camera.getVerticalDegreesToTarget(); //actual value pixels
    // tvert = camera.getHeightPixelsOfTarget(); 
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
  }

  @Override
  protected void interrupted() {
    end();
  }

  private void calculate() {
    final double output = controller.calculate(getDrivetrain().getHeading());
    getDrivetrain().setSetpoint(FPS, getThrottle() + output, getThrottle() - output);
  }

  // private double calculateGroundDistanceToTarget() {
  //   return (targetHeight - 15*(ty/tvert) - cameraHeight)/Math.tan(cameraElevation);
  // }

  private double calculateDistanceToTarget() {
    return (56 - cameraHeight)/Math.tan(Math.toRadians(cameraElevation + ty));//89.75 is height in actual arena
    // return (40 - cameraHeight)/Math.tan((cameraElevation + ty)*(pi/180)); 
  }
}
