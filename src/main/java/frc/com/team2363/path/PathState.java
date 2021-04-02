package frc.com.team2363.path;

import frc.com.team2363.geometry.Pose2d;

public class PathState {
    private Pose2d pose;
    private double curvature, dcurvature;

    public PathState(Pose2d pose, double curvature, double dcurvature) {
        this.pose = pose;
        this.curvature = curvature;
        this.dcurvature = dcurvature;
    }

    public Pose2d getPose() {
        return pose;
    }

    public double getCurvature() {
        return curvature;
    }

    public double getDCurvature() {
        return dcurvature;
    }
}