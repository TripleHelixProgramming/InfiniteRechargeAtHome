package lib.geometry;

import java.text.DecimalFormat;

public class Rotation2d {
    private double radians;

    final double cosAngle;
    final double sinAngle;

    public Rotation2d(double x, double y, boolean normalize) {
        if (normalize) {
            // From trig, we know that sin^2 + cos^2 == 1, but as we do math on this object we might accumulate rounding errors.
            // Normalizing forces us to re-scale the sin and cos to reset rounding errors.
            double magnitude = Math.hypot(x, y);
            if (magnitude > 1E-6) {
                sinAngle = y / magnitude;
                cosAngle = x / magnitude;
            } else {
                sinAngle = 0;
                cosAngle = 1;
            }
        } else {
            cosAngle = x;
            sinAngle = y;
        }
    }

    public Rotation2d(double radians) {
        this.radians = radians;
        sinAngle = Math.sin(radians);
        cosAngle = Math.cos(radians);
    }

    public void setRadians(double radians) {
        this.radians = radians;
    }

    public double getRadians() {
        return radians;
    }

    public double cos() {
        return cosAngle;
    }

    public double sin() {
        return sinAngle;
    }

    public double tan() {
        if (Math.abs(cosAngle) < 1E-6) {
            if (sinAngle >= 0.0) {
                return Double.POSITIVE_INFINITY;
            } else {
                return Double.NEGATIVE_INFINITY;
            }
        }
        return sinAngle / cosAngle;
    }

    public String toString() {
        DecimalFormat df = new DecimalFormat("0.0000");
        return df.format(Math.toDegrees(radians));
    }
}