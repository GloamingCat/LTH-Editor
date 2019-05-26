package gui.views.fieldTree;

import lwt.LImageHelper;
import lwt.editor.LView;
import gui.helper.FieldHelper;
import gui.helper.FieldPainter;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.wb.swt.SWTResourceManager;

import data.field.Field;
import data.field.FieldImage;
import data.field.Layer;
import data.field.Party;

public class FieldCanvas extends LView {

	public Field field;
	public Layer currentLayer;
	public Party currentParty;
	public float scale = 1;
	public int x0;
	public int y0;
	
	protected Point mousePoint = new Point(0, 0);
	protected int tileX = 0;
	protected int tileY = 0;
	protected int height = 0;
	
	protected Image buffer;

	public class PainterThread extends Thread {
		
		private int ki, kj;
		private Point size;
		
		public int liney;
		public Image line;
		
		public PainterThread(Point size, int ki, int kj) {
			super();
			this.ki = ki;
			this.kj = kj;
			this.size = size;
		}

		public void run() {
			int tileCount = Math.max(field.sizeX, field.sizeY) - 1;
			Image img = LImageHelper.newImage(size.x + (FieldHelper.config.grid.tileW + 
					FieldHelper.config.grid.tileB) * tileCount, size.y);
			GC gc = new GC(img);
			liney = y0 + FieldHelper.math.tile2Pixel(ki, kj, 0).y - size.y + FieldHelper.config.grid.tileH;
			for(int i = ki, j = kj; i < field.sizeX && j < field.sizeY; i++, j++) {
				Point pos = FieldHelper.math.tile2Pixel(i, j, 0);
				int x = x0 + pos.x - size.x / 2;
				gc.drawImage(tileImages[i][j], 0, 0, size.x, size.y, x, 0, size.x, size.y);
			}
			gc.dispose();
			line = LImageHelper.correctTransparency(img);
		}
		
	}
	
	
	// Image cache
	private Image[][] tileImages;
	public FieldPainter painter = new FieldPainter(1, true);

	public FieldCanvas(Composite parent, int style) {
		super(parent, style | SWT.DOUBLE_BUFFERED);
		setBackground(SWTResourceManager.getColor(new RGB(250, 250, 250)));
		
		addPaintListener(new PaintListener() {
	        public void paintControl(PaintEvent e) {
	        	if (field != null) {
	        		e.gc.drawImage(buffer, 
	        			0, 0, 
	        			buffer.getBounds().width, 
	        			buffer.getBounds().height, 
	        				0, 0, 
	        				Math.round(buffer.getBounds().width * scale), 
	        				Math.round(buffer.getBounds().height * scale));
	        		drawCursor(e.gc);
	        	}
	        }
	    });
		
		addMouseMoveListener(new MouseMoveListener() {
			public void mouseMove(MouseEvent e) {
				Point tilePos = FieldHelper.math.pixel2Tile(
						e.x * 1.0f / scale - x0,
						e.y * 1.0f / scale - y0, 
						height * FieldHelper.config.grid.pixelsPerHeight);
				if ((tileX != tilePos.x || tileY != tilePos.y) && field != null) {
					if (tilePos.x >= 0 && tilePos.x < field.sizeX && tilePos.y >= 0 && tilePos.y < field.sizeY) {
						tileX = tilePos.x;
						tileY = tilePos.y;
						mousePoint = FieldHelper.math.tile2Pixel(tileX, tileY, height);
						onTileEnter(tileX, tileY);
						redraw();
					}
				}
			}
		});
		
		addDisposeListener(new DisposeListener() {
			@Override
			public void widgetDisposed(DisposeEvent arg0) {
				clearTileImages(0, 0);
			}
		});
		
	}
	
	public void onTileEnter(int x, int y) { }

	// -------------------------------------------------------------------------------------
	// Draw Buffer
	// -------------------------------------------------------------------------------------
	
	public void redrawBuffer() {
		if (buffer != null)
			buffer.dispose();
		
		Point size = FieldHelper.math.pixelSize(field.sizeX, field.sizeY);
		int w = x0 * 2;
		int h = (FieldHelper.math.pixelDisplacement(field.sizeY) + 200 + 
				FieldHelper.config.grid.pixelsPerHeight * field.layers.maxHeight());
		buffer = new Image(Display.getCurrent(), size.x + w, size.y + h); 
		GC gc = new GC(buffer);
		gc.setBackground(getBackground());
		gc.fillRectangle(buffer.getBounds());
		
		for (FieldImage bg : field.prefs.images) {
			if (bg.visible && !bg.foreground)
				painter.paintBackground(field, bg, x0, y0, gc);
		}

		final PainterThread[] threads = new PainterThread[field.sizeX + field.sizeY - 1];
		final Point tsize = tileImageSize();
		
		for(int k = field.sizeX - 1; k >= 0; k--) {
			int p = field.sizeX - k - 1;
			threads[p] = new PainterThread(tsize, k, 0);
			threads[p].start();
		}
		for(int k = 1; k < field.sizeY; k++) {
			int p = k + field.sizeX - 1;
			threads[p] = new PainterThread(tsize, 0, k);
			threads[p].start();
		}
		
		try {
			for (int i = 0; i < threads.length; i++) {
				threads[i].join();
				gc.drawImage(threads[i].line, 0, threads[i].liney);
				threads[i].line.dispose();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		for (FieldImage bg : field.prefs.images) {
			if (bg.visible && bg.foreground)
				painter.paintBackground(field, bg, x0, y0, gc);
		}
		
		gc.dispose();
	}

	protected void drawCursor(GC gc) {
		float scale = painter.scale;
		painter.scale = scale * 0.75f;
		gc.setForeground(getForeground());
		painter.drawTile(gc, mousePoint.x + x0, mousePoint.y + y0);
		painter.scale = scale;
	}
	
	// -------------------------------------------------------------------------------------
	// Draw in tile image
	// -------------------------------------------------------------------------------------
	
	public void updateTileImage(int x, int y) {
		if (tileImages[x][y] != null)
			tileImages[x][y].dispose();
		Point size = tileImageSize();
		Point[] shift = FieldHelper.math.neighborShift;
		tileImages[x][y] = painter.createTileImage(x, y, size.x, size.y, currentLayer, field);
		for(int k = 0; k < shift.length; k++) {
			int _x = x + shift[k].x;
			int _y = y + shift[k].y;
			if (_x >= 0 && _x < field.sizeX && _y >= 0 && _y < field.sizeY) {
				if (tileImages[_x][_y] != null)
					tileImages[_x][_y].dispose();
				tileImages[_x][_y] = painter.createTileImage(_x, _y, size.x, size.y, currentLayer, field);
			}
		}
		redrawBuffer();
		redraw();
	}
	
	public void updateAllTileImages() {
		if (field == null) {
			clearTileImages(0, 0);
		} else {
			clearTileImages(field.sizeX, field.sizeY);
			Point size = tileImageSize();
			for(int i = 0; i < field.sizeX; i++) {
				for(int j = 0; j < field.sizeY; j++) {
					tileImages[i][j] = painter.createTileImage(i, j, size.x, size.y, currentLayer, field);
				}
			}
		}
		redrawBuffer();
		redraw();
	}
	
	private void clearTileImages(int sizeX, int sizeY) {
		if (tileImages != null) {
			for (int i = 0; i < tileImages.length; i++) {
				for(int j = 0; j < tileImages[i].length; j++) {
					if (tileImages[i][j] != null)
						tileImages[i][j].dispose();
				}
			}
		}
		tileImages = new Image[sizeX][sizeY];
		System.gc();
	}
	
	private Point tileImageSize() {
		int imgW = FieldHelper.config.grid.tileW * 3;
		int imgH = FieldHelper.config.grid.tileH * (field.layers.maxHeight() + 5);
		return new Point(imgW, imgH);
	}
	
	// -------------------------------------------------------------------------------------
	// Visualization
	// -------------------------------------------------------------------------------------

	public void rescale(float scale) {
		this.scale = scale;
		Point pixelSize;
		x0 = 100;
		if (field == null) {
			pixelSize = new Point(0, 0);
			y0 = 0;
		} else {
			pixelSize = FieldHelper.math.pixelSize(field.sizeX, field.sizeY);
			y0 = (FieldHelper.math.pixelDisplacement(field.sizeY) + 200 + 
					FieldHelper.config.grid.pixelsPerHeight * field.layers.maxHeight());
		}
		setSize(Math.round((pixelSize.x + x0*2 - FieldHelper.config.grid.tileW) * scale), 
				Math.round((pixelSize.y + y0) * scale));
	}
	
	public void setCurrentLayer(Layer layer) {
		if (layer != null)
			setHeight(layer.info.height - 1);
		if (currentLayer != layer) {
			currentLayer = layer;
			updateAllTileImages();
		}
	}
	
	public void setHeight(int h) {
		height = h;
	}
	
	public void setParty(Party party) {
		if (party != currentParty) {
			currentParty = party;
			redraw();
		}
	}
	
	public void setShowGrid(boolean value) {
		if (value != painter.showGrid) {
			painter.showGrid = value;
			updateAllTileImages();
		}
	}
	
	public void setField(Field field) {
		if (field == null) {
			this.field = null;
			this.currentLayer = null;
			rescale(1);
			clearTileImages(0, 0);
		} else {
			this.field = field;
			this.currentLayer = null;
			rescale(scale);
			updateAllTileImages();
		}
	}
	
}
