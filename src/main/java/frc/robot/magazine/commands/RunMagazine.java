package frc.robot.magazine.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.magazine.Magazine;

public class RunMagazine extends Command {

    public RunMagazine() {
        requires(Magazine.getMagazine());
    }

    @Override
    protected void initialize() {
        Magazine.getMagazine().setPower(-0.10);
    }

    @Override
    protected void execute() {
    }

    @Override
    protected boolean isFinished() {
        return false; // This command never finishes
    }

    @Override
    protected void interrupted() {
        Magazine.getMagazine().setPower(0.0);
    }
}