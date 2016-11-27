package data;

import lwt.dataestructure.LDataList;

public class GameCharacter {

	public String name = "New Character";
	public int colliderSize = 1;
	public int colliderHeight = 1;
	
	public LDataList<Script> startListeners = new LDataList<>();
	public LDataList<Script> collisionListeners = new LDataList<>();
	public LDataList<Script> interactListeners = new LDataList<>();
	
	public LDataList<Node> animations = new LDataList<>();
	public LDataList<Tag> tags = new LDataList<>();
	
	public GameCharacter() {
		Node anim = new Node();
		animations.add(anim);
	}
	
	public String toString() {
		return name;
	}
	
}
