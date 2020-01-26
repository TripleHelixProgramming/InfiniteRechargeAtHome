/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.shooter;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.ControlType;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

import frc.robot.shooter.commands.StopShooter;
import frc.robot.shooter.commands.TestWithController;

public class Shooter extends Subsystem {

    private static Shooter INSTANCE = new Shooter();

    /**
     * @return the singleton instance of the Drivetrain subsystem
     */
    public static Shooter getShooter() {
      if (INSTANCE == null) {
        INSTANCE = new Shooter();
      }
      return INSTANCE;
    }

    // Solenoid ids for hood position
    public static int HOOD_NEAR_SOLENOID= 12;
    public static int HOOD_FAR_SOLENOID= 13;    // Solenoid extended = far
    private static final int SHOOTER_ID = 1;

    public double MAX_RPM = 5700;

    private final CANSparkMax motor;
    private final CANPIDController pidController;
    private final CANEncoder encoder;
    public double kP, kI, kD, kIz, kFF, kMaxOutput, kMinOutput;
    public double setpoint = 0.0;
    public int ballCount = 0;
    
    // The solenoids responsible for raising & extending the climber.
    private DoubleSolenoid hood = new DoubleSolenoid(HOOD_NEAR_SOLENOID, HOOD_FAR_SOLENOID);
    
    public Shooter() {

        // initialize motor
        motor = new CANSparkMax(SHOOTER_ID, MotorType.kBrushless);
        motor.restoreFactoryDefaults();
        pidController = motor.getPIDController();
        encoder = motor.getEncoder();

        // PID coefficients
        kP = 10e-7;
        kI = 0.0;
        kD = 0.0;
        kIz = 0.0;
        kFF = 1;
        kMaxOutput = 1;
        kMinOutput = -1;
        setpoint = 0.0;  // .55 is setpoint with the above PIDF to shoot from the trench

        // set PID coefficients
        pidController.setP(kP);
        pidController.setI(kI);
        pidController.setD(kD);
        pidController.setIZone(kIz);
        pidController.setFF(kFF);
        pidController.setOutputRange(kMinOutput, kMaxOutput);

        // display PID coefficients on SmartDashboard
        SmartDashboard.putNumber("P Gain", kP);
        SmartDashboard.putNumber("I Gain", kI);
        SmartDashboard.putNumber("D Gain", kD);
        SmartDashboard.putNumber("I Zone", kIz);
        SmartDashboard.putNumber("Feed Forward", kFF);
        SmartDashboard.putNumber("Max Output", kMaxOutput);
        SmartDashboard.putNumber("Min Output", kMinOutput);
        SmartDashboard.putNumber("Set Point", setpoint);
    }

    @Override
    public void periodic() {
        
        // read PID coefficients from SmartDashboard
        final double p = SmartDashboard.getNumber("P Gain", 0);
        final double i = SmartDashboard.getNumber("I Gain", 0);
        final double d = SmartDashboard.getNumber("D Gain", 0);
        final double iz = SmartDashboard.getNumber("I Zone", 0);
        final double ff = SmartDashboard.getNumber("Feed Forward", 0);
        final double max = SmartDashboard.getNumber("Max Output", 0);
        final double min = SmartDashboard.getNumber("Min Output", 0);
        final double sp = SmartDashboard.getNumber("Set Point", 0);

        // if PID coefficients on SmartDashboard have changed, write new values to
        // controller
        if ((p != kP)) {
            pidController.setP(p);
            kP = p;
        }
        if ((i != kI)) {
            pidController.setI(i);
            kI = i;
        }
        if ((d != kD)) {
            pidController.setD(d);
            kD = d;
        }
        if ((iz != kIz)) {
            pidController.setIZone(iz);
            kIz = iz;
        }
        if ((ff != kFF)) {
            pidController.setFF(ff);
            kFF = ff;
        }
        if ((max != kMaxOutput) || (min != kMinOutput)) {
            pidController.setOutputRange(min, max);
            kMinOutput = min;
            kMaxOutput = max;
        }
        if ((sp != setpoint)) {
            pidController.setReference(setpoint, ControlType.kVelocity);
            setpoint = sp;
            SmartDashboard.putNumber("Set Point", setpoint);
            SmartDashboard.putNumber("Shooter Velocity", encoder.getVelocity());
        }
    }

    public double getMAXRPM(){
        return(MAX_RPM);
    }

    public void resetBallCount() {
        ballCount = 0;
    }

    public void setHoodPosition(int hood_position) {
        if (hood_position == 1) {
            setHoodToFar();
        } else setHoodToNear();
    }

    public void setHoodToFar() {
        hood.set(Value.kForward);
    }

    public void setHoodToNear() {
        hood.set(Value.kReverse);
    }

    public void setSetPoint(final double sp) {
        setpoint = sp;
        pidController.setReference(setpoint, ControlType.kVelocity);

        SmartDashboard.putNumber("Set Point", setpoint);
        SmartDashboard.putNumber("Shooter Velocity", encoder.getVelocity());
    }

    public double getRPM() {
        return encoder.getVelocity();
    }

    @Override
    protected void initDefaultCommand() {
        // setDefaultCommand(new TestWithController());
        // setDefaultCommand(new StopShooter());
    }
}
