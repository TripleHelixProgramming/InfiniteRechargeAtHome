/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.drivetrain.commands;

import com.team2363.controller.PIDController;
import static frc.robot.drivetrain.Drivetrain.getDrivetrain;
import static frc.robot.drivetrain.Drivetrain.CommandUnits.PERCENT_FULLSPEED;
import com.team2363.logger.HelixEvents;

import edu.wpi.first.wpilibj.Notifier;
import edu.wpi.first.wpilibj.command.Command;

public class TurnToAngle extends Command {

  private final PIDController controller = new PIDController(0.04, 0.003, 0.002);
  private final Notifier notifier = new Notifier(this::calculate);
  private double angle;

  public TurnToAngle(double angle) {
    requires(getDrivetrain());

    // getPIDController().setToleranceBuffer(10);
    controller.setAbsoluteTolerance(1);
    controller.setContinuous(true);
    controller.setInputRange(-180, 180);
    controller.setOutputRange(-.3, .3);  // Output in range of throttle you want to turn at

    this.angle = angle;
  }

  // Called just before this Command runs the first time
  protected void initialize() {
    notifier.startPeriodic(0.001);
    // HelixEvents.addEvent("Drivetrain", "Starting to turn to angle");
  }

  // Called repeatedly when this Command is scheduled to run
  protected void execute() {
    controller.setReference(angle);
  }

  // Make this return true when this Command no longer needs to run execute()
  protected boolean isFinished() {
    if (Math.abs(angle - getDrivetrain().getHeading()) < 1) {
      return true;
    }
    return false;
  }

  // Called once after isFinished returns true
  protected void end() {
    notifier.stop();
    // HelixEvents.addEvent("DRIVETRAIN", "Finished turning to angle");
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  protected void interrupted() {
    end();
  }

  private void calculate() {
    final double output = controller.calculate(getDrivetrain().getHeading());
    getDrivetrain().setSetpoint(PERCENT_FULLSPEED, -output, output);
  }
}