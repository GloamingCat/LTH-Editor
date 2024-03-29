package data.field;

import data.subcontent.Position;
import data.subcontent.Tile;
import lwt.dataestructure.LDataList;

public class Transition {

	public Transition() { }

	public Position destination = new Position();
	public Position tl = null;
	public Position br = null;
	public Portal origin = new Portal();
	public int fade = 30;
	
	public String toString() {
		return destination.toString();
	}
	
	public Transition clone() {
		Transition t = new Transition();
		t.destination = destination.clone();
		if (tl != null) {
			t.tl = tl.clone();
			t.br = br.clone();
		}
		t.origin = origin.clone();
		return t;
	}
	
	public void convert() {
		System.out.println(tl.toString());
		System.out.println(br.toString());
		origin = new Portal();
		for (int h = tl.h; h <= br.h; h++) {
			for (int x = tl.x; x <= br.x; x++) {
				for (int y = tl.y; y <= br.y; y++) {
					System.out.println(x + " " + y + " " + h);
					origin.add(new Tile(x, y, h));
				}
			}
		}
		tl = null;
		br = null;
	}
	
	public static class Portal extends LDataList<Tile> {
		
		private static final long serialVersionUID = -8436984653173379845L;
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
			Portal p = new Portal();
			for (Tile t : this) {
				p.add(t.clone());
			}
			return p;
		}
		
	}
	
}
