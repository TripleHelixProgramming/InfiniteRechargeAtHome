/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.spacer;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.revrobotics.CANDigitalInput;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANDigitalInput.LimitSwitch;
import com.revrobotics.CANDigitalInput.LimitSwitchPolarity;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.team2363.logger.HelixLogger;

import edu.wpi.first.wpilibj.Notifier;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Add your docs here.
 */
public class Spacer extends Subsystem {

  private static Spacer INSTANCE = null;

  private static final int SPACER_ID = 19;
  private static final int INDEXER_ID = 18;

  private static final double INDEXER_SPEED = .2;
  private static double OSCILATTE_INTERVAL = 4.0;
  private double currentIndexerPower = 0.0;
  private double lastOscillatePower = 0.0;

  private CANSparkMax motor = new CANSparkMax(SPACER_ID, MotorType.kBrushless);
  private TalonSRX indexer = new TalonSRX(INDEXER_ID);

  private final Notifier oscilateIndexer = new Notifier(this::Oscillate);

  private final CANDigitalInput limit;
  
  public Spacer() {
    super();

    // initialize motor
    indexer.configFactoryDefault();
    motor.restoreFactoryDefaults();
    motor.setIdleMode(IdleMode.kBrake);
    motor.setSmartCurrentLimit(30);

    //  Disable Limit Switches
    limit = new CANDigitalInput(motor,LimitSwitch.kForward,LimitSwitchPolarity.kNormallyOpen);
    limit.enableLimitSwitch(false);

    oscilateIndexer.startSingle(OSCILATTE_INTERVAL);

    setupLogs();
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
      currentIndexerPower = 0.0;
    else 
      currentIndexerPower = INDEXER_SPEED;

    indexer.set(ControlMode.PercentOutput, currentIndexerPower);
  }

  public boolean isBallPresent() {
    return motor.getForwardLimitSwitch(LimitSwitchPolarity.kNormallyOpen).get();
  }

  private void Oscillate() {

    if (currentIndexerPower == 0.0) {
      oscilateIndexer.startSingle(OSCILATTE_INTERVAL);
      lastOscillatePower = -INDEXER_SPEED;
      return;
    } 

    if (lastOscillatePower == INDEXER_SPEED) {
      indexer.set(ControlMode.PercentOutput, -INDEXER_SPEED);
      lastOscillatePower = -INDEXER_SPEED;
      oscilateIndexer.startSingle(1.0);
    } else {
      indexer.set(ControlMode.PercentOutput, INDEXER_SPEED);
      lastOscillatePower = INDEXER_SPEED;
      oscilateIndexer.startSingle(OSCILATTE_INTERVAL);
    }
    return;
  }
  
  private void setupLogs() {
    HelixLogger.getInstance().addDoubleSource("SPACER CURRENT", motor::getOutputCurrent);
    HelixLogger.getInstance().addDoubleSource("SPACER VOLTAGE", motor::getBusVoltage);
  }

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());

  }

}
