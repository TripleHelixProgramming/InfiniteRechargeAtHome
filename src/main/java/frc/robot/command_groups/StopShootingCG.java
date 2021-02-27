/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.command_groups;

import com.team2363.logger.HelixEvents;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.magazine.commands.StopMagazine;
import frc.robot.command_groups.LoadMagazineCG;

public class StopShootingCG extends CommandGroup {
  /**
   * Add your docs here.
   */
  public StopShootingCG() {

    HelixEvents.getInstance().addEvent("MAGAZINE", "Stop Shooting CG");

    addSequential(new StopMagazine());
    addSequential(new LoadMagazineCG());
  
  }
}