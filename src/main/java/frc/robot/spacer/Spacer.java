/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.spacer;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANDigitalInput.LimitSwitchPolarity;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Add your docs here.
 */
public class Spacer extends Subsystem {

  private static Spacer INSTANCE = null;

  private static final int SPACER_ID = 19;
  private static final int INDEXER_ID = 18;

  private static final double INDEXER_SPEED = .2;

  private CANSparkMax motor = new CANSparkMax(SPACER_ID, MotorType.kBrushless);
  private TalonSRX indexer = new TalonSRX(INDEXER_ID);

  private final CANEncoder encoder;
  
  public Spacer() {
    super();
    // initialize motor
    indexer.configFactoryDefault();
    motor.restoreFactoryDefaults();
    motor.setIdleMode(IdleMode.kBrake);
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
    if (power == 0.0) 
      indexer.set(ControlMode.PercentOutput, 0.0);
    else
      indexer.set(ControlMode.PercentOutput, INDEXER_SPEED);

  }

  public double getMotorPosition() {
    return encoder.getPosition();
  }

  public boolean isBallPresent() {
    return motor.getForwardLimitSwitch(LimitSwitchPolarity.kNormallyOpen).get();
  }

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());

  }

}
