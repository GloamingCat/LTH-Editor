package data.subcontent;

public class Point {
	
	public int x = 0;
	public int y = 0;
	//public int z = 0;
	
	public Point() {}
	public Point(Point i) {
		x = i.x;
		y = i.y;
	}

	public Point(int x, int y) {
		this.x = x;
		this.y = y;
		//this.z = 0;
	}
	
	public String toString() {
		return "(" + x + "," + y + ")";
	}
	
	public boolean equals(Object other) {
		if (other instanceof Point) {
			Point t = (Point) other;
			return t.x == x && t.y == y;// && t.z == z;
		} else return false;
	}
	
	public Point clone() {
		return new Point(x, y);
	}
	
}