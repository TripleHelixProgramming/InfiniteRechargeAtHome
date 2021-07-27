package frc.paths;

public abstract class Path {
    public abstract double[][] getPath();
    public static int LEFT_VELOCITY = 0;
    public static int RIGHT_VELOCITY = 1;
    public static int CENTER_POSITION = 2;
    public static int HEADING = 3;
}