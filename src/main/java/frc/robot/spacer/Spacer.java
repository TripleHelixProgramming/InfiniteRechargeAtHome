/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.spacer;

import com.revrobotics.CANDigitalInput;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANDigitalInput.LimitSwitch;
import com.revrobotics.CANDigitalInput.LimitSwitchPolarity;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.team2363.logger.HelixLogger;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Add your docs here.
 */
public class Spacer extends Subsystem {

  private static Spacer INSTANCE = null;

  private static final int SPACER_ID = 16;

  public Boolean spacer_running = false;

  private CANSparkMax motor = new CANSparkMax(SPACER_ID, MotorType.kBrushless);

  private final CANDigitalInput limit;
  
  public Spacer() {
    super();

    // initialize motor
    motor.restoreFactoryDefaults();
    motor.setIdleMode(IdleMode.kBrake);
    motor.setSmartCurrentLimit(30);

    //  Disable Limit Switches
    limit = new CANDigitalInput(motor,LimitSwitch.kForward,LimitSwitchPolarity.kNormallyOpen);
    limit.enableLimitSwitch(false);

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
      spacer_running = false;
    else 
      spacer_running = true;
  }

  public boolean isBallPresent() {
    return motor.getForwardLimitSwitch(LimitSwitchPolarity.kNormallyOpen).get();
  }

  public boolean isSpacerRunning() {
    return spacer_running;
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
