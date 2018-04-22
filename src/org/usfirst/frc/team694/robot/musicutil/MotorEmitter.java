package org.usfirst.frc.team694.robot.musicutil;

import edu.wpi.first.wpilibj.SpeedController;

public class MotorEmitter implements Emitter {

	private static final int MIDDLE_C_MIDI = 60;
	
	// When we're doing middle c, what should we scale our output to?
	protected double middleCScale;

	protected SpeedController motor;

	protected int velocity = 0;

	public MotorEmitter(SpeedController motor, double middleCScale) {
		this.motor = motor;
		this.middleCScale = middleCScale;
	}

	// Returns the frequency our motor needs to travel to create a note
	protected double getNoteFreq(int note) {
		note -= MIDDLE_C_MIDI;
		return middleCScale * Math.pow(2.0, (double) note/12.0);
	}

	@Override
	public void emitNote(int note, int velocity) {
		this.velocity = velocity;
		double freq = getNoteFreq(note);
		motor.set(freq);
	}

	@Override
	public void silence() {
		motor.set(0);
	}

	@Override
	public int getVelocity() {
		return velocity;
	}

}
