/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.command_groups;

import com.team319.trajectory.Path;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.paths.OppTrenchLayupPartOne;
import frc.paths.OppTrenchLayupPartTwo;
import frc.robot.drivetrain.commands.PathFollower;
import frc.robot.drivetrain.commands.StopDrivetrain;
import frc.robot.magazine.Magazine.BallHandlingState;
import frc.robot.shooter.commands.StopShooter;

public class LayupAutoCG extends CommandGroup {
  /**
   * Add your docs here.
   */
  public LayupAutoCG(Path phase1, Path phase2) {
    addParallel(new StartIntakeCG(true), 4);
    addSequential(new PathFollower(phase1).reverse());

    addParallel(new StopIntakeCG(), 4);
    addSequential(new PathFollower(phase2));
    addSequential(new StopDrivetrain());
    
    addSequential(new LayUpCG());
    addSequential(new StopDrivetrain());
    addSequential(new SetBallHandlingCG(BallHandlingState.SHOOT), 3);

    addSequential(new StopShooter());
    addSequential(new SetBallHandlingCG(BallHandlingState.STOP), 1);
  }
}
