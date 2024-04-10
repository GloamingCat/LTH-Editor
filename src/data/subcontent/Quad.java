package data.subcontent;

import lui.graphics.LRect;
import project.Project;

public class Quad {

	// Texture
	public String path = "";
	
	// Quad
	public int x = 0;
	public int y = 0;
	public int width = 0;
	public int height = 0;
	
	public Quad() {}
	
	public Quad(String path, int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.path = path;
	}

	public Quad clone() {
		return new Quad(path, x, y, width, height);
	}
	
	public boolean equals(Object other) {
		if (other == null)
			return false;
		if (other instanceof Quad) {
			Quad q = (Quad) other;
			return q.x == x && q.y == y 
					&& q.width == width && q.height == height 
					&& q.path.equals(path);
		} else {
			return false;
		}
	}
	
	public String fullPath() {
		return Project.current.imagePath() + path;
	}
	
	public LRect getRect() {
		return new LRect(x, y, width, height);
	}

}
