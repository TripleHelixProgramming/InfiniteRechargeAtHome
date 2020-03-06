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
import frc.paths.TrenchLayupPartOne;
import frc.paths.TrenchLayupPartTwo;
import frc.paths.BaselineAuto;
import frc.paths.OppTrenchLayupPartOne;
import frc.paths.OppTrenchLayupPartTwo;
import frc.paths.RendLayupPartOne;
import frc.paths.RendLayupPartTwo;
import frc.paths.Right;
import frc.paths.RightSweep;
import frc.robot.drivetrain.Drivetrain;
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
		REND_LAYUP(0.0, 0.0),
		TRENCH_LAYUP(0.0, 0.0),
		OPP_TRENCH_LAYUP(0.0, 0.0);
		
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
	private static DigitalInput rendLayup = new DigitalInput(2);
	private static DigitalInput oppTrenchLayup = new DigitalInput(3);
	private static DigitalInput trenchLayup = new DigitalInput(4);

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
			return new FarAutoCG(
				new Right(),
				new RightSweep(),
				true
			);
		case BASELINE_AUTO:   
			// Just get off the baseline
			return new FarAutoCG(
				new BaselineAuto(), 
				null, 
				false);
		case REND_LAYUP:
			return new LayupAutoCG(
				new RendLayupPartOne(),
				new RendLayupPartTwo()
			);
		case TRENCH_LAYUP:
			return new LayupAutoCG(
				new TrenchLayupPartOne(),
				new TrenchLayupPartTwo()
			);
		case OPP_TRENCH_LAYUP:
			return new LayupAutoCG(
				new OppTrenchLayupPartOne(),
				new OppTrenchLayupPartTwo()
			);
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
		} else if (!rendLayup.get()) { 
			return AutoMode.REND_LAYUP;
        } else if (!oppTrenchLayup.get()){
			return AutoMode.OPP_TRENCH_LAYUP;
		} else if (!trenchLayup.get()) {
			return AutoMode.TRENCH_LAYUP;
		} else {
            return AutoMode.NONE;
        }
	}

	private static void putSmartDash(AutoMode mode) {
		SmartDashboard.putString("AUTO MODE:", mode.toString());
	}	
}