package data;

import data.subcontent.AudioPlay;
import data.subcontent.Property;
import lui.base.data.LDataList;

public class Terrain extends Data {

	// General
	public int animID = -1;
	public LDataList<AudioPlay> sounds = new LDataList<>();
	
	// Movement
	public boolean passable = true;
	public int moveCost = 100;
	public LDataList<Property> jobMoveCost = new LDataList<>(); 
	
	// Status
	public int statusID = -1;
	public boolean removeOnExit;
	
}
