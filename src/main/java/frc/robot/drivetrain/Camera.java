/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.drivetrain;

import static edu.wpi.first.networktables.NetworkTableInstance.getDefault;

import edu.wpi.first.wpilibj.Preferences;

public class Camera {

    private String name;
    private double cameraAlignment;

    static double cameraHeight = 21.25; // (inches) currently the height on the programming bot
    static double bottomTargetHeight = 81.25; // (inches) use 89.75 for actual arena height
    static double cameraElevation = 29.05; //(degrees) currently the angle on the programming bot

    static double g = 386.4;
    static double h = 44;
    static double r = 2.5;
    static double shooterElevation = 29;

    public Camera(String name) {
        this.name = name;
        cameraAlignment = Preferences.getInstance().getDouble(name + "-alignment", 0);
    }

    public double getCameraAlignment() {
        return cameraAlignment;
    }

    public void setCameraAlignment(double cameraAlignment) {
        this.cameraAlignment = cameraAlignment;
        Preferences.getInstance().putDouble(name + "-alignment", cameraAlignment);
    }

    public void setToMode() {
        getDefault().getTable(name).getEntry("pipeline").setNumber(0);
    }
    
    public void setDockingMode() {
        getDefault().getTable(name).getEntry("pipeline").setNumber(1);
        getDefault().getTable(name).getEntry("ledMode").setNumber(3);
        // NetworkTableInstance.getDefault().getTable(name).getEntry("pipeline").setNumber(1);
        getDefault().getTable(name).getEntry("stream").setNumber(0);
    }

    // public void setMiddleDockingMode() {
    //     getDefault().getTable(name).getEntry("pipeline").setNumber(2);
    //     getDefault().getTable(name).getEntry("ledMode").setNumber(0);
    //     NetworkTableInstance.getDefault().getTable(name).getEntry("pipeline").setNumber(2);
    //     getDefault().getTable(name).getEntry("stream").setNumber(0);
    // }

    public void setDriverMode() {
        getDefault().getTable(name).getEntry("pipeline").setNumber(0);
        getDefault().getTable(name).getEntry("ledMode").setNumber(1);
        // NetworkTableInstance.getDefault().getTable(name).getEntry("pipeline").setNumber(2);
        getDefault().getTable(name).getEntry("stream").setNumber(0);
    }

    public boolean isTargetFound() {
        double v = getDefault().getTable(name).getEntry("tx").getDouble(0);
        return v != 0;
    }

    public boolean isTargetClose() {
        return getVerticalDegreesToTarget() > getCameraAlignment();
    }

    public double getRotationalDegreesToTarget() {
        return getDefault().getTable(name).getEntry("tx").getDouble(0);
    }

    public double getVerticalDegreesToTarget() {
        return getDefault().getTable(name).getEntry("ty").getDouble(0);
    }

    public double getAreaOfTarget() {
        return getDefault().getTable(name).getEntry("ta").getDouble(0);
    }

    public void getType() {
        // SmartDashboard.putString("Type", "" + getDefault().getTable(name).getEntry("tcornx").getNumberArray(new Number [4])[0]);
    }

    public double getTargetSkew() {
        return getVerticalDegreesToTarget() / (getAreaOfTarget() - Math.abs(getRotationalDegreesToTarget() * 0.01));
    }

    public double calculateDistanceToTarget() {
        return (bottomTargetHeight - cameraHeight)/Math.tan(Math.toRadians(cameraElevation + getVerticalDegreesToTarget()));
        // calculates ground distane from robot to target, only accurate when tx = 0
    }

    public double calculateRPM() {

        // double calcRPMNum = Math.pow(calculateDistanceToTarget(),2)*g;
        // double calcRPMDenFirstTerm = calculateDistanceToTarget()*Math.sin(Math.toRadians(2*shooterElevation));
        // double calcRPMDenSecondTerm = 2*h*Math.pow(Math.cos(Math.toRadians(shooterElevation)), 2);
        // double linearVelocity = Math.sqrt((calcRPMNum)/(calcRPMDenFirstTerm-calcRPMDenSecondTerm));
        
        // return (int)((linearVelocity*60)/(2*Math.PI*r));
        return 39.5 * (calculateDistanceToTarget() / 12) + 1987;
    }

    public static void main(String... args) {
        Camera camera = new Camera("x");
        System.out.println(camera.getTargetSkew());
    }
}