/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.climber;

import edu.wpi.first.wpilibj.command.Subsystem;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

public class Climber extends Subsystem {

    private static Climber INSTANCE = null;

    // The solenoid responsible for extending the climber.
    // TODO: Make constants/enumerations for the solenoid IDs.
    private DoubleSolenoid solenoid = new DoubleSolenoid(0, 1);

    private Climber() {
        super();
    }

    /**
     * @return the singleton instance of the climber subsystem
     */
    public static Climber getClimber() {
        if (INSTANCE == null) {
            INSTANCE = new Climber();
        }
        return INSTANCE;
    }

    public void extend() {
        solenoid.set(Value.kForward);
    }

    public void retract() {
        solenoid.set(Value.kForward);
    }

    public boolean isExtended() {
        return solenoid.get() == Value.kForward;
    }

    public boolean isRetracted() {
        return solenoid.get() == Value.kReverse;
    }

    @Override
    public void initDefaultCommand() {
        
    }

    @Override
    public void periodic() {

    }

}