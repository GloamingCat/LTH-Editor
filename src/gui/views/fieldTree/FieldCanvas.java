package gui.views.fieldTree;

import lwt.editor.LView;
import gui.helper.FieldHelper;
import gui.helper.FieldPainter;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;

import data.Field;
import data.Layer;

public class FieldCanvas extends LView {

	public Field field;
	public float scale = 1;
	protected int x0;
	protected int y0;
	protected Layer currentLayer;

	// Image cache
	private Image[][] tileImages;
	private FieldPainter painter = new FieldPainter(1, true);
	
	public FieldCanvas(Composite parent, int style) {
		super(parent, style | SWT.DOUBLE_BUFFERED);
				
		addPaintListener(new PaintListener() {
	        public void paintControl(PaintEvent e) {
	        	if (field != null && currentLayer != null) {
	        		drawAllTiles(e.gc);
	        	}
	        }
	    });
	}

	private void drawAllTiles(GC egc) {
		Image img = new Image(egc.getDevice(), getSize().x, getSize().y); 
		GC gc = new GC(img);
		gc.setBackground(egc.getBackground());
		painter.paintBackground(field, x0, y0, maxHeight(), gc);

		for(int k = field.sizeX - 1; k >= 0; k--) {
			for(int i = k, j = 0; i < field.sizeX && j < field.sizeY; i++, j++) {
				drawTile(gc, i, j);
			}
		}
		for(int k = 1; k < field.sizeY; k++) {
			for(int i = 0, j = k; i < field.sizeX && j < field.sizeY; i++, j++) {
				drawTile(gc, i, j);
			}
		}
		egc.drawImage(img, 0, 0, img.getBounds().width, img.getBounds().height, 
				0, 0, Math.round(img.getBounds().width * scale), Math.round(img.getBounds().height * scale));
	}
	
	private void drawTile(GC gc, int x, int y) {
		Point pos = FieldHelper.math.tile2Pixel(x, y, 0);
		Image img = tileImages[x][y];
		int w = img.getBounds().width;
		int h = img.getBounds().height;
		gc.drawImage(img, 0, 0, w, h, x0 + pos.x - w / 2, y0 + pos.y - h + FieldHelper.config.tileH, w, h);
	}
	
	// -------------------------------------------------------------------------------------
	// Draw in tile image
	// -------------------------------------------------------------------------------------
	
	public void updateTileImage(int x, int y) {
		if (tileImages[x][y] != null)
			tileImages[x][y].dispose();
		
		int imgW = FieldHelper.config.tileW * 3;
		int imgH = FieldHelper.config.tileH * (maxHeight() + 6);
		
		Point[] shift = FieldHelper.math.neighborShift;
		
		tileImages[x][y] = painter.createTileImage(x, y, imgW, imgH, currentLayer, field);
		for(int k = 0; k < shift.length; k++) {
			int _x = x + shift[k].x;
			int _y = y + shift[k].y;
			if (_x >= 0 && _x < field.sizeX && _y >= 0 && _y < field.sizeY) {
				if (tileImages[_x][_y] != null)
					tileImages[_x][_y].dispose();
				tileImages[_x][_y] = painter.createTileImage(_x, _y, imgW, imgH, currentLayer, field);
			}
		}
		redraw();
	}
	
	public void updateAllTileImages() {
		if (currentLayer == null) {
			if (field == null) {
				clearTileImages(0, 0);
			} else {
				clearTileImages(field.sizeX, field.sizeY);
			}
		} else {
			clearTileImages(field.sizeX, field.sizeY);
			int imgW = FieldHelper.config.tileW * 3;
			int imgH = FieldHelper.config.tileH * (maxHeight() + 6);
			for(int i = 0; i < field.sizeX; i++) {
				for(int j = 0; j < field.sizeY; j++) {
					tileImages[i][j] = painter.createTileImage(i, j, imgW, imgH, currentLayer, field);
				}
			}
		}
		redraw();
	}
	
	private void clearTileImages(int sizeX, int sizeY) {
		if (tileImages != null) {
			for (int i = 0; i < tileImages.length; i++) {
				for(int j = 0; j < tileImages[0].length; j++) {
					if (tileImages[i][j] != null)
						tileImages[i][j].dispose();
				}
			}
		}
		tileImages = new Image[sizeX][sizeY];
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
			y0 = (FieldHelper.math.pixelDisplacement(field.sizeY) + 200 + FieldHelper.config.pixelsPerHeight * maxHeight());
		}
		setSize(Math.round((pixelSize.x + x0*2) * scale), Math.round((pixelSize.y + y0 + x0*2) * scale));
		redraw();
	}
	
	public void setCurrentLayer(Layer layer) {
		if (currentLayer != layer) {
			currentLayer = layer;
			updateAllTileImages();
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
			clearTileImages(0, 0);
			rescale(1);
		} else {
			this.field = field;
			updateAllTileImages();
			rescale(scale);
		}
	}
	
	// -------------------------------------------------------------------------------------
	// Auxiliary
	// -------------------------------------------------------------------------------------
	
	public int maxHeight() {
		int maxHeight = 0;
		for(Layer l : field.layers) {
			maxHeight = Math.max(maxHeight, l.info.height);
		}
		return maxHeight;
	}
	
}
