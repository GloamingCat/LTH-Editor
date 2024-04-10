package data.field;

import lui.base.data.LDataList;
import data.subcontent.Script;

public class CharTile {
	
	public String key = "CharKey";
	public boolean persistent = false;
	public boolean passable = false;
	public boolean visible = true;
	
	public int x = 1;
	public int y = 1;
	public int h = 1;
	
	public int party = -1;
	public int charID = -1;
	public int battlerID = -1;
	
	public String animation = "Idle";
	public int frame = 1;
	public int direction = 315;
	
	public int defaultSpeed = 100;
	
	public LDataList<Script> scripts = new LDataList<>();
	public boolean repeatCollisions = false;
	
	public CharTile() {}

	public String toString() {
		return key;
	}
	
}