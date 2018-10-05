package data;

import lwt.dataestructure.LDataList;
import data.subcontent.Audio;
import data.subcontent.Bonus;

public class Terrain extends Data {

	// General
	public boolean passable = true;
	public Audio audio = new Audio();
	public int animID = -1;
	
	// Only on battle
	public int moveCost = 100;
	public int statusID = -1;
	public boolean removeOnExit;
	public boolean resetTime = true;
	public int lifeTime = -1; // lifeTime <= 0 for no lifeTime
	
	// Bonus
	public LDataList<Bonus> attributes = new LDataList<>();
	public LDataList<Bonus> elements = new LDataList<>();
	
}
