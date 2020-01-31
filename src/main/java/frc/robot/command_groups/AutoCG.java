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
import frc.robot.magazine.Magazine.BallHandlingState;
import frc.robot.shooter.Position;
import frc.robot.magazine.commands.ResetBallCount;
import frc.robot.shooter.commands.SpinShooterUp;
import frc.robot.shooter.commands.StopShooter;

public class AutoCG extends CommandGroup {
  /**
   * Add your docs here.
   */

  // Add Commands here:
  // e.g. addSequential(new Command1());
  // addSequential(new Command2());
  // these will run in order.

  // To run multiple commands at the same time,
  // use addParallel()
  // e.g. addParallel(new Command1());
  // addSequential(new Command2());
  // Command1 and Command2 will run in parallel.

  // A command group will require all of the subsystems that each member
  // would require.
  // e.g. if Command1 requires chassis, and Command2 requires arm,
  // a CommandGroup containing them would require both the chassis and the
  // arm.

  public AutoCG(Path path){
    addSequential(new PathFollower(path));
  }
  
  public AutoCG(Path path, Position pos, double pigeon_offset, double delay) {
    this(path, pos, pigeon_offset, delay, null);
  }

  public AutoCG(Path path, Position pos, double pigeon_offset, double delay, Path phase2) {
    addSequential(new aimInPlace());
    addSequential(new SpinShooterUp(pos));
    addParallel(new SetBallHandlingCG(BallHandlingState.SHOOT));

    // Wait for time that it takes 5 balls to be shot
    addSequential(new WaitCommand(4.0));
    // Reset ball count back to zero
    addSequential(new ResetBallCount());
    // Stop the shooter
    addSequential(new StopShooter());

    // Re-orient Pigeon using pigeon offset for position.  Run path to get more balls
    // and deploy the Intake delay seconds along the path.

    addParallel(new IntakeDeployCG(delay));
    addSequential(new PathFollower(path));
    addParallel(new StopIntakeCG());

    // Do we need a wait at the end of one path before starting the next??
    // addSequential(new WaitCommand(4.0));

    // We have balls now, so run path back to shooting position.
    if (phase2 != null) {
      addSequential(new PathFollower(phase2));
    }

    //  Turn to angle for position.
    addSequential(new aimInPlace());
    addSequential(new SpinShooterUp(pos));
    addParallel(new SetBallHandlingCG(BallHandlingState.SHOOT));

    // Wait for time that it takes 5 balls to be shot
    addSequential(new WaitCommand(4.0));
    // Reset ball count back to zero
    addSequential(new ResetBallCount());
    // Stop feeding balls
    addParallel(new SetBallHandlingCG(BallHandlingState.STOP));
    // Stop the shooter
    addSequential(new StopShooter());
  }

  private class IntakeDeployCG extends CommandGroup {
    public IntakeDeployCG(final double delay) {
      addSequential(new WaitCommand(delay));
      addSequential(new StartIntakeCG());
    }
  }
}
