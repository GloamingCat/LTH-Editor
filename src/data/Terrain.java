package data;

import data.subcontent.Audio;
import lwt.dataestructure.LDataList;

public class Terrain extends Data {

	// General
	public int animID = -1;
	public LDataList<Audio> sounds = new LDataList<>();
	
	// Movement
	public boolean passable = true;
	public int moveCost = 100;
	
	// Status
	public int statusID = -1;
	public boolean removeOnExit;
	public boolean allMembers = false;
	
}
