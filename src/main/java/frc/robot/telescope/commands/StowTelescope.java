
package frc.robot.telescope.commands;

import com.team2363.logger.HelixEvents;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.telescope.Telescope;

public class StowTelescope extends Command {

  private Telescope myTelescope = null;
  private boolean raised = false;

  public StowTelescope(double rotations) {
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    myTelescope = Telescope.getTelescope();
    requires(Telescope.getTelescope());
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    HelixEvents.getInstance().addEvent("TELESCOPE", "Starting StowTelescope");
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    if myTelescope.isRaised() {
      return;
    } else {
      rotations = getPosition() + rotations;
      myTelescope.setRotations(rotations);
    }
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return getPosition() < 0.0;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    myTelescope.lower();
    HelixEvents.getInstance().addEvent("TELESCOPE", "Ending StowTelescope");
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    HelixEvents.getInstance().addEvent("TELESCOPE", "Ending StowTelescope");
  }
}