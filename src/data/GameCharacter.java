package data;

import data.subcontent.Icon;
import data.subcontent.Node;
import data.subcontent.Transform;
import lwt.dataestructure.LDataList;

public class GameCharacter extends Data {

	public int battlerID = -1;
	public int shadowID = -1;
	public LDataList<Tile> tiles = new LDataList<>();
	public Transform transform = Transform.neutral();
	public LDataList<Portrait> portraits = new LDataList<>();
	public LDataList<Node> animations = new LDataList<>();
	
	public GameCharacter() {
		animations.add(new Node());
		tiles.add(new Tile());
	}
	
	public static class Portrait extends Icon {
		public String name = "New portrait";
		public String toString() {
			return name;
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
			} else {
				return false;
			}
		}
	}
	
}
