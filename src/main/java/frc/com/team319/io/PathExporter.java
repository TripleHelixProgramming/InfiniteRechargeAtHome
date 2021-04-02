package frc.com.team319.io;

import java.util.List;
import java.io.File;
import frc.com.team319.trajectory.BobPath;
import frc.com.team319.trajectory.RobotConfig;

public class PathExporter {
    /**
     * Exports a <code>List</code> of type <code>BobPath</code> 
     * to the default directory as CSV.
     * 
     * @param paths <code>List</code> of type <code>BobPath</code> to export
     */
    public static void exportPaths(List<BobPath> paths) {
        File file = new File(RobotConfig.pathsDirectory, "paths");
        for (BobPath path : paths) {
            exportPath(path, file);
        }
    }

    /**
     * Exports a <code>BobPath</code> to the <code>File</code> specified as a CSV.
     * 
     * @param path type <code>BobPath</code>
     * @param file directory to save <code>path</code> in
     */
    public static void exportPath(BobPath path, File filePath) {
        if (path == null) {
            System.out.println("The BobPath provided is null.");
            return;
        }
        if (filePath == null) {
            System.out.println("The File provided is null.");
            return;
        }
        FileUtil.write(filePath, path.getName() + ".csv", path.toString());
    }
}