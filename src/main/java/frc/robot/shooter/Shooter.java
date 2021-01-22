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
import com.team2363.logger.HelixLogger;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import frc.robot.Preferences;

public class Shooter extends Subsystem {

    private static Shooter INSTANCE;

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
    private static double RPM_DELTA = 1000.0;

    // Solenoid ids for hood position & climber
    private static int HOOD_NEAR_SOLENOID = 3;
    private static int HOOD_FAR_SOLENOID = 2; // Solenoid extended = far

    // Master & Slave motor CAN IDs
    private static final int SHOOTER_MASTER_ID = 22;
    private static final int SHOOTER_SLAVE_ID = 13;

    public double MAX_RPM = 5700;
    public double MAX_CLIMB_RPM = 2500;

    private CANSparkMax master, slave;

    private CANPIDController pidController;
    private CANEncoder encoder;
    public double kP, kI, kD, kIz, kFF, kMaxOutput, kMinOutput;

    // The solenoids responsible for raising & extending the climber.
    private DoubleSolenoid hood = new DoubleSolenoid(HOOD_FAR_SOLENOID, HOOD_NEAR_SOLENOID);

    // Various states for the Shooter, since shooter motors are also used for climbing.
    // SHOOT is positive velocity (rpms) of motors.
    // CLIMB is negative velocity (rpms) of motors.
    // STOP is 0.0 rpms.
    public enum ShooterState {
        SHOOT, CLIMB, STOP
    };
    private ShooterState currentState = ShooterState.STOP;
    private Position currentPosition = Position.UNKNOWN;
    private double currentRPM = 0.0;

    // Live adjustment of the RPM
    private int bumpTicks = 0; // how many ticks to adjust the rpm by bumpRPM

    public Shooter() {

        setUpMotors();
        setUpPIDF();

        // Set Shooter to stop state.
        stop();
        setupLogs();
    }

    public void setUpMotors() {

        // Initialize motors
        master = new CANSparkMax(SHOOTER_MASTER_ID, MotorType.kBrushless);
        slave = new CANSparkMax(SHOOTER_SLAVE_ID, MotorType.kBrushless);

        // Set all motors to factory defaults.
        master.restoreFactoryDefaults();
        slave.restoreFactoryDefaults();

        // Keeps the motor from slamming to a hault when STOP mode is set.
        master.setClosedLoopRampRate(2.0);
        master.setSmartCurrentLimit(60);

        // Tell the slave to follow the master and invert it since on opposite side.
        slave.follow(master, true);

        encoder = master.getEncoder();

    }

    public void setUpPIDF() {

        pidController = master.getPIDController();

        // PID coefficients
        kP = 0.00015;
        kI = 0.0;
        kD = 0.0;
        kIz = 0.0;
        kFF = 0.000181;
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

        putSmartDash();
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
            currentRPM = sRPM;
        }

        putSmartDash();
    }

    // Called in the periodic() and other times to display info on the SmartDashboard.
    public void putSmartDash() {

        SmartDashboard.putNumber("P Gain", kP);
        SmartDashboard.putNumber("I Gain", kI);
        SmartDashboard.putNumber("D Gain", kD);
        SmartDashboard.putNumber("I Zone", kIz);
        SmartDashboard.putNumber("Feed Forward", kFF);
        SmartDashboard.putNumber("Max Output", kMaxOutput);
        SmartDashboard.putNumber("Min Output", kMinOutput);

        SmartDashboard.putString("Shooter State", currentState.toString());
        SmartDashboard.putNumber("Set RPM", currentRPM);
        SmartDashboard.putNumber("Shooter Velocity", encoder.getVelocity() * 30.0 / 18.0);

        SmartDashboard.putString("Current Position", currentPosition.toString());
        SmartDashboard.putNumber("Position RPM", currentPosition.getRPM());
        SmartDashboard.putNumber("Bumped RPM", currentRPM);
        SmartDashboard.putNumber("Bumped Increment", currentPosition.getBumpRPM());
        SmartDashboard.putNumber("Bump Ticks", bumpTicks);
        SmartDashboard.putString("Hood Position", getHoodPosition());
    }

    // returns the max velocity (in RPMs) of the CAN SPark Max/NEOs  5700
    public double getMAXRPM(){
        return(MAX_RPM);
    }

    public double getMAX_CLIMB_RPM(){
        return(MAX_CLIMB_RPM);
    }

    // Get the current shooter velocity from the encoder (in RPMs)
    public double getRPM() {
        return encoder.getVelocity() * 30.0 / 18.0;
    }

    public boolean isAtRPM() {
        return (Math.abs(currentRPM - getRPM()) <= RPM_DELTA);
    }

    public void setRPM(ShooterState state, double rpm) {

        currentState = state;
        currentRPM = rpm;
        
        pidController.setReference(currentRPM, ControlType.kVelocity);

        // DO NOT REMOVE PutSmartDash() -- needed to update rpm in SmartDash and be
        // changeable from the smartDashboard for tuning.
        putSmartDash();
    }

    public void stop() {
        setRPM(ShooterState.STOP, 0.0);
        setHoodToFar();
    }

    // Doesn't alter any handling of the shooter but
    // allows the commands to know what the last position
    // was so that when it's bumped we know which position
    // is altered.
    public void setCurrentPosition(Position pos) {
        currentPosition = pos;
        bumpTicks = Preferences.getPreferences().getPositionBumpTicks(currentPosition);
    }

    // Gets the last position set with setPosition.
    // Defaults to to Position.UNKONWN
    public Position getCurrentPosition() {
        return currentPosition;
    }

    public double getPosition() {
        return master.getEncoder().getPosition();
    }

    // Gets the current number of bump ticks relative to the last position.
    public void setBumpTicks(int direction) {
        switch (direction) {
        case Shooter.BUMP_UP:
            ++bumpTicks;
            break;
        case Shooter.BUMP_DOWN:
            --bumpTicks;
            break;
        }

        Preferences.getPreferences().setPositionBumpTicks(currentPosition, bumpTicks);
    }

    public int getBumpTicks() {
        return bumpTicks;
    }

    public void setHoodPosition(int hood_position) {
        if (hood_position == 1) {
            setHoodToFar();
        } else
            setHoodToNear();
    }

    public String getHoodPosition() {
        if (hood.get() == Value.kReverse) 
            return ("FAR");
        else
            return ("NEAR");
    }

    public void setHoodToFar() {
        hood.set(Value.kForward);
    }

    public void setHoodToNear() {
        hood.set(Value.kReverse);
    }

    private void setupLogs() {
        HelixLogger.getInstance().addSource("SHOOTER CURRENT", master::getOutputCurrent);
        HelixLogger.getInstance().addSource("SHOOTER VOLTAGE", master::getBusVoltage);
        HelixLogger.getInstance().addSource("SHOOTER VELOCITY", encoder::getVelocity);
    }

    @Override
    protected void initDefaultCommand() {
        // The shooter is PID Controlled. It will keep going at it's last setpoint. All
        // SpinUpShooter Commands are on toggle button with StopShooter().
        // setDefaultCommand(new MyDefaultCommand());
    }
}
