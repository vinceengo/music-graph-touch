package music;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.Sequence;

import music.instruments.AbstractInstrument;
import scene.Connector;
import scene.MusicVertex;

/**
 * Pour bâtir la séquence de musique qui sera jouer par le MusicPlayer
 * @author Vincent
 *
 */
public class MusicSequenceBuilder {
	
	private long ticks; // temps en ticks (millisecondes) pour la composition de la séquence
	private ArrayList<MusicVertex> lMv;
	private ArrayList<Object[]> lTimedMV;
	
	public MusicSequenceBuilder(ArrayList<MusicVertex> lMv) {
		ticks = 0; // on débute à 0
		this.lMv = lMv;
	}
	
	public ArrayList<Object[]> getLTimedMV() {
		return lTimedMV;
	}
	
	/**
	 * Bâtir la séquence de musique
	 * @param startSM : Le noeud de départ
	 * @param tempoTimingType
	 * @param timingResolution
	 * @throws InvalidMidiDataException
	 */
	public Sequence buildMusicSequence(MusicVertex mvRoot, float tempoTimingType, int timingResolution) 
			throws InvalidMidiDataException {
		Sequence sequence = new Sequence(tempoTimingType, timingResolution); // music sequence
		Queue<MusicVertex> queue = new LinkedList<MusicVertex>();
		
		lTimedMV = new ArrayList<Object[]>();
		
		resetVisitedMusicVertex(); // remettre les noeuds à non visité
		
		// ajouter l'échantillon de musique au temps donné
		addMusicSample(mvRoot.getMusicSample(), sequence, mvRoot.getInstrument(), ticks);
		lTimedMV.add(new Object[]{ mvRoot, ticks });
		//System.out.println("test object " + ((MusicVertex)lTimedMV.get(0)[0]).getInstrument().getName());
		
		queue.add(mvRoot);
		mvRoot.setVisited(true);
		
		while (!queue.isEmpty()) {
			MusicVertex mv = queue.remove(); // extraire le noeud inséré en premier
			ticks += mv.getTimePosition(); // incrémenter les ticks avec le temps de départ du noeud courant
			System.out.println("ticks de la queue : " + ticks);
			System.out.println("dans boucle queue pas vide");
			
			for (Connector c : mv.getConnectors()) {
				MusicVertex mvChild = c.getTarget(mv); // récupérer la cible du connecteur
				System.out.println("dans boucle connecteur");
				
				if (!mvChild.isVisited()) {
					System.out.println("si enfant non visité");
					if (mvChild.getMusicSample() != null) { // si échantillon existe
						if (!mvChild.getMusicSample().getMusicNotes().isEmpty()) { // si échantillon pas vide
							System.out.println("échantillon pas vide");
							addMusicSample(mvChild.getMusicSample(), sequence, mvChild.getInstrument(), ticks + (Math.round(c.getLength()) * 5));
							lTimedMV.add(new Object[]{ mvChild, ticks + (Math.round(c.getLength()) * 5) });
							mvChild.setTimePosition(Math.round(c.getLength()) * 5);
							System.out.println("Longueur du connecteur * 5 (millisecondes) : " + (Math.round(c.getLength()) * 5));
							System.out.println("ticks add : " + ticks);
							
							queue.add(mvChild);
							mvChild.setVisited(true);
						} else { // échantillon vide, continuer quand même au prochain
							System.out.println("échantillon vide, continuer quand même au prochain");
							mvChild.setTimePosition(Math.round(c.getLength()) * 5);
							System.out.println("Longueur du connecteur * 5 (millisecondes) : " + (Math.round(c.getLength()) * 5));
							System.out.println("ticks add : " + ticks);
							queue.add(mvChild);
							mvChild.setVisited(true);
						}
					}
				}
			}
		}
		return sequence;
	}
	
	private void addMusicSample(MusicSample musicSample, Sequence sequence, AbstractInstrument instrument, long startTick) {
		try {
			musicSample.buildTrack(sequence, instrument, startTick);
		} catch (InvalidMidiDataException e) {
			e.printStackTrace();
			System.out.println("Instrument invalide.");
		}
	}
	
	private void resetVisitedMusicVertex() {
		for (MusicVertex mv : lMv) {
			mv.setVisited(false);
			System.out.println("resetVisitedMusicVertex()");
		}
	}

}
