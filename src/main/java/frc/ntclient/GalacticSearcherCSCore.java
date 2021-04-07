package frc.ntclient;

import java.util.*;

import edu.wpi.cscore.HttpCamera;
import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.*;
import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.MjpegServer;
import edu.wpi.first.cameraserver.CameraServer;
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

public class GalacticSearcherCSCore {
    public static void main(String[] args) {
        new GalacticSearcherCSCore().run();
    }

    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }
    
    public void run() {
        NetworkTableInstance inst = NetworkTableInstance.getDefault();
        NetworkTable table = inst.getTable("datatable");
        NetworkTableEntry selectedGSPath = table.getEntry("selectedGSPath");
        inst.startDSClient();  // recommended if running on DS computer; this gets the robot IP from the DS

        HttpCamera camera = new HttpCamera("Limelight", "http://10.23.63.11:5802");
        System.out.println("Limelight capture opened: " + camera.isConnected());
        CvSink cvSink = new CvSink("opencv_http Camera_limelight");
        cvSink.setSource(camera);

        //initialize image sources for reference images
        List<GalacticSearchPath> paths = new ArrayList<GalacticSearchPath>();
        paths.add(new GalacticSearchPath(PathName.ARED, "C:\\GRIP\\ARed.jpg"));
        paths.add(new GalacticSearchPath(PathName.ABLUE, "C:\\GRIP\\ABlue.jpg"));
        paths.add(new GalacticSearchPath(PathName.BRED, "C:\\GRIP\\BRed.jpg"));
        paths.add(new GalacticSearchPath(PathName.BBLUE, "C:\\GRIP\\BBlue.jpg"));

        for (GalacticSearchPath path : paths) {
            path.readImage();
            System.out.println(path.getPathName().toString() + " reference image is size " + path.getImage().rows() + " rows by " + path.getImage().cols() + " columns");
        }

        //create pipeline
        Mat frame = new Mat();
        cvSink.grabFrame(frame);
        System.out.println("Video capture is size " + frame.rows() + " rows by " + frame.cols() + " columns");

        //run pipeline
        while (camera.isConnected()) { //capture.isOpened()
            cvSink.grabFrame(frame);
            while (!frame.empty()) {  //capture.read(frame)
                for (GalacticSearchPath path : paths) {
                    GripPipeline pipeline = new GripPipeline();
                    pipeline.process(frame, path.getImage());
                    double meanvalue = findMeanValue(pipeline.cvAbsdiffOutput());
                    path.setMeanValue(meanvalue);
                }
                Collections.sort(paths);
                PathName selectedPath = paths.get(0).getPathName();
                System.out.println("Best path is " + selectedPath.toString());
                selectedGSPath.setValue(selectedPath.ordinal());
            }
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