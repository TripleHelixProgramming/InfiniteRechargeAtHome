/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.drivetrain.commands;

import frc.robot.drivetrain.commands.ResetPigeon;
import edu.wpi.first.wpilibj.command.CommandGroup;

public class BouncePathCG extends CommandGroup {
  public BouncePathCG() {
    // addSequential(new PathFollower("CannibalBackwards1").reverse());
    // addSequential(new ResetPigeon());
    // addSequential(new PathFollower("CannibalBackwards2"));
    // addSequential(new ResetPigeon());
    // addSequential(new PathFollower("CannibalBackwards3").reverse().mirror());
    // addSequential(new ResetPigeon());
    // addSequential(new PathFollower("CannibalBackwards4"));
  }

}
