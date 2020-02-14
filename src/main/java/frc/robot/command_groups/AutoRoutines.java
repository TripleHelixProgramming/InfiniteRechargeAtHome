/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.command_groups;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.paths.BaseLineThruTrench;
import frc.paths.RightTurn;
import frc.paths.ThreeFeetBackward;
import frc.paths.ThreeFeetForward;
import frc.paths.ThruTrenchToBaseLine;
import frc.robot.shooter.Position;

public class AutoRoutines {
	
	// AutoType Order must match paths order below.
	public enum AutoMode {
        // Auto (delay for intake)
		LEFT_AUTO(25.0, 0.5),
		RIGHT_AUTO(30.0, 2.5),
		SUPER_AUTO(0.0, 1.0),
		BASELINE_AUTO(0.0, 0.0),
		TEST_AUTO_CG(0.0, 0.5),
		TEST_RIGHT_TURN(0.0, 0.0),
		TEST_3FEET_FORWARD(0.0, 0.0),
		NONE(0.0, 0.0);
		
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
	
    private static DigitalInput left = new DigitalInput(0);
    private static DigitalInput middle = new DigitalInput(1);
	private static DigitalInput right = new DigitalInput(2);


	/* 
	 * Base onselectedAutoMode Robot Position on the alliance wall & plates states, determines 
	 * which auto routine to run, gripper height, and whether left & right motion
	 * profiles need to be reverse base on field symmetry.
	 * 
	 */
	public static Command getAutoRoutine (AutoMode mode) {
		
		putSmartDash(mode);

		// TO DO:  Uncomment the logic below after paths have been created.

		switch (mode) {
		// case LEFT_AUTO:
		// 	return new AutoCG(
		// 		Position.LEFT_AUTO,
		// 		mode.getPigeonOffset(),
		// 		mode.getDelay(),
		// 		new ThreeFeetBackward(),
		// 		new ThreeFeetForward() 
		// 	);
		// case RIGHT_AUTO:
		// 	return new AutoCG(
		// 		Position.RIGHT_AUTO,
		// 		mode.getPigeonOffset(),
		// 		mode.getDelay(),
		// 		new RightAuto(),
		// 		new RightAutoPhase2() 
		// 	);
		// case SUPER_AUTO:
		// 	return new AutoCG(
		// 		Position.LEFT_AUTO,
		// 		mode.getPigeonOffset(),
		// 		mode.getDelay(),
		// 		new ThreeFeetBackward(),
		// 		new ThreeFeetForward() 
		// 	);
		case TEST_AUTO_CG:
			return new AutoCG(
				Position.DUMP_BALLS,
				mode.getPigeonOffset(),
				mode.getDelay(),
				new ThreeFeetBackward()
			);
		case TEST_RIGHT_TURN:
			// Tuning Auto
			return new AutoCG(new RightTurn());
		case TEST_3FEET_FORWARD:
			// Tuning Auto
			return new AutoCG(new ThreeFeetForward());
		case BASELINE_AUTO:   
			// Just get off the baseline
			return new AutoCG(new ThreeFeetBackward());
		default:  
			// Auto Mode of NONE or unkown mode passed in, so no auto command
			return null;
		}
	}
	
	public static AutoMode getSelectedAutoMode() {

		if (!left.get()) {
			return AutoMode.LEFT_AUTO;
		} else if (!right.get()) {  // Our Side only auto
			return AutoMode.RIGHT_AUTO;
		} else if (!middle.get()) { 
			return AutoMode.SUPER_AUTO;
        } else {
            return AutoMode.BASELINE_AUTO;
        }
	}

	private static void putSmartDash(AutoMode mode) {
		SmartDashboard.putString("AUTO MODE:", mode.toString());
	}	
}