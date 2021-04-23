package lib.trajectory;

import java.util.List;

import lib.geometry.*;
import lib.trajectory.constraints.*;

public class TrajectoryParameterizer {
    public static Trajectory generateTrajectory(
        // Path path, 
        List<RegionalConstraint> regionalConstraints, 
        List<TrajectoryConstraint> constraints) {
        Trajectory trajectory = new Trajectory();
        // double s0 = 0;
        // double ds = 1.0 / 12.0;
        // for (RegionalConstraint constraint : regionalConstraints) {
        //     for (int i = 0; i < Math.ceil((constraint.getDistance() - s0) / ds) + 1; i += ds) {
        //         double s = Math.min(i * ds, constraint.getDistance()) + s0;
        //         double v = constraint.getMaxVelocity(s - s0);
        //         Pose2d pose = path.getPose(s);
        //         double curvature = path.getCurvature(s);
        //         trajectory.addState(new TrajectoryState(s, v, pose, curvature));
        //     }
        //     s0 += constraint.getDistance();
        // }

        // for (int i = 1; i < trajectory.getStates().size(); i++) {
        //     double vi = trajectory.getStates().get(i - 1).getVelocity();
        //     double a = trajectory.getStates().get(i - 1).getAcc;
        //     ds = trajectory.getStates().get(i).getDistance() - trajectory.getStates().get(i - 1).getDistance();
        // }

        // for (int i = trajectory.getStates().size() - 1; i > 0; i--) {
            
        // }

        // // Calculates the time of each trajectory state
        // for (int i = 0; i < trajectory.getStates().size(); i++) {
        //     if (i == 0) {
        //         trajectory.getStates().get(0).setTime(0);
        //     } else {
        //         TrajectoryState previousState = trajectory.getStates().get(i - 1);
        //         TrajectoryState currentState = trajectory.getStates().get(i);
        //         double dt = previousState.timeBetweenStates(currentState);
        //         currentState.setTime(dt + previousState.getTime());
        //     }
        // }

        return trajectory;
    }
}