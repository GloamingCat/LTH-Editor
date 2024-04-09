package data;

import data.subcontent.Audio;
import data.subcontent.Quad;
import data.subcontent.Transform;
import lbase.data.LDataList;
import lwt.graphics.LRect;

public class Animation extends Data {
	
	// General
	public String script = "";
	public Quad quad = new Quad();
	public int rows = 1;
	public int cols = 1;
	
	// Transform
	public Transform transform = new Transform();
	
	// Animation
	public String introPattern = "";
	public String introDuration = "";
	public String loopPattern = "";
	public String loopDuration = "";
	public LDataList<Audio> audio = new LDataList<>();
	
	public int getFrame(int i) {
		try {
			if (introPattern.isEmpty()) {
				if (loopPattern.isEmpty())
					return i;
				else
					return Integer.parseInt(loopPattern.split(" ")[i]);
			} else
				return Integer.parseInt(introPattern.split(" ")[i]);
		} catch (Exception e) {
			e.printStackTrace();
			return i;
		}
	}
	
	public LRect getCell(int i, int j) {
		if (cols == 0 || rows == 0)
			return null;
		int w = quad.width / cols;
		int h = quad.height / rows;
		return new LRect(quad.x + i * w, quad.y + j * h, w, h);
	}

}
