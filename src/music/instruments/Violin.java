package music.instruments;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;

import scene.GraphicsWrapper;

public class Violin extends AbstractInstrument {

	public Violin() {
		this.setVelocity(80);
		this.setDuration(150);
		this.setBank(0);
		this.setProgram(40);
	}
	
	// Pour jouer un patron de musique dans le séquenceur
		public void playSample(int sample, int channel, Sequencer sequencer, Sequence sequence) 
				throws InvalidMidiDataException {
			// créer un File à partir du filePathName
			String filePathName = "samples/violin/sample" + sample + ".txt";
			
			super.playSample(filePathName, channel, sequencer, sequence);
		}
	
	@Override
	public void drawIcon(GraphicsWrapper gw, float transX, float transY, float scaleX, float scaleY) {
		gw.setLineWidth(3);
		gw.localWorldTrans(transX, transY, scaleX, scaleY);
			// icônes "Violon"
			gw.setColor(1, 1, 1);
			// bâton
			int height = 150;
			int lineWidth = 6;
			gw.setLineWidth(lineWidth);
			gw.drawLine(-lineWidth / 2, -height / 2, -lineWidth / 2, height / 2);
			gw.setLineWidth(2);
			int topWidth = 20;
			gw.drawLine(-topWidth / 2 - lineWidth / 2, height / 10 - height / 2, topWidth / 2 - lineWidth / 2, height / 10 - height / 2);
			gw.drawLine(-topWidth / 2 - lineWidth / 2, height / 5 - height / 2, topWidth / 2 - lineWidth / 2, height / 5 - height / 2);
			// corps
			int topCircleWidth = 26;
			int topCircleHeight = 18;
			gw.drawEllipse(-lineWidth / 2, height / 8, topCircleWidth, topCircleHeight);
			int bottomCircleWidth = 35;
			int bottomCircleHeight = 25;
			int gap = 10;
			gw.drawEllipse(-lineWidth / 2, height / 8 + topCircleHeight + bottomCircleHeight - gap, bottomCircleWidth, bottomCircleHeight);
			int smallCircle = 8;
			int space = 20;
			gw.setColor(0.2f,0.2f,0.2f);
			gw.fillCircle(-lineWidth / 2 - smallCircle - space, (topCircleHeight + bottomCircleHeight) / 2, smallCircle);
			gw.fillCircle(-lineWidth / 2 - smallCircle + space, (topCircleHeight + bottomCircleHeight) / 2, smallCircle);
			gw.setColor(1, 1, 1);
			// wire
			gw.setLineWidth(1);
			gw.setColor(0,0,0);
			int wireHeight = 50;
			int wireSpace = 4;
			gw.drawLine(-lineWidth / 2 - wireSpace, height / 8 - topCircleHeight / 2, -lineWidth / 2 - wireSpace, height / 8 + wireHeight - topCircleHeight / 2);
			gw.drawLine(-lineWidth / 2, height / 8 - topCircleHeight / 2, -lineWidth / 2, height / 8 + wireHeight - topCircleHeight / 2);
			gw.drawLine(-lineWidth / 2 + wireSpace, height / 8 - topCircleHeight / 2, -lineWidth / 2 + wireSpace, height / 8 + wireHeight - topCircleHeight / 2);
			gw.setColor(1, 1, 1);
		gw.popMatrix();
		gw.setLineWidth(1);
	}
}