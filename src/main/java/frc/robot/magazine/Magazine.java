/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.magazine;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANDigitalInput.LimitSwitchPolarity;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.spacer.Spacer;

public class Magazine extends Subsystem {

  private static Magazine INSTANCE = null;

  private static final int MAGAZINE_ID = 20;

  private final CANSparkMax motor;
  private final CANEncoder encoder;

  private int SHOOTER_BB_CHANNEL = 0;
  private int SPACER_BB_CHANNEL = 1;
  private int TURN_BB_CHANNEL = 2;

  // Number of balls currently in the system.
  public int ball_count = 0;

  // The various states of the Ball Handling subsystems, which include the magazine,
  // the spacer and the intake. Each subsytem, based on the state given to
  // it via the SetBallHandlingTo() command.  The state is passed to each 
  // subsystem from controller action command groups
  // like ShootCG, IntakeCG, etc.
  public enum BallHandlingState {
    SHOOT, INTAKE, SHOOT_NO_LOGIC, INTAKE_NO_LOGIC, SHOOT_ONE, STOP
  };

  private Magazine() {
    super();

    // initialize motor
    motor = new CANSparkMax(MAGAZINE_ID, MotorType.kBrushless);
    motor.restoreFactoryDefaults();
    motor.setIdleMode(IdleMode.kBrake);
    encoder = motor.getEncoder();

    ball_count = 0;
  }

  /**
   * @return the singleton instance of the magazine subsystem
   */
  public static Magazine getMagazine() {
    if (INSTANCE == null) {
      INSTANCE = new Magazine();
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

  public Boolean hasBalls() {
    return (ball_count > 0);
  }

  public void IncreaseBallCount() {
    ball_count++;
  }
  
  public void ResetBallCount() {
    ball_count = 0;
  }

  public Boolean ballAtShooter() {
    return motor.getForwardLimitSwitch(LimitSwitchPolarity.kNormallyOpen).get();
    // return(false);
  }

  public Boolean ballAtSpacer() {
    return Spacer.getSpacer().isBallPresent();
  }

  @Override
  public void periodic() {

    super.periodic();

    SmartDashboard.putNumber("Ball Count", ball_count);
  }

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }
}
