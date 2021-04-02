package frc.com.team2363.motion;

import frc.com.team2363.path.PathState;

public abstract class Constraint {
    public abstract double getMaxVelocity(PathState state);
    public abstract double getMaxAcceleration(PathState state, double velocity);
}