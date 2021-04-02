package frc.com.team319.io;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import frc.com.team2363.trajectory.TrajectoryPlanner;

public class FileManager {
    private static final File deployPathsDirectory = new File("src/main/deploy/paths/");
    private static final File localPathsDirectory = new File("src/main/local/paths/");
    // private static final File deployPathsDirectory = new File("home/lvuser/deploy/paths/");
    // private static final File localPathsDirectory = new File("home/lvuser/local/paths/");

    public static void generate() {
        purgeFiles();
        ConfigImporter.importConfig(new File(deployPathsDirectory, "config/config.csv"));
        File configDeployFile = new File(deployPathsDirectory, "config/config.csv");
        File configLocalFile = new File(localPathsDirectory, "config/config.csv");
        // if (!FileUtil.compareFileContents(configDeployFile, configLocalFile)) {
        //     generateConfig();
        //     generateAllPaths();
        // } else {
        //     for (String path : getNewPaths()) {
        //         generatePath(path);
        //     }
        // }
        generateConfig();
        generateAllPaths();
    }
    private static void generateAllPaths() {
        String[] fileList = new File(deployPathsDirectory, "paths/").list();
        for (String file : fileList) {
            generatePath(file);
        }
    }
    private static void generatePath(String filename) {
        try {
            File deployFile = new File(deployPathsDirectory, "paths/" + filename);
            String pathData = Files.readString(Paths.get(deployFile.getPath()));
            FileUtil.write(new File(localPathsDirectory, "paths/"), filename, pathData);
            String trajectoryData = TrajectoryPlanner.generate(FileUtil.parseDoubleCSV(deployFile)).toString();
            FileUtil.write(new File(localPathsDirectory, "trajectories/"), filename, trajectoryData);
        } catch (IOException e) {
            System.out.println(e);
            System.out.println(e.getStackTrace());
        }
    }
    private static void purgeFiles() {
        String[] fileList = new File(localPathsDirectory, "paths/").list();
        if (fileList == null) {
            return;
        }
        for (String fileName : fileList) {
            File deployFile = new File(deployPathsDirectory, "paths/" + fileName);
            if (!deployFile.exists()) {
                new File(localPathsDirectory, "paths/" + fileName).delete();
                new File(localPathsDirectory, "trajectories/" + fileName).delete();
            }
        }
    }
    private static void generateConfig() {
        File deployConfigFile = new File(deployPathsDirectory, "/config/config.csv");
        File localConfigFile = new File(localPathsDirectory, "/config/config.csv");
        try {
            FileUtil.write(localConfigFile, Files.readString(Paths.get(deployConfigFile.getPath())));
        } catch(IOException e) {
            System.out.println("Problem writing to config file: " + e);
            System.out.println(e.getStackTrace());
        }
    }

    private static List<String> getNewPaths() {
        File deployPathsPathsDirectory = new File(deployPathsDirectory, "paths/"); // This is the directory where FTPd paths go. Sorry for the confusing name.
        File localPathsPathsDirectory = new File(localPathsDirectory, "paths/"); // Where local paths are stored.
        List<String> fixedPathList = Arrays.asList(deployPathsPathsDirectory.list());
        List<String> newPaths = new ArrayList<>();
        fixedPathList.forEach((val) -> newPaths.add(val));
        for (int i = newPaths.size() - 1; i >= 0; i--) {
            File deployPathFile = new File(deployPathsPathsDirectory, newPaths.get(i));
            File localPathFile = new File(localPathsPathsDirectory, newPaths.get(i));
            if (FileUtil.compareFileContents(deployPathFile, localPathFile)) {
                newPaths.remove(i);
            }
        }
        return newPaths;
    }
}