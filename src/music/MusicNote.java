package music;

import java.io.Serializable;

/**
 * Une note de musique qui peut être ajoutée à un MusicSample
 * @author Vincent
 *
 */
public class MusicNote implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7503301646628898509L;
	private int key; // la hauteur de la note, entre 21 et 108 pour un piano standard
	private long noteLength; // la durée en millisecondes de la note
	private int velocity; // la vitesse à laquelle la note est jouée (représente le volume)
	private long startTick; // temps à laquelle la note va jouer dans l'échantillon
	
	public MusicNote(int key, int velocity) {
		this.key = key;
		this.velocity = velocity;
	}

	public MusicNote(int key, long noteLength, int velocity) {
		this.key = key;
		this.noteLength = noteLength;
		this.velocity = velocity;
	}
	
	public MusicNote(int key, int velocity, long startTick) {
		this.key = key;
		this.velocity = velocity;
		this.startTick = startTick;
	}
	
	public MusicNote(int key, long noteLength, int velocity, long startTick) {
		this.key = key;
		this.noteLength = noteLength;
		this.velocity = velocity;
		this.startTick = startTick;
	}
	
	public int getKey() {
		return key;
	}

	public void setKey(int key) {
		this.key = key;
	}

	public long getNoteLength() {
		return noteLength;
	}

	public void setNoteLength(int noteLength) {
		this.noteLength = noteLength;
	}
	
	public int getVelocity() {
		return velocity;
	}
	
	public void setStartTick(long startTick) {
		this.startTick = startTick;
	}
	
	public long getStartTick() {
		return startTick;
	}
}