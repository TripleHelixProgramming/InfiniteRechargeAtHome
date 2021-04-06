package frc.ntclient;

//import java.util.*;
import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;

public class GalacticSearchPath implements Comparable<GalacticSearchPath> {
    
    static {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }
    
    public enum PathName {
        ARED,
        ABLUE,
        BRED,
        BBLUE
    }

    private PathName pathname;
    private String filepath;
    private Mat image;
    private double meanvalue;

    public GalacticSearchPath(PathName name, String filepath) {
        this.pathname = name;
        this.filepath = filepath;
    }

    public PathName getPathName() {
        return pathname;
    }

    public String getFilePath() {
        return filepath;
    }

    public Mat getImage() {
        return image;
    }

    public double getMeanValue() {
        return meanvalue;
    }

    public void setPathName(PathName name) {
        this.pathname = name;
    }

    public void setFilePath(String filepath) {
        this.filepath = filepath;
    }

    public void setMeanValue(double meanvalue) {
        this.meanvalue = meanvalue;
    }

    public void readImage() {
        Mat image = Imgcodecs.imread(this.getFilePath());
        this.image = image;
    }

    @Override
    public int compareTo(GalacticSearchPath o) {
        return Double.compare(this.getMeanValue(), o.getMeanValue());
    }
}