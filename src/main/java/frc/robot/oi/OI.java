
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
import edu.wpi.first.wpilibj.buttons.Trigger;
import frc.robot.command_groups.SetBallHandlingCG;
import frc.robot.command_groups.StartIntakeCG;
import frc.robot.command_groups.StopIntakeCG;
import frc.paths.bluezone;
import frc.paths.goback;
import frc.paths.yellowzone;
import frc.robot.command_groups.AimSpin;
import frc.robot.command_groups.ClimbCG;
import frc.robot.command_groups.Close;
import frc.robot.command_groups.Far;
import frc.robot.command_groups.LayUpCG;
import frc.robot.command_groups.SecondClose;
import frc.robot.command_groups.SecondFar;
import frc.robot.drivetrain.commands.CarsonDrive;
import frc.robot.drivetrain.commands.visionAim;
import frc.robot.flashlight.commands.flashlightOff;
import frc.robot.flashlight.commands.flashlightOn;
import frc.robot.intake.Intake;
import frc.robot.intake.commands.RetractIntake;
import frc.robot.intake.commands.ReverseIntake;
import frc.robot.magazine.Magazine.BallHandlingState;
import frc.robot.magazine.commands.SetMagazineTo;
import frc.robot.magazine.commands.ShootOne;
import frc.robot.shooter.Position;
import frc.robot.shooter.Shooter;
import frc.robot.shooter.commands.BumpShooter;
import frc.robot.shooter.commands.HoodGoDown;
import frc.robot.shooter.commands.HoodGoUp;
import frc.robot.shooter.commands.SpinShooterUp;
import frc.robot.shooter.commands.StopShooter;
import frc.robot.shooter.commands.setRealRPM;
import frc.robot.spacer.commands.SetSpacerTo;
import frc.robot.telescope.commands.StowTelescope;
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
  private final String OPERATOR = "P";
  private final int OPERATOR_PORT = 1;

  private Joystick driver = getPatroller().get(DRIVER, DRIVER_PORT);
  private Joystick operator = getPatroller().get(OPERATOR, OPERATOR_PORT);

  private OI() {

    // Intake buttons - Right trigger activates intake, left trigger retractes and
    // disables
    new JoystickButton(operator, ControllerMap.PS4_R1).whenPressed(new StartIntakeCG(true));
    new JoystickButton(operator, ControllerMap.PS4_L1).whenReleased(new StopIntakeCG());

    // Set shooter speeds - Triangle farthest zone from goal, circle third farthest,
    // x second closest, square closest
    new JoystickButton(operator, ControllerMap.PS4_TRIANGLE).whenPressed(new Far());
    new JoystickButton(operator, ControllerMap.PS4_TRIANGLE).whenReleased(new StopShooter());

    new JoystickButton(operator, ControllerMap.PS4_CIRCLE).whenPressed(new SecondFar());
    new JoystickButton(operator, ControllerMap.PS4_CIRCLE).whenReleased(new StopShooter());

    new JoystickButton(operator, ControllerMap.PS4_X).whenPressed(new SecondClose());
    new JoystickButton(operator, ControllerMap.PS4_X).whenReleased(new StopShooter());

    new JoystickButton(operator, ControllerMap.PS4_SQUARE).whenPressed(new Close());
    new JoystickButton(operator, ControllerMap.PS4_SQUARE).whenReleased(new StopShooter());

    new JoystickButton(driver, ControllerMap.X_BOX_Y).whenPressed(new LayUpCG(new goback()));
    new JoystickButton(driver, ControllerMap.X_BOX_B).whenPressed(new LayUpCG(new bluezone()));
    // new JoystickButton(driver, ControllerMap.X_BOX_A).whenPressed(new LayUpCG(new yellowzone()));

    new JoystickButton(operator, ControllerMap.PS4_OPTIONS).whenPressed(new HoodGoUp());
    new JoystickButton(operator, ControllerMap.PS4_SHARE).whenPressed(new HoodGoDown());

    // Aiming is on a whileHeld reft button

    // Shooting is on a whenPressed / whenReleased right button
    new JoystickButton(driver, ControllerMap.X_BOX_RB).whenPressed(new SetBallHandlingCG(BallHandlingState.SHOOT));
    new JoystickButton(driver, ControllerMap.X_BOX_RB).whenReleased(new SetBallHandlingCG(BallHandlingState.INTAKE));

    new JoystickButton(driver, ControllerMap.X_BOX_LB).whenPressed(new SecondFar());
    new JoystickButton(driver, ControllerMap.X_BOX_LB).whenReleased(new StopShooter());

    // new JoystickButton(operator, ControllerMap.PS4_CIRCLE).whenPressed(new StartIntakeCG(true));

    new JoystickButton(driver, ControllerMap.X_BOX_A).whenPressed(new flashlightOff());
    new JoystickButton(driver, ControllerMap.X_BOX_A).whenReleased(new flashlightOn());

    new JoystickButton(driver, ControllerMap.X_BOX_X).whileHeld(new visionAim());

    // new JoystickButton(driver, ControllerMap.X_BOX_X).whenPressed(new SetBallHandlingCG(BallHandlingState.SHOOT));
    // new JoystickButton(driver, ControllerMap.X_BOX_X).whenReleased(new SetBallHandlingCG(BallHandlingState.STOP));



    new CTrigger().whenActive(new ClimbCG());

    // Bumping up and down  
    new Button() {
        @Override
        public boolean get() {
          return (driver.getPOV() == 0);
        }
    }.whenPressed(new BumpShooter(Shooter.BUMP_UP));

    new Button() {
      @Override
      public boolean get() {
        return (driver.getPOV() == 180);
      }
    }.whenPressed(new BumpShooter(Shooter.BUMP_DOWN));
}

  /**
   * @return the raw controller throttle
   */
  public double getThrottle() {
    return driver.getRawAxis(X_BOX_LEFT_STICK_Y);
  }

  public boolean getRightTrigger() {
    return driver.getRawAxis(ControllerMap.X_BOX_RIGHT_TRIGGER) > 0.5;
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
    double stick = -operator.getRawAxis(PS4_LEFT_STICK_Y);
    // stick *= Math.abs(stick);
    // if (Math.abs(stick) < 0.05) {
    //   stick = 0;
    // }
    return stick;
  }

  class CTrigger extends Trigger {
    @Override
    public boolean get(){
      return (operator.getRawButton(ControllerMap.PS4_PS) && (getClimberPower() > 0.8));    
    }
  }

  class RightTriggerButton extends Trigger {
    @Override
    public boolean get(){
      return (driver.getRawAxis(ControllerMap.X_BOX_RIGHT_TRIGGER) > 0.8);    
    }
  }

}