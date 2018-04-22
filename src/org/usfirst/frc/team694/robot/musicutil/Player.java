package org.usfirst.frc.team694.robot.musicutil;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Stack;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Track;

/** Player.java
 * 
 *  Plays a song
 *
 */
public class Player extends Thread {

	private static final double SPEED_SCALE = 0.1;

	private static final int NOTE_ON = 0x90;
	private static final int NOTE_OFF = 0x80;

	// Emitters currently mapped to play a note
	private HashMap<Integer, Emitter> noteMap;

	// Emitters ready to be used
	private Stack<Emitter> readyEmitters;

	private Sequence seq;
	private Track track;

	private int eventIndex = 0;

	private long startTime; // in MS

	private MidiEvent currentEvent;

	private boolean running;
	
	public Player(String fname) {
		try {
			seq = MidiSystem.getSequence(new File(fname));
			track = seq.getTracks()[0];
			System.out.println("[Player] TRACK LOADED! " + track.size());
		} catch (InvalidMidiDataException | IOException e) {
			System.out.println("Reading had problems");
			e.printStackTrace();
		}

		noteMap = new HashMap<Integer, Emitter>();
		readyEmitters = new Stack<Emitter>();
	}

	public void addEmitter(Emitter emitter) {
		readyEmitters.push(emitter);
	}

	@Override
	public void run() {
		super.run();
		System.out.println("[Player] START!");

		running = true;
		startTime = System.currentTimeMillis();

		// Start by the 1st note
		startTime += (track.get(0).getTick() - 5) / SPEED_SCALE;

		while(running) {
			long currentTime = (long) (SPEED_SCALE * (System.currentTimeMillis() - startTime));
//			System.out.println("time: " + currentTime + ", " + track.get(0).getTick());

			if (eventIndex >= track.size()) {
				running = false;
				break;
			}

			// Play all of the current notes
			while(currentEvent == null || currentEvent.getTick() <= currentTime) {
				currentEvent = track.get(eventIndex);
				MidiMessage m = currentEvent.getMessage();
				if (m instanceof ShortMessage) {
					ShortMessage sm = (ShortMessage) m;
					int note = sm.getData1();
					int vel = sm.getData2();

					if (sm.getCommand() == NOTE_ON) {
						System.out.println("[Player] Note on: note " + note);
						// If we need an emitter here and we have one ready, pop it off and put it here
						if (!noteMap.containsKey(note)) {
							if (!readyEmitters.isEmpty()) {
								Emitter emitter = readyEmitters.pop();
								emitter.emitNote(note, vel);
								noteMap.put(note, emitter);
							} else {
								System.out.println("[Player] Run out of emitters. Checking if we have any emitters with smaller velocity");
								// Find smallest emitter
								int smallestVel = vel;
								int smallestVelNote = note;
								Emitter smallestEmitter = null;
								for(Emitter emitter : noteMap.values()) {
									if (emitter.getVelocity() <= smallestVel) {
										smallestVel = vel;
										smallestEmitter = emitter;
									}
								}
								if (smallestEmitter != null) {
									System.out.println("[Player] Replacement emitter found! prev velocity: " + smallestVel + ", new vel: " + vel);
									// Move to proper note in notemap and start playing
									noteMap.remove(smallestVelNote);
									noteMap.put(note, smallestEmitter);
									smallestEmitter.emitNote(note, vel);
								} else {
									System.out.println("[Player] Replacement emitter Not found below vel " + vel);
								}
							}
						}
					} else if (sm.getCommand() == NOTE_OFF) {
						// If we have an emitter there, pop it off and put it in the ready pile
						if (noteMap.containsKey(note)) {
							Emitter emitter = noteMap.remove(note);
							emitter.silence();
							readyEmitters.push(emitter);
						}
					}
				}
				eventIndex++;
			}
		}
		System.out.println("[Player] finish!");
	}

	
/*	// The song
	private LinkedList<Frame> frames;

	// One frame (Every note played at this time, at the same time)
	private class Frame {
		private Stack<Note> notes;
		private double time;
		public Frame(double time) {
			this.time = time;
			notes = new Stack<Note>();
		}
		public void addNote(Note note) {
			notes.push(note);
		}
		public Note popNote() {
			return notes.pop();
		}

		public double getTime() {
			return time;
		}
	}
	*/
}

