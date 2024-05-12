package data.subcontent;

import gui.Vocab;

public class Transformation {
	
	public static final String[] types = new String[] {
			Vocab.instance.OFFSETX,
			Vocab.instance.OFFSETY,
			Vocab.instance.OFFSETDEPTH,
			Vocab.instance.SCALEX,
			Vocab.instance.SCALEY,
			Vocab.instance.ROTATION,
			Vocab.instance.RED,
			Vocab.instance.GREEN,
			Vocab.instance.BLUE,
			Vocab.instance.ALPHA,
			Vocab.instance.HUE,
			Vocab.instance.SATURATION,
			Vocab.instance.BRIGHTNESS
	}; 

	public int type = 0;
	public int value = 0;
	public boolean override = false;
	
	public String toString() {
		return types[type] + ": " + value;
	}
	
}
