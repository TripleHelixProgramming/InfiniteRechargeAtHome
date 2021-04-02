package frc.com.team2363.motion;

import java.text.DecimalFormat;

public class MotionState {
    private double distance, velocity, acceleration;

    public MotionState(double distance, double velocity, double acceleration) {
        this.distance = distance;
        this.velocity = velocity;
        this.acceleration = acceleration;
    }

    public MotionState interpolate(MotionState state, double t) {
        double acc = (state.getVelocity() - velocity) / timeBetweenStates(state);
        double vel = velocity + acc * t;
        double pos = distance + velocity * t + 0.5 * acc * t * t;
        return new MotionState(pos, vel, acc);
    }

    public double timeBetweenStates(MotionState state) {
        return Math.abs(2 * (state.getDistance() - distance) / (state.getVelocity() + velocity));
    }

    public void setAcceleration(double acceleration) {
        this.acceleration = acceleration;
    }

    public double getAcceleration() {
        return acceleration;
    }

    public void setVelocity(double velocity) {
        this.velocity = velocity;
    }
    
    public double getVelocity() {
        return velocity;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public double getDistance() {
        return distance;
    }

    public String toString() {
        DecimalFormat df = new DecimalFormat("0.0000");
        StringBuilder data = new StringBuilder();
        data.append(df.format(distance)).append(",");
        data.append(df.format(velocity)).append(",");
        data.append(df.format(acceleration));
        return data.toString();
    }
}