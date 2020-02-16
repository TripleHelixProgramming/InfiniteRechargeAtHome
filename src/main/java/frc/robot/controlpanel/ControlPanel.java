/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.controlpanel;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.controlpanel.commands.RetractArm;
import frc.robot.controlpanel.model.ColorSensor;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

public class ControlPanel extends Subsystem {

  private static ControlPanel INSTANCE = null;

  // The colors we care about.
  public static final int COLOR_UNKNOWN = 0;
  public static final int COLOR_BLUE = 1;
  public static final int COLOR_GREEN = 2;
  public static final int COLOR_RED = 3;
  public static final int COLOR_YELLOW = 4;

  // TODO: Get proper IDs
  public static int CONTROL_PANEL_RETRACT_ID = 6;
  public static int CONTROL_PANEL_DEPLOY_ID = 7;

  // TODO: Get the proper ID
  private static final int CONTROL_PANEL_MOTOR_ID = 15;

  // The solenoid responsible for the cylinder that controls the intake arm.
  private DoubleSolenoid solenoid = new DoubleSolenoid(CONTROL_PANEL_DEPLOY_ID, CONTROL_PANEL_RETRACT_ID);

  private final ColorSensor colorSensor;
  private CANSparkMax motor = new CANSparkMax(CONTROL_PANEL_MOTOR_ID, MotorType.kBrushless);

  private ControlPanel() {
    super();
    this.colorSensor = new ColorSensor();

    motor.restoreFactoryDefaults();
    motor.setIdleMode(IdleMode.kBrake);
  }

  /**
   * @return the singleton instance of the Drivetrain subsystem
   */
  public static ControlPanel getControlPanel() {
    if (INSTANCE == null) {
      INSTANCE = new ControlPanel();
    }
    return INSTANCE;
  }

  // Extends the control panel manipulator arm.
  public void extend() {
    solenoid.set(Value.kForward);
  }

  // Retracts the control panel manipulator arm.
  public void retract() {
    solenoid.set(Value.kReverse);
  }

  // Status of the control panel manipulator arm's extended state.
  public boolean isExtended() {
    return solenoid.get() == Value.kForward;
  }

  // Status of the control panel manipulator arm's retracted state.
  public boolean isRetracted() {
    return solenoid.get() == Value.kReverse;
  }

  public void spinMotor() {
    motor.set(0.2);
  }

  public void stopMotor() {
    motor.set(0.0);
  }

  // Returns the currently sensed color from the control panel color sensor.
  public int getCurrentColor() {
    return colorSensor.getCurrentColor();
  }

  // This will attempt to fetch the game data color value, if present.
  // Returns our enumerated color values.
  // Note: the color needed is the color the field sensor needs to see
  // which is not the color our robot will see. This needs to be translated
  // so we turn to the correct color for our sensor to corrispond to the
  // color needed on the field sensor (which is the return of this function)
  public int getFieldRequiredColor() {
    int color = COLOR_UNKNOWN;

    String gameData = DriverStation.getInstance().getGameSpecificMessage();
    if (gameData.length() > 0) {
      switch (gameData.charAt(0)) {
      case 'B':
        color = COLOR_BLUE;
        break;
      case 'G':
        color = COLOR_GREEN;
        break;
      case 'R':
        color = COLOR_RED;
        break;
      case 'Y':
        color = COLOR_YELLOW;
        break;
      default:
        // This is corrupt data
        break;
      }
    } else {
      // Code for no data received yet
    }

    return color;
  }

  public int translateFieldColor(int color) {
    switch (color) {
    case COLOR_BLUE:
      return COLOR_RED;
    case COLOR_GREEN:
      return COLOR_YELLOW;
    case COLOR_RED:
      return COLOR_BLUE;
    case COLOR_YELLOW:
      return COLOR_RED;
    default:
      return color;
    }
  }

  @Override
  public void initDefaultCommand() {
    setDefaultCommand(new RetractArm());
  }

  @Override
  public void periodic() {
    colorSensor.periodic();
  }
}