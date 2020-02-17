/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.shooter.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.drivetrain.Drivetrain;
import frc.robot.oi.commands.RumbleController;
import frc.robot.shooter.Position;
import frc.robot.shooter.Shooter;
import frc.robot.shooter.Shooter.ShooterState;

public class SpinShooterUp extends Command {

  Position position;
  
  public int rpm = 0;
  public int hood_position = 0;

  private int rpmDelta;

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

    Shooter.getShooter().setCurrentPosition(position);

    // Get motor setpoint & expected rpm from position enum.
    rpm = position.getRPM();
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
      rpmDelta = (int)(position.getBumpRPM() * Shooter.getShooter().getBumpTicks());
      rpm = Drivetrain.getDrivetrain().getFrontCamera().calculateRPM() + rpmDelta;
      Shooter.getShooter().setRPM(ShooterState.SHOOT, rpm);
    } 
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    // Was looking for a PID controller method on the SPARK MAX that tells us it is at
    // the setpoint, but could not find one, so doing it this way -   +/- a # of rpms
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
