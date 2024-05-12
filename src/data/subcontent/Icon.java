package data.subcontent;

import data.Animation;
import lui.graphics.LRect;
import project.Project;

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
	
	public String fullPath() {
		Animation anim = getAnimation();
		return anim == null ? null : anim.quad.fullPath();
	}

	public LRect getRectangle() {
		Animation anim = getAnimation();
		if (anim == null)
			return null;
		return anim.getCell(col, row);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Icon other) {
            return other.id == id && other.col == col && other.row == row;
		} else return false;
	}

	@Override
	public String toString() {
		Animation anim = (Animation) Project.current.animations.getTree().get(id);
		String name = (anim == null ? ("NULL " + id) : anim.toString());
		return name + ", " + col + ", " + row;
	}
	
}
