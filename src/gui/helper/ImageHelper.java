package gui.helper;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Display;
import org.eclipse.wb.swt.SWTResourceManager;

import project.Project;
import data.Animation;
import data.Obstacle;
import data.Ramp.PointSet;

public class ImageHelper {

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
	
	public static Image getStringImage(String s, int w, int h) {
		Image image = new Image(Display.getCurrent(), w, h);
		//
		GC gc = new GC(image);
		Point size = gc.stringExtent(s);
		int x = (w - size.x) / 2;
		int y = (h - size.y) / 2;
		gc.setForeground(Display.getCurrent().getSystemColor(SWT.COLOR_BLACK));
		gc.drawText(s, x, y);
		gc.dispose();
		//
		return image;
	}
	
	public static Image getImageQuad(Image image, int x, int y, int w, int h) {
		try {
			//System.out.println(image.getBounds().width + " " + image.getBounds().height);
			//System.out.println(w + " " + h);
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
		TilePainter.setScale(2);
		TilePainter.paintRamp(gc, FieldHelper.config.tileW / 2 + 2, 
				FieldHelper.config.tileH / 2 + 2, points, 1);
		gc.dispose();
		return img;
	}

}
