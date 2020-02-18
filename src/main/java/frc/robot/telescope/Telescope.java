/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.telescope;

import edu.wpi.first.wpilibj.command.Subsystem;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANPIDController;
import com.revrobotics.ControlType;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

public class Telescope extends Subsystem {

    private static Telescope INSTANCE = null;

    // TO DO: Update solenoid IDs once robot is wired.
    public static int TELESCOPE_RAISE_ID= 5;
    public static int TELESCOPE_LOWER_ID= 4;

    // TO DO: Get CAN ID for the motor controller, see pinned #Software items for the 2020 Robot Worksheet.
    public static int TELESCOPE_MOTOR_ID = 17;

    // The solenoid responsible for the cylinder that controls the telescope deployment.
    private DoubleSolenoid solenoid = new DoubleSolenoid(TELESCOPE_RAISE_ID, TELESCOPE_LOWER_ID);

    // The controller for the motor that deploys the telescope.
    private CANSparkMax motor = new CANSparkMax(TELESCOPE_MOTOR_ID, MotorType.kBrushless);
    private final CANEncoder encoder;

    private CANPIDController pidController;
    public double kP, kI, kD, kIz, kFF, kMaxOutput, kMinOutput;

    public enum TelescopeState {
        DEPLOYED, STOWED, TRANSITION
      }

    private TelescopeState currentState;
    
    private Telescope() {
        super();

        // initialize motor
        motor.restoreFactoryDefaults();
        motor.setIdleMode(IdleMode.kBrake);
        motor.setSmartCurrentLimit(20);
        encoder = motor.getEncoder();

        pidController = motor.getPIDController();

        // PID coefficients
        kP = 0.1; // 6e-5;
        kI = 0.0;
        kD = 0.0;
        kIz = 0.0;
//        kFF = 0.00015;
        kFF = 0.0;
        kMaxOutput = 1;
        kMinOutput = -1;
        //currentRPM = 0;
        // set PID coefficients
        pidController.setP(kP);
        pidController.setI(kI);
        pidController.setD(kD);
        pidController.setIZone(kIz);
        pidController.setFF(kFF);
        pidController.setOutputRange(kMinOutput, kMaxOutput);

        currentState = TelescopeState.STOWED;
    }

    /**
     * @return the singleton instance of the telescope subsystem
     */
    public static Telescope getTelescope() {
        if (INSTANCE == null) {
            INSTANCE = new Telescope();
        }
        return INSTANCE;
    }

    public void setRotations(double rotations) {
        pidController.setReference(rotations, ControlType.kPosition);
        SmartDashboard.putNumber("Telescope SetPoint", rotations);
        SmartDashboard.putNumber("Telescope ProcessVariable", encoder.getPosition());
        SmartDashboard.putNumber("Telescope Difference", rotations - encoder.getPosition());
      }

    public double getPosition() {
        return encoder.getPosition();
      }

    public void setState(TelescopeState state){
        currentState = state;
    }

    // Tilts up the telescope.
    public void deploy() {
        solenoid.set(Value.kForward);
    }

    // Tilts down the telescope.
    public void stow() {
        solenoid.set(Value.kReverse);
    }

    // // Status of the telescope's extended state.
    // public boolean isDeployed() {
    //     return currentState == TelescopeState.DEPLOYED;
    // }
    
    // // Status of the telescope's stowed state.
    // public boolean isStowed() {
    //     return currentState == TelescopeState.STOWED;
    // }

    @Override
    public void initDefaultCommand() {
        //setDefaultCommand(new RetractIntake());
    }

    @Override
    public void periodic() {
    }
}