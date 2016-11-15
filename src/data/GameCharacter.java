package data;

import lwt.dataestructure.LDataList;

public class GameCharacter {

	public String name = "New Character";
	public int colliderSize = 1;
	public int colliderHeight = 1;
	public String eventsheet = "";
	public String param = "";
	public int direction = 315;
	public LDataList<FieldNode> animations = new LDataList<FieldNode>();
	
	public GameCharacter() {
		FieldNode anim = new FieldNode();
		animations.add(anim);
	}
	
	public String toString() {
		return name;
	}
	
}
