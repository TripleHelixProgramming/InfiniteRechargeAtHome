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
import frc.robot.command_groups.AimAndSpinCG;
import frc.robot.command_groups.SetBallHandlingCG;
import frc.robot.command_groups.StartIntakeCG;
import frc.robot.command_groups.ClimbCG;
import frc.robot.drivetrain.commands.CameraInfo;
import frc.robot.drivetrain.commands.ManualVisionDriving;
import frc.robot.drivetrain.commands.RampDown;
import frc.robot.drivetrain.commands.VisionTakeOverGroup;
import frc.robot.drivetrain.commands.aimInPlace;

import frc.robot.magazine.Magazine.BallHandlingState;
import frc.robot.shooter.Position;
import frc.robot.shooter.Shooter;
import frc.robot.shooter.commands.BumpShooter;
import frc.robot.shooter.commands.SpinShooterUp;
import edu.wpi.first.wpilibj.buttons.Button;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
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
    
    // Starts the Intake and Ball Handling for intake.  When toggled off the command is taken
    // off the scheduler. Then the default command for the intake subsystem starts which is 
    // RetractIntake(), which pulls the intake in and stops the rollers.
    new JoystickButton(operator, ControllerMap.PS4_R1).toggleWhenPressed(new StartIntakeCG(true));
    new JoystickButton(operator, ControllerMap.PS4_L1).toggleWhenPressed(new StartIntakeCG(false));

    // All SpinUpShooter() commands should rumble the controller when shooter is at speed.
    new JoystickButton(operator, ControllerMap.PS4_SQUARE).whenPressed(new SpinShooterUp(Position.DUMP_BALLS));
    new JoystickButton(operator, ControllerMap.PS4_CIRCLE).whenPressed(new SpinShooterUp(Position.TRENCH));
    new JoystickButton(operator, ControllerMap.PS4_TRIANGLE).whenPressed(new SpinShooterUp(Position.LAYUP));
    new JoystickButton(operator, ControllerMap.PS4_X).whenPressed(new AimAndSpinCG());

    // Aiming is on a whileHeld reft button
    new JoystickButton(driver, ControllerMap.X_BOX_LB).whileHeld(new aimInPlace());

    // Shooting is on a whenPressed / whenReleased right button
    new JoystickButton(driver, ControllerMap.X_BOX_RB).whenPressed(new SetBallHandlingCG(BallHandlingState.SHOOT));
    new JoystickButton(driver, ControllerMap.X_BOX_RB).whenReleased(new SetBallHandlingCG(BallHandlingState.STOP));
    // new JoystickButton(driver, ControllerMap.X_BOX_RB).whenPressed(new SetBallHandlingCG(BallHandlingState.SHOOT_ONE));
    // new JoystickButton(driver, ControllerMap.X_BOX_RB).whenReleased(new SetBallHandlingCG(BallHandlingState.ADVANCE));
    // new JoystickButton(driver, ControllerMap.X_BOX_A).whenReleased(new SetBallHandlingCG(BallHandlingState.STOP));

    // new ClimbTrigger().whenActive(new ClimbCG());

    // Bumping up and down

  //   new Button() {

  //   @Override
  //   public boolean get() {
  //     return (operator.getPOV() == 0);
  //   }
  // }.whenPressed(new BumpShooter(Shooter.BUMP_UP));

  // new Button() {

  // @Override
  // public boolean get() {
  //   return (operator.getPOV() == 180);
  // }
  // }.whenPressed(new BumpShooter(Shooter.BUMP_DOWN));
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
   * 
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

  // Get Climber power from operator controller right joystick y-axis
  public double getClimberPower() {
    double stick = -operator.getRawAxis(PS4_RIGHT_STICK_Y);
    // stick *= Math.abs(stick);
    // if (Math.abs(stick) < 0.05) {
    //   stick = 0;
    // }
    return stick;
  }
}