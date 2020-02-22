/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.command_groups;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.magazine.Magazine.BallHandlingState;
import frc.robot.magazine.commands.SetMagazineTo;
import frc.robot.shooter.commands.StopShooter;
import frc.robot.spacer.commands.SetSpacerTo;

public class SetBallHandlingCG extends CommandGroup {

  public SetBallHandlingCG(BallHandlingState state) {
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
    // Shooter is at required RPM for position. Start feeding balls in.
    addParallel(new SetSpacerTo(state));
    addParallel(new SetMagazineTo(state));
    // if (state == BallHandlingState.STOP) {
    //   addSequential(new StopShooter());
    // }
  }
}
