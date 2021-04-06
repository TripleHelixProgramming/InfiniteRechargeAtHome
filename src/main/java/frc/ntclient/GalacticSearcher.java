package frc.ntclient;

import java.util.*;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

import org.opencv.core.*;
//import org.opencv.core.Core.*;
import org.opencv.imgproc.Imgproc;
//import org.opencv.features2d.FeatureDetector;
//import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.videoio.VideoCapture;
//import org.opencv.imgproc.*;
//import org.opencv.objdetect.*;

import frc.ntclient.GalacticSearchPath.PathName;
import frc.ntclient.GripPipeline;

public class GalacticSearcher {
    public static void main(String[] args) {
        new GalacticSearcher().run();
    }

    static {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }
    
    public void run() {
        NetworkTableInstance inst = NetworkTableInstance.getDefault();
        NetworkTable table = inst.getTable("datatable");
        NetworkTableEntry selectedGSPath = table.getEntry("selectedGSPath");
        inst.startClientTeam(2363);  // or use inst.startClient("hostname") or similar
        inst.startDSClient();  // recommended if running on DS computer; this gets the robot IP from the DS
        
        //create video capture
        VideoCapture capture = new VideoCapture("http://10.23.63.11:5802");

        //initialize image sources for reference images
        List<GalacticSearchPath> paths = new ArrayList<GalacticSearchPath>();
        paths.add(new GalacticSearchPath(PathName.ARED, "C:\\GRIP\\ARed.jpg"));
        paths.add(new GalacticSearchPath(PathName.ABLUE, "C:\\GRIP\\ABlue.jpg"));
        paths.add(new GalacticSearchPath(PathName.BRED, "C:\\GRIP\\BRed.jpg"));
        paths.add(new GalacticSearchPath(PathName.BBLUE, "C:\\GRIP\\BBlue.jpg"));

        for (GalacticSearchPath path : paths) {
            path.readImage();
        }

        //create pipeline
        Mat frame = new Mat();
        capture.read(frame);

        //run pipeline
        while (capture.isOpened()) {
            if (capture.read(frame)) {
                for (GalacticSearchPath path : paths) {
                    GripPipeline pipeline = new GripPipeline();
                    pipeline.process(frame, path.getImage());
                    double meanvalue = findMeanValue(pipeline.cvAbsdiffOutput());
                    path.setMeanValue(meanvalue);
                }
            }

            Collections.sort(paths);
            PathName selectedPath = paths.get(0).getPathName();
            selectedGSPath.setValue(selectedPath.ordinal());
        }

        //Close capture
    }

    public double findMeanValue(Mat bgr) {
        Mat hsv = new Mat();
        Imgproc.cvtColor(bgr, hsv, Imgproc.COLOR_BGR2HSV);

        Scalar means = Core.mean(hsv);
        return means.val[2];
    }
}