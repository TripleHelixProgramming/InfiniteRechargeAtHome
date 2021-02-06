/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.command_groups;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.paths.RedZone;
import frc.robot.drivetrain.commands.PathFollower;
import frc.robot.drivetrain.commands.visionAim;
import frc.robot.magazine.Magazine.BallHandlingState;
import frc.robot.shooter.Position;
import frc.robot.shooter.commands.SpinShooterUp;
import frc.robot.shooter.commands.StopShooter;
import frc.robot.shooter.commands.setRealRPM;

public class ShooterChallenge extends CommandGroup {
  public ShooterChallenge() {
    addParallel(new setRealRPM(3350));
    addSequential(new PathFollower("RedZone"));
    addSequential(new SetBallHandlingCG(BallHandlingState.SHOOT), 3);
    addSequential(new StopShooter());
    addSequential(new SetBallHandlingCG(BallHandlingState.STOP), 0.1);
  }
}
