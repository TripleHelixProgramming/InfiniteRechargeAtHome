package frc.com.team2363.trajectory;

import java.text.DecimalFormat;

import frc.com.team2363.geometry.Pose2d;
import frc.com.team2363.motion.MotionState;

public class TrajectoryState {
    private Pose2d pose;
    private MotionState center;
    private double leftVel, rightVel, leftAcc, rightAcc;
    private double angularAcc;

    public TrajectoryState(MotionState state) {
        center = state;
    }

    public void setPose(Pose2d pose) {
        this.pose = pose;
    }

    public Pose2d getPose() {
        return pose;
    }

    public void setLeftAcceleration(double left) {
        this.leftAcc = left;
    }

    public double getLeftAcceleration() {
        return leftAcc;
    }

    public void setLeftVelocity(double left) {
        this.leftVel = left;
    }

    public double getLeftVelocity() {
        return leftVel;
    }

    public void setCenterState(MotionState center) {
        this.center = center;
    }

    public MotionState getCenterState() {
        return center;
    }

    public void setRightAcceleration(double right) {
        this.rightAcc = right;
    }

    public double getRightAcceleration() {
        return rightAcc;
    }

    public void setRightVelocity(double right) {
        this.rightVel = right;
    }

    public double getRightVelocity() {
        return rightVel;
    }

    public void setAngularAcceleration(double acc) {
        angularAcc = acc;
    }

    public String toString() {
        DecimalFormat df = new DecimalFormat("0.0000");
        // return df.format(pose.getRotation().getRadians()) + "," + df.format(center.getDistance()) + "," + df.format(leftVel) + "," + df.format(rightVel);
        return df.format(leftAcc) + "," + df.format(rightAcc) + "," + df.format(center.getAcceleration()) + "," + df.format(angularAcc);
    }
}