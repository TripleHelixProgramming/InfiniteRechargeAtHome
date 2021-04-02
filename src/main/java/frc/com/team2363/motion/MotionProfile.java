package frc.com.team2363.motion;

import java.util.ArrayList;
import java.util.List;
import frc.com.team2363.path.Path;
import frc.com.team2363.path.PathState;
import frc.com.team2363.trajectory.Trajectory;
import frc.com.team2363.trajectory.TrajectoryState;
import frc.com.team319.trajectory.RobotConfig;

public class MotionProfile {
    public static void generate(Trajectory traj, List<MotionSegment> segments, List<Constraint> constraints, Path path) {
        List<MotionState> states = new ArrayList<MotionState>();
        final double ds = 0.1;
        double si = 0;
        double velocity, acceleration;
        for (MotionSegment segment : segments) {
            for (int i = 0; i < Math.ceil(segment.getDistance() / ds) + 1; i++) {
                double s = Math.min(segment.getDistance(), i * ds) + si;
                if (s == si) {
                    velocity = segment.getStartVelocity();
                } else if (s == si + segment.getDistance()) {
                    velocity = segment.getFinalVelocity();
                } else {
                    velocity = segment.getMaxVelocity();
                }
                acceleration = RobotConfig.maxAcceleration;
                PathState state = path.getState(s);
                for (Constraint constraint : constraints) {
                    velocity = Math.min(constraint.getMaxVelocity(state), velocity);
                }
                for (Constraint constraint : constraints) {
                    acceleration = Math.min(constraint.getMaxAcceleration(state, velocity), acceleration);
                }
                states.add(new MotionState(s, velocity, acceleration));
            }
            si += segment.getDistance();
        }

        for (int i = 1; i < states.size(); i++) {
            double vi = states.get(i - 1).getVelocity();
            double d = states.get(i).getDistance() - states.get(i - 1).getDistance();
            double a = states.get(i - 1).getAcceleration();
            double vf = Math.sqrt(vi * vi + 2 * a * d);
            states.get(i).setVelocity(Math.min(vf, states.get(i).getVelocity()));
        }

        for (int i = states.size() - 2; i >= 0; i--) {
            double vi = states.get(i + 1).getVelocity();
            double d = states.get(i + 1).getDistance() - states.get(i).getDistance();
            double a = states.get(i).getAcceleration();
            double vf = Math.sqrt(vi * vi + 2 * a * d);
            states.get(i).setVelocity(Math.min(vf, states.get(i).getVelocity()));
        }

        double[] timestamps = new double[states.size()];
        if (timestamps.length > 1) {
            timestamps[0] = 0;
            for (int i = 1; i < timestamps.length; i++) {
                timestamps[i] = states.get(i - 1).timeBetweenStates(states.get(i)) + timestamps[i - 1];
            }
            int index = 0;
            double ti = 0;
            double t = 0;
            for (int i = 0; i < Math.ceil(timestamps[timestamps.length - 1] / traj.getDt()) + 1; i++) {
                t = Math.min(timestamps[timestamps.length - 1], i * traj.getDt());
                while (timestamps[index + 1] < t) {
                    ti = timestamps[index + 1];
                    index++;
                }
                MotionState newState = states.get(index).interpolate(states.get(index + 1), t - ti);
                traj.addState(new TrajectoryState(newState));
            }
        }
    }
}