package data;

import data.subcontent.Audio;
import data.subcontent.Quad;
import data.subcontent.Script;
import data.subcontent.Transform;
import lwt.dataestructure.LDataList;

public class Animation extends Data {
	
	// General
	public Script script = new Script();
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
	
	public int getFirstFrame() {
		try {
		if (introPattern.isEmpty()) {
			if (loopPattern.isEmpty())
				return 0;
			else
				return Integer.parseInt(loopPattern.split(" ")[0]);
		} else
			return Integer.parseInt(introPattern.split(" ")[0]);
		} catch (Exception e) {
			return 0;
		}
	}

}
