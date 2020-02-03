/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.spacer;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Add your docs here.
 */
public class Spacer extends Subsystem {

  private static Spacer INSTANCE = null;

  private static final int SPACER_ID = 19;

  private CANSparkMax motor = new CANSparkMax(SPACER_ID, MotorType.kBrushless);
  private final CANEncoder encoder;
  
  public Spacer() {
    super();
    // initialize motor
    motor.restoreFactoryDefaults();
    encoder = motor.getEncoder();
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

  public double getMotorPosition() {
    return encoder.getPosition();
  }

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());

  }
}
