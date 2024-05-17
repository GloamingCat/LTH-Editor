package data.field;

import lui.base.data.LDataList;
import lui.base.data.LInitializable;
import data.GameCharacter;
import data.subcontent.Script;
import project.Project;

public class CharTile implements LInitializable {
	
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
	
	public String animation = "";
	public int frame = 1;
	public int direction = 315;
	
	public int defaultSpeed = 100;
	
	public LDataList<Script> scripts = new LDataList<>();
	public boolean repeatCollisions = false;
	
	public CharTile() {}

	public void initialize() {
		charID = Project.current.characters.getData().findID() - 1;
		if (charID != -1) {
			GameCharacter c = (GameCharacter) Project.current.characters.getData().get(charID);
			animation = c.animations.isEmpty() ? "Idle" : c.animations.getFirst().name;
		}
	}

	@Override
	public String toString() {
		return key;
	}
	
}