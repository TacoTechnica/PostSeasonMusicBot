package org.usfirst.frc.team694.robot.musicutil;

import edu.wpi.first.wpilibj.SpeedController;

public class MotorEmitter extends Emitter {

	// When we're doing middle c, what should we scale our output to?
	protected double middleCScale;

	protected SpeedController motor;

	public MotorEmitter(SpeedController motor, double middleCScale) {
		this.motor = motor;
		this.middleCScale = middleCScale;
	}

	// Returns the frequency our motor needs to travel to create a note
	protected double getNoteFreq(int note) {
		note -= Player.MIDDLE_C_MIDI;
		return middleCScale * Math.pow(2.0, (double) note/12.0);
	}

	@Override
	public void emitNote(int note, int vel) {
		super.emitNote(note, vel);

		double freq = getNoteFreq(note);
		motor.set(freq);
	}

	@Override
	public void silence() {
		motor.set(0);
	}


}
