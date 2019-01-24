package gui.helper;

import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Display;
import org.eclipse.wb.swt.SWTResourceManager;

import data.Animation;
import data.GameCharacter;
import data.Obstacle;
import data.Terrain;
import data.config.Region;
import data.config.Config.Grid;
import data.field.CharTile;
import data.field.Field;
import data.field.Layer;
import data.subcontent.Quad;
import project.Project;

public class FieldPainter {
	
	public float scale = 1;
	public boolean showGrid = false;
	
	public FieldPainter() {	}
	
	public FieldPainter(float scale, boolean showGrid) {
		this.scale = scale;
		this.showGrid = showGrid;
	}
	
	// -------------------------------------------------------------------------------------
	// Grid
	// -------------------------------------------------------------------------------------
	
	public int[] getTilePolygon(int x0, int y0) {
		Point[] shift = FieldHelper.math.vertexShift;
		int[] p = new int[shift.length * 2];
		for(int i = 0; i < shift.length; i++) {
			p[i * 2] = Math.round((shift[i].x * scale + x0));
			p[i * 2 + 1] = Math.round((shift[i].y * scale + y0));
		}
		return p;
	}
	
	public Image createGridTile() {
		Grid conf = FieldHelper.config.grid;
		int w = conf.tileW;
		int h = conf.tileH;
		
		Image img = new Image(Display.getCurrent(), w, h);

		GC gc = new GC(img);
		paintEdges(gc, w / 2, h / 2);
		gc.dispose();
		
		ImageData data = img.getImageData();
		data.transparentPixel = -256;
		
		return new Image(img.getDevice(), data, data.getTransparencyMask());
	}
	
	public void paintEdges(GC gc, int x0, int y0) {
		float scale = this.scale;
		this.scale *= 0.95f;
		int[] p = getTilePolygon(x0, y0);
		this.scale = scale;
		gc.drawPolygon(p);
	}
	
	public void paintHex(GC gc, int x0, int y0) {
		int[] p = getTilePolygon(x0, y0);
		gc.fillPolygon(p);
	}
	
	// -------------------------------------------------------------------------------------
	// Tiles
	// -------------------------------------------------------------------------------------

	public void paintTerrain(Layer layer, int x, int y, GC gc, int x0, int y0) {
		try {
			int id = layer.grid[x][y];
			Image img = TilePainter.getTerrainTile(id, true);
			if (img == null)
				return;
			Terrain terrain = (Terrain) Project.current.terrains.getTree().get(id);
			Animation anim = (Animation) Project.current.animations.getTree().get(terrain.animID);
			int[] rows = FieldHelper.math.autotile(layer.grid, x, y);
			int w = img.getBounds().width / (anim.cols * 2);
			int h = img.getBounds().height / (anim.rows * 2);
			int dx = x0 - (anim.transform.offsetX * anim.transform.scaleX) / 10000;
			int dy = y0 - (anim.transform.offsetY * anim.transform.scaleY) / 10000;
			gc.drawImage(img, 
					0, h * 2 * rows[0], w, h, 
					dx - w, dy - h, w, h);
			gc.drawImage(img, 
					w, h * 2 * rows[1], w, h, 
					dx, dy - h, w, h);
			gc.drawImage(img, 
					0, h + h * 2 * rows[2], w, h, 
					dx - w, dy, w, h);
			gc.drawImage(img, 
					w, h + h * 2 * rows[3], w, h, 
					dx, dy, w, h);
		} catch(IndexOutOfBoundsException e) {
			e.printStackTrace();
		}
	}
	
	public void paintObstacle(Layer layer, int x, int y, GC gc, int x0, int y0) {
		try {
			Image img = TilePainter.getObstacleTile(layer.grid[x][y]);
			if (img == null)
				return;
			Obstacle obj = (Obstacle) Project.current.obstacles.getTree().get(layer.grid[x][y]);
			Animation anim = (Animation) Project.current.animations.getTree().get(obj.image.id);
			gc.drawImage(img, x0 - obj.transform.offsetX - anim.transform.offsetX, 
					y0 - obj.transform.offsetY - anim.transform.offsetY);
		} catch (IndexOutOfBoundsException e) {
			e.printStackTrace();
		}
	}
	
	public void paintRegion(Layer layer, int x, int y, GC gc, int x0, int y0) {
		try {
			Image img = TilePainter.getRegionTile(layer.grid[x][y], false);
			if (img == null)
				return;
			Region r = (Region) Project.current.regions.getData().get(layer.grid[x][y]);
			gc.setAlpha(200);
			gc.setBackground(SWTResourceManager.getColor(r.rgb));
			paintHex(gc, x0, y0);
			gc.setAlpha(255);
			Grid conf = FieldHelper.config.grid;
			gc.drawImage(img, x0 - conf.tileW / 2, y0 - conf.tileH / 2);
		} catch (IndexOutOfBoundsException e) {
			e.printStackTrace();
		}
	}
	
	public void paintCharacter(CharTile tile, GC gc, int x0, int y0) {
		try {
			Image img = TilePainter.getCharacterTile(tile);
			if (img == null)
				return;
			GameCharacter c = (GameCharacter) Project.current.characters.getTree().get(tile.charID);
			Animation anim = (Animation) Project.current.animations.getTree().
					get(c.findAnimation(tile.animation));
			if (c.shadowID >= 0) {
				Animation shadow = (Animation) Project.current.animations.getTree().get(c.shadowID);
				int sw = shadow.quad.width / shadow.cols;
				int sh = shadow.quad.height / shadow.rows;
				int scol = shadow.getFirstFrame();
				gc.drawImage(shadow.quad.getImage(), 
						shadow.quad.x + sw * scol, shadow.quad.y, sw, sh, 
						x0 - shadow.transform.offsetX, y0 - shadow.transform.offsetY, sw, sh);
			}
			gc.drawImage(img, x0 - c.transform.offsetX - anim.transform.offsetX, 
					y0 - c.transform.offsetY - anim.transform.offsetY);
		} catch (IndexOutOfBoundsException e) {
			e.printStackTrace();
		}
	}
	
	// -------------------------------------------------------------------------------------
	// Field
	// -------------------------------------------------------------------------------------
	
	public void paintBackground(Field field, Quad quad, int x0, int y0, GC gc) {
		if (quad.path.isEmpty() || quad.width == 0 || quad.height == 0)
			return;
		Image bg = quad.getImage();
		Point center = FieldHelper.math.pixelCenter(field.sizeX, field.sizeY, field.layers.maxHeight());
		x0 += center.x - quad.width / 2;
		y0 += center.y - quad.height / 2;
		gc.drawImage(bg, 
			quad.x, quad.y, quad.width, quad.height,
			x0, y0, quad.width, quad.height);
	}
	
	public Image createTileImage(int x, int y, int imgW, int imgH, Layer currentLayer, Field field) {
		Image img = ImageHelper.newImage(imgW, imgH);
	    
	    int x0 = imgW / 2;
	    int y0 = imgH - FieldHelper.config.grid.tileH;

	    int pph = FieldHelper.config.grid.pixelsPerHeight;
	    
		GC gc = new GC(img);
		// Terrain Layers
		for (Layer layer : field.layers.terrain) {
			if (!layer.visible)
				continue;
			if (layer.grid[x][y] >= 0) {
				paintTerrain(layer, x, y, gc, x0, y0 - layer.info.height * pph);
			}
			if (showGrid && layer == currentLayer) {
				paintEdges(gc, x0, y0 - layer.info.height * pph);
			}
		}
		// Region Layers
		for (Layer layer : field.layers.region) {
			if (!layer.visible || layer != currentLayer)
				continue;
			paintRegion(layer, x, y, 
					gc, x0, y0 - layer.info.height * pph);
			if (showGrid && layer == currentLayer) {
				paintEdges(gc, x0, y0 - layer.info.height * pph);
			}
			break;
		}
		// Obstacle Layers
		for (Layer layer : field.layers.obstacle) {
			if (!layer.visible)
				continue;
			if (showGrid && layer == currentLayer) {
				paintEdges(gc, x0, y0 - layer.info.height * pph);
			}
			if (layer.grid[x][y] >= 0) {
				paintObstacle(layer, x, y, gc, x0, y0 - layer.info.height * pph);
			}
		}
		// Characters
		for (CharTile tile : field.characters) {
			if (tile.x != x || tile.y != y)
				continue;
			paintCharacter(tile, gc, x0, y0 - tile.h * pph);
		}
		gc.dispose();
		return ImageHelper.correctTransparency(img);
	}

}

