package data.subcontent;

import lui.graphics.LRect;
import project.Project;

public class Quad extends LRect {

	// Texture
	public String path = "";
	
	public Quad() {}
	
	public Quad(String path, int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.path = path;
	}

	public Quad clone() {
		Quad quad = (Quad) super.clone();
		quad.path = path;
		return quad;
	}
	
	public boolean equals(Object other) {
		if (other == null)
			return false;
		if (other instanceof Quad q) {
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

}
