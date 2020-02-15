
package frc.robot.controlpanel.commands;

import com.team2363.logger.HelixEvents;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.controlpanel.ControlPanel;

public class RetractArm extends Command {

    private ControlPanel controlPanel = null;

    public RetractArm() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        controlPanel = ControlPanel.getControlPanel();
        requires(controlPanel);
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
        HelixEvents.getInstance().addEvent("CONTROL PANEL", "Starting RetractArm");

        controlPanel.retract();
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
        // Nothing to do, retracting is handled when initialized.
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        // Finish is done when the arm is retracted.
        return controlPanel.isRetracted();
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
        HelixEvents.getInstance().addEvent("CONTROL PANEL", "Ending RetractArm");
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
        // Default Command will retract and stop Intake
    }
}