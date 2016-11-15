package gui.helper;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Display;

import data.Config;
import data.Ramp;
import project.Project;

public class TilePainter {

	private static int scale = 1;
	
	public static void setScale(int i) {
		scale = i;
	}
	
	public static void paintTile(GC gc, int x0, int y0) {
		Point[] shift = FieldHelper.math.vertexShift;
		int[] p = new int[shift.length * 2];
		for(int i = 0; i < shift.length; i++) {
			p[i * 2] = (shift[i].x + x0) * scale;
			p[i * 2 + 1] = (shift[i].y + y0) * scale;
		}
		gc.drawPolygon(p);
	}
	
	public static void paintRamp(Image image, int x0, int y0, int id) {
		Ramp ramp = (Ramp) Project.current.ramps.getList().get(id);
		Config conf = FieldHelper.config;
		GC gc = new GC(image);
		paintRamp(gc, x0 - conf.tileW / 2, y0 - conf.tileH / 2, ramp);
	}
	
	public static void paintRamp(GC gc, int x0, int y0, Ramp ramp) {
		Config conf = FieldHelper.config;
		gc.setBackground(new Color(Display.getCurrent(), new RGB(127, 127, 127)));
		paintTile(gc, x0, y0 + conf.pixelsPerHeight);
		Point b1 = new Point((ramp.b1x + x0) * scale, (ramp.b1y + y0) * scale);
		Point t1 = new Point((ramp.t1x + x0) * scale, (ramp.t1y + y0) * scale);
		Point b2 = new Point((ramp.b2x + x0) * scale, (ramp.b2y + y0) * scale);
		Point t2 = new Point((ramp.t2x + x0) * scale, (ramp.t2y + y0) * scale);
		int[] p = new int[] {b1.x, b1.y + conf.pixelsPerHeight * scale, t1.x, t1.y, 
				t2.x, t2.y, b2.x, b2.y + conf.pixelsPerHeight * scale};
		gc.drawPolygon(p);
		gc.drawLine(t1.x, t1.y, t1.x, t1.y + conf.pixelsPerHeight * scale);
		gc.drawLine(t2.x, t2.y, t2.x, t2.y + conf.pixelsPerHeight * scale);
	}
	
}

