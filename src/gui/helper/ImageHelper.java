package gui.helper;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Transform;
import org.eclipse.swt.widgets.Display;
import org.eclipse.wb.swt.SWTResourceManager;

import project.Project;
import data.Animation;
import data.Obstacle;
import data.Quad;
import data.Ramp.PointSet;

public class ImageHelper {

	private static FieldPainter fieldPainter = new FieldPainter(2, false);
	
	public static Image getObstacleImage(int id) {
		Obstacle obj = (Obstacle) Project.current.obstacles.getList().get(id);
		String path = obj.quad.imagePath;
		Image image = SWTResourceManager.getImage(Project.current.imagePath() + path);
		
		int w = obj.quad.width;
		int h = obj.quad.height;
		int srcX = obj.quad.x;
		int srcY = obj.quad.y;
		
		return getImageQuad(image, srcX, srcY, w, h);
	}
	
	public static Image getAnimationFrame(Animation anim, int col, int row) {
		String path = anim.imagePath;
		Image image = SWTResourceManager.getImage(Project.current.imagePath() + path);
		
		int w = image.getBounds().width / anim.cols;
		int h = image.getBounds().height / anim.rows;
		
		return getImageQuad(image, w * col, h * row, w, h);
	}
	
	public static Image getStringImage(String s, int w, int h, Color background, boolean borders) {
		Image image = new Image(Display.getCurrent(), w, h);
		//
		GC gc = new GC(image);
		if (background != null) {
			gc.setBackground(background);
			gc.fillRectangle(0, 0, w, h);
		}
		Point size = gc.stringExtent(s);
		int x = (w - size.x) / 2;
		int y = (h - size.y) / 2;
		gc.setForeground(Display.getCurrent().getSystemColor(SWT.COLOR_BLACK));
		gc.drawText(s, x, y);
		if (borders) {
			gc.drawRectangle(2, 2, w - 5, h - 5);
		}
		gc.dispose();
		//
		return image;
	}
	
	public static Image getStringImage(String s, int w, int h, Color background) {
		return getStringImage(s, w, h, background, false);
	}
	
	public static Image getImageQuad(Image image, int x, int y, int w, int h) {
		try {
			Image subImage = new Image(Display.getCurrent(), w, h);
			GC gc = new GC(subImage);
			gc.drawImage(image, x, y, w, h, 0, 0, w, h);
			gc.dispose();
			return subImage;
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
			return SWTResourceManager.getImage("");
		}
	}
	
	public static Image rampImage(PointSet points) {
		Image img = new Image(Display.getCurrent(), (FieldHelper.config.tileW + 4) * 2, 
				(FieldHelper.config.tileH + FieldHelper.config.pixelsPerHeight + 4) * 2);
		GC gc = new GC(img);
		fieldPainter.paintRamp(gc, FieldHelper.config.tileW / 2 + 2, 
				FieldHelper.config.tileH / 2 + 2, points, 1);
		gc.dispose();
		return img;
	}
	
	public static Image transform(data.Transform t, Image original) {
		return transform(t.offsetX, t.offsetY, t.rotation, t.scaleX, t.scaleY, 
				t.red, t.green, t.blue, t.alpha, original);
	}
	
	public static Image transform(int ox, int oy, int rot, int sx, int sy, 
			int r, int g, int b, int a, Image original) {
		int w = original.getBounds().x;
		int W = w * sx / 100;
		int h = original.getBounds().y;
		int H = h * sx / 100;
		int d = (int) Math.sqrt(W * W + H * H) + 1;
		Image img = new Image(original.getDevice(), d, d);
		
		Transform transform = new Transform(original.getDevice());
        transform.translate(w/2 - ox, h - oy);
        transform.scale(sx / 100, sy / 100);
        transform.rotate(rot);
        transform.translate(ox - w/2, oy - h);
		
		GC gc = new GC(img);
		gc.setTransform(transform);
		gc.drawImage(original, 0, 0);
		gc.dispose();
		return img;
	}

	public static Image getImageQuad(Quad s) {
		Image img = SWTResourceManager.getImage(Project.current.imagePath() + s.imagePath);
		return getImageQuad(img, s.x, s.y, s.width, s.height);
	}

}
