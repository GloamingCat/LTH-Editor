package data.subcontent;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;

import project.Project;
import data.Animation;

public class Icon {
	
	public int id = -1;
	public int col = 0;
	public int row = 0;
	
	public Icon() {}
	
	public Icon(int id, int col, int row) {
		this.id = id;
		this.col = col;
		this.row = row;
	}
	
	public Animation getAnimation() {
		return (Animation) Project.current.animations.getTree().get(id);
	}
	
	public String getImagePath() {
		Animation anim = getAnimation();
		return anim == null ? null : anim.quad.path;
	}
	
	public Image getImage() {
		return getAnimation().quad.getImage();
	}

	public Rectangle getRectangle() {
		Animation anim = getAnimation();
		if (anim == null)
			return null;
		if (anim.cols == 0 || anim.rows == 0)
			return null;
		int w = anim.quad.width / anim.cols;
		int h = anim.quad.height / anim.rows;
		int x = anim.quad.x + w * col;
		int y = anim.quad.y + h * row;
		return new Rectangle(x, y, w, h);
	}
	
	public boolean equals(Object obj) {
		if (obj instanceof Icon) {
			Icon other = (Icon) obj;
			return other.id == id && other.col == col && other.row == row;
		} else return false;
	}
	
	public String toString() {
		return id + ", " + col + ", " + row;
	}
	
}
