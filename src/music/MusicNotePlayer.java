package music;

import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Synthesizer;

import music.instruments.AbstractInstrument;

/**
 * Pour jouer une note de musique sur le SoundBoard
 * @author vince
 *
 */
public class MusicNotePlayer {
	
	private Synthesizer synth; // synthétiseur pour jouer les notes
	private MidiChannel[] midiChannels; // les canaux midi (0 à 15) du synthétiseur
	
	public MusicNotePlayer() {
		try {
			synth = MidiSystem.getSynthesizer();
			synth.open();
			midiChannels = synth.getChannels();
		} catch (MidiUnavailableException e) {
			e.printStackTrace();
		}
	}
	
	 /**
	  * Joue une MusicNote dans le midiChannel 0 du Synthesizer
	  * @param musicNote
	  */
	public void playMusicNote(MusicNote musicNote, AbstractInstrument instrument) {
		//System.out.println("playMusicNote key : " + musicNote.getKey() + ", length : " + musicNote.getNoteLength() + ", velocity : " + musicNote.getVelocity());
		midiChannels[0].programChange(instrument.getBank(), instrument.getProgram());
		midiChannels[0].noteOn(musicNote.getKey(), musicNote.getVelocity());
	}
	
	/**
	 * Arrêter de jouer la MusicNote dans le midiChannel 0 du Synthesizer
	 * @param musicNote
	 */
	public void stopMusicNote(MusicNote musicNote) {
		//System.out.println("stopMusicNote key : " + musicNote.getKey() + ", length : " + musicNote.getNoteLength() + ", velocity : " + musicNote.getVelocity());
		midiChannels[0].noteOff(musicNote.getKey(), musicNote.getVelocity());
	}

}