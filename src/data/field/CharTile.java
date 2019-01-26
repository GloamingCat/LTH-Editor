package data.field;

import data.subcontent.Script;

public class CharTile {
	
	public String key = "charKey";
	public boolean persistent = false;
	
	public int x = 0;
	public int y = 0;
	public int h = 0;
	
	public int party = -1;
	public int charID = 11;
	public int battlerID = -1;
	
	public String animation = "Idle";
	public int row = 7;
	
	public Script startScript = new Script();
	public Script collisionScript = new Script();
	public Script interactScript = new Script();
	
	public CharTile() {}

	public String toString() {
		return key;
	}
	
}