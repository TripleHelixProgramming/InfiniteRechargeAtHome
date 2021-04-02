package frc.com.team2363.path;

public class SplineSegment {
    double t0, t1, s0, s1, length;

    public SplineSegment(double t0, double t1, double s0, double s1) {
        this.t0 = t0;
        this.t1 = t1;
        this.s0 = s0;
        this.s1 = s1;
    }

    public double interpolate(double s) {
        return ((s - s0) / (s1 - s0)) * (t1 - t0) + t0;
    }

    public double getInitialDistance() {
        return s0;
    }

    public double getFinalDistance() {
        return s1;
    }
}