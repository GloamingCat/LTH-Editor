package data;

import data.subcontent.Audio;
import data.subcontent.Bonus;
import lwt.dataestructure.LDataList;

public class Terrain extends Data {

	// General
	public int animID = -1;
	public LDataList<Audio> sounds = new LDataList<>();
	
	// Movement
	public boolean passable = true;
	public int moveCost = 100;
	public LDataList<Bonus> jobMoveCost = new LDataList<>(); 
	
	// Status
	public int statusID = -1;
	public boolean removeOnExit;
	
}
