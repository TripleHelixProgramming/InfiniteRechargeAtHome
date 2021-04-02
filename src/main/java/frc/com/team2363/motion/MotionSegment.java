package frc.com.team2363.motion;

public class MotionSegment {
    private double startVelocity, maxVelocity, finalVelocity;
    private double distance;

    public MotionSegment(double startVelocity, double maxVelocity, double finalVelocity, double distance) {
        this.startVelocity = startVelocity;
        this.maxVelocity = maxVelocity;
        this.finalVelocity = finalVelocity;
        this.distance = distance;
    }

    public double getStartVelocity() {
        return startVelocity;
    }

    public double getMaxVelocity() {
        return maxVelocity;
    }

    public double getFinalVelocity() {
        return finalVelocity;
    }

    public double getDistance() {
        return distance;
    }

    public String toString() {
        return startVelocity + "," + maxVelocity + "," + finalVelocity + "," + distance;
    }
}