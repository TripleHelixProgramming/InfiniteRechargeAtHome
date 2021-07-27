package lib.geometry;

import java.text.DecimalFormat;

public class Translation2d {
    double x;
    double y;

    public Translation2d(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double x() {
        return x;
    }

    public double y() {
        return y;
    }

    public double getDistance(Translation2d translation) {
        return Math.hypot(translation.x() - x, translation.y() - y);
    }

    public static boolean isColinear(Translation2d p0, Translation2d p1, Translation2d p2) {
        return p0.x * (p1.y() - p2.y()) + p1.x() * (p2.y() - p0.y()) + p2.x() * (p0.y() - p1.y()) == 0;
    }

    public String toString() {
        DecimalFormat df = new DecimalFormat("0.0000");
        return df.format(x) + "," + df.format(y);
    }

    public Translation2d rotateBy(final Rotation2d rotation) {
        return new Translation2d(x * rotation.cos() - y * rotation.sin(), x * rotation.sin() + y * rotation.cos());
    }
}