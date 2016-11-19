package data;

import lwt.dataestructure.LDataList;

public class Terrain {

	// General
	public String name = "";
	public boolean passable = true;
	public int depth = 0;
	
	// Animation
	public String imagePath = "";
	public int frameCount = 1;
	public int duration = 60;
	
	// Only on battle
	public int statusID;
	public boolean removeOnExit;
	public int lifeTime = -1; // lifeTime <= 0 for no lifeTime
	
	// Audio
	public Audio audio = new Audio();
	
	// Tags
	public LDataList<Tag> tags = new LDataList<>();
	
	public String toString() {
		return name;
	}
	
}
