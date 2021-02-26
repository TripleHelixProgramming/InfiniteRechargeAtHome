/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.command_groups;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.spacer.commands.RunSpacer;
import frc.robot.magazine.commands.StopMagazine;

public class StopShootingCG extends CommandGroup {
  /**
   * Add your docs here.
   */
  public StopShootingCG() {

    addSequential(new StopMagazine());
    addSequential(new RunSpacer());
  
  }
}