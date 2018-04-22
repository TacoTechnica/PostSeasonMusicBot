package org.usfirst.frc.team694.robot.musicutil;

/** Emitter.java
 * 
 * This emits noise at a certain note that we give it
 * 
 */

public interface Emitter {
	public void emitNote(int note, int vel);
	public void silence();
	public int getVelocity();
}
