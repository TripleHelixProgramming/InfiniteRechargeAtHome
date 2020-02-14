/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.shooter;

/**
 * An enumerated list of shooting positions and corresponding PID control 
 * setpoints, expected rpm for that setpoint, and associated hood position.
 */
public enum Position {

    // POSITION(rpms expected, hood position)
    MIDFIELD_SHOOT(3135, 1),
    TRENCH_SHOOT(3300, 1),
    DUMP_BALLS(1000, 1),
    LAYUP_SHOOT(3135, 0),
    UNKNOWN(0, 1);

    private int rpm;
    private int hoodPosition;
    private double bumpRPM;

    // This changes the default global bump RPM.
    // The enum is structured so that it could handle different
    // bump rpm values per position but for now there's only the one.
    public static final int DEFAULT_BUMP_RPM = 10;

    private Position(int rpm, int hp) {
        this.rpm = rpm;
        this.hoodPosition = hp;
        this.bumpRPM = DEFAULT_BUMP_RPM;
    }

    public int getRPM() {
        return rpm;
    }
    
    public int getHoodPosition() {
        return hoodPosition;
    }

    public double getBumpRPM() {
        return bumpRPM;
    }
}
