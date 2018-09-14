package data;

import data.subcontent.Audio;
import data.subcontent.Quad;
import data.subcontent.Tag;
import data.subcontent.Transform;
import lwt.dataestructure.LDataList;

public class Animation extends Data {
	
	// General
	public String script = "";
	public Quad quad = new Quad();
	public int rows = 1;
	public int cols = 1;
	public LDataList<Tag> tags = new LDataList<>();
	
	// Transform
	public Transform transform = new Transform();
	
	// Animation
	public String introPattern = "";
	public String introDuration = "";
	public String loopPattern = "";
	public String loopDuration = "";
	public LDataList<Audio> audio = new LDataList<>();
	
}
