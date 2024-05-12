package data;

import data.subcontent.Icon;
import data.subcontent.Tile;
import data.subcontent.Transform;
import lui.base.data.LDataList;
import lui.datainterface.LInitializable;

public class Obstacle extends Data implements LInitializable {

	// General
	public Icon image = new Icon();
	public Transform transform = new Transform();
	public LDataList<ObstacleTile> tiles = new LDataList<>();
	
	public Obstacle() {}

	@Override
	public void initialize() {
		tiles.add(new ObstacleTile());
	}
	
	public static class ObstacleTile extends Tile {
		
		public boolean[] neighbors = new boolean[8];
		public int mode = 0;

		@Override
		public boolean equals(Object other) {
			if (!super.equals(other))
				return false;
			if (other instanceof ObstacleTile t) {
                if (mode != t.mode)
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

		@Override
		public String toString() {
			String s = super.toString();
			if (mode == 0) {
				return s;
			} else if (mode == 1) {
				return s + " (ramp)";
			} else {
				return s + " (bridge)";
			}
		}
		
	}
	
}
