package frc.com.team2363.motion;

import frc.com.team2363.path.PathState;

import com.team319.trajectory.RobotConfig;

public class DrivetrainConstraint extends Constraint {
    private double maxVelocity;
    private double maxAcceleration;

    public DrivetrainConstraint(double maxVelocity, double maxAcceleration) {
        this.maxVelocity = maxVelocity;
        this.maxAcceleration = maxAcceleration;
    }

    public double getMaxVelocity(PathState state) {
        return maxVelocity / (1.0 + Math.abs(state.getCurvature()) * RobotConfig.wheelBase / 2.0);
    }

    public double getMaxAcceleration(PathState state, double velocity) {
        double angularAcceleration = velocity * state.getCurvature() + velocity * velocity * state.getDCurvature();
        return maxAcceleration * maxAcceleration / (maxAcceleration + Math.abs(angularAcceleration) * RobotConfig.wheelBase / 2.0);
    }
}