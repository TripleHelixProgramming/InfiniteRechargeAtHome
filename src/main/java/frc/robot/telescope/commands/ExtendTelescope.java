
package frc.robot.telescope.commands;

import com.team2363.logger.HelixEvents;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.telescope.Telescope;
import frc.robot.oi.OI;

public class ExtendTelescope extends Command {

  private Telescope myTelescope = null;
  private double inputPosition, currentPosition;
  private double ROTATIONS_SCALE = 0.1;
  private double MIN_POSITION = 0.1;

  public ExtendTelescope() {
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    myTelescope = Telescope.getTelescope();
    requires(Telescope.getTelescope());
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    HelixEvents.getInstance().addEvent("TELESCOPE", "Starting ExtendTelescope");
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    inputPosition = ROTATIONS_SCALE * OI.getOI().getClimberPower();
    currentPosition = myTelescope.getPosition();
    inputPosition =  currentPosition + inputPosition;
    myTelescope.setRotations(inputPosition);
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return ((inputPosition < currentPosition) && (currentPosition < MIN_POSITION));
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    HelixEvents.getInstance().addEvent("TELESCOPE", "Ending ExtendTelescope");
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    HelixEvents.getInstance().addEvent("TELESCOPE", "ExtendTelescope interrupted");
  }
}