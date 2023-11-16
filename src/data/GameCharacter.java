package data;

import project.Project;
import data.subcontent.Icon;
import data.subcontent.Node;
import data.subcontent.Script;
import data.subcontent.Tile;
import data.subcontent.Transform;
import lwt.dataestructure.LDataList;

public class GameCharacter extends Data {

	public int battlerID = -1;
	public int shadowID = -1;
	public int koAnimID = -1;
	public int koFadeout = -1;
	public LDataList<Tile> tiles = new LDataList<>();
	public Transform transform = new Transform();
	public LDataList<Portrait> portraits = new LDataList<>();
	public LDataList<Node> animations = new LDataList<>();
	
	public LDataList<Script> scripts = new LDataList<>();
	public boolean repeatCollisions = false;
	
	public GameCharacter() {
		animations.add(new Node());
		tiles.add(new Tile());
	}
	
	public int defaultAnimationID() {
		if (animations.isEmpty())
			return -1;
		int id = -1;
		for (Node n : animations) {
			if (n.name.contains("Battle:Idle")) {
				id = n.id;
				break;
			}
		}
		if (id == -1 && animations.size() > 0)
			id = animations.get(0).id;
		return id;
	}
	
	public Animation defaultAnimation() {
		int id = defaultAnimationID();
		if (id == -1) {
			return null;
		} else {
			return (Animation) Project.current.animations.getData().get(id);
		}
	}
	
	public int findAnimation(String key) {
		if (animations.isEmpty())
			return -1;
		for (Node n : animations) {
			if (n.name.equals(key))
				return n.id;
		}
		return -1;
	}
	
	public static class Portrait extends Icon {
		
		public String name = "New";
		
		public String toString() {
			//Animation anim = (Animation) Project.current.animations.getTree().get(id);
			//return name + ": " + (anim == null ? "NULL" : anim.toString());
			return name;
		}
		
		public boolean equals(Object obj) {
			if (!super.equals(obj))
				return false;
			if (obj instanceof Portrait) {
				Portrait other = (Portrait) obj;
				return other.name.equals(name);
			} else return false;
		}
		
	}
	
}
