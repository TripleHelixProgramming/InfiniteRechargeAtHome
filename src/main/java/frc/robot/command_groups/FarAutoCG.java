/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.command_groups;

import com.team319.trajectory.Path;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.drivetrain.commands.AutoAimInPlace;
import frc.robot.drivetrain.commands.PathFollower;
import frc.robot.drivetrain.commands.StopDrivetrain;
import frc.robot.drivetrain.commands.TurnToAngle;
import frc.robot.magazine.Magazine.BallHandlingState;
import frc.robot.shooter.Position;
import frc.robot.shooter.commands.SpinShooterUp;
import frc.robot.shooter.commands.StopShooter;

public class FarAutoCG extends CommandGroup {
  public FarAutoCG(Path phase1, Path phase2, boolean runPhase2) {
    addParallel(new SpinShooterUp(Position.TRENCH_SHOOT));
    addParallel(new StartIntakeCG(true), 4);
    addSequential(new PathFollower(phase1).reverse());

    addSequential(new TurnToAngle(0));
    addSequential(new AutoAimInPlace(), 2);
    addSequential(new StopDrivetrain());

    addSequential(new SetBallHandlingCG(BallHandlingState.SHOOT), 3);
    addSequential(new StopShooter());

    if (runPhase2) {
      addSequential(new TurnToAngle(0));

      addParallel(new SpinShooterUp(Position.TRENCH_SHOOT));
      addParallel(new StartIntakeCG(true), 4);
      addSequential(new PathFollower(phase2).reverse());

      addParallel(new StopIntakeCG());
      addSequential(new PathFollower(phase2));

      addSequential(new AutoAimInPlace(), 2);
      addSequential(new StopDrivetrain());

      addSequential(new SetBallHandlingCG(BallHandlingState.SHOOT), 3);
      addSequential(new StopShooter());
      addSequential(new SetBallHandlingCG(BallHandlingState.STOP), 1);
    }
  }
}
