/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.command_groups;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.paths.EightteenInchesBack;
import frc.robot.drivetrain.commands.PathFollower;
import frc.robot.shooter.Position;
import frc.robot.shooter.commands.SpinShooterUp;

public class LayUpCG extends CommandGroup {
  /**
   * Add your docs here.
   */
  public LayUpCG() {
    addParallel(new PathFollower(new EightteenInchesBack()).reverse());
    addParallel(new SpinShooterUp(Position.LAYUP_SHOOT));
  }
}
