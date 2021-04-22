package lib.trajectory.constraints;

public abstract class TrajectoryConstraint {
    public abstract double getMaxVelocity(double curvature);
    public abstract double getMaxAcceleration(double curvature, double dcurvature);
}