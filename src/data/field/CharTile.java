package data.field;

import lwt.dataestructure.LDataList;
import data.subcontent.Script;

public class CharTile {
	
	public String key = "CharKey";
	public boolean persistent = false;
	public boolean passable = false;
	
	public int x = 1;
	public int y = 1;
	public int h = 1;
	
	public int party = -1;
	public int charID = -1;
	public int battlerID = -1;
	
	public String animation = "Idle";
	public int direction = 315;
	
	public LDataList<Script> scripts = new LDataList<>();
	
	public CharTile() {}

	public String toString() {
		return key;
	}
	
}