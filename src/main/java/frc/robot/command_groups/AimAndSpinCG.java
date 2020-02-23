/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.command_groups;

import edu.wpi.first.wpilibj.command.CommandGroup;

import frc.robot.drivetrain.commands.AimInPlace;
import frc.robot.shooter.Position;
import frc.robot.shooter.commands.SpinShooterUp;


public class AimAndSpinCG extends CommandGroup {
  public AimAndSpinCG() {
    addSequential(new AimInPlace());
    addSequential(new SpinShooterUp(Position.UNKNOWN));
  }
}