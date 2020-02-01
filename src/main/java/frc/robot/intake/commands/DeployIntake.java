
package frc.robot.intake.commands;

import com.team2363.logger.HelixEvents;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.intake.Intake;

public class DeployIntake extends Command {

  public DeployIntake() {
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    requires(Intake.getIntake());
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    HelixEvents.getInstance().addEvent("INTAKE", "Starting DeployIntake");
    Intake.getIntake().extend();
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
      Intake.getIntake().rollerIn();
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return false;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    HelixEvents.getInstance().addEvent("INTAKE", "Ending DeployIntake");
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    Intake.getIntake().rollerOff();
    Intake.getIntake().retract();
  }
}