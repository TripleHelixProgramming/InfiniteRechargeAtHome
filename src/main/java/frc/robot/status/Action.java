/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.status;

public class Action implements Runnable {

    // If this returns true the action runner thread will stop invoking run on this action.
    public boolean isFinished() {
        return true;
    }

    // How long to delay the action runner thread before calling run again.
    // The thread will have a minium that is enforced if this value is to low.
    public double getDelay() {
        return 0.0;
    }

    // Every loop of the action runner thread will call this method.
    public void run() {
        
    }
}
