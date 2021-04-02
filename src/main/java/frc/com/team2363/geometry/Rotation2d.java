package frc.com.team2363.geometry;

import java.text.DecimalFormat;

public class Rotation2d {
    private double radians;

    public Rotation2d(double radians) {
        this.radians = radians;
    }

    public void setRadians(double radians) {
        this.radians = radians;
    }

    public double getRadians() {
        return radians;
    }

    public String toString() {
        DecimalFormat df = new DecimalFormat("0.0000");
        return df.format(Math.toDegrees(radians));
    }
}