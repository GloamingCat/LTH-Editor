package gui.widgets;

import data.Animation;
import data.subcontent.Icon;
import data.subcontent.Node;
import data.subcontent.Quad;
import data.subcontent.Transform;
import lui.base.data.LPoint;
import lui.container.LContainer;
import lui.container.LImage;
import lui.graphics.LColor;
import lui.graphics.LPainter;
import project.Project;

import javax.swing.*;

public class TransformedImage extends LImage {

	private final LColor originColor = new LColor(UIManager.getColor("textText"));

    public TransformedImage(LContainer parent) {
        super(parent);
		addPainter(new LPainter() {
			@Override
			public void paint() {
				this.setPaintColor(originColor);
				int x = (int) getImageX();
				int y = (int) getImageY();
				x -= 1;
				drawLine(x - 2, y, x + 2, y);
				drawLine(x, y - 2, x, y + 2);
			}
		});
    }

	public void update(Icon icon, Transform transform, boolean colorOnly) {
		update(icon == null ? -1 : icon.id, transform, colorOnly);
	}

	public void update(Node node, Transform transform, boolean colorOnly) {
		update(node == null ? -1 : node.id, transform, colorOnly);
	}

	public void update(int id, Transform transform, boolean colorOnly) {
		Animation anim = (Animation) Project.current.animations.getTree().get(id);
		if (anim != null) {
			resetTransform();
			anim.transform.applyTo(this);
			if (transform != null) {
				if (colorOnly)
					transform.applyColorTo(this);
				else
					transform.applyTo(this);
			}
			if (anim.quad.path.isEmpty())
				setImage((String) null);
			else
				setImage(Project.current.imagePath() + anim.quad.path, anim.quad);
		} else {
			setImage((String) null);
		}
		repaint();
	}

	public void updateImage(Icon icon) {
		updateImage(icon == null ? -1 : icon.id);
	}

	public void updateImage(Node n) {
		updateImage(n == null ? -1 : n.id);
	}

	public void updateImage(int id) {
		Animation anim = (Animation) Project.current.animations.getTree().get(id);
		updateImage(anim == null ? null : anim.quad);
	}

	public void updateImage(Quad quad) {
		if (quad != null) {
			if (quad.path.isEmpty()) {
				setImage((String) null);
				return;
			}
			setImage(Project.current.imagePath() + quad.path, quad);
		} else {
			setImage((String) null);
		}
		repaint();
	}

	public void updateOffset(int id, LPoint offset) {
		Animation anim = (Animation) Project.current.animations.getTree().get(id);
		if (anim != null)
			setOffset(offset.x + anim.transform.offsetX, offset.y + anim.transform.offsetY);
		else
			setOffset(offset.x, offset.y);
		repaint();
	}

	public void updateScale(int id, LPoint scale) {
		Animation anim = (Animation) Project.current.animations.getTree().get(id);
		if (anim != null)
			setScale((scale.x * anim.transform.scaleX) / 10000f, (scale.y * anim.transform.scaleY) / 10000f);
		else
			setScale(scale.x / 100f, scale.y / 100f);
		repaint();
	}

	public void updateRotation(int id, int rotation) {
		Animation anim = (Animation) Project.current.animations.getTree().get(id);
		if (anim != null)
			setRotation(rotation + anim.transform.rotation);
		else
			setRotation(rotation);
		repaint();
	}

	public void updateRGBA(int id, int[] color) {
		Animation anim = (Animation) Project.current.animations.getTree().get(id);
		if (anim != null)
			setRGBA((color[0] * anim.transform.red) / 65025f, (color[1] * anim.transform.green) / 65025f,
					(color[2] * anim.transform.blue) / 65025f, (color[3] * anim.transform.alpha) / 65025f);
		else
			setRGBA(color[0] / 255f, color[1] / 255f, color[2] / 255f, color[3] / 255f);
		repaint();
	}

	public void updateHSV(int id, int[] color) {
		Animation anim = (Animation) Project.current.animations.getTree().get(id);
		if (anim != null)
			setHSV(color[0] + anim.transform.hue, (color[1] * anim.transform.saturation) / 10000f,
					(color[2] * anim.transform.brightness) / 10000f);
		else
			setHSV(color[0], color[1] / 100f, color[2] / 100f);
		repaint();
	}

}
