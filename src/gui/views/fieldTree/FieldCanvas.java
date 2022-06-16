package gui.views.fieldTree;

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
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.wb.swt.SWTResourceManager;

import data.field.CharTile;
import data.field.Field;
import data.field.FieldImage;
import data.field.Layer;
import data.field.Party;
import data.subcontent.Point;

public class FieldCanvas extends LView {

	public Field field;
	public Layer currentLayer;
	public Party currentParty;
	public CharTile currentChar;
	public float scale = 1;
	public int x0;
	public int y0;
	
	protected Point mousePoint = new Point(0, 0);
	protected int tileX = 0;
	protected int tileY = 0;
	protected int height = 0;
	protected Point clickedTile = null;
	protected int clickedHeight = 0;
	
	protected Image buffer;

	public static abstract class PainterThread extends Thread {
		public int liney;
		public Image line;
	}
	
	// Image cache
	public Image[][] tileImages;
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
	        		if (clickedTile != null) {
	        			Point clickPoint = FieldHelper.math.tile2Pixel(clickedTile.x, 
	        					clickedTile.y, clickedHeight);
	        			drawCursor(e.gc, painter.cursorColor, clickPoint);
	        		}
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
		final PainterThread[] threads = FieldHelper.math.getPainterThreads(this);
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
	
	public void drawCursor(GC gc) {
		drawCursor(gc, getForeground(), mousePoint);
	}

	public void drawCursor(GC gc, Color color, Point point) {
		float previousScale = painter.scale;
		painter.scale = scale * 0.75f;
		gc.setForeground(color);
		painter.drawTile(gc, Math.round(scale * (point.x + x0)), Math.round(scale * (point.y + y0)));
		painter.scale = previousScale;
	}
	
	// -------------------------------------------------------------------------------------
	// Draw in tile image
	// -------------------------------------------------------------------------------------
	
	public void updateTileImage(int x, int y) {
		updateTileImage(x, y, true);
	}
	
	public void updateTileImage(int x, int y, boolean updateNeighbors) {
		try {
			if (tileImages[x][y] != null)
				tileImages[x][y].dispose();
		} catch (IndexOutOfBoundsException e) {
			e.printStackTrace();
			return;
		}
		Point size = tileImageSize();
		painter.imgW = size.x;
		painter.imgH = size.y;
		Point[] shift = FieldHelper.math.neighborShift;
		tileImages[x][y] = painter.createTileImage(field, x, y, 
				currentLayer, currentChar);
		if (!updateNeighbors)
			return;
		for(int k = 0; k < shift.length; k++) {
			int _x = x + shift[k].x;
			int _y = y + shift[k].y;
			if (_x >= 0 && _x < field.sizeX && _y >= 0 && _y < field.sizeY) {
				if (tileImages[_x][_y] != null)
					tileImages[_x][_y].dispose();
				tileImages[_x][_y] = painter.createTileImage(field, _x, _y,
						currentLayer, currentChar);
			}
		}
	}
	
	public void updateAllTileImages() {
		if (field == null) {
			clearTileImages(0, 0);
		} else {
			clearTileImages(field.sizeX, field.sizeY);
			Point size = tileImageSize();
			painter.imgW = size.x;
			painter.imgH = size.y;
			for(int i = 0; i < field.sizeX; i++) {
				for(int j = 0; j < field.sizeY; j++) {
					tileImages[i][j] = painter.createTileImage(field, i, j, 
							currentLayer, currentChar);
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
	
	public Point tileImageSize() {
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
			y0 = (FieldHelper.math.pixelDisplacement(field.sizeY) + x0 +
					FieldHelper.config.grid.pixelsPerHeight * field.layers.maxHeight());
		}
		setSize(Math.round((pixelSize.x + x0*2 - FieldHelper.config.grid.tileB) * scale), 
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
			if (party != null)
				height = party.h - 1;
			redraw();
		}
	}
	
	public void setCharacter(CharTile tile) {
		if (tile != currentChar) {
			if (currentChar != null) {
				int oldx = currentChar.x;
				int oldy = currentChar.y;
				currentChar = tile;
				if (oldx <= field.sizeX && oldy <= field.sizeY)
					updateTileImage(oldx - 1, oldy - 1, false);
			}
			currentChar = tile;
			if (tile != null) {
				height = tile.h - 1;
				updateTileImage(tile.x - 1, tile.y - 1, false);
			}
			redrawBuffer();
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
	
	public void setClickedTile(int x, int y, int h) {
		clickedTile = new Point(x, y);
		clickedHeight = h;
		redraw();
	}
	
}
