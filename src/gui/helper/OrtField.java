package gui.helper;

import java.util.ArrayList;

import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;

import data.subcontent.Point;
import gui.views.fieldTree.FieldCanvas;
import gui.views.fieldTree.FieldCanvas.PainterThread;
import lwt.LImageHelper;

public class OrtField extends FieldMath {

	public OrtField() {
		super(6, 270);
	}
	
	protected void removePoints(ArrayList<Point> p) {
		if (!conf.allNeighbors) {
			Point p0 = p.get(0);
			Point p1 = p.get(2);
			Point p2 = p.get(4);
			Point p3 = p.get(6);
			p.remove(p0);
			p.remove(p1);
			p.remove(p2);
			p.remove(p3);
		}
	}
	
	@Override
	public Point pixelSize(int sizeX, int sizeY) {
		return new Point(sizeX * conf.tileW, sizeY * conf.tileH);
	}
	
	@Override
	public Point pixelCenter(int sizeX, int sizeY, int height) {
		return new Point((sizeX * conf.tileW) / 2 , 
				(sizeY * conf.tileH + pixelDisplacement(height)) / 2);
	}
	
	@Override
	public int pixelDisplacement(int height) {
		return 0;
	}

	@Override
	public Point pixel2Tile(float x, float y, float d) {
		float newH = d / conf.pixelsPerHeight;
		int newX = Math.round(x / conf.tileW);
		int newY = Math.round((y + newH) / conf.tileH);
		return new Point(newX, newY);
	}

	@Override
	public Point tile2Pixel(float x, float y, float h) {		
		float newH = h * conf.pixelsPerHeight;
		int newX = Math.round(x * conf.tileW);
		int newY = Math.round(y * conf.tileH - newH);
		return new Point(newX, newY);
	}

	@Override
	public int[] autotile(int[][] grid, int x, int y) {
		int[] rows = new int[] {0, 0, 0, 0};
		if (sameType(grid, x, y, x, y - 1)) {
			rows[0] = 1;
			rows[1] = 1;
		}
		if (sameType(grid, x, y, x, y + 1)) {
			rows[2] = 1;
			rows[3] = 1;
		}
		if (sameType(grid, x, y, x + 1, y)) {
			rows[1] += 2;
			rows[3] += 2;
		}
		if (sameType(grid, x, y, x - 1, y)) {
			rows[0] += 2;
			rows[2] += 2;
		}
		if (rows[3] >= 3 && sameType(grid, x, y, x + 1, y + 1)) {
			rows[3] = 4;
		}
		if (rows[0] >= 3 && sameType(grid, x, y, x - 1, y - 1)) {
			rows[0] = 4;
		}
		if (rows[1] >= 3 && sameType(grid, x, y, x + 1, y - 1)) {
			rows[1] = 4;
		}
		if (rows[2] >= 3 && sameType(grid, x, y, x - 1, y + 1)) {
			rows[2] = 4;
		}
		if (rows[0] == 0 && rows[1] == 0 && rows[2] == 0 && rows[3] == 0) {
			rows[0] = rows[1] = rows[2] = rows[3] = 5;
		}
		return rows;
	}
	
	// -------------------------------------------------------------------------------------
	// Field Canvas
	// -------------------------------------------------------------------------------------
	
	private class OrtPainterThread extends PainterThread {
		
		private FieldCanvas canvas;
		private int j;
		private Point size;
		
		public OrtPainterThread(FieldCanvas canvas, Point size, int j) {
			super();
			this.j = j;
			this.size = size;
			this.canvas = canvas;
		}

		public void run() {
			Image img = LImageHelper.newImage(size.x + (FieldHelper.config.grid.tileW) * canvas.field.sizeX, size.y);
			GC gc = new GC(img);
			liney = canvas.y0 + tile2Pixel(0, j, 0).y - size.y + FieldHelper.config.grid.tileH;
			for(int i = 0; i < canvas.field.sizeX ; i++) {
				Point pos = FieldHelper.math.tile2Pixel(i, j, 0);
				int x = canvas.x0 + pos.x - size.x / 2;
				gc.drawImage(canvas.tileImages[i][j], 0, 0, size.x, size.y, x, 0, size.x, size.y);
			}
			gc.dispose();
			line = LImageHelper.correctTransparency(img);
		}
		
	}
	
	public PainterThread[] getPainterThreads(FieldCanvas canvas) {
		final PainterThread[] threads = new PainterThread[canvas.field.sizeY];
		final Point tsize = canvas.tileImageSize();
		for(int j = 0; j < canvas.field.sizeY; j++) {
			threads[j] = new OrtPainterThread(canvas, tsize, j);
			threads[j].start();
		}
		return threads;
	}

}
