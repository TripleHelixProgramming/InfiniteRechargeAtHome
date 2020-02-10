
package frc.robot.telescope.commands;

import com.team2363.logger.HelixEvents;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.telescope.Telescope;

public class DeployTelescope extends Command {

  private Telescope myTelescope = null;

  public DeployTelescope(double rotations) {
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    myTelescope = Telescope.getTelescope();
    requires(Telescope.getTelescope());
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    HelixEvents.getInstance().addEvent("TELESCOPE", "Starting DeployTelescope");

    if (!myTelescope.isRaised()) {
      myTelescope.raise();
    }
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    if myTelescope.isRaised() {
      rotations = getPosition() + rotations;
      myTelescope.setRotations(rotations);
    } else {
      return;
    }
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return false; // Never finish
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    HelixEvents.getInstance().addEvent("TELESCOPE", "Ending DeployTelescope");
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    HelixEvents.getInstance().addEvent("TELESCOPE", "Ending DeployTelescope");
  }
}