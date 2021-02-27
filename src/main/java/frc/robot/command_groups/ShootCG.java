/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.command_groups;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.magazine.commands.FireMagazine;
import frc.robot.spacer.commands.StopSpacer;

public class ShootCG extends CommandGroup {
  /**
   * Add your docs here.
   */
  public ShootCG() {

    addSequential(new StopSpacer());
    addSequential(new FireMagazine(), 1.5);
  
  }
}