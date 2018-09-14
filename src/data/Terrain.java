package data;

import data.subcontent.Audio;
import data.subcontent.Quad;
import data.subcontent.Tag;
import lwt.dataestructure.LDataList;

public class Terrain extends Data {

	// General
	public boolean passable = true;
	public int depth = 0;
	
	// Animation
	public Quad quad = new Quad();
	public int frameCount = 1;
	public int duration = 60;
	
	// Only on battle
	public int moveCost = 100;
	public int statusID;
	public boolean removeOnExit;
	public boolean resetTime = true;
	public int lifeTime = -1; // lifeTime <= 0 for no lifeTime
	
	// Audio
	public Audio audio = new Audio();
	
	// Tags
	public LDataList<Tag> tags = new LDataList<>();
	
}
