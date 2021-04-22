package lib.trajectory;

import java.util.ArrayList;
import java.util.List;

public class Trajectory {
    List<TrajectoryState> states;

    public Trajectory() {
        states = new ArrayList<TrajectoryState>();
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