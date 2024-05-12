package data.subcontent;

public class Tile implements Cloneable {
	
	public int dx = 0;
	public int dy = 0;
	public int height = 0;
	
	public Tile() {}
	
	public Tile(int x, int y, int h) {
		dx = x;
		dy = y;
		height = h;
	}
	
	public String toString() {
		return "(" + dx + "," + dy + "," + height + ")";
	}
	
	public boolean equals(Object other) {
		if (other instanceof Tile t) {
            return t.dx == dx && t.dy == dy && t.height == height;
		} else return false;
	}
	
	public Tile clone() {
        try {
            Tile tile = (Tile) super.clone();
			tile.dx = dx;
			tile.dy = dy;
			tile.height = height;
			return tile;
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
	}
	
}