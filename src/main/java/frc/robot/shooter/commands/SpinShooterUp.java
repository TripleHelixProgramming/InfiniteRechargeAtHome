/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.shooter.commands;

import com.team2363.logger.HelixEvents;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.drivetrain.Drivetrain;
import frc.robot.oi.commands.RumbleController;
import frc.robot.shooter.Position;
import frc.robot.shooter.Shooter;
import frc.robot.shooter.Shooter.ShooterState;

public class SpinShooterUp extends Command {

  Position position;
  
  public double rpm = 0.0;
  public int hood_position = 0;

  private int rpmDelta;
  public double robot_rpm_boost;

  Command rumbleCommand = new RumbleController();

  // Spin up with a position.
  public SpinShooterUp(Position pos) {
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    requires(Shooter.getShooter());
    position = pos;
    Shooter.getShooter().setCurrentPosition(position);
  }

  // This will use the last position.
  public SpinShooterUp() {
    this(Shooter.getShooter().getCurrentPosition());
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    HelixEvents.getInstance().addEvent("SHOOTER", "SpinUpShooter for " + position.toString());

    Shooter.getShooter().setCurrentPosition(position);

    // Configure the controllers based on the name of the bot.
    String botName = frc.robot.Preferences.getPreferences().getRobotName();

    robot_rpm_boost = 0.0;
    // Compensate for bot2 shooting lower than bot1.
    if ("Bot1".equalsIgnoreCase(botName) == true) {
        robot_rpm_boost = 150.0;
    }

    // Get motor setpoint & expected rpm from position enum.
    rpm = position.getRPM() + robot_rpm_boost;
    hood_position = position.getHoodPosition();

    // Alter the rpm and bump_setpoint based on the number of ticks.
    rpmDelta = (int)(position.getBumpRPM() * Shooter.getShooter().getBumpTicks());
    rpm += rpmDelta;

    Shooter.getShooter().setHoodPosition(hood_position);
    Shooter.getShooter().setRPM(ShooterState.SHOOT, rpm);
    
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {

    //  if shooting from a unknown position. Use camera to get distance to
    //  target, then calculate the setpoint and expected rpms for that distance.
    if (position == Position.UNKNOWN) {
      rpm = Drivetrain.getDrivetrain().getFrontCamera().calculateRPM() + robot_rpm_boost;
      // Shooter.getShooter().setHoodPosition(Drivetrain.getDrivetrain().getFrontCamera().determineHoodPostion());
      Shooter.getShooter().setRPM(ShooterState.SHOOT, rpm);
    } 
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    // Was looking for a PID controller method on the SPARK MAX that tells us it is at
    // the setpoint, but could not find one, so doing it this way -   +/- a # of rpms
    if (position == Position.UNKNOWN) return false;
    HelixEvents.getInstance().addEvent("SHOOTER", "SpinUpShooter: isAtRPM");
    return (Shooter.getShooter().isAtRPM());
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
			if (!rumbleCommand.isRunning()) {
				rumbleCommand.start();
			}
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
  }
}
