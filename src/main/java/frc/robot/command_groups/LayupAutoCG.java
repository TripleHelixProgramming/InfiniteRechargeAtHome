/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.command_groups;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.paths.LayupAutoPartOne;
import frc.paths.LayupAutoPartTwo;
import frc.robot.drivetrain.commands.PathFollower;
import frc.robot.drivetrain.commands.StopDrivetrain;
import frc.robot.magazine.Magazine.BallHandlingState;
import frc.robot.shooter.Position;
import frc.robot.shooter.commands.SpinShooterUp;
import frc.robot.shooter.commands.StopShooter;

public class LayupAutoCG extends CommandGroup {
  /**
   * Add your docs here.
   */
  public LayupAutoCG() {

    addParallel(new StartIntakeCG(true), 4);
    addSequential(new PathFollower(new LayupAutoPartOne()).reverse());
    addParallel(new StopIntakeCG());
    addParallel(new SpinShooterUp(Position.LAYUP_SHOOT));
    addSequential(new PathFollower(new LayupAutoPartTwo()));
    addSequential(new StopDrivetrain());
    addSequential(new SetBallHandlingCG(BallHandlingState.SHOOT), 5);
    addSequential(new StopShooter());
  }
}
