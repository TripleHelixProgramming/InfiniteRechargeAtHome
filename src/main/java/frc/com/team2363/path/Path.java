package frc.com.team2363.path;

import java.util.ArrayList;
import java.util.List;

public class Path {
    List<Double> segmentLengths;
    List<Spline> splines;
    double totalLength;

    public Path(List<Spline> data) {
        totalLength = 0;
        segmentLengths = new ArrayList<Double>();
        splines = data;
        for (Spline spline : data) {
            segmentLengths.add(spline.getLength());
            totalLength += spline.getLength();
        }
    }

    public List<Double> getSegmentLengths() {
        return segmentLengths;
    }

    public PathState getState(double s) {
        double newS = Math.max(0, Math.min(s, totalLength));
        int splineIndex = 0;
        double si = 0;
        while (splines.get(splineIndex).getLength() + si < newS) {
            si += splines.get(splineIndex).getLength();
            splineIndex++;
        }
        Spline spline = splines.get(splineIndex);
        double t = spline.calculateInput(newS - si);
        return new PathState(spline.getPose(t), spline.getCurvature(t), spline.getDCurvature(t));
    }
}