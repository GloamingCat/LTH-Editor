package data;

import data.subcontent.Icon;
import data.subcontent.Tile;
import data.subcontent.Transform;
import lwt.dataestructure.LDataList;

public class Obstacle extends Data {

	// General
	public Icon image = new Icon();
	public Transform transform = new Transform();
	public LDataList<ObstacleTile> tiles = new LDataList<>();
	
	public Obstacle() {
		tiles.add(new ObstacleTile());
	}
	
	public static class ObstacleTile extends Tile {
		
		public boolean[] neighbors = new boolean[8];
		public boolean ramp = false;
		
		public boolean equals(Object other) {
			if (!super.equals(other))
				return false;
			if (other instanceof ObstacleTile) {
				ObstacleTile t = (ObstacleTile) other;
				if (ramp != t.ramp)
					return false;
				for (int i = 0; i < 8; i++) {
					if (neighbors[i] != t.neighbors[i])
						return false;
				}
				return true;
			} else {
				return false;
			}
		}
		
		public String toString() {
			String s = super.toString();
			return ramp ? s + " (ramp)" : s;
		}
		
	}
	
}
