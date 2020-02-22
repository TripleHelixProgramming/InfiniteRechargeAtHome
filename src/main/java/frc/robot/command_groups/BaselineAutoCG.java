package frc.robot.command_groups;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.magazine.Magazine.BallHandlingState;
import frc.robot.shooter.Position;
import frc.robot.shooter.commands.SpinShooterUp;
import frc.paths.BaselineAuto;
import frc.robot.drivetrain.commands.AimInPlace;
import frc.robot.drivetrain.commands.AutoAimInPlace;
import frc.robot.drivetrain.commands.PathFollower;
import frc.robot.drivetrain.commands.StopDrivetrain;
import frc.robot.drivetrain.commands.TurnToAngle;

public class BaselineAutoCG extends CommandGroup {

    public BaselineAutoCG() {
        addParallel(new SpinShooterUp(Position.TRENCH_SHOOT));
        addSequential(new PathFollower(new BaselineAuto()).reverse());
        // addSequential(new TurnToAngle(-15.0));
        addSequential(new AutoAimInPlace());
        addSequential(new StopDrivetrain());
        addSequential(new SetBallHandlingCG(BallHandlingState.SHOOT), 4.0);
        addSequential(new SetBallHandlingCG(BallHandlingState.STOP), 1.0);
        addSequential(new TurnToAngle(0.0));
        addSequential(new StopDrivetrain());
    }

}
