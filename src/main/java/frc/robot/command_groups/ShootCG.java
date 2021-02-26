/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.command_groups;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.magazine.commands.ReadyMagazine;
import frc.robot.magazine.commands.RunMagazine;

public class ShootCG extends CommandGroup {
  /**
   * Add your docs here.
   */
  public ShootCG() {

    //if shooter is at speed
    addSequential(new ReadyMagazine());
    addSequential(new RunMagazine(), 1.0);
  
  }
}