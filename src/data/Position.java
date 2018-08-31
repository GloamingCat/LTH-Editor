package data;

public class Position {
	
	public int fieldID = 0;
	public int x = 0;
	public int y = 0;
	public int z = 0;
	public int direction = 270;
	
	public Position() {}
	
	public Position(Position copy) {
		this.fieldID = copy.fieldID;
		this.x = copy.x;
		this.y = copy.y;
		this.z = copy.z;
		this.direction = copy.direction;
	}
	
	public String toString() {
		String dir = direction == -1 ? "" : ", " + direction + "°";
		return "Field " + fieldID + " (" + x + "," + y + "), Layer " + z + dir;
	}
	
	public boolean equals(Object original) {
		if (original instanceof Position) {
			Position p = (Position) original;
			return p.fieldID == fieldID && p.direction == direction
					&& p.x == x && p.y == y && p.z == z;
		} else {
			return false;
		}
	}
	
	public Position clone() {
		return new Position(this);
	}
	
}