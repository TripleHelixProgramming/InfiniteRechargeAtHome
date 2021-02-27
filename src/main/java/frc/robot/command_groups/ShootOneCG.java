/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.command_groups;

import com.team2363.logger.HelixEvents;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.magazine.Magazine;
import frc.robot.spacer.Spacer;
import frc.robot.magazine.commands.RunMagazine;
import frc.robot.spacer.commands.RunSpacer;
import frc.robot.spacer.commands.StopSpacer;

public class ShootOneCG extends CommandGroup {

  public ShootOneCG() {

    HelixEvents.getInstance().addEvent("MAGAZINE", "Shoot One CG");

    addParallel(new AddBallInverted());
    addSequential(new ShootOne(), 1.5);
    addSequential(new StopShootingCG());
  }

  class AddBallInverted extends CommandGroup {
    public AddBallInverted() {
      addSequential(new StopSpacerUntilBeamCleared());
      addSequential(new StopSpacer(), 0.1);
      addSequential(new RunSpacerUntilBeamBroken());
      addSequential(new RunSpacer(), 0.3);
    }

    @Override
    protected void end() {
      nextCommand().start();
    }
  
    class StopSpacerUntilBeamCleared extends StopSpacer {
      @Override
      protected boolean isFinished() {
        return !Spacer.getSpacer().ballAtSpacer();
      }
    }

    class RunSpacerUntilBeamBroken extends RunSpacer {
      @Override
      protected boolean isFinished() {
        return Spacer.getSpacer().ballAtSpacer();
      }
    }

    protected Command nextCommand() {
      return new AddBallInverted();
    }
  }

  class ShootOne extends CommandGroup {
    public ShootOne() {
      addSequential(new RunMagazineUntilBeamBroken());
      addSequential(new RunMagazineUntilBeamCleared());
      addSequential(new RunMagazineUntilBeamBroken());
    }

    class RunMagazineUntilBeamBroken extends RunMagazine {
      @Override
      protected boolean isFinished() {
        return Magazine.getMagazine().ballAtShooter();
      }
    }

    class RunMagazineUntilBeamCleared extends RunMagazine {
      @Override
      protected boolean isFinished() {
        return !Magazine.getMagazine().ballAtShooter();
      }
    }
  }
}