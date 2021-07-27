package lib.trajectory;

import lib.geometry.Pose2d;

public class TrajectoryState {
    private double t;
    private double d;
    private double v;
    private double a;
    private Pose2d pose;
    private double curvature;

    public TrajectoryState(double d, double v, Pose2d pose, double curvature) {
        this.d = d;
        this.v = v;
    }

    public double getTime() {
        return t;
    }

    public void setTime(double t) {
        this.t = t;
    }

    public double getDistance() {
        return d;
    }

    public void setDistance(double d) {
        this.d = d;
    }

    public double getVelocity() {
        return v;
    }

    public void setVelocity(double v) {
        this.v = v;
    }

    public double getAcceleration() {
        return a;
    }

    public void setAcceleration(double a) {
        this.a = a;
    }
    
    public double timeBetweenStates(TrajectoryState state) {
        return Math.abs(2 * (state.getDistance() - d) / (state.getVelocity() - v));
    }
}