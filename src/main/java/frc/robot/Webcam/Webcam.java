/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.Webcam;

import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Add your docs here.
 */
public class Webcam extends Subsystem {
  private static Webcam INSTANCE = null;

  private Mat referenceImage;
  private Mat testImage;

  public static Webcam getWebcam() {
    if (INSTANCE == null) INSTANCE = new Webcam();
    return INSTANCE;
  }

  public Webcam() {
    UsbCamera camera = CameraServer.getInstance().startAutomaticCapture();
    camera.setResolution(640, 480);
  }

  public Mat getImage() {
    Mat mat = new Mat();
    // CameraServer.getInstance().getVideo().grabFrame(mat);
    return mat;
  }

  public void compareImages() {
    SmartDashboard.putNumber("Similarity", Imgproc.compareHist(referenceImage, testImage, 0));
  }

  public void setReferenceImage() {
    referenceImage = getImage();
  }

  public void setTestImage() {
    testImage = getImage();
  }

  @Override
  public void initDefaultCommand() {
  }
}