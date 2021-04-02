package frc.com.team319.io;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import frc.com.team319.trajectory.RobotConfig;
import frc.com.team319.ui.DraggableWaypoint;
import frc.com.team319.ui.Plotter;

/**
 * This class imports path files as type <code>Plotter</code>.
 */
public class PathImporter {
    /**
     * Default import method that imports all paths in the <code>/paths</code> directory.
     * 
     * @return <code>List</code> of type <code>Plotter</code> representing all paths
     */
    public static List<Plotter> importPaths() {
        return importPaths(new File(RobotConfig.pathsDirectory, "paths"));
    }

    /**
     * Imports all paths in the speicifed directory.
     * 
     * @param filePath directory to search for paths
     * @return
     */
    public static List<Plotter> importPaths(File filePath) {
        List<Plotter> plotterList = new ArrayList<>();
        if (filePath == null || !filePath.exists()) {
            return plotterList;
        }
        try {
            String[] files = filePath.list();
            for (String file : files) {
                plotterList.add(importPath(new File(filePath, file)));
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return plotterList;
    }

    public static Plotter importPath(File file) {
        List<List<String>> list = FileUtil.parseCSV(file);
        String pathName = file.getName();
        pathName = pathName.substring(0, pathName.length() - 4);
        Plotter plotter = new Plotter(pathName);
        if (list == null) {
            return plotter;
        }
        try {
            for (List<String> waypoint : list) {
                double x = Double.parseDouble(waypoint.get(0).trim());
                double y = Double.parseDouble(waypoint.get(1).trim());
                double heading = Double.parseDouble(waypoint.get(2).trim());
                double currentVelocity = Double.parseDouble(waypoint.get(3).trim());
                double maxVelocity = Double.parseDouble(waypoint.get(4).trim());
                DraggableWaypoint dWaypoint = new DraggableWaypoint(x, y, heading, currentVelocity, maxVelocity, plotter);
                plotter.addWaypoint(dWaypoint);
            }
        } catch (Exception e) {
            if (e instanceof IndexOutOfBoundsException) {
                System.out.println("Not enough columns on path file CSV (STOP EDITING PATH FILES)");
            } else if (e instanceof NumberFormatException) {
                System.out.println("Number formatted incorrectly on path file CSV (STOP EDITING PATH FILES)");
            }
            System.out.println(e);
        }
        return plotter;
    }
}
