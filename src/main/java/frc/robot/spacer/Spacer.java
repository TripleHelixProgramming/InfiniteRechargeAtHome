/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.spacer;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.magazine.Magazine.BallHandlingState;
import frc.robot.spacer.commands.SetSpacerTo;

/**
 * Add your docs here.
 */
public class Spacer extends Subsystem {

  private static Spacer INSTANCE = null;

  private static final int SPACER_ID = 15;

  private final CANSparkMax motor;

  public Spacer() {

    // initialize motor
    motor = new CANSparkMax(SPACER_ID, MotorType.kBrushless);
    motor.restoreFactoryDefaults();
  }

  /**
   * @return the singleton instance of the spacer subsystem
   */
  public static Spacer getSpacer() {
    if (INSTANCE == null) {
      INSTANCE = new Spacer();
    }
    return INSTANCE;
  }

  public void setPower(double power) {
    // set motors to power;
    motor.set(power);
  }

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());

  }
}
