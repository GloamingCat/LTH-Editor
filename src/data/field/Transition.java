package data.field;

import data.subcontent.Position;
import data.subcontent.Tile;
import lui.base.data.LDataList;

public class Transition implements Cloneable {

	public Transition() { }

	public Position destination = new Position();
	public Portal origin = new Portal();
	public String condition = "";
	
	public String toString() {
		if (condition.isEmpty())
			return destination.toString();
		else
			return destination.toString() + " if " + condition;
	}
	
	public Transition clone() {
        try {
			Transition t = (Transition) super.clone();
			t.destination = destination.clone();
			t.origin = origin.clone();
			t.condition = condition;
			return t;
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
	}
	
	public static class Portal extends LDataList<Tile> {
		public Portal() {}
		
		public Portal(boolean[][][] mask) {
			for (int i = 0; i < mask.length; i++)
				for (int j = 0; j < mask[i].length; j++)
					for (int k = 0; k < mask[i][j].length; k++) 
						if (mask[i][j][k])
							add(new Tile(j + 1, k + 1, i + 1));
		}
		
		public boolean[][][] getMask(int width, int height, int layers) {
			boolean[][][] mask = new boolean[layers][width][height];
			for (Tile tile : this) {
				if (tile.height <= layers && tile.dx <= width && tile.dy <= height) 
					mask[tile.height - 1][tile.dx - 1][tile.dy - 1] = true;
			}
			return mask;
		}
		
		public Portal clone() {
            Portal p = (Portal) super.clone();
            p.replaceAll(Tile::clone);
			return p;
		}
		
	}
	
}
