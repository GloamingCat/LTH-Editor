package data.subcontent;

import org.eclipse.swt.graphics.Rectangle;

public class Quad {

	// Texture
	public String path = "";
	
	// Quad
	public int x = 0;
	public int y = 0;
	public int width = 0;
	public int height = 0;
	
	public Quad clone() {
		Quad q = new Quad();
		q.x = x;
		q.y = y;
		q.width = width;
		q.height = height;
		q.path = path;
		return q;
	}
	
	public boolean equals(Object other) {
		if (other == null)
			return false;
		if (other instanceof Quad) {
			Quad q = (Quad) other;
			return q.x == x && q.y == y && q.width == width && q.height == height && q.path.equals(path);
		} else {
			return false;
		}
	}
	
	public Rectangle getRectangle() {
		return new Rectangle(x, y, width, height);
	}
	
}
