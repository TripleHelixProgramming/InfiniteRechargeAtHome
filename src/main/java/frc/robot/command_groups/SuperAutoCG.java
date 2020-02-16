/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.command_groups;

import com.team319.trajectory.Path;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;
import frc.robot.drivetrain.commands.PathFollower;
import frc.robot.intake.commands.RetractIntake;
import frc.robot.magazine.Magazine.BallHandlingState;
import frc.robot.magazine.commands.ResetBallCount;
import frc.robot.shooter.Position;
import frc.robot.shooter.commands.SpinShooterUp;
import frc.robot.shooter.commands.StopShooter;

public class SuperAutoCG extends CommandGroup {

  //  Auto command group that shoots from a known positions, runs a path
  //  to collect balls, reverses its path back to known position and 
  //  shoots again.
  public SuperAutoCG(Position pos1, Position pos2, double delay1, double delay2, Path path1,  Path path2, Path path3, Path path4) {

    addParallel(new IntakeDeployCG(delay1));
    addSequential(new PathFollower(path1));
    addParallel(new RetractIntake());

    addSequential(new PathFollower(path2));

    // Runs command group to shoot from a know position.
    addSequential(new ShootCG(pos1));

    addParallel(new IntakeDeployCG(delay2));
    addSequential(new PathFollower(path3));
    addParallel(new RetractIntake());

    addSequential(new PathFollower(path4));

    // Runs command group to shoot from a know position.
    addSequential(new ShootCG(pos2)); 

    // Stop the ball handling system - magazine & spacer
    addSequential(new SetBallHandlingCG(BallHandlingState.STOP));
  }

  // Deploys & runs the intake after "delay" seconds have passed.
  private class IntakeDeployCG extends CommandGroup {
    public IntakeDeployCG(final double delay) {
      addSequential(new WaitCommand(delay));
      addSequential(new StartIntakeCG(true));
    }
  }

  private class ShootCG extends CommandGroup {
    public ShootCG(Position pos) {

      // Center on target --  with a timeout
      // addSequential(new aimInPlace(), 1.0);  
      
      // Spin shooter up to the expected rpms for that position.
      addSequential(new SpinShooterUp(pos));

      // Once shooter is at expected rpms, then start the magazine to 
      // feed bals in.
      addSequential(new SetBallHandlingCG(BallHandlingState.SHOOT), 4.0);

      // Reset ball count back to zero
      addSequential(new ResetBallCount());

      // Stop the shooter
      addSequential(new StopShooter());
    }
  }
}
