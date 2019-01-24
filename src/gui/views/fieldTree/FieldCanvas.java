package gui.views.fieldTree;

import lwt.editor.LView;
import gui.helper.FieldHelper;
import gui.helper.FieldPainter;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.wb.swt.SWTResourceManager;

import data.field.Field;
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

	// Image cache
	private Image[][] tileImages;
	public FieldPainter painter = new FieldPainter(1, true);

	public FieldCanvas(Composite parent, int style) {
		super(parent, style | SWT.DOUBLE_BUFFERED);
		setBackground(new Color(getDisplay(), new RGB(255, 255, 255)));
				
		addPaintListener(new PaintListener() {
	        public void paintControl(PaintEvent e) {
	        	if (field != null) {
	        		e.gc.setBackground(SWTResourceManager.getColor(SWT.COLOR_BLACK));
	        		drawAllTiles(e.gc);
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
						redraw();
						onTileEnter(tileX, tileY);
					}
				}
			}
		});
	}
	
	public void onTileEnter(int x, int y) { }

	protected void drawAllTiles(GC egc) {
		Point size = FieldHelper.math.pixelSize(field.sizeX, field.sizeY);
		int w = x0 * 2;
		int h = (FieldHelper.math.pixelDisplacement(field.sizeY) + 200 + 
				FieldHelper.config.grid.pixelsPerHeight * field.layers.maxHeight());
		Image img = new Image(egc.getDevice(), size.x + w, size.y + h); 
		GC gc = new GC(img);
		gc.setBackground(egc.getBackground());
		gc.fillRectangle(img.getBounds());
		painter.paintBackground(field, field.prefs.background, x0, y0, gc);
		painter.paintBackground(field, field.prefs.parallax, x0, y0, gc);
		
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
		drawCursor(gc);
		egc.drawImage(img, 0, 0, img.getBounds().width, img.getBounds().height, 
				0, 0, Math.round(img.getBounds().width * scale), Math.round(img.getBounds().height * scale));
	}
	
	protected void drawTile(GC gc, int i, int j) {
		Point pos = FieldHelper.math.tile2Pixel(i, j, 0);
		Image img = tileImages[i][j];
		int w = img.getBounds().width;
		int h = img.getBounds().height;
		int x = x0 + pos.x - w / 2;
		int y = y0 + pos.y - h + FieldHelper.config.grid.tileH;
		gc.drawImage(img, 0, 0, w, h, x, y, w, h);
	}
	
	protected void drawCursor(GC gc) {
		float scale = painter.scale;
		painter.scale = scale * 0.75f;
		painter.paintEdges(gc, mousePoint.x + x0, mousePoint.y + y0);
		painter.scale = scale;
	}
	
	// -------------------------------------------------------------------------------------
	// Draw in tile image
	// -------------------------------------------------------------------------------------
	
	public void updateTileImage(int x, int y) {
		if (tileImages[x][y] != null)
			tileImages[x][y].dispose();
		int imgW = FieldHelper.config.grid.tileW * 3;
		int imgH = FieldHelper.config.grid.tileH * (field.layers.maxHeight() + 6);
		Point[] shift = FieldHelper.math.neighborShift;
		tileImages[x][y] = painter.createTileImage(x, y, imgW, imgH, 
				currentLayer, field);
		for(int k = 0; k < shift.length; k++) {
			int _x = x + shift[k].x;
			int _y = y + shift[k].y;
			if (_x >= 0 && _x < field.sizeX && _y >= 0 && _y < field.sizeY) {
				if (tileImages[_x][_y] != null)
					tileImages[_x][_y].dispose();
				tileImages[_x][_y] = painter.createTileImage(_x, _y, imgW, imgH, 
						currentLayer,field);
			}
		}
		redraw();
	}
	
	public void updateAllTileImages() {
		if (field == null) {
			clearTileImages(0, 0);
		} else {
			clearTileImages(field.sizeX, field.sizeY);
			int imgW = FieldHelper.config.grid.tileW * 3;
			int imgH = FieldHelper.config.grid.tileH * (field.layers.maxHeight() + 6);
			for(int i = 0; i < field.sizeX; i++) {
				for(int j = 0; j < field.sizeY; j++) {
					tileImages[i][j] = painter.createTileImage(i, j, imgW, imgH, 
							currentLayer, field);
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
			y0 = (FieldHelper.math.pixelDisplacement(field.sizeY) + 200 + 
					FieldHelper.config.grid.pixelsPerHeight * field.layers.maxHeight());
		}
		setSize(Math.round((pixelSize.x + x0*2 - FieldHelper.config.grid.tileW) * scale), 
				Math.round((pixelSize.y + y0) * scale));
	}
	
	public void setCurrentLayer(Layer layer) {
		if (currentLayer != layer) {
			currentLayer = layer;
			if (layer != null)
				setHeight(layer.info.height);
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
			clearTileImages(0, 0);
			rescale(1);
		} else {
			this.field = field;
			updateAllTileImages();
			rescale(scale);
		}
	}
	
}
