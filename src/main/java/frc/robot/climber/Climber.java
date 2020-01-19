/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.climber;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.climber.commands.Climb;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

/**
TODO:  Verify the hardware devices that make up this subsystem before running. Make sure
code reflects the devices & actions.  Once done, uncomment the default command.

**/

public class Climber extends Subsystem {

    private static Climber INSTANCE = null;

    // Solenoid ids for raising / lowering & extending / retracting
    public static int CLIMBER_RAISE_SOLENOID= 0;
    public static int CLIMBER_LOWER_SOLENOID= 1;
    public static int CLIMBER_EXTEND_SOLENOID= 2;
    public static int CLIMBER_RETRACT_SOLENOID= 3;

    public static int CLIMBER_MASTER_ID= 14;
    public static int CLIMBER_SLAVE_ID= 15;

    // 2 motors will be master / slave for the wench mechanism
    private TalonSRX master = new TalonSRX(CLIMBER_MASTER_ID);
	private TalonSRX slave = new TalonSRX(CLIMBER_SLAVE_ID);

    // The solenoids responsible for raising & extending the climber.
    private DoubleSolenoid raiseSolenoid = new DoubleSolenoid(CLIMBER_RAISE_SOLENOID, CLIMBER_LOWER_SOLENOID);
    private DoubleSolenoid extendSolenoid = new DoubleSolenoid(CLIMBER_EXTEND_SOLENOID, CLIMBER_RETRACT_SOLENOID);

    private Climber() {
        super();
        setUpMotors();
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

    private void setUpMotors() {

        slave.follow(master);
		master.setInverted(true);
		slave.setInverted(false);
    }

    // Extend climber hook delivery mechanism upward
    public void extend() {
        extendSolenoid.set(Value.kForward);
    }

    // Retract climber hook delivery mechanism downward
    public void retract() {
        extendSolenoid.set(Value.kReverse);
    }

    // Is hook delivery mechanism extended
    public boolean isExtended() {
        return extendSolenoid.get() == Value.kForward;
    }

    // Is hook delivery mechanism retracted
    public boolean isRetracted() {
        return extendSolenoid.get() == Value.kReverse;
    }

    // Raise the climber mechanism from horizontal to vertical position
    public void raise() {
        raiseSolenoid.set(Value.kForward);
    }

    // Lower the climber mechanism to horizontal position
    public void lower() {
        raiseSolenoid.set(Value.kForward);
    }

    // Is climber mechanism vertical
    public boolean isRaised() {
        return raiseSolenoid.get() == Value.kForward;
    }

    // Is climber mechanism horizontal
    public boolean isLowered() {
        return raiseSolenoid.get() == Value.kReverse;
    }

    // Run the wench motor at specified power
    public void setPower(double power) {
	    master.set(ControlMode.PercentOutput, power);
    }

    @Override
    public void initDefaultCommand() {
        // setDefaultCommand(new Climb());
    }
}