package data;

import lwt.dataestructure.LDataList;

public class Obstacle {

	// General
	public String name = "";
	public int colliderHeight = 1;
	
	public Quad quad = new Quad();
	public Transform transform = new Transform();
	public LDataList<Tile> tiles = new LDataList<>();
	public LDataList<Tag> tags = new LDataList<>();
	
	public static class Tile {
		public int dx = 0;
		public int dy = 0;
		public int rampID = -1;
		public boolean[] neighbors = new boolean[8];
	}
	
	public Obstacle() {
		tiles.add(new Tile());
	}
	
	public String toString() {
		return name;
	}
	
}
