/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.command_groups;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;
import com.team319.trajectory.Path;
import frc.robot.drivetrain.commands.PathFollower;
import frc.robot.drivetrain.commands.aimInPlace;
import frc.robot.intake.commands.RetractIntake;
import frc.robot.magazine.Magazine.BallHandlingState;
import frc.robot.shooter.Position;
import frc.robot.magazine.commands.ResetBallCount;
import frc.robot.shooter.commands.SpinShooterUp;
import frc.robot.shooter.commands.StopShooter;

public class AutoCG extends CommandGroup {
  // addSequential(new Command1());
  // addSequential(new Command2());
  // The commands above will run in order.

  // addParallel(new Command1());
  // addSequential(new Command2());
  // Command1 and Command2 will run in parallel.

  // A command group will require all of the subsystems that each command
  // would require.

  //  Auto Command Group to just run a path and that's it.
  public AutoCG(Path path){
    addSequential(new PathFollower(path));
  }
  
  // Auto command group that shoots, then runs a path while collecting balls
  // but does not run a path back to shoot the balls.
  public AutoCG(Position pos, double pigeon_offset, double delay, Path path) {
    this(pos, pigeon_offset, delay, path, null);
  }

  //  Auto command group that shoots from a known positions, runs a path
  //  to collect balls, reverses its path back to known position and 
  //  shoots again.
  public AutoCG(Position pos, double pigeon_offset, double delay, Path path,  Path phase2) {
    
    // Runs command group to shoot from a know position.
    addSequential(new ShootCG(pos));

    // Re-orient the robot before running path.  Run path to get more balls
    // and deploy the Intake delay seconds along the path.
    // AddSequential(new TurnToAngle(??));
    addParallel(new IntakeDeployCG(delay));
    addSequential(new PathFollower(path));
    addParallel(new RetractIntake());

    // Do we need a wait here before reversing??
    // addSequential(new WaitCommand(??)); 

    // Run path back to shooting position, if 2nd path is not null.
    if (phase2 == null) {
      // Just reverse the first path.
      addSequential(new PathFollower(path).reverse());
    } else {
      // Run a different path as phase 2.
      addSequential(new PathFollower(phase2));
    }

    // Adjust angle & Shoot the balls we just picked up.
    // AddSequential(new TurnToAngle(??));
    addSequential(new ShootCG(pos));

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
      // Center on target --  this may need a timeout?
      addSequential(new aimInPlace());  
      // Spin shooter up to the expected rpms for that position.
      addSequential(new SpinShooterUp(pos));
      // Once shooter is at expected rpms, then start the magazine to 
      // feed bals in.
      addParallel(new SetBallHandlingCG(BallHandlingState.SHOOT));
      // Wait for time that it takes 5 balls to be shot
      addSequential(new WaitCommand(4.0));
      // Reset ball count back to zero
      addSequential(new ResetBallCount());
      // Stop the shooter
      addSequential(new StopShooter());
    }
  }
}
