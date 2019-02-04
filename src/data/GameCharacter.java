package data;

import project.Project;
import data.subcontent.Icon;
import data.subcontent.Node;
import data.subcontent.Transform;
import lwt.dataestructure.LDataList;

public class GameCharacter extends Data {

	public int battlerID = -1;
	public int shadowID = -1;
	public LDataList<Tile> tiles = new LDataList<>();
	public Transform transform = new Transform();
	public LDataList<Portrait> portraits = new LDataList<>();
	public LDataList<Node> animations = new LDataList<>();
	
	public GameCharacter() {
		animations.add(new Node());
		tiles.add(new Tile());
	}
	
	public Animation defaultAnimation() {
		if (animations.isEmpty())
			return null;
		int id = 0;
		for (Node n : animations) {
			if (n.name.contains("Battle:Idle")) {
				id = n.id;
				break;
			}
		}
		return (Animation) Project.current.animations.getData().get(id);
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
	
	public static class Tile {
		
		public int dx = 0;
		public int dy = 0;
		public int height = 0;
		
		public Tile() {}
		public Tile(Tile i) {
			dx = i.dx;
			dy = i.dy;
		}
		
		public String toString() {
			return "(" + dx + "," + dy + "," + height + ")";
		}
		
		public boolean equals(Object other) {
			if (other instanceof Tile) {
				Tile t = (Tile) other;
				return t.dx == dx && t.dy == dy && t.height == t.height;
			} else return false;
		}
	}
	
}
