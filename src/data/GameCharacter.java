package data;

import lwt.dataestructure.LDataList;

public class GameCharacter {

	public String name = "New Character";
	public int colliderSize = 1;
	public int colliderHeight = 1;
	public String eventsheet = "";
	public String param = "";
	public int direction = 315;
	public LDataList<Node> animations = new LDataList<Node>();
	public LDataList<Tag> tags = new LDataList<Tag>();
	
	public GameCharacter() {
		Node anim = new Node();
		animations.add(anim);
	}
	
	public String toString() {
		return name;
	}
	
}
