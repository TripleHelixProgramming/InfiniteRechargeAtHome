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

import frc.lib.util.control.ExpCurve;
import frc.lib.util.control.SplineCurve;
import frc.lib.util.control.SplineType;
import frc.robot.drivetrain.Drivetrain;
import frc.robot.oi.OI;

public class TestDrive extends HelixDrive {

  private final double deadZone = 0.05;

  private final ExpCurve expThrottleCurve;
  private final ExpCurve expTurnCurve;

  private final SplineCurve splineThrottleCurve;
  private final SplineCurve splineTurnCurve;

  private final double[][] splineThrottlePoints;
  private final double[][] splineTurnPoints;

  private double throttleMap;
  private double turnMap;

  public TestDrive() {
    requires(Drivetrain.getDrivetrain());

    expThrottleCurve = new ExpCurve(10.0, 0.0, 1.0, deadZone);
    expTurnCurve = new ExpCurve(16.0, 0.0, 0.5, deadZone);

    splineThrottlePoints = new double[][]{{-1.0, -1.0}, {0.0, 0.0}, {1.0, 1.0}};
    splineTurnPoints = new double[][]{{-1.0, -1.0}, {0.0, 0.0}, {1.0, 1.0}};
    
    splineThrottleCurve = new SplineCurve(SplineType.SMOOTH, splineThrottlePoints, 0.0, 1.0, deadZone);
    splineTurnCurve = new SplineCurve(SplineType.SMOOTH, splineTurnPoints, 0.0, 1, deadZone);
  }

  @Override
  protected void initialize() {
    super.initialize();
  }

  @Override
  protected double getThrottle() {
      throttleMap = expThrottleCurve.calculateMappedVal(OI.getOI().getThrottle());
      return -throttleMap;
  }

  @Override
  protected double getTurn() {
      turnMap = expTurnCurve.calculateMappedVal(OI.getOI().getTurn());
      return turnMap;
  }

  @Override
  protected void useOutputs(final double left, final double right) {
      Drivetrain.getDrivetrain().setSetpoint(PERCENT_FULLSPEED, left, right);
  }
}
