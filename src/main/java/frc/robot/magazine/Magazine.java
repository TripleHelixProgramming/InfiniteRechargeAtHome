/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.magazine;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.magazine.commands.SetMagazineTo;

public class Magazine extends Subsystem {

  private static Magazine INSTANCE = null;

  private static final int MAGAZINE_ID = 16;

  private final CANSparkMax motor;

  private int SHOOTER_BB_CHANNEL = 0;
  private int SPACER_BB_CHANNEL = 1;
  private int TURN_BB_CHANNEL = 2;

  private DigitalInput shooter_bb = new DigitalInput(SHOOTER_BB_CHANNEL);
  private DigitalInput spacer_bb = new DigitalInput(SPACER_BB_CHANNEL);
  private DigitalInput turn_bb = new DigitalInput(TURN_BB_CHANNEL);

  // Number of balls currently in the system.
  public int ball_count;

  // Various states of the Ball Handling subsystems, which are the magazine,
  // spacer, indexer and intake. Each subsytem, based on the state given to
  // it via the Run<> command, checks the necessary beam breaks and state value
  // to know how to run itself. The beam breaks are in the magazine subsystem.
  // The state is passed to each subsystem from controller action command groups
  // like ShootCG, IntakeCG, etc.
  public enum BallHandlingState {
    SHOOT, INTAKE, STOP, FULL
  };

  private Magazine() {
    super();

    // initialize motor
    motor = new CANSparkMax(MAGAZINE_ID, MotorType.kBrushless);
    motor.restoreFactoryDefaults();

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

  public Boolean hasBalls() {
    return (ball_count > 0);
  }

  public Boolean ballAtShooter() {
    return !shooter_bb.get();
  }

  public Boolean ballAtTurn() {
    return !turn_bb.get();
  }

  public Boolean ballAtSpacer() {
    return !spacer_bb.get();
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
