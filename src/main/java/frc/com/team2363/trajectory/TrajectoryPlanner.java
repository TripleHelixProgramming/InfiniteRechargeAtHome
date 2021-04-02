package frc.com.team2363.trajectory;

import java.util.ArrayList;
import java.util.List;

import frc.com.team2363.geometry.Pose2d;
import frc.com.team2363.geometry.Rotation2d;
import frc.com.team2363.geometry.Translation2d;
import frc.com.team2363.motion.Constraint;
import frc.com.team2363.motion.DrivetrainConstraint;
import frc.com.team2363.motion.MotionProfile;
import frc.com.team2363.motion.MotionSegment;
import frc.com.team2363.path.Path;
import frc.com.team2363.path.PathPlanner;
import frc.com.team319.trajectory.RobotConfig;

public class TrajectoryPlanner {
    public static Trajectory generate(List<List<Double>> waypoints) {
        Trajectory traj = new Trajectory(RobotConfig.dt);
        if (waypoints.size() < 2) return traj;
        Path path = PathPlanner.generate(waypoints);
        List<Constraint> constraints = new ArrayList<Constraint>();
        constraints.add(new DrivetrainConstraint(RobotConfig.maxVelocity, RobotConfig.maxAcceleration));
        List<MotionSegment> segments = new ArrayList<MotionSegment>();
        for (int i = 0; i < path.getSegmentLengths().size(); i++) {
            double v0 = waypoints.get(i).get(3);
            double v1 = waypoints.get(i + 1).get(4);
            double v2 = waypoints.get(i + 1).get(3);
            segments.add(new MotionSegment(v0, v1, v2, path.getSegmentLengths().get(i)));
        }  
        MotionProfile.generate(traj, segments, constraints, path);
        assignPoses(traj, path);
        correctHeading(traj);
        makeLeftAndRightTrajectories(traj, path);
        return traj;
    }

    private static void assignPoses(Trajectory traj, Path path) {
        for (TrajectoryState state : traj.getStates()) {
            state.setPose(path.getState(state.getCenterState().getDistance()).getPose());
        }
    }

    private static void correctHeading(Trajectory trajectory) {
        // Fix headings so they are continuously additive 
		double lastUncorrectedHeading = trajectory.getStates().get(0).getPose().getRotation().getRadians();
		double lastCorrectedHeading = lastUncorrectedHeading;
		for (int i = 1; i < trajectory.getStates().size(); i++) {
			double uncorrectedHeading = trajectory.getStates().get(i).getPose().getRotation().getRadians();
			double headingDelta = 0;
			
			if (lastUncorrectedHeading < 0 && uncorrectedHeading > 0  && lastUncorrectedHeading < -Math.PI / 2) {
				headingDelta = -(2 * Math.PI - Math.abs(lastUncorrectedHeading) - Math.abs(uncorrectedHeading));
			} else if (lastUncorrectedHeading > 0 && uncorrectedHeading < 0 && lastUncorrectedHeading > Math.PI / 2) {
				headingDelta = 2 * Math.PI - Math.abs(lastUncorrectedHeading) - Math.abs(uncorrectedHeading);
			} else {
				headingDelta = lastUncorrectedHeading - uncorrectedHeading;
			}

            double correctedHeading = lastCorrectedHeading - headingDelta;
            Translation2d translation = trajectory.getStates().get(i).getPose().getTranslation();
			trajectory.getStates().get(i).setPose(new Pose2d(translation, new Rotation2d(correctedHeading)));
			lastUncorrectedHeading = uncorrectedHeading;
			lastCorrectedHeading = correctedHeading;
		}
    }
    
    public static void makeLeftAndRightTrajectories(Trajectory traj, Path path) {
        double maxAbsAccel = 0;
		for (int i = 0; i < traj.getStates().size(); i++) {
            TrajectoryState currentState = traj.getStates().get(i);
            TrajectoryState lastState = traj.getStates().get(Math.max(0, i - 1));
            double curvature = path.getState(currentState.getCenterState().getDistance()).getCurvature();
            double angular = curvature * RobotConfig.wheelBase / 2;
            currentState.setLeftVelocity(currentState.getCenterState().getVelocity() * (1 - angular));
            currentState.setRightVelocity(currentState.getCenterState().getVelocity() * (1 + angular));
            lastState.setLeftAcceleration((currentState.getLeftVelocity() - lastState.getLeftVelocity()) / RobotConfig.dt);
            lastState.setRightAcceleration((currentState.getRightVelocity() - lastState.getRightVelocity()) / RobotConfig.dt);
            maxAbsAccel = Math.max(maxAbsAccel, Math.max(Math.abs(lastState.getLeftAcceleration()), Math.abs(lastState.getRightAcceleration())));
            currentState.setAngularAcceleration(currentState.getCenterState().getAcceleration() * curvature);
        }
        System.out.println(maxAbsAccel);
	}
}