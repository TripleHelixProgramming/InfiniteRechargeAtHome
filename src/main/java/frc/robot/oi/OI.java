/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.oi;

import static com.team2363.utilities.ControllerMap.*;
import static com.team2363.utilities.ControllerPatroller.getPatroller;

import com.team2363.utilities.ControllerMap;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import frc.robot.drivetrain.commands.CameraInfo;
import frc.robot.drivetrain.commands.ManualVisionDriving;
import frc.robot.drivetrain.commands.RampDown;
import frc.robot.drivetrain.commands.VisionTakeOverGroup;
import frc.robot.drivetrain.commands.aimInPlace;
import frc.robot.intake.commands.DeployIntake;
import frc.robot.intake.commands.RetractIntake;
import frc.robot.magazine.commands.RunMagazine;
import frc.robot.spacer.commands.SpacerCommand;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 * 
 * Here is the list of button names
 * 
   //XboxOne Joysticks
    X_BOX_LEFT_STICK_X = 0;
    X_BOX_LEFT_STICK_Y = 1;
    X_BOX_LEFT_TRIGGER = 2;
    X_BOX_RIGHT_TRIGGER = 3;
    X_BOX_RIGHT_STICK_X = 4;
    X_BOX_RIGHT_STICK_Y = 5;
	
	//XboxOne Buttons
    X_BOX_A = 1;
    X_BOX_B = 2;
    X_BOX_X = 3;
    X_BOX_Y = 4;
    X_BOX_LB = 5;
    X_BOX_RB = 6;
    X_BOX_LOGO_LEFT = 7;
    X_BOX_LOGO_RIGHT = 8;
    X_BOX_LEFT_STICK_BUTTON = 9;
    X_BOX_RIGHT_STICK_BUTTON = 10;

	//PS4 joystick axis
    PS4_LEFT_STICK_X = 0;
    PS4_LEFT_STICK_Y = 1;
    PS4_RIGHT_STICK_X = 2;
    PS4_LEFT_TRIGGER = 3;
    PS4_RIGHT_TRIGGER = 4;
    PS4_RIGHT_STICK_Y = 5;
		
	//PS4 Buttons
    PS4_SQUARE = 1;
    PS4_X = 2;
    PS4_CIRCLE = 3;
    PS4_TRIANGLE = 4;
    PS4_L1 = 5;
    PS4_R1 = 6;
    PS4_L2 = 7;
    PS4_R2 = 8;
    PS4_SHARE = 9;
    PS4_OPTIONS = 10;
    PS4_L3 = 11;
    PS4_R3 = 12;
    PS4_PS = 13;
 * 
 */

public class OI {

  private static OI INSTANCE;

  /**
   * @return retrieves the singleton instance of the Operator Interface
   */
  public static OI getOI() {
    if (INSTANCE == null) {
      INSTANCE = new OI();
    }
    return INSTANCE;
  }

  private final String DRIVER = "Xbox";
  private final int DRIVER_PORT = 0;
  private final String OPERATOR = "P4";
  private final int OPERATOR_PORT = 1;

  private Joystick driver = getPatroller().get(DRIVER, DRIVER_PORT);
  private Joystick operator = getPatroller().get(OPERATOR, OPERATOR_PORT);

  private OI() { 
   // new JoystickButton(driver, 3).whileHeld(new CameraInfo());
   // new JoystickButton(driver, 2).whileHeld(new aimInPlace());

    new JoystickButton(driver, ControllerMap.X_BOX_A).toggleWhenPressed(new DeployIntake());
    
    new JoystickButton(driver, ControllerMap.X_BOX_X).toggleWhenPressed(new SpacerCommand());
    new JoystickButton(driver, ControllerMap.X_BOX_Y).toggleWhenPressed(new RunMagazine());

  }

  /**
   * @return the raw controller throttle
   */
  public double getThrottle() {
    return driver.getRawAxis(X_BOX_LEFT_STICK_Y); 
	}
  
  /**
   * @return the raw controller turn
   */
  public double getTurn() {
    return driver.getRawAxis(X_BOX_RIGHT_STICK_X);
  }
  
  /**
	 * Turns on and off the rumble function on the driver and operator controllers
	 * @param set true to turn on rumble
	 */
	public void setControllerRumble(boolean rumble) {
		if (rumble) {
			setRumble(driver, 1);
			setRumble(operator, 1);
		} else {
			setRumble(driver, 0);
			setRumble(operator, 0);
		}
  }
  
  private void setRumble(Joystick controller, int state) {
    controller.setRumble(RumbleType.kLeftRumble, state);
		controller.setRumble(RumbleType.kRightRumble, state);
  }

  //Get Climber power from operator controller right joystick y-axis
	public double getClimberPower() {
		double stick = -operator.getRawAxis( PS4_RIGHT_STICK_Y);
		stick *= Math.abs(stick);
		if (Math.abs(stick) < 0.05) {
			stick = 0;
		}
		return stick;
	}
}