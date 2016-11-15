package data;

import lwt.dataestructure.LDataList;

public class Terrain {

	// General
	public String name = "";
	public boolean passable = true;
	public int depth = 0;
	
	// Graphics
	public String imagePath = "";
	public int frameCount = 1;
	public int frameDuration = 7;
	
	// Only on battle
	public int statusID;
	public boolean removeOnExit;
	public int lifeTime = -1; // lifeTime <= 0 for no lifeTime
	
	public LDataList<Tag> tags = new LDataList<>();
	
	public String toString() {
		return name;
	}
	
}
