package data.subcontent;

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
	
}
