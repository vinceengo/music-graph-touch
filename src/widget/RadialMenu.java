package widget;

import music.instruments.Clarinet;
import music.instruments.Contrabass;
import music.instruments.FrenchHorn;
import music.instruments.Guitar;
import music.instruments.Piano;
import music.instruments.Violin;
import scene.GraphicsWrapper;
import scene.Point2D;
import scene.SceneMusic;

public class RadialMenu extends AbstractRadial {
	private SceneMusic sm;
	private float radiusInstru = 40;
	
	public RadialMenu(float x, float y) {
		super(30, 100, 6, new float[] {0.7f, 0.7f, 0f});
		position = new Point2D(x, y);
	}
	
	public SceneMusic getSM() {
		return sm;
	}
	
	protected void drawOptions(GraphicsWrapper gw, int level, float x, float y) {
		// Créer l'instrument
		switch (level) {
			case 0:
				new Piano().drawIcon(gw, x, y, 0.4f, 0.4f);
				break;
			case 1:
				new Guitar().drawIcon(gw, x, y, 0.4f, 0.4f);
				break;
			case 2:
				new Violin().drawIcon(gw, x, y, 0.4f, 0.4f);
				break;
			case 3:
				new Clarinet().drawIcon(gw, x, y, 0.4f, 0.4f);
				break;
			case 4:
				new Contrabass().drawIcon(gw, x, y, 0.4f, 0.4f);
				break;
			case 5:
				new FrenchHorn().drawIcon(gw, x, y, 0.4f, 0.4f);
				break;
		}
	}
	
	protected void actionOnSelect(int selected, float x, float y) {
		switch (selected) {
			case 0:
				sm = new SceneMusic(new Piano(), radiusInstru);
				break;
			case 1:
				sm = new SceneMusic(new Guitar(), radiusInstru);
				break;
			case 2:
				sm = new SceneMusic(new Violin(), radiusInstru);
				break;
			case 3:
				sm = new SceneMusic(new Clarinet(), radiusInstru);
				break;
			case 4:
				sm = new SceneMusic(new Contrabass(), radiusInstru);
				break;
			case 5:
				sm = new SceneMusic(new FrenchHorn(), radiusInstru);
				break;
		}
	}
	
	protected void actionOnMove(float x, float y) {
		// faire suivre le composant graphique à l'utilisateur
		if (sm != null)
			sm.setPosition(x, y);
	}

	public void draw(GraphicsWrapper gw) {
		drawRadial(gw);
		
		if (sm != null) {
			sm.draw(gw);
		}
	}
}
