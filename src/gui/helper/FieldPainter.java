package gui.helper;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.wb.swt.SWTResourceManager;

import data.Animation;
import data.GameCharacter;
import data.Obstacle;
import data.Terrain;
import data.config.Region;
import data.config.Config.Grid;
import data.field.CharTile;
import data.field.Field;
import data.field.FieldImage;
import data.field.Layer;
import data.subcontent.Quad;
import data.subcontent.Point;
import lwt.LImageHelper;
import project.Project;

public class FieldPainter {
	
	public Color gridColor = SWTResourceManager.getColor(new RGB(50, 50, 50));
	public Color cursorColor = SWTResourceManager.getColor(SWT.COLOR_RED);
	
	public float scale = 1;
	public boolean showGrid = false;
	public int imgW, imgH;
	
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
	
	public void drawTile(GC gc, int x0, int y0) {
		int[] p = getTilePolygon(x0, y0);
		gc.drawPolygon(p);
	}
	
	public void drawGrid(GC gc, int x0, int y0) {
		drawGrid(gc, x0, y0, gridColor);
	}
	
	public void drawGrid(GC gc, int x0, int y0, Color color) {
		float scale = this.scale;
		this.scale *= 0.95f;
		gc.setAlpha(127);
		gc.setForeground(color);
		drawTile(gc, x0, y0);
		gc.setAlpha(255);
		this.scale = scale;
	}
	
	public void fillTile(GC gc, int x0, int y0) {
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
			int[] rows = FieldHelper.math.autotile(layer, x, y);
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
		} catch (java.lang.IllegalArgumentException e) {
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
			Rectangle rect = obj.image.getRectangle();
			float scaleX = img.getBounds().width * 1f / rect.width;
			float scaleY = img.getBounds().height * 1f / rect.height;
			float offsetX = (obj.transform.offsetX + anim.transform.offsetX) * scaleX;
			float offsetY = (obj.transform.offsetY + anim.transform.offsetY) * scaleY;
			gc.drawImage(img, x0 - Math.round(offsetX), y0 - Math.round(offsetY));
		} catch (IndexOutOfBoundsException e) {
			e.printStackTrace();
		} catch (java.lang.IllegalArgumentException e) {
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
			fillTile(gc, x0, y0);
			gc.setAlpha(255);
			Grid conf = FieldHelper.config.grid;
			gc.drawImage(img, x0 - conf.tileW / 2, y0 - conf.tileH / 2);
		} catch (IndexOutOfBoundsException e) {
			e.printStackTrace();
		} catch (java.lang.IllegalArgumentException e) {
			e.printStackTrace();
		}
	}
	
	public void paintCharacter(CharTile tile, GC gc, int x0, int y0) {
		try {
			Image img = TilePainter.getCharacterTile(tile);
			if (img == null)
				return;
			float sxc = 1, syc = 1;
			int oxc = 0, oyc = 0;
			Animation anim;
			GameCharacter c = (GameCharacter) Project.current.characters.getTree().get(tile.charID);
			if (c != null) {
				anim = (Animation) Project.current.animations.getTree().
						get(c.findAnimation(tile.animation));
				sxc = c.transform.scaleX / 100f;
				syc = c.transform.scaleY / 100f;
				oxc = (int)(c.transform.offsetX * sxc);
				oyc = (int)(c.transform.offsetY * syc);
				Animation shadow = (Animation) Project.current.animations.getTree().get(c.shadowID);
				if (shadow != null) {
					int sw = shadow.quad.width / shadow.cols;
					int sh = shadow.quad.height / shadow.rows;
					float sx = shadow.transform.scaleX / 100f;
					float sy = shadow.transform.scaleY / 100f;
					Image simg = shadow.quad.getImage();
					gc.drawImage(simg, shadow.quad.x, shadow.quad.y, sw, sh, 
							x0 - (int)(shadow.transform.offsetX * sx),
							y0 - (int)(shadow.transform.offsetY * sy), 
							(int)(sw * sx), (int)(sh * sy));
				}
			} else {
				int animID = Project.current.animations.getData().get(tile.animation);
				anim = (Animation) Project.current.animations.getTree().get(animID);
			}
			int w = img.getBounds().width;
			int h = img.getBounds().height;
			float sxa = anim.transform.scaleX / 100f;
			float sya = anim.transform.scaleY / 100f;
			gc.drawImage(img, 0, 0, w, h,
					x0 - (int)(anim.transform.offsetX * sxa) - oxc, 
					y0 - (int)(anim.transform.offsetY * sya) - oyc,
					(int)(sxc * sxa * w),
					(int)(syc * sya * h));
		} catch (IndexOutOfBoundsException e) {
			e.printStackTrace();
		} catch (java.lang.IllegalArgumentException e) {
			e.printStackTrace();
		}
	}
	
	// -------------------------------------------------------------------------------------
	// Field
	// -------------------------------------------------------------------------------------
	
	public void paintBackground(Field field, FieldImage img, int x0, int y0, GC gc) {
		Animation anim = (Animation) Project.current.animations.getTree().get(img.id);
		if (anim == null)
			return;
		Quad quad = anim.quad;
		if (quad.path.isEmpty() || quad.width == 0 || quad.height == 0)
			return;
		Image bg = quad.getImage();
		if (bg == null)
			return;
		Point center = FieldHelper.math.pixelCenter(field.sizeX, field.sizeY, field.layers.maxHeight());
		x0 += center.x - quad.width / 2;
		y0 += center.y - quad.height / 2;
		int w = Math.min(quad.width - quad.x, bg.getBounds().width);
		int y = Math.min(quad.height - quad.y, bg.getBounds().height);
		gc.drawImage(bg, quad.x, quad.y, w, y,
			x0, y0, quad.width, quad.height);
	}
	
	public Image createTileImage(Field field, int x, int y, Layer currentLayer, CharTile currentChar) {
		Image img = LImageHelper.newImage(imgW, imgH);
	    
	    int x0 = imgW / 2;
	    int y0 = imgH - FieldHelper.config.grid.tileH;

	    int pph = FieldHelper.config.grid.pixelsPerHeight;
	    
		GC gc = new GC(img);
		gc.setAlpha(0);
		gc.fillRectangle(0, 0, imgW, imgH);
		gc.setAlpha(255);
		// Terrain Layers
		for (Layer layer : field.layers.terrain) {
			if (!layer.visible)
				continue;
			if (layer.grid[x][y] >= 0)
				paintTerrain(layer, x, y, gc, x0, y0 - (layer.info.height - 1) * pph);
			if (showGrid && layer == currentLayer)
				drawGrid(gc, x0, y0 - (layer.info.height - 1) * pph);
		}
		// Region Layers
		for (Layer layer : field.layers.region) {
			if (!layer.visible || layer != currentLayer)
				continue;
			paintRegion(layer, x, y, 
					gc, x0, y0 - (layer.info.height - 1) * pph);
			if (showGrid && layer == currentLayer)
				drawGrid(gc, x0, y0 - (layer.info.height - 1) * pph);
			break;
		}
		// Obstacle Layers
		for (Layer layer : field.layers.obstacle) {
			if (!layer.visible)
				continue;
			if (showGrid && layer == currentLayer)
				drawGrid(gc, x0, y0 - (layer.info.height - 1) * pph);
			if (layer.grid[x][y] >= 0)
				paintObstacle(layer, x, y, gc, x0, y0 - (layer.info.height - 1) * pph);
		}
		// Characters
		for (CharTile tile : field.characters) {
			if (tile.x - 1 != x || tile.y - 1 != y)
				continue;
			if (tile == currentChar) {
				drawGrid(gc, x0, y0 - (tile.h - 1) * pph);
			}
			paintCharacter(tile, gc, x0, y0 - (tile.h - 1) * pph);
		}
		gc.dispose();
		return LImageHelper.correctTransparency(img);
	}

}

