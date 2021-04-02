package frc.com.team2363.path;

import java.util.ArrayList;
import java.util.List;

import frc.com.team2363.geometry.Pose2d;
import frc.com.team2363.geometry.Rotation2d;
import frc.com.team2363.geometry.Translation2d;

public class Spline {
    private double x0, x1, dx0, dx1, ddx0, ddx1, y0, y1, dy0, dy1, ddy0, ddy1;
    private double ax, bx, cx, dx, ex, fx, ay, by, cy, dy, ey, fy;
    private double vectorMagnitude;
    private double length;
    private List<SplineSegment> segments;

    public Spline(double x0, double y0, double theta0, double x1, double y1, double theta1) {
        vectorMagnitude = 1.5 * Math.hypot(x1 - x0, y1 - y0);

        this.x0 = x0;
        this.x1 = x1;
        this.dx0 = Math.cos(theta0) * vectorMagnitude;
        this.dx1 = Math.cos(theta1) * vectorMagnitude;
        this.ddx0 = 0;
        this.ddx1 = 0;

        this.y0 = y0;
        this.y1 = y1;
        this.dy0 = Math.sin(theta0) * vectorMagnitude;
        this.dy1 = Math.sin(theta1) * vectorMagnitude;
        this.ddy0 = 0;
        this.ddy1 = 0;

        solveCoefficients();
    }

    private void solveCoefficients() {
        ax = -6 * x0 - 3 * dx0 - 0.5 * ddx0 + 0.5 * ddx1 - 3 * dx1 + 6 * x1;
        bx = 15 * x0 + 8 * dx0 + 1.5 * ddx0 - ddx1 + 7 * dx1 - 15 * x1;
        cx = -10 * x0 - 6 * dx0 - 1.5 * ddx0 + 0.5 * ddx1 - 4 * dx1 + 10 * x1;
        dx = 0.5 * ddx0;
        ex = dx0;
        fx = x0;

        ay = -6 * y0 - 3 * dy0 - 0.5 * ddy0 + 0.5 * ddy1 - 3 * dy1 + 6 * y1;
        by = 15 * y0 + 8 * dy0 + 1.5 * ddy0 - ddy1 + 7 * dy1 - 15 * y1;
        cy = -10 * y0 - 6 * dy0 - 1.5 * ddy0 + 0.5 * ddy1 - 4 * dy1 + 10 * y1;
        dy = 0.5 * ddy0;
        ey = dy0;
        fy = y0;
    }

    public double getLength() {
        return length;
    }

    public Pose2d getPose(double t) {
        return new Pose2d(getTranslation(t), getRotation(t));
    }

    public Translation2d getTranslation(double t) {
        return new Translation2d(x(t), y(t));
    }

    public Rotation2d getRotation(double t) {
        return new Rotation2d(Math.atan2(dy(t), dx(t)));
    }

    public double getCurvature(double t) {
        return (dx(t) * ddy(t) - ddx(t) * dy(t)) / ((dx(t) * dx(t) + dy(t) * dy(t)) * Math.sqrt((dx(t) * dx(t) + dy
                (t) * dy(t))));
    }

    public double getDCurvature(double t) {
        double dx2dy2 = (dx(t) * dx(t) + dy(t) * dy(t));
        double num = (dx(t) * dddy(t) - dddx(t) * dy(t)) * dx2dy2 - 3 * (dx(t) * ddy(t) - ddx(t) * dy(t)) * (dx(t) * ddx(t) + dy(t) * ddy(t));
        return num / (dx2dy2 * dx2dy2 * Math.sqrt(dx2dy2));
    }

    private double x(double t) {
        return ax * t * t * t * t * t + bx * t * t * t * t + cx * t * t * t + dx * t * t + ex * t + fx;
    }

    private double y(double t) {
        return ay * t * t * t * t * t + by * t * t * t * t + cy * t * t * t + dy * t * t + ey * t + fy;
    }

    private double dx(double t) {
        return 5 * ax * t * t * t * t + 4 * bx * t * t * t + 3 * cx * t * t + 2 * dx * t + ex;
    }

    private double dy(double t) {
        return 5 * ay * t * t * t * t + 4 * by * t * t * t + 3 * cy * t * t + 2 * dy * t + ey;
    }

    private double ddx(double t) {
        return 20 * ax * t * t * t + 12 * bx * t * t + 6 * cx * t + 2 * dx;
    }

    private double ddy(double t) {
        return 20 * ay * t * t * t + 12 * by * t * t + 6 * cy * t + 2 * dy;
    }

    private double dddx(double t) {
        return 60 * ax * t * t + 24 * bx * t + 6 * cx;
    }

    private double dddy(double t) {
        return 60 * ay * t * t + 24 * by * t + 6 * cy;
    }

    public double calculateInput(double s) {
        // TODO: implement binary search to improve search speed
        double restrictedS = Math.max(0, Math.min(s, length));
        for (SplineSegment segment : segments) {
            if (segment.getFinalDistance() >= restrictedS) {
                return segment.interpolate(restrictedS);
            }
        }
        return 0;
    }

    public void parameterize() {
        length = 0;
        segments = new ArrayList<SplineSegment>();
        parameterize(segments, 0, 1);
    }

    private void parameterize(List<SplineSegment> segments, double t0, double t1) {
        double finalRotation = Math.atan2(dy(t1), dx(t1));
        double offsetRotation = Math.atan2(dy(t0), dx(t0));
        double diff = (finalRotation - offsetRotation - Math.PI) % (2 * Math.PI) - Math.PI;
        double deltaRotation = diff < -Math.PI ? diff + 2 * Math.PI : diff;
        double distance = getTranslation(t0).getDistance(getTranslation(t1));
        if (distance > 0.075 || Math.abs(deltaRotation) > 0.05) {
            parameterize(segments, t0, (t0 + t1) / 2.0);
            parameterize(segments, (t0 + t1) / 2.0, t1);
        } else {
            double segmentLength = calculateLength(t0, t1);
            segments.add(new SplineSegment(t0, t1, length, length + segmentLength));
            length += segmentLength;
        }
    }

    public double calculateLength(double t0, double t1) {
        // TODO: implement guassian quadrature integration or something else faster
		final int iterations = 1000;
		double integral = 0;
		double integrand, last_integrand = Math.hypot(dx(0), dy(0)) / (iterations / (t1 - t0));
		for (int i = 1; i <= iterations; ++i) {
			double t = (t1 - t0) * ((double) i) / iterations + t0;
			integrand = Math.hypot(dx(t), dy(t)) / (iterations / (t1 - t0));
			integral += (integrand + last_integrand) / 2;
			last_integrand = integrand;
		}
		return integral;
	}
}