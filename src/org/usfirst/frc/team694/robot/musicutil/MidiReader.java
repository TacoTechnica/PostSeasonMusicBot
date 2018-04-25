package org.usfirst.frc.team694.robot.musicutil;

import java.io.File;
import java.io.IOException;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Track;

@Deprecated
public class MidiReader {
	public static final int NOTE_ON = 0x90;
	public static final int NOTE_OFF = 0x80;

	public void readNotes(String fname) {
		Sequence seq = null;
		try {
			seq = MidiSystem.getSequence(new File(fname));
		} catch (InvalidMidiDataException | IOException e) {
			System.out.println("Reading had problems");
			e.printStackTrace();
		}

		for(Track track : seq.getTracks()) {
			System.out.println("Track! size = " + track.size() + "\n");
			for(int i = 0; i < track.size(); i++) {
				MidiEvent event = track.get(i);
				System.out.print("EVENT: tick: " + event.getTick());
				MidiMessage message = event.getMessage();
				if (message instanceof ShortMessage) {
					ShortMessage sm = (ShortMessage) message;
					System.out.print(", command: " + sm.getCommand() + ", channel: " + sm.getChannel() + ", note: " + sm.getData1() + ", vel: " + sm.getData2());
					if (sm.getCommand() == NOTE_ON) {
						System.out.print(": ON!");
					} else if (sm.getCommand() == NOTE_OFF) {
						System.out.print(": OFF!");
					} else {
						System.out.print(": ...");
					}
				}
				System.out.println();
			}
		}
	}
	
	public static void main(String[] args) {
		new MidiReader().readNotes("res/moonlight.mid");
	}
}
