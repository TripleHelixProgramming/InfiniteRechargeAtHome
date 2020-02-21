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
import frc.paths.Biggie;
import frc.paths.Right;
import frc.paths.RightSweep;
import frc.paths.ThruTrenchToBaseLine;
import frc.robot.shooter.Position;

public class AutoRoutines {
	
	// AutoType Order must match paths order below.
	public enum AutoMode {
        // Auto (delay for intake)
		MIDFIELD_AUTO(25.0, 0.5),				// Midfield Auto
		TRENCH_AUTO(30.0, 2.5),					// Our Trench auto
		SUPER_AUTO(0.0, 1.0),					// Our Super Auto
		BASELINE_AUTO(0.0, 0.0),				// Get off the baseline
		TEST_RIGHT_TURN(0.0, 0.0),				// For tuning
		TEST_3FEET_FORWARD(0.0, 0.0),			// For tuning
		NONE(0.0, 0.0);							// Don't run any auto
		
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
		// case MIDFIELD_AUTO:
		// 	return new AutoCG(
		// 		Position.MIDFIELD_SHOOT,
		// 		mode.getPigeonOffset(),
		// 		mode.getDelay(),
		// 		new MidFieldAuto(),
		// 		new MidFieldAutoPhase2() 
		// 	);
		// case SUPER_AUTO:
		// 	return new SuperAutoCG(
		// 		Position.MIDFIELD_SHOOT,
		//		Position.TRENCH_SHOOT,
		// 		mode.getDelay(),     	// delay 1
		//		2.0,					// delay 2
		// 		new OpponentTrench(),
		// 		new OppTrenchToMidField().
		//		new MidFieldThruOurTrench(),
		//		new ThruTrenchToTrenchShoot()
		// 	);
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
			return new AutoCG(new Biggie());
		default:  
			// Auto Mode of NONE or unkown mode passed in, so no auto command
			return null;
		}
	}
	
	public static AutoMode getSelectedAutoMode() {

		if (!left.get()) {
			return AutoMode.SUPER_AUTO;
		} else if (!right.get()) {  // Our Side only auto
			return AutoMode.TRENCH_AUTO;
		} else if (!middle.get()) { 
			return AutoMode.MIDFIELD_AUTO;
        } else {
            return AutoMode.BASELINE_AUTO;
        }
	}

	private static void putSmartDash(AutoMode mode) {
		SmartDashboard.putString("AUTO MODE:", mode.toString());
	}	
}