/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.command_groups;

import com.team319.trajectory.Path;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.paths.EightteenInchesBack;
import frc.paths.goback;
import frc.robot.drivetrain.commands.PathFollower;
import frc.robot.drivetrain.commands.ResetPigeon;
import frc.robot.shooter.Position;
import frc.robot.shooter.commands.HoodGoUp;
import frc.robot.shooter.commands.SpinShooterUp;
import frc.robot.shooter.commands.setRealRPM;

public class LayUpCG extends CommandGroup {
  /**
   * Add your docs here.
   */
  public LayUpCG(Path input) {
    addSequential(new ResetPigeon());
    addSequential(new PathFollower(input).reverse());
  }
}
