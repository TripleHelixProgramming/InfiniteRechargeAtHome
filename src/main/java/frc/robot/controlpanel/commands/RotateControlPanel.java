
package frc.robot.controlpanel.commands;

import com.team2363.logger.HelixEvents;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.controlpanel.ControlPanel;

public class RotateControlPanel extends Command {

    private ControlPanel controlPanel = null;

    // How many ticks (of periodic) to run after field color is detected.
    private static final int STOP_COLOR_RUN_TICKS = 5;

    // Command options.
    private int numberOfRevolutions = 1;
    private boolean stopOnFieldColor = false;

    private int stopColorTicks = STOP_COLOR_RUN_TICKS;

    private int revolutionsSpun = 0;

    // Rotates the control panel a specific number of revolutions.
    // This may be implemmented based on timing, using the color value,
    // or distance computed with the encoder.
    // If stopOnFieldColor is set to true, the control panel will rotate
    // revolutions then rotate until the field color requirement is met.
    // The command will handle translating the field color to the color needed
    // at the sensor.
    public RotateControlPanel(int numberOfRevolutions, boolean stopOnFieldColor) {
        controlPanel = ControlPanel.getControlPanel();
        requires(controlPanel);

        this.numberOfRevolutions = numberOfRevolutions;
        this.stopOnFieldColor = stopOnFieldColor;
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
        HelixEvents.getInstance().addEvent("CONTROL PANEL", "Starting RotateControlPanel");

        // Start spinning.
        controlPanel.spinMotor();
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
        
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        
        if (revolutionsSpun < numberOfRevolutions) {
            // Keep going until we spin enough.

            // TODO: There is no code yet that currently determines the rotation count,
            // wether it's timing, encoder, or looking at colors changing.

            return false;
        }

        if (stopOnFieldColor == true) {
            int fieldColor = controlPanel.getFieldRequiredColor();
            int sensorColor = controlPanel.translateFieldColor(fieldColor);

            if (sensorColor == ControlPanel.COLOR_UNKNOWN) {
                // The color is unknown so stop, we're finished.
                return true;
            }

            if (controlPanel.getCurrentColor() == sensorColor) {
                // The color we're interested in has been found.

                // Now that the color is found, keep going until ticks are at zero.
                if (stopColorTicks-- > 0) {
                    return false;
                }

                // No more ticks, and color is found.
                return true;
            }

            // The command needs to run until we find our stop color.
            return false;
        }

        // Default, just stop.
        return true;
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
        HelixEvents.getInstance().addEvent("CONTROL PANEL", "Ending RotateControlPanel");

        // Make sure the motor's off.
        controlPanel.stopMotor();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
        // Stop the motor if interrupted.
        controlPanel.stopMotor();
    }
}