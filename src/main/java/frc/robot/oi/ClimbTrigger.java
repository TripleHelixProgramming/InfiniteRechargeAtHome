package frc.robot.oi;

import edu.wpi.first.wpilibj.buttons.Trigger;

public class ClimbTrigger extends Trigger {
    @Override
    public boolean get(){
        return OI.getOI().getClimberPower() > 0.8;
    }
}