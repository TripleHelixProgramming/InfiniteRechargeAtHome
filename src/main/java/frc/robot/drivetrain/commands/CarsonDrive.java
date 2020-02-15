/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.drivetrain.commands;

import static frc.robot.drivetrain.Drivetrain.CommandUnits.PERCENT_FULLSPEED;

import com.team2363.commands.HelixDrive;
import com.team2363.utilities.RollingAverager;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.drivetrain.Drivetrain;
import frc.robot.oi.OI;

public class CarsonDrive extends HelixDrive {

  double deadZone = 0.05;

  private final RollingAverager throttle = new RollingAverager(7);

  public CarsonDrive() {
    requires(Drivetrain.getDrivetrain());
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    super.initialize();
    for (int i = 0; i < 7; i++) {
        throttle.getNewAverage(0);
    }
  }

  @Override
  protected double getThrottle() {
      double newThrottle = OI.getOI().getThrottle();
      if (Math.abs(newThrottle) < deadZone) {
          return 0;
      }
      return -regraphDeadzone(OI.getOI().getThrottle());
  }

  @Override
  protected double getTurn() {
      if (Math.abs(OI.getOI().getTurn()) < deadZone) {
          return 0;
      }
      return regraphDeadzone(OI.getOI().getTurn()) * 0.5;
  }

  @Override
  protected void useOutputs(final double left, final double right) {
      Drivetrain.getDrivetrain().setSetpoint(PERCENT_FULLSPEED, left, right);
  }

  private double regraphDeadzone(double throttleInput) {
    double absThrottle = Math.abs(throttleInput);
    double termOne = absThrottle / throttleInput;
    double termTwo = 1/(1 - deadZone);
    double termThree = absThrottle - deadZone;
    return (termOne * termTwo * termThree);
  }
}
