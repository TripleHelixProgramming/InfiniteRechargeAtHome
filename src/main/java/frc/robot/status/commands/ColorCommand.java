
package frc.robot.status.commands;

import com.team2363.logger.HelixEvents;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.util.Color;
import frc.robot.status.Action;
import frc.robot.status.LedAction;
import frc.robot.status.Status;

public class ColorCommand extends Command {

    private Status status = null;
    private Action action = null;

    // Default constructor - will toggle the light.
    public ColorCommand(int red, int green, int blue, int brightness) {
        status = Status.getStatus();
        requires(status);

        action = new LedAction(red, green, blue, brightness);
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
        HelixEvents.getInstance().addEvent("STATUS", "Starting RunRainbow");

        status.setAction(action);
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
        // Nothing to do, extending is handled when initialized.
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
        HelixEvents.getInstance().addEvent("STATUS", "Ending RunRainbow");
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {

    }
}