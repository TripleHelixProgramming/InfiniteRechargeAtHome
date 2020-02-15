
package frc.robot.controlpanel.commands;

import com.team2363.logger.HelixEvents;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.controlpanel.ControlPanel;

public class DeployArm extends Command {

    private ControlPanel controlPanel = null;

    public DeployArm() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        controlPanel = ControlPanel.getControlPanel();
        requires(controlPanel);
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
        HelixEvents.getInstance().addEvent("CONTROL PANEL", "Starting DeployArm");

        controlPanel.extend();
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
        // Nothing to do, extending is handled when initialized.
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        // Finish is done when the arm is extended.
        return controlPanel.isExtended();
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
        HelixEvents.getInstance().addEvent("CONTROL PANEL", "Ending DeployArm");
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
        // Default Command will retract and stop Intake
    }
}