package widget;

import music.instruments.Clarinet;
import music.instruments.Contrabass;
import music.instruments.FrenchHorn;
import music.instruments.Guitar;
import music.instruments.Piano;
import music.instruments.Violin;
import scene.GraphicsWrapper;
import scene.Point2D;
import scene.MusicVertex;

public class RadialMenu extends AbstractRadial {
	private boolean shownInstru = false;
	private MusicVertex mv;
	private float radiusInstru = 40;
	
	public RadialMenu() {
		super(30, 100, 6, new float[] {0.7f, 0.7f, 0f});
	}
	
	public void show(float x, float y) {
		super.show(x, y);
		
		// réinitialiser pour ne pas modifier l'ancien
		mv = null;
	}
	
	public boolean isShown() {
		return super.shown || shownInstru;
	}
	
	public MusicVertex close() {
		super.hide();
		shownInstru = false;
		
		return mv;
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
				mv = new MusicVertex(new Piano(), radiusInstru);
				break;
			case 1:
				mv = new MusicVertex(new Guitar(), radiusInstru);
				break;
			case 2:
				mv = new MusicVertex(new Violin(), radiusInstru);
				break;
			case 3:
				mv = new MusicVertex(new Clarinet(), radiusInstru);
				break;
			case 4:
				mv = new MusicVertex(new Contrabass(), radiusInstru);
				break;
			case 5:
				mv = new MusicVertex(new FrenchHorn(), radiusInstru);
				break;
		}
		
		shownInstru = true;
	}
	
	protected void actionOnMove(float x, float y) {
		// faire suivre le composant graphique à l'utilisateur
		if (shownInstru)
			mv.setPosition(x, y);
	}

	public void draw(GraphicsWrapper gw) {
		drawRadial(gw);
		
		if (shownInstru) {
			mv.draw(gw);
		}
	}
}
