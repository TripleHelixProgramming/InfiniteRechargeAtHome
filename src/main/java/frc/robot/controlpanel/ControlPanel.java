/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.controlpanel;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.controlpanel.model.ColorSensor;

public class ControlPanel extends Subsystem {

    private final ColorSensor colorSensor;

    private static ControlPanel INSTANCE = new ControlPanel();

    private ControlPanel() {
        super();
        this.colorSensor = new ColorSensor();
    }

  /**
   * @return the singleton instance of the Drivetrain subsystem
   */
  public static ControlPanel getControlPanel() {
    if (INSTANCE == null) {
      INSTANCE = new ControlPanel();
    }
    return INSTANCE;
  }    

    @Override
    public void initDefaultCommand() {
      // setDefaultCommand(new SampleDrive());
    }

    @Override
    public void periodic() {
        colorSensor.periodic();
    }
  
}