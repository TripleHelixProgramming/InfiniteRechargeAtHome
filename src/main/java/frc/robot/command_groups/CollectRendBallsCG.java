/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.command_groups;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;
import frc.paths.CollectRendBalls;
import frc.paths.CollectRendBalls3;
import frc.paths.CoolectRendBalls2;
import frc.paths.g;
import frc.robot.drivetrain.commands.AutoAimInPlace;
import frc.robot.drivetrain.commands.PathFollower;
import frc.robot.drivetrain.commands.StopDrivetrain;
import frc.robot.drivetrain.commands.TurnToAngle;
import frc.robot.intake.commands.DeployIntake;
import frc.robot.magazine.Magazine.BallHandlingState;
import frc.robot.shooter.Position;
import frc.robot.shooter.commands.SpinShooterUp;
import frc.robot.shooter.commands.StopShooter;

public class CollectRendBallsCG extends CommandGroup {

  // This command group collects the balls from the rendevous area and shoots them, scoring 5 balls in total

  public CollectRendBallsCG() {

    // Spin up the shooter and move to the two balls in the rendevous area
    addParallel(new SpinShooterUp(Position.TRENCH_SHOOT));
    addSequential(new PathFollower(new CollectRendBalls3()).reverse());
    addSequential(new StopDrivetrain());
    
    // Collect the balls in the rendevous area
    addSequential(new StartIntakeCG(true),4);
    addSequential(new StopIntakeCG(), 1);
    
    // Aim toward the target
    addSequential(new TurnToAngle(-10.0));
    addSequential(new StopDrivetrain());
    
    // Shoot the balls the robot has collected
    addSequential(new SetBallHandlingCG(BallHandlingState.SHOOT), 4.0);
    addParallel(new SetBallHandlingCG(BallHandlingState.STOP),1);
    addSequential(new StopShooter());
  }
}
