/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.intake;

import edu.wpi.first.wpilibj.command.Subsystem;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

public class Intake extends Subsystem {

    private static Intake INSTANCE = null;

    // TO DO: Update solenoid IDs once robot is wired.
    public static int INTAKE_RETRACT_ID= 4;
    public static int INTAKE_DEPLOY_ID= 5;

   // TO DO: Get CAN ID for the victor, see pinned #Software items for the 2020 Robot Worksheet.
    public static int INTAKE_MOTOR_ID = 21;

    // The solenoid responsible for the cylinder that controls the intake arm.
    private DoubleSolenoid solenoid = new DoubleSolenoid(INTAKE_DEPLOY_ID, INTAKE_RETRACT_ID);

    // The controller for the motor that spins the intake roller.
    private VictorSPX motor = new VictorSPX(INTAKE_MOTOR_ID);

    private Intake() {
        super();
    }

    /**
     * @return the singleton instance of the intake subsystem
     */
    public static Intake getIntake() {
        if (INSTANCE == null) {
            INSTANCE = new Intake();
        }
        return INSTANCE;
    }

    // Spins the intake roller to bring balls/cells into the robot.
    public void rollerIn() {
        motor.set(ControlMode.PercentOutput, -.5);
    }

    // Spins the intake roller to eject balls/cells from the robot.
    public void rollerOut() {
        motor.set(ControlMode.PercentOutput, .5);
    }

    // Stops the intake roller.
    public void rollerOff() {
        motor.set(ControlMode.PercentOutput, 0.0);
    }

    // Extends the intake arm/platform.
    public void extend() {
        solenoid.set(Value.kForward);
    }

    // Retracts the intake arm/platform.
    public void retract() {
        solenoid.set(Value.kForward);
    }

    // Status of the intake arm/platform's extended state.
    public boolean isExtended() {
        return solenoid.get() == Value.kForward;
    }

    // Status of the intake arm/platform's retracted state.
    public boolean isRetracted() {
        return solenoid.get() == Value.kReverse;
    }

    @Override
    public void initDefaultCommand() {
        // setDefaultCommand(new SampleDrive());
    }

    @Override
    public void periodic() {

    }

}