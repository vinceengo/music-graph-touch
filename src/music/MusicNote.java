package music;

/**
 * Une note de musique qui peut être ajoutée à un MusicSample
 * @author Vincent
 *
 */
public class MusicNote {
	
	private int key; // la hauteur de la note
	private int noteLength; // la durée en millisecondes de la note
	
	private final String[] notes = {
			"C", "D", "E", "F", "G", "A", "B"
	};
	
	
	public MusicNote(int key, int noteLength) {
		this.key = key;
		this.noteLength = noteLength;
	}

	public int getKey() {
		return key;
	}

	public void setKey(int key) {
		this.key = key;
	}

	public int getNoteLength() {
		return noteLength;
	}

	public void setNoteLength(int noteLength) {
		this.noteLength = noteLength;
	}
}