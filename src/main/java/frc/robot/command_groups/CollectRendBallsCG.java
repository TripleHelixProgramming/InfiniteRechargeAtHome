/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.command_groups;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.paths.CollectRendBalls;
import frc.robot.drivetrain.commands.AutoAimInPlace;
import frc.robot.drivetrain.commands.PathFollower;
import frc.robot.drivetrain.commands.StopDrivetrain;
import frc.robot.drivetrain.commands.TurnToAngle;
import frc.robot.magazine.Magazine.BallHandlingState;

public class CollectRendBallsCG extends CommandGroup {
  /**
   * Add your docs here.
   */
  public CollectRendBallsCG() {

    // addParallel(new StartIntakeCG(true),4);
    addSequential(new PathFollower(new CollectRendBalls()).reverse());
    // addParallel(new SetBallHandlingCG(BallHandlingState.INTAKE),2);
    // addSequential(new PathFollower(new CollectRendBalls()));
    // addSequential(new TurnToAngle(-10.0));
    // addSequential(new AutoAimInPlace());
    addSequential(new StopDrivetrain());
    // addSequential(new SetBallHandlingCG(BallHandlingState.SHOOT), 4.0);
    // addSequential(new SetBallHandlingCG(BallHandlingState.STOP), 1.0);
  }
}
