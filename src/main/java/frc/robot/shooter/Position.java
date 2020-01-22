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

    // POSITION(motor percentage setpoint, rpms expected, hood position)
    RIGHT_AUTO(.55, 3135, 0),
    CENTER_AUTO(.55, 3135, 0),
    LEFT_AUTO(.55, 3135, 0),
    LAYUP(.55, 3135, 0);

    private double setPoint;
    private double rpm;
    private int hoodPosition;

    private Position(double sp, double rpm, int hp) {
        this.setPoint = sp;
        this.rpm = rpm;
        this.hoodPosition = hp;
    }

    public double getSetPoint() {
        return setPoint;
    }

    public double getRPM() {
        return rpm;
    }
    
    public double getHoodPosition() {
        return hoodPosition;
    }
}
