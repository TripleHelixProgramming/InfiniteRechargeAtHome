package frc.robot.spacer.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.spacer.Spacer;

public class SpacerCommand extends Command {

    public SpacerCommand(){
        requires(Spacer.getSpacer());
    }
    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
        Spacer.getSpacer().setPower(-0.10);
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
        Spacer.getSpacer().setPower(0.0);
    }

}