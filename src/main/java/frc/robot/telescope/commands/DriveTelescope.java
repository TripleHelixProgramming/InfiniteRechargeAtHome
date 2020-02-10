
package frc.robot.telescope.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.telescope.Telescope;
import frc.robot.oi.OI;

public class DriveTelescope extends Command {

  private Telescope myTelescope = null;
  double inputPosition, currentPosition;

  public DriveTelescope() {
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    myTelescope = Telescope.getTelescope();
    requires(Telescope.getTelescope());
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    inputPosition = OI.getOI().getClimberPower();
    currentPosition = Telescope.getTelescope().getPosition();
    
    if (!myTelescope.isRaised() && inputPosition > 0.5) {
      myTelescope.raise();
      return;
    }

    if myTelescope.isRaised() {
      if ((inputPosition < currentPosition) && (currentPosition < 0.1)) {
        //inputPosition = 0.0;
        myTelescope.lower();
        return;
      }

        inputPosition =  currentPosition + inputPosition;
        myTelescope.setRotations(inputPosition);
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
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
  }
}