package scene;

import java.io.Serializable;
import java.util.ArrayList;

import music.MusicSample;
import music.instruments.AbstractInstrument;

public class MusicVertex implements Serializable {
	
	private static final long serialVersionUID = -3118443078685533900L;
	
	private AbstractInstrument mi;
	private Point2D position;
	private float radius;
	private float[] color = {0.7f,0.7f,0f};
	private float[] colorStart = {0.7f,0,0.7f};
	private float[] colorSample = {0.0f,0.7f,0.0f};
	private float[] colorSelected = {0f,0f,0.8f};
	private float[] colorPlaying = {0.8f,0.1f,0.0f};
	private Point2D posConnector = null; // connecteur utilisé pour esquisser
	private ArrayList<Connector> conn; // le composant connecté
	private boolean start = false; // identifier si c'est le noeud de départ
	private MusicSample musicSample; // l'échantillon de musique du noeud
	private boolean visited; // pour déterminer si le noeud a été visité ou non
	private long timePosition; // position de départ en millisecondes dans la séquence
	private int channel = -1; // le channel du noeud (un maximum de 15 noeuds, 16 - 1 noeud de percurssion)
	private boolean selected;
	private boolean playing;
	private MusicVertex previousMV; // le noeud précédent
	private transient double progress;
	
	public MusicVertex(AbstractInstrument mi, float radius) {
		this.radius = radius;
		this.mi = mi;
		
		conn = new ArrayList<Connector>();
		
		timePosition = 0;
	}
	
	public MusicVertex(AbstractInstrument mi, float radius, int channel) {
		this.radius = radius;
		this.mi = mi;
		this.channel = channel;
		
		conn = new ArrayList<Connector>();
		
		timePosition = 0;
		musicSample = new MusicSample();
	}
	
	public void setProgress(double progress) {
		this.progress = progress;
	}
	
	public void setPreviousMV(MusicVertex mv) {
		this.previousMV = mv;
	}
	
	public MusicVertex getPreviousMV() {
		return previousMV;
	}
	
	public void setTimePosition(long ticks) {
		timePosition = ticks;
	}
	
	public long getTimePosition() {
		return timePosition;
	}
	
	public boolean isVisited() {
		return visited;
	}
	
	public void setVisited(boolean visited) {
		this.visited = visited;
	}
	
	public int getChannel() {
		return channel;
	}
	
	public void setChannel(int channel) {
		this.channel = channel;
	}
	
	public AbstractInstrument getInstrument() {
		return mi;
	}
	
	public ArrayList<Connector> getConnectors() {
		return conn;
	}
	
	public MusicSample getMusicSample() {
		return musicSample;
	}
	
	public void setMusicSample(MusicSample musicSample) {
		this.musicSample = musicSample;
	}
	
	public float getRadius() {
		return radius;
	}
	
	public void deleteConnIfLinked(MusicVertex mv) {
		Connector toDelete = null;
		
		for (Connector c : conn) {
			if (c.getTarget(this) == mv) {
				toDelete = c;
				break;
			}
		}

		if (toDelete != null)
			conn.remove(toDelete);
	}
	
	public void setPosition(float x, float y) {
		this.position = new Point2D(x, y);
		sortConnectorsByLength();
		for (Connector c : conn) { // aussi trier les connecteurs des enfants
			c.getTarget(this).sortConnectorsByLength();
		}
	}
	
	public boolean isInside(float x, float y) {
		return position.distance(new Point2D(x, y)) <= radius;
	}
	
	public void setStart(boolean isStart) {
		this.start = isStart;
	}
	
	public boolean isStart() {
		return start;
	}
	
	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	
	public void setPlaying(boolean playing) {
		this.playing = playing;
	}
	
	public void doneConnect(MusicVertex mv) {
		posConnector = null;
		
		if (mv != null) {
			Connector newConn = new Connector(this, mv);
			conn.add(newConn);
			sortConnectorsByLength();
			mv.getConnectors().add(newConn);
		}
	}
	
	public void sortConnectorsByLength() {
		for (int i = 0; i < conn.size() - 1; i++) {
			for (int j = 0; j < conn.size() - i - 1; j++) {
				if (conn.get(j).getLength() > conn.get(j + 1).getLength()) {
					Connector tempConnector = conn.get(j);
					conn.set(j, conn.get(j + 1));
					conn.set(j + 1, tempConnector);
				}
			}
		}
	}
	
	public void extendConnector(float x, float y) {
		posConnector = new Point2D(x, y);
	}
	
	public Point2D getPosition() {
		return position;
	}
	
	public void draw(GraphicsWrapper gw) {
		if (playing)
			gw.setColor(colorPlaying[0],colorPlaying[1],colorPlaying[2]);
		else if (selected)
			gw.setColor(colorSelected[0],colorSelected[1],colorSelected[2]);
		else if (start)
			gw.setColor(colorStart[0],colorStart[1],colorStart[2]);
		else if (musicSample != null && !musicSample.getMusicNotes().isEmpty())
			gw.setColor(colorSample[0],colorSample[1],colorSample[2]);
		else
			gw.setColor(color[0],color[1],color[2]);
		gw.setLineWidth(3);
		gw.drawCircle(position.x() - radius, position.y() - radius, radius);
		gw.drawPartOfCircle(position.x(), position.y(), radius, 0, (int) (progress * 360), true);
		mi.drawIcon(gw, position.x(), position.y(), 0.4f, 0.4f);
		gw.setLineWidth(3);
		gw.setColor(1f,1f,1f);
		
		// dessiner le connecteur
		if (posConnector != null) {
			if (!isInside(posConnector.x(), posConnector.y())) {
				// vecteur utilisé pour que le connnecteur commence au bord
				// même longueur pour connected et position, car supposé d'avoir les mêmes longueurs
				Vector2D v = Point2D.diff(position, posConnector);
				float angle = v.angle();
				gw.setColor(color[0],color[1],color[2]);
				gw.drawLine(
						(float) (position.x() - Math.cos(angle) * radius), 
						(float) (position.y() - Math.sin(angle) * radius), 
						(float) (posConnector.x()), 
						(float) (posConnector.y())
				);
				gw.setLineWidth(1);
				gw.setColor(1f,1f,1f);
			}
		}
		
		// s'il y a un composant connecté
		if (conn != null) {
			for (Connector c : conn)
				c.draw(gw);
		}
	}
}
