/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.indexer;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.indexer.commands.SetIndexerTo;
import frc.robot.magazine.Magazine.BallHandlingState;

/**
 * Add your docs here.
 */
public class Indexer extends Subsystem {

  private static Indexer INSTANCE = null;

  private static final int INDEXER_ID = 27;
  private final CANSparkMax motor;

  /**
   * @return the singleton instance of the Indexer subsystem
   */
  public static Indexer getIndexer() {
    if (INSTANCE == null) {
      INSTANCE = new Indexer();
    }
    return INSTANCE;
  }

  private Indexer() {
   super();

    // initialize motor
    motor = new CANSparkMax(INDEXER_ID, MotorType.kBrushless);
    motor.restoreFactoryDefaults();
  }

  public void setPower(double power) {
    // set motors to power;
    motor.set(power);
  }

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
    setDefaultCommand(new SetIndexerTo(BallHandlingState.STOP));
  }
}
