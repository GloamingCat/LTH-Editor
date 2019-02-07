package data.subcontent;

public class Tile {
	
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
			return t.dx == dx && t.dy == dy && t.height == height;
		} else return false;
	}
}