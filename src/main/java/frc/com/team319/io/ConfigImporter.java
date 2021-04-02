package frc.com.team319.io;

import java.io.File;
import java.util.List;
import frc.com.team319.trajectory.RobotConfig;

public class ConfigImporter {
    /**
     * Imports a config from the default directory.
     */
    public static void importConfig() {
        importConfig(null);
    }
    
    /**
     * Imports a config <code>File</code>
     * 
     * @param file the directory where the config file is found
     * @return <code>true</code> if the file exists, and <code>false</code> if the file does not exist
     */
    public static boolean importConfig(File file) {
        if (file == null) {
            file = new File(RobotConfig.pathsDirectory, "config/config.csv");
        }
        if (!file.exists()) {
            return false;
        }
        List<List<String>> data = FileUtil.parseCSV(file);
        try {
            RobotConfig.length = Double.parseDouble(data.get(0).get(0).trim());
            RobotConfig.width = Double.parseDouble(data.get(1).get(0).trim());
            RobotConfig.wheelBase = Double.parseDouble(data.get(2).get(0).trim());
            RobotConfig.maxVelocity = Double.parseDouble(data.get(3).get(0).trim());
            RobotConfig.maxAcceleration = Double.parseDouble(data.get(4).get(0).trim());
            RobotConfig.dt = Double.parseDouble(data.get(5).get(0).trim());
        } catch (Exception e) {
            if (e instanceof IndexOutOfBoundsException) {
                System.out.println("Not enough lines on config file (STOP MESSING WITH THE CONFIG FILE!!!)");
            } else if (e instanceof NumberFormatException) {
                System.out.println("Number formatted incorrectly on config file (STOP MESSING WITH THE CONFIG FILE!!!)");
            }
            System.out.println(e);
            return false;
        }
        return true;
    }
}