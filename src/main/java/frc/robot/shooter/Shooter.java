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
import frc.robot.Preferences;
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

    // Bump direction
    public static final int BUMP_UP = 1;
    public static final int BUMP_DOWN = -1;
    private static double RPM_DELTA = 10.0;

    // Solenoid ids for hood position
    public static int HOOD_NEAR_SOLENOID= 12;
    public static int HOOD_FAR_SOLENOID= 13;    // Solenoid extended = far
    private static final int SHOOTER_MASTER_ID = 13;
    private static final int SHOOTER_SLAVE_ID = 22;

    public double MAX_RPM = 5700;
    public double CLIMB_RPM = 3000;

    private final CANSparkMax master, slave;
    private final CANPIDController pidController;
    private final CANEncoder encoder;
    public double kP, kI, kD, kIz, kFF, kMaxOutput, kMinOutput;

    private Position lastPosition = Position.UNKNOWN;
    
    // Live adjustment of the RPM
    public double bumpRPM = 0; // the amount of RPM to change per tick
    public int bumpTicks = 0; // how many ticks to adjust the rpm by bumpRPM
    
    // The solenoids responsible for raising & extending the climber.
    private DoubleSolenoid hood = new DoubleSolenoid(HOOD_NEAR_SOLENOID, HOOD_FAR_SOLENOID);
    
    public enum ShooterState {
        SHOOT, CLIMB, STOP
    };
    public ShooterState currentState = ShooterState.STOP;
    private double currentRPM = 0.0;

    public Shooter() {

        // initialize master
        master = new CANSparkMax(SHOOTER_MASTER_ID, MotorType.kBrushless);
        slave = new CANSparkMax(SHOOTER_SLAVE_ID, MotorType.kBrushless);
        master.restoreFactoryDefaults();
        slave.restoreFactoryDefaults();

        // master.setInverted(false);
        // slave.setInverted(true);
        slave.follow(master);

        encoder = master.getEncoder();
        encoder.setInverted(false);
        // Mayy also need to set slave encoder inversion.

        pidController = master.getPIDController();

        // Set Shooter to stop state.
        Stop();

        // PID coefficients
        kP = 10e-7;
        kI = 0.0;
        kD = 0.0;
        kIz = 0.0;
        kFF = 1;
        kMaxOutput = 1;
        kMinOutput = -1;
        currentRPM = 0;

        // set PID coefficients
        pidController.setP(kP);
        pidController.setI(kI);
        pidController.setD(kD);
        pidController.setIZone(kIz);
        pidController.setFF(kFF);
        pidController.setOutputRange(kMinOutput, kMaxOutput);

        PutSmartDash();
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
        final double sRPM = SmartDashboard.getNumber("Set RPM", 0);

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
        if ((sRPM != currentRPM)) {
            pidController.setReference(sRPM, ControlType.kVelocity);
            currentRPM = (int)sRPM;
        }

        PutSmartDash();
    }

    public void PutSmartDash() {
        // display PID coefficients on SmartDashboard
        SmartDashboard.putNumber("P Gain", kP);
        SmartDashboard.putNumber("I Gain", kI);
        SmartDashboard.putNumber("D Gain", kD);
        SmartDashboard.putNumber("I Zone", kIz);
        SmartDashboard.putNumber("Feed Forward", kFF);
        SmartDashboard.putNumber("Max Output", kMaxOutput);
        SmartDashboard.putNumber("Min Output", kMinOutput);

        SmartDashboard.putString("Shooter State", currentState.toString());
        SmartDashboard.putNumber("Shooter RPM", currentRPM);
        SmartDashboard.putNumber("Shooter Velocity", encoder.getVelocity());
    }

    public double getMAXRPM(){
        return(MAX_RPM);
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

    public void setRPM(ShooterState state, double rpm) {

        if (state == ShooterState.CLIMB) currentRPM = -rpm;
        
        currentRPM = rpm;
        currentState = state;

        pidController.setReference(currentRPM, ControlType.kVelocity);
    }

    public double getRPM() {
        return encoder.getVelocity();
    }

    public void Stop() {
        setRPM(ShooterState.SHOOT, 0.0);
    }

    public boolean isAtRPM() {
        return (Math.abs(currentRPM - getRPM()) <= RPM_DELTA);
    }

    // Doesn't alter any handling of the shooter but 
    // allows the commands to know what the last position
    // was so that when it's bumped we know which position
    // is altered.
    public void setPosition(Position pos) {
        this.lastPosition = pos;
        bumpTicks = Preferences.getPreferences().getPositionBumpTicks(lastPosition);
    }
    
    // Gets the last position set with setPosition. 
    // Defaults to to Position.UNKONWN
    public Position getLastPosition() {
        return lastPosition;
    }
    
    // Gets the current number of bump ticks relative to the last position.
    public void setBumpTicks(int direction)
    {
        switch (direction)
        {
            case Shooter.BUMP_UP:
                ++bumpTicks;
                break;
            case Shooter.BUMP_DOWN:
                --bumpTicks;
                break;
        }

        Preferences.getPreferences().setPositionBumpTicks(lastPosition, bumpTicks);
    }

    public int getBumpTicks() {
        return bumpTicks;
    } 

    @Override
    protected void initDefaultCommand() {
        // The shooter is PID Controlled.  It will keep going at it's last setpoint. All
        // SpinUpShooter Commands are on toggle button with StopShooter().  
    }
}
