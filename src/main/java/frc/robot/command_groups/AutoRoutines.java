/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.command_groups;

import com.team2363.logger.HelixEvents;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.paths.RightTurn;
import frc.paths.ThreeFeetForward;
import frc.paths.Right;
import frc.paths.RightSweep;
import frc.robot.shooter.Position;

public class AutoRoutines {
	
	// AutoType Order must match paths order below.
	public enum AutoMode {
        // Auto (delay for intake)
		TRENCH_AUTO(30.0, 2.5),					// Our Trench auto
		BASELINE_AUTO(0.0, 0.0),				// Get off the baseline
		TEST_RIGHT_TURN(0.0, 0.0),				// For tuning
		TEST_3FEET_FORWARD(0.0, 0.0),			// For tuning
		NONE(0.0, 0.0), 						// Don't run any auto
		COLLECT_REND_BALLS(0.0, 0.0);
		
		private double pigeon_offset;
		private double delay;
		
		private AutoMode(double offset, double delay) {
			this.pigeon_offset = offset;
			this.delay = delay;
			
		}
		
		// Get delay in second, ball pickup path for deploying the intake.
		public double getDelay() {
			return delay;
		}

		// Get the angle offset for the pigeon that is associated with the starting
		// autonomous position.
		public double getPigeonOffset() {
			return pigeon_offset;
		}
	}
	
    private static DigitalInput trench = new DigitalInput(0);
    private static DigitalInput baseline = new DigitalInput(1);
	private static DigitalInput midfield = new DigitalInput(2);

	/* 
	 * Base onselectedAutoMode Robot Position on the alliance wall & plates states, determines 
	 * which auto routine to run, gripper height, and whether left & right motion
	 * profiles need to be reverse base on field symmetry.
	 * 
	 */
	public static Command getAutoRoutine (AutoMode mode) {
		
		putSmartDash(mode);
		HelixEvents.getInstance().addEvent("AUTO SELECTION", mode.toString());

		switch (mode) {

		case TRENCH_AUTO:
			return new AutoCG(
				Position.TRENCH_SHOOT,
				mode.getPigeonOffset(),
				mode.getDelay(),
				new Right(),
				new RightSweep()
			);
		case TEST_RIGHT_TURN:
			// Tuning Auto
			return new AutoCG(new RightTurn());
		case TEST_3FEET_FORWARD:
			// Tuning Auto
			return new AutoCG(new ThreeFeetForward());
		case BASELINE_AUTO:   
			// Just get off the baseline
			return new BaselineAutoCG();
		case COLLECT_REND_BALLS:
			return new CollectRendBallsCG();
		case NONE:
		default:  
			// Auto Mode of NONE or unkown mode passed in, so no auto command
			return null;
		}
	}
	
	public static AutoMode getSelectedAutoMode() {

		if (!trench.get()) {
			return AutoMode.TRENCH_AUTO;
		} else if (!baseline.get()) {  // Our Side only auto
			return AutoMode.BASELINE_AUTO;
		} else if (!midfield.get()) { 
			return AutoMode.COLLECT_REND_BALLS;
        } else {
            return AutoMode.NONE;
        }
	}

	private static void putSmartDash(AutoMode mode) {
		SmartDashboard.putString("AUTO MODE:", mode.toString());
	}	
}