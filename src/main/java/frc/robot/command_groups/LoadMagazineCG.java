/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.command_groups;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.spacer.Spacer;
import frc.robot.magazine.Magazine;
import frc.robot.spacer.commands.RunSpacer;
import frc.robot.spacer.commands.StopSpacer;
import frc.robot.magazine.commands.RunMagazine;
import frc.robot.magazine.commands.StopMagazine;

public class LoadMagazineCG extends CommandGroup {

  public LoadMagazineCG() {
    addSequential(new RunSpacerUntilBeamBroken());
    addSequential(new AddBall());
    addSequential(new RunMagazineMore(), 0.05);
    addSequential(new StopMagazine());
  }

  @Override
  protected void end() {
    if (!Magazine.getMagazine().ballAtShooter()) {
      nextCommand().start();
    }
  }

  class AddBall extends CommandGroup {
    public AddBall() {
      addParallel(new RunMagazineUntilBeamCleared());
      addSequential(new RunSpacerMore(), 0.3);
      addSequential(new StopSpacer());
    }

    class RunMagazineUntilBeamCleared extends RunMagazine {
      @Override
      protected boolean isFinished() {
        return !Spacer.getSpacer().ballAtSpacer() || Magazine.getMagazine().ballAtShooter();
      }
    }
  
    class RunSpacerMore extends RunSpacer {
      @Override
      protected boolean isFinished() {
        return Magazine.getMagazine().ballAtShooter();
      }
    }
  }

  class RunSpacerUntilBeamBroken extends RunSpacer {
    @Override
    protected boolean isFinished() {
      return Spacer.getSpacer().ballAtSpacer() || Magazine.getMagazine().ballAtShooter();
    }
  }

  class RunMagazineMore extends RunMagazine {
    @Override
    protected boolean isFinished() {
      return Magazine.getMagazine().ballAtShooter();
    }
  }

  protected Command nextCommand() {
    return new LoadMagazineCG();
  }
}