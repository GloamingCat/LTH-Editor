package data;

import data.subcontent.Node;
import data.subcontent.Quad;
import data.subcontent.Tag;
import data.subcontent.Transform;
import lwt.dataestructure.LDataList;

public class GameCharacter extends Data {

	public int battlerID = -1;
	public LDataList<Tile> tiles = new LDataList<>();
	
	public LDataList<Node> animations = new LDataList<>();
	public Transform animXform = Transform.neutral();
	public LDataList<Portrait> portraits = new LDataList<>();
	public Transform portraitXform = Transform.neutral();
	public LDataList<Tag> tags = new LDataList<>();
	
	public GameCharacter() {
		Node anim = new Node();
		animations.add(anim);
		tiles.add(new Tile());
	}
	
	public static class Portrait {
		public String name = "New portrait";
		public Quad quad = new Quad();
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
			return "(" + dx + "," + dy + ")";
		}
		
		public boolean equals(Object other) {
			if (other instanceof Tile) {
				Tile t = (Tile) other;
				return t.dx == dx && t.dy == dy;
			} else {
				return false;
			}
		}
	}
	
}
