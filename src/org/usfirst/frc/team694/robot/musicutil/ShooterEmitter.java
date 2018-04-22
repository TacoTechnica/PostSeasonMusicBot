package org.usfirst.frc.team694.robot.musicutil;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

public class ShooterEmitter extends MotorEmitter {

	private static final double MIDDLE_C_SPEED = 500;

	public ShooterEmitter(WPI_TalonSRX motor, double middleCScale) {
		super(motor, middleCScale);
	}

	@Override
	public void emitNote(int note, int vel) {
		this.velocity = vel;
		double speed = MIDDLE_C_SPEED * getNoteFreq(note);
		motor.set(ControlMode.Velocity, speed);
	}

}
