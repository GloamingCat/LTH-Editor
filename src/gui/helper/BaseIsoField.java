package gui.helper;

import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;

import data.subcontent.Point;
import gui.views.fieldTree.FieldCanvas;
import lwt.LImageHelper;
import gui.views.fieldTree.FieldCanvas.PainterThread;

public abstract class BaseIsoField extends FieldMath {
	
	
	public BaseIsoField(int rows) {
		super(rows, 315);
	}
	
	@Override
	public Point pixelSize(int sizeX, int sizeY) {
		return new Point((sizeX + sizeY - 1) * (conf.tileW + conf.tileB) / 2 + (conf.tileW - conf.tileB) / 2,
						 (sizeX + sizeY - 1) * (conf.tileH + conf.tileS) / 2 + (conf.tileH - conf.tileS) / 2);
	}
	
	@Override
	public Point pixelCenter(int sizeX, int sizeY, int height) {
		Point s = pixelSize(sizeX, sizeY);
		return new Point((s.x - conf.tileW) / 2 , - height * conf.pixelsPerHeight / 2);
	}
	
	@Override
	public int pixelDisplacement(int height) {
		return (height + 1) * (conf.tileH + conf.tileS) / 2;
	}

	@Override
	public Point pixel2Tile(float x, float y, float d) {
		float newH = d / conf.pixelsPerHeight;
		float sxy = x * 2 / (conf.tileW + conf.tileB);			// sum
		float dyx = (y + newH) * 2 / (conf.tileH + conf.tileS); // dif
		int newX = Math.round((sxy - dyx) / 2);
		int newY = Math.round((sxy + dyx) / 2);
		return new Point(newX, newY);
	}

	@Override
	public Point tile2Pixel(float x, float y, float h) {
		float newHeight = h * conf.pixelsPerHeight;
		int newX = Math.round((x + y) * (conf.tileW + conf.tileB) / 2);
		int newY = Math.round((y - x) * (conf.tileH + conf.tileS) / 2 - newHeight);
		return new Point(newX, newY);
	}
	
	// -------------------------------------------------------------------------------------
	// Field Canvas
	// -------------------------------------------------------------------------------------
	
	private class IsoPainterThread extends PainterThread {
		
		private FieldCanvas canvas;
		private int ki, kj;
		private Point size;
		
		public IsoPainterThread(FieldCanvas canvas, Point size, int ki, int kj) {
			super();
			this.ki = ki;
			this.kj = kj;
			this.size = size;
			this.canvas = canvas;
		}

		public void run() {
			int tileCount = Math.max(canvas.field.sizeX, canvas.field.sizeY) - 1;
			Image img = LImageHelper.newImage(size.x + (FieldHelper.config.grid.tileW + 
					FieldHelper.config.grid.tileB) * tileCount, size.y);
			GC gc = new GC(img);
			liney = canvas.y0 + tile2Pixel(ki, kj, 0).y - size.y + FieldHelper.config.grid.tileH;
			for(int i = ki, j = kj; i < canvas.field.sizeX && j < canvas.field.sizeY; i++, j++) {
				Point pos = FieldHelper.math.tile2Pixel(i, j, 0);
				int x = canvas.x0 + pos.x - size.x / 2;
				gc.drawImage(canvas.tileImages[i][j], 0, 0, size.x, size.y, x, 0, size.x, size.y);
			}
			gc.dispose();
			line = LImageHelper.correctTransparency(img);
		}
		
	}
	
	public PainterThread[] getPainterThreads(FieldCanvas canvas) {
		final PainterThread[] threads = new PainterThread[canvas.field.sizeX + canvas.field.sizeY - 1];
		final Point tsize = canvas.tileImageSize();
		
		for(int k = canvas.field.sizeX - 1; k >= 0; k--) {
			int p = canvas.field.sizeX - k - 1;
			threads[p] = new IsoPainterThread(canvas, tsize, k, 0);
			threads[p].start();
		}
		for(int k = 1; k < canvas.field.sizeY; k++) {
			int p = k + canvas.field.sizeX - 1;
			threads[p] = new IsoPainterThread(canvas, tsize, 0, k);
			threads[p].start();
		}
		return threads;
	}
	
}
