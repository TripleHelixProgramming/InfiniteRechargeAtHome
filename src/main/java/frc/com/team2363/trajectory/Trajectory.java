package frc.com.team2363.trajectory;

import java.util.ArrayList;
import java.util.List;

public class Trajectory {
    List<TrajectoryState> states;
    double dt;

    public Trajectory(double dt) {
        states = new ArrayList<TrajectoryState>();
        this.dt = dt;
    }

    public double getDt() {
        return dt;
    }

    public List<TrajectoryState> getStates() {
        return states;
    }

    public void addState(TrajectoryState state) {
        states.add(state);
    }

    public String toString() {
        StringBuilder data = new StringBuilder();
        for (int i = 0; i < states.size(); i++) {
            data.append(states.get(i));
            if (i != states.size() - 1) {
                data.append("\n");
            }
        }
        return data.toString();
    }
}