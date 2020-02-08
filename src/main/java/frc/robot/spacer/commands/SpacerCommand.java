package frc.robot.spacer.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.spacer.Spacer;

public class SpacerCommand extends Command {

    Spacer m_spacer;
    public SpacerCommand(){
        m_spacer = Spacer.getSpacer();
        requires(m_spacer);
    }
    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
       // Spacer.getSpacer().setPower(-0.10);
    }

    @Override
    protected void execute() {        
    }

    @Override
    protected boolean isFinished() {
        return false; // This command never finishes.
    }

    @Override
    protected void interrupted() {
        m_spacer.setPower(0.0);
    }

}