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
import frc.robot.drivetrain.commands.StopDrivetrain;
import frc.robot.drivetrain.commands.TurnToAngle;
import frc.paths.GhostBiggie;
import frc.robot.drivetrain.Drivetrain;
import frc.robot.drivetrain.commands.AimInPlace;
import frc.robot.drivetrain.commands.AutoAimInPlace;
import frc.robot.intake.commands.DeployIntake;
import frc.robot.intake.commands.RetractIntake;
import frc.robot.magazine.Magazine.BallHandlingState;
import frc.robot.shooter.Position;
import frc.robot.magazine.commands.ResetBallCount;
import frc.robot.shooter.commands.SpinShooterUp;
import frc.robot.shooter.commands.StopShooter;

public class AutoCG extends CommandGroup {

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
    // addSequential(new ShootCG(pos));

    // Re-orient the robot before running path.  Run path to get more balls
    // and deploy the Intake delay seconds along the path.
    // AddSequential(new TurnToAngle(??));
    addParallel(new StartIntakeCG(true), 4); //added.25sp
    addParallel(new SpinShooterUp(pos));
    addSequential(new PathFollower(path).reverse());
  
    // Do we need a wait here before reversing??
    addSequential(new TurnToAngle(15));
    // addSequential(new AutoAimInPlace());
    addParallel(new StopDrivetrain(), 1);
    addSequential(new SetBallHandlingCG(BallHandlingState.SHOOT), 2.75); //added .25s
    addSequential(new TurnToAngle(0));

    // Run path back to shooting position, if 2nd path is not null.
    addParallel(new SetBallHandlingCG(BallHandlingState.INTAKE), 2);
    addSequential(new PathFollower(phase2).reverse());
    
    addParallel(new SpinShooterUp(pos));
    addParallel(new SetBallHandlingCG(BallHandlingState.INTAKE), 2);
    addSequential(new PathFollower(new GhostBiggie()));
    addParallel(new RetractIntake());
    addSequential(new StopDrivetrain());
    addSequential(new SetBallHandlingCG(BallHandlingState.SHOOT), 4);

    // Adjust angle & Shoot the balls we just picked up.
    // addSequential(new TurnToAngle(-30));
    // addSequential(new StopDrivetrain());
    // addSequential(new ShootCG(pos));

    // Stop the ball handling system - magazine & spacer
    // addSequential(new SetBallHandlingCG(BallHandlingState.STOP));
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
      // addSequential(new AimInPlace());  
      // Spin shooter up to the expected rpms for that position.
      addSequential(new SpinShooterUp(pos));

      // Once shooter is at expected rpms, then start the magazine to 
      // feed bals in.
      addSequential(new SetBallHandlingCG(BallHandlingState.SHOOT), 4.0);

      // Reset ball count back to zero
      addParallel(new ResetBallCount());

      // Stop the shooter
      addSequential(new StopShooter());
    }
  }
}
