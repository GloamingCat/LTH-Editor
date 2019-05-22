package data.field;

import data.subcontent.Script;

public class CharTile {
	
	public String key = "charKey";
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
	
	public Script loadScript = new Script();
	public Script collideScript = new Script();
	public Script interactScript = new Script();
	
	public CharTile() {}

	public String toString() {
		return key;
	}
	
}