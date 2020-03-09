
package frc.robot.status.commands;

import com.team2363.logger.HelixEvents;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.status.LedAction;
import frc.robot.status.Status;

public class RunRainbow extends Command {

    private Status status = null;

    private Thread actionThread = null;

    // Default constructor - will toggle the light.
    public RunRainbow() {
        status = Status.getStatus();
        requires(status);

        // Default will toggle.
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
        HelixEvents.getInstance().addEvent("STATUS", "Starting RunRainbow");

        LedAction action = new LedAction();
        actionThread = new Thread(action);
        actionThread.start();
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
        // Nothing to do, extending is handled when initialized.
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        return actionThread.isAlive() == false;
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
        actionThread.interrupt();
    }
}