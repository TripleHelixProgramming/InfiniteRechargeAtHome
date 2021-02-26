/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.command_groups;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.spacer.commands.AdvanceSpacer;
import frc.robot.spacer.commands.RunSpacer;

public class LoadMagazineCG extends CommandGroup {

  public LoadMagazineCG() {
    addSequential(new AdvanceSpacer(800), 0.5); //never finishes, stops spacer when ending
    addSequential(new RunSpacer(800));          //finshes when ballAtSpacer
  }
}
