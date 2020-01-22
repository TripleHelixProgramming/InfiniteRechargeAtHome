/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.indexer;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.indexer.commands.SetIndexerTo;
import frc.robot.magazine.Magazine.BallHandlingState;

/**
 * Add your docs here.
 */
public class Indexer extends Subsystem {

  private static Indexer INSTANCE = null;
  /**
   * @return the singleton instance of the Indexer subsystem
   */
  public static Indexer getIndexer() {
    if (INSTANCE == null) {
      INSTANCE = new Indexer();
    }
    return INSTANCE;
  }

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
    setDefaultCommand(new SetIndexerTo(BallHandlingState.STOP));
  }
}
