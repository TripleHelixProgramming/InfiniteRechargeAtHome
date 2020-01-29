/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.drivetrain.commands;
import static frc.robot.drivetrain.Drivetrain.getDrivetrain;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.drivetrain.Camera;

public class CameraInfo extends Command {

  private final Camera camera;

  double h1;
  double h2;
  double a1;
  double ty;
  double tx;
  double d;

  public CameraInfo() {
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);

    requires(getDrivetrain());
    camera = getDrivetrain().getFrontCamera();

  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    camera.setDockingMode();
    h1 = 16;
    h2 = 39;
    a1 = 0;
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    ty = camera.getVerticalDegreesToTarget();
    tx = camera.getRotationalDegreesToTarget();
    d = (h2 - h1) / Math.tan(ty + a1);
    SmartDashboard.putNumber("tx", tx);
    SmartDashboard.putNumber("ty", ty);
    SmartDashboard.putNumber("d",d);
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return false;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
  }
}
