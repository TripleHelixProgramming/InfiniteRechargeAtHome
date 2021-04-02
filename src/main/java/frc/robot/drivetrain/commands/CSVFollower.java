package frc.robot.drivetrain.commands;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import com.team2363.controller.PIDController;

import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj.Notifier;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public abstract class CSVFollower extends Command {
  private Notifier pathNotifier = new Notifier(this::moveToNextSegment);
  private Notifier pidNotifier = new Notifier(this::calculateOutputs);

  private String filename;
  private String line = null;
  private boolean mirror, reverse, isFinished;
  private double mCurrentDistance, mTargetDistance;
  private double mCurrentHeading, mTargetHeading; 
  private double mLeftVelocity, mRightVelocity;
  private BufferedReader pathReader;
  private String[] timestamp;

  public CSVFollower(String filename) {
    try {
      this.filename = "home/lvuser/local/paths/trajectories/" + filename + ".csv";
      pathReader = new BufferedReader(new FileReader(this.filename));
      pathReader.readLine();
      line = pathReader.readLine();
      timestamp = line.split(",");
    } catch (IOException e) {
      isFinished = true;
    }
  }

  public CSVFollower mirror() {
    mirror = true;
    return this;
  }

  public CSVFollower reverse() {
    reverse = true;
    return this;
  }

  public abstract void resetDistance();
  public abstract PIDController getHeadingController();
  public abstract PIDController getDistanceController();
  public abstract double getCurrentDistance();
  public abstract double getCurrentHeading();
  public abstract void useOutputs(double left, double right);

  @Override
  protected void initialize() {
    resetDistance();
    getDistanceController().reset();
    getHeadingController().reset();
    isFinished = false;

    pathNotifier.startPeriodic(0.02);
    pidNotifier.startPeriodic(getDistanceController().getPeriod());
  }

  @Override
  protected void execute() {
    SmartDashboard.putNumber("Distance Path Error", getDistanceController().getError());
    SmartDashboard.putNumber("Heading Path Error", getHeadingController().getError());
  }

  @Override
  protected boolean isFinished() {
    return isFinished;
  }

  @Override
  protected void end() {
    try {
      pathReader.close();
    } catch (IOException e) {

    }
    pathNotifier.stop();
    pidNotifier.stop();
  }

  @Override
  protected void interrupted() {
    end();
  }


  private void moveToNextSegment() {
    try {
      line = pathReader.readLine();
      if (line == null) {
        isFinished = true;
      } else {
        timestamp = line.split(",");
      }
    } catch (IOException e) {

    }
  }

  private void calculateOutputs() {

    if (line == null) return;
    
    mCurrentDistance = getCurrentDistance();
    mTargetDistance = reverse ? -getIndex(1) : getIndex(1);
    mCurrentHeading = getCurrentHeading();
    mTargetHeading = mirror ? -getIndex(0) : getIndex(0);
    mLeftVelocity = getIndex(mirror ^ reverse ? 3 : 2);
    mRightVelocity = getIndex(mirror ^ reverse ? 2 : 3);

    if (reverse) {
      mLeftVelocity *= -1;
      mRightVelocity *= -1;
    }

    getDistanceController().setReference(mTargetDistance);
    getHeadingController().setReference(mTargetHeading);

    double correctedLeftVelocity = mLeftVelocity + getDistanceController().calculate(mCurrentDistance) - getHeadingController().calculate(mCurrentHeading);
    double correctedRightVelocity = mRightVelocity + getDistanceController().calculate(mCurrentDistance) + getHeadingController().calculate(mCurrentHeading);

    useOutputs(correctedLeftVelocity, correctedRightVelocity);
  }

  public double getIndex(int index) {
    return Double.parseDouble(timestamp[index].replaceAll("\\s", ""));
  }
}