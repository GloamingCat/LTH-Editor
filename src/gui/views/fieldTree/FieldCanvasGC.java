package gui.views.fieldTree;

import gui.helper.FieldHelper;
import gui.helper.FieldPainter;
import lwt.LImageHelper;
import lwt.container.LContainer;

import java.util.ArrayList;
import java.util.Iterator;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;

import data.field.CharTile;
import data.field.FieldImage;
import data.subcontent.Point;

public class FieldCanvasGC extends FieldCanvas {

	// Image cache
	public Image[][] tileImages;
	public FieldPainter painter;

	public FieldCanvasGC(LContainer parent) {
		super(parent);
		painter = new FieldPainter(1);
	}

	//////////////////////////////////////////////////
	// {{ Draw
	

	protected class PainterThread extends Thread {
		public int liney;
		public Image line;
		private Point size;
		private ArrayList<Point> tiles;
		
		public PainterThread(Point size, ArrayList<Point> tiles) {
			super();
			this.size = size;
			this.tiles = tiles;
		}

		public void run() {
			int imgW = FieldHelper.math.lineWidth(field.sizeX, field.sizeY);
			Image img = LImageHelper.newImage(size.x + imgW, size.y);
			GC gc = new GC(img);
			Point o = tiles.get(0);
			liney = y0 + FieldHelper.math.tile2Pixel(o.x, o.y, 0).y - size.y + FieldHelper.config.grid.tileH;
			for(Point p : tiles) {
				Point pos = FieldHelper.math.tile2Pixel(p.x, p.y, 0);
				int x = x0 + pos.x - size.x / 2;
				gc.drawImage(tileImages[p.x][p.y], 0, 0, size.x, size.y, x, 0, size.x, size.y);
			}
			gc.dispose();
			line = LImageHelper.correctTransparency(img);
		}
		
	}

	protected void drawLines(GC gc) {
		final Point tsize = tileImageSize();
		Iterator<ArrayList<Point>> it = FieldHelper.math.lineIterator(field.sizeX, field.sizeY);
		ArrayList<PainterThread> threads = new ArrayList<>();
		while (it.hasNext()) {
			ArrayList<Point> list = it.next();
			if (list.isEmpty())
				continue;
			PainterThread thread = new PainterThread(tsize, list);
			threads.add(thread);
			thread.start();
		}
		try {
			for (PainterThread thread : threads) {
				thread.join();
				gc.drawImage(thread.line, 0, thread.liney);
				thread.line.dispose();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
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
		drawLines(gc);
		for (FieldImage bg : field.prefs.images) {
			if (bg.visible && bg.foreground)
				painter.paintBackground(field, bg, x0, y0, gc);
		}
		gc.dispose();
	}
	
	public void drawCursor(GC gc, Color color, Point point) {
		float previousScale = painter.scale;
		painter.scale = scale * 0.75f;
		gc.setForeground(color);
		painter.drawTile(gc, Math.round(scale * (point.x + x0)), Math.round(scale * (point.y + y0)));
		painter.scale = previousScale;
	}
	// }}

	//////////////////////////////////////////////////
	// {{ Tile Images
	
	public void onDispose() {
		super.onDispose();
		clearTileImages(0, 0);
	}
	
	public void onTileChange(int x, int y) {
		updateTileImage(x, y, true);
	}
	
	public void onTileChange(ArrayList<Point> tiles) {
		for (Point p : tiles) {
			updateTileImage(p.x, p.y, true);
		}
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
		tileImages[x][y] = painter.createTileImage(field, x, y, showGrid,
				currentLayer, currentChar);
		if (!updateNeighbors)
			return;
		for(int k = 0; k < shift.length; k++) {
			int _x = x + shift[k].x;
			int _y = y + shift[k].y;
			if (_x >= 0 && _x < field.sizeX && _y >= 0 && _y < field.sizeY) {
				if (tileImages[_x][_y] != null)
					tileImages[_x][_y].dispose();
				tileImages[_x][_y] = painter.createTileImage(field, _x, _y, showGrid,
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
					tileImages[i][j] = painter.createTileImage(field, i, j, showGrid,
							currentLayer, currentChar);
				}
			}
		}
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
	// }}

	//////////////////////////////////////////////////
	// {{  Visualization

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

	public void refresh() {
		if (field == null) {
			clearTileImages(0, 0);
		} else {
			updateAllTileImages();
		}
		super.refresh();
	}
	// }}

}
