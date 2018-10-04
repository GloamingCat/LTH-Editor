package data;

import data.subcontent.Icon;
import data.subcontent.Transform;
import lwt.dataestructure.LDataList;

public class Obstacle extends Data {

	// General
	public Icon image = new Icon();
	public Transform transform = new Transform();
	public LDataList<Tile> tiles = new LDataList<>();
	
	public Obstacle() {
		tiles.add(new Tile());
	}
	
	public static class Tile extends GameCharacter.Tile {
		
		public boolean[] neighbors = new boolean[8];
		
		public boolean equals(Object other) {
			if (!super.equals(other))
				return false;
			if (other instanceof Tile) {
				Tile t = (Tile) other;
				for (int i = 0; i < 8; i++) {
					if (neighbors[i] != t.neighbors[i])
						return false;
				}
				return true;
			} else {
				return false;
			}
		}
	}
	
}
