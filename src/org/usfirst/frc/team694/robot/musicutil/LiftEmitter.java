package org.usfirst.frc.team694.robot.musicutil;

import org.usfirst.frc.team694.robot.subsystems.Lift;

public class LiftEmitter implements Emitter {

	private Lift lift;

	private double upCScale;
	private double downCScale;

	private int direction = 1;

	private int vel;

	public LiftEmitter(Lift lift, double upCScale, double downCScale) {
		this.lift = lift;
		this.upCScale = upCScale;
		this.downCScale = downCScale;
	}

	protected double getNoteFreq(int note) {
		note -= Player.MIDDLE_C_MIDI;
		double scale = Math.pow(2.0, (double) note/12.0);
		return scale * direction * ((direction == 1) ? upCScale : downCScale);
	}

	@Override
	public void emitNote(int note, int vel) {
		this.vel = vel;

		if (direction == -1 && lift.isAtBottom()) {
			direction = 1;
		} else if (direction == 1 && lift.isAtTop()) {
			direction = -1;
		}

		double freq = getNoteFreq(note);
		lift.moveDangerous(freq);
	}

	@Override
	public void silence() {
		lift.setHeight(lift.getLiftHeight());
	}

	@Override
	public int getVelocity() {
		return vel;
	}

}
