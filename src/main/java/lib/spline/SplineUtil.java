package lib.spline;

import java.util.List;

import lib.gui.ui.DraggableWaypoint;

public class SplineUtil {
    public static Spline[] generateSplines(List<DraggableWaypoint> waypoints) {
        Spline[] splines = new Spline[waypoints.size() - 1];
        for (int i = 0; i < waypoints.size() - 1; i++) {
            DraggableWaypoint w0 = waypoints.get(0);
            DraggableWaypoint w1 = waypoints.get(1);
            splines[i] = new Spline(w0.getX(), w0.getY(), w0.getHeading(), w1.getX(), w1.getY(), w1.getHeading());
        }
        return splines;
    }
}