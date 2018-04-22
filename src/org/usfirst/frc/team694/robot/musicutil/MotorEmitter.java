package org.usfirst.frc.team694.robot.musicutil;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

public class MotorEmitter implements Emitter {

	// When we're doing middle c, what should we scale our output to?
	protected double middleCScale;

	protected WPI_TalonSRX motor;

	protected int velocity = 0;

	public MotorEmitter(WPI_TalonSRX motor, double middleCScale) {
		this.motor = motor;
		this.middleCScale = middleCScale;
	}

	// Returns the frequency our motor needs to travel to create a note
	protected double getNoteFreq(int note) {
		return middleCScale * Math.pow(2.0, (double) note/12.0);
	}

	@Override
	public void emitNote(int note, int velocity) {
		this.velocity = velocity;
		double freq = getNoteFreq(note);
		motor.set(ControlMode.PercentOutput, freq);
	}

	@Override
	public void silence() {
		motor.set(ControlMode.PercentOutput, 0);
	}

	@Override
	public int getVelocity() {
		return velocity;
	}

}
