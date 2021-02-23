/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.command_groups;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.shooter.commands.HoodGoDown;
import frc.robot.shooter.commands.HoodGoUp;
import frc.robot.shooter.commands.setRealRPM;

public class SecondClose extends CommandGroup {
  /**
   * Add your docs here.
   */
  public SecondClose() {
    addSequential(new HoodGoUp());
    addParallel(new setRealRPM(1875));
  }
}
