package org.usfirst.frc.team694.robot.musicutil;

/** Emitter.java
 * 
 * This emits noise at a certain note that we give it
 * 
 */

public abstract class Emitter {
	protected int vel;

	public void emitNote(int note, int vel) {
		this.vel = vel;
	}

	public abstract void silence();

	public int getVelocity() {
		return vel;
	}
}
