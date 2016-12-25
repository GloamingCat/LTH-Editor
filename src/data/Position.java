package data;

public class Position {
	
	public int fieldID = 0;
	public int x = 0;
	public int y = 0;
	public int z = 0;
	public int direction = 270;
	
	public String toString() {
		return "Field " + fieldID + " (" + x + "," + y + "," + z + ")" + ", " + direction;
	}
	
}