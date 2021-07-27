package lib.trajectory.constraints;

import lib.geometry.Pose2dWithCurvature;

public class RegionalConstraint {
    private double v0, v1, v2;
    private double distance;

    public RegionalConstraint(double v0, double v1, double v2, double distance) {
        this.v0 = v0;
        this.v1 = v1;
        this.v2 = v2;
        this.distance = distance;
    }

    public double getDistance() {
        return distance;
    }

    public double getMaxVelocity(double s) {
        if (s == 0) {
            return v0;
        } else if (s == distance) {
            return v2;
        } else {
            return v1;
        }
    }
}