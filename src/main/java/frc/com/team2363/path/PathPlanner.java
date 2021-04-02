package frc.com.team2363.path;

import java.util.ArrayList;
import java.util.List;

import frc.com.team319.ui.DraggableWaypoint;

public class PathPlanner {
    public static Spline[] generateGUISplines(List<DraggableWaypoint> waypoints) {
        Spline[] splines = new Spline[waypoints.size() - 1];
        for (int i = 0; i < waypoints.size() - 1; i++) {
            DraggableWaypoint w0 = waypoints.get(i);
            DraggableWaypoint w1 = waypoints.get(i + 1);
            splines[i] = new Spline(w0.getX(), w0.getY(), w0.getHeading(), w1.getX(), w1.getY(), w1.getHeading());
        }
        return splines;
    }

    public static Path generate(List<List<Double>> waypoints) {
        List<Spline> splines = new ArrayList<Spline>();
        for (int i = 0; i < waypoints.size() - 1; i++) {
            double x0 = waypoints.get(i).get(0);
            double y0 = waypoints.get(i).get(1);
            double theta0 = waypoints.get(i).get(2);
            double x1 = waypoints.get(i + 1).get(0);
            double y1 = waypoints.get(i + 1).get(1);
            double theta1 = waypoints.get(i + 1).get(2);
            Spline spline = new Spline(x0, y0, theta0, x1, y1, theta1);
            spline.parameterize();
            splines.add(spline);
        }
        return new Path(splines);
    }
}