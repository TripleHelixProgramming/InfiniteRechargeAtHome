package lib.trajectory.constraints;

import java.util.Arrays;

import com.team319.trajectory.RobotConfig;

public class DifferentialKinematicsConstraint extends TrajectoryConstraint {
    private double maxVelocity;
    private double maxAcceleration;

    public DifferentialKinematicsConstraint(double maxVelocity, double maxAcceleration) {
        this.maxVelocity = maxVelocity;
        this.maxAcceleration = maxAcceleration;
    }

    public double getMaxVelocity(double curvature) {
        // Calculates max velocity that will not exeed velocity constraints on either wheel
        double velocity = maxVelocity / (1.0 + Math.abs(curvature) * RobotConfig.wheelBase / 2.0);
        // Calculates max velocity that yields an existing solution for linear acceleration
        // if (state.getDCurvature() != 0) {
        //     velocity = Math.min(velocity, Math.sqrt(maxAcceleration / Math.abs(state.getDCurvature())));
        // }
        return velocity;
    }

    public double getMaxAcceleration(double curvature, double dcurvature) {
        // double acceleration = Double.POSITIVE_INFINITY;
        // Calculates max acceleration that will not exceed acceleration constraints on either wheel
        // for (double sign : Arrays.asList(-1.0, 1.0)) {
        //     double numeratorTerm = maxAcceleration - sign * velocity * velocity * state.getDCurvature() * RobotConfig.wheelBase / 2;
        //     double denominatorTerm = sign * state.getCurvature() * RobotConfig.wheelBase / 2;
        //     if (denominatorTerm != -1.0) {
        //         acceleration = Math.min(acceleration, numeratorTerm / (1.0 + denominatorTerm));
        //     }
        // }
        return RobotConfig.maxAcceleration / (1 + Math.abs(curvature));
    }
}