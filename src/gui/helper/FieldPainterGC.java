package gui.helper;

import lui.LovelyTheme;
import lui.graphics.LColor;
import lui.graphics.LPainter;
import lui.base.data.LPoint;
import lui.graphics.LRect;
import lui.graphics.LTexture;

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
import data.subcontent.Transform;
import project.Project;

public class FieldPainterGC {

	public LColor gridColor = LovelyTheme.DARK;

	public int imgW, imgH;

	// -------------------------------------------------------------------------------------
	// Grid
	// -------------------------------------------------------------------------------------

	public void drawGrid(LPainter gc, int x0, int y0) {
		drawGrid(gc, x0, y0, gridColor);
	}

	public void drawGrid(LPainter gc, int x0, int y0, LColor color) {
		gc.setTransparency(127);
		gc.setPaintColor(color);
		int[] p = FieldHelper.getTilePolygon(x0, y0, 0.95f);
		gc.drawPolygon(p, true);
		gc.setTransparency(255);
	}

	// -------------------------------------------------------------------------------------
	// Tiles
	// -------------------------------------------------------------------------------------

	public void paintTerrain(Layer layer, int x, int y, LPainter gc, int x0, int y0) {
		try {
			int id = layer.grid[x][y];
			LTexture img = TilePainter.getTerrainTile(id, true);
			if (img == null)
				return;
			Terrain terrain = (Terrain) Project.current.terrains.getTree().get(id);
			Animation anim = (Animation) Project.current.animations.getTree().get(terrain.animID);
			int[] rows = FieldHelper.math.autotile(layer, x, y);
			LPoint size = img.getSize();
			int w = size.x / (anim.cols * 2);
			int h = size.y / (anim.rows * 2);
			int dx = x0 - (anim.transform.offsetX * anim.transform.scaleX) / 100;
			int dy = y0 - (anim.transform.offsetY * anim.transform.scaleY) / 100;
			float sx = w / 100f * anim.transform.scaleX;
			float sy = h / 100f * anim.transform.scaleY;
			gc.drawImage(img, 0, h * 2 * rows[0], w, h, dx, dy, sx, sy);
			gc.drawImage(img, w, h * 2 * rows[1], w, h, dx + w, dy, sx, sy);
			gc.drawImage(img, 0, h + h * 2 * rows[2], w, h, dx, dy + h, sx, sy);
			gc.drawImage(img, w, h + h * 2 * rows[3], w, h, dx + w, dy + h, sx, sy);
		} catch (IndexOutOfBoundsException e) {
			e.printStackTrace();
		} catch (java.lang.IllegalArgumentException e) {
			e.printStackTrace();
		}
	}

	public void paintObstacle(Layer layer, int x, int y, LPainter gc, int x0, int y0) {
		try {
			LTexture img = TilePainter.getObstacleTile(layer.grid[x][y]);
			if (img == null)
				return;
			Obstacle obj = (Obstacle) Project.current.obstacles.getTree().get(layer.grid[x][y]);
			Animation anim = (Animation) Project.current.animations.getTree().get(obj.image.id);
			LRect rect = obj.image.getRectangle();
			LPoint size = img.getSize();
			float scaleX = size.x * 1f / rect.width;
			float scaleY = size.y * 1f / rect.height;
			float offsetX = (obj.transform.offsetX + anim.transform.offsetX) * scaleX;
			float offsetY = (obj.transform.offsetY + anim.transform.offsetY) * scaleY;
			gc.drawImage(img, x0 - Math.round(offsetX), y0 - Math.round(offsetY));
		} catch (IndexOutOfBoundsException e) {
			e.printStackTrace();
		} catch (java.lang.IllegalArgumentException e) {
			e.printStackTrace();
		}
	}

	public void paintRegion(Layer layer, int x, int y, LPainter gc, int x0, int y0) {
		try {
			LTexture img = TilePainter.getRegionTile(layer.grid[x][y], false);
			if (img == null)
				return;
			Region r = (Region) Project.current.regions.getData().get(layer.grid[x][y]);
			gc.setTransparency(200);
			gc.setFillColor(r.color);
			int[] p = FieldHelper.getTilePolygon(x0, y0, 1);
			gc.fillPolygon(p);
			gc.setTransparency(255);
			Grid conf = FieldHelper.config.grid;
			gc.drawImage(img, x0 - conf.tileW / 2, y0 - conf.tileH / 2);
		} catch (IndexOutOfBoundsException e) {
			e.printStackTrace();
		} catch (java.lang.IllegalArgumentException e) {
			e.printStackTrace();
		}
	}

	public void paintCharacter(CharTile tile, LPainter gc, int x0, int y0) {
		try {
			LTexture img = TilePainter.getCharacterTile(tile);
			if (img == null)
				return;
			float sxc = 1, syc = 1;
			int oxc = 0, oyc = 0;
			Animation anim;
			GameCharacter c = (GameCharacter) Project.current.characters.getTree().get(tile.charID);
			if (c != null) {
				anim = (Animation) Project.current.animations.getTree().get(c.findAnimation(tile.animation));
				sxc = c.transform.scaleX / 100f;
				syc = c.transform.scaleY / 100f;
				oxc = (int) (c.transform.offsetX * sxc);
				oyc = (int) (c.transform.offsetY * syc);
				Animation shadow = (Animation) Project.current.animations.getTree().get(c.shadowID);
				if (shadow != null) {
					int sw = shadow.quad.width / shadow.cols;
					int sh = shadow.quad.height / shadow.rows;
					float sx = shadow.transform.scaleX / 100f;
					float sy = shadow.transform.scaleY / 100f;
					LTexture simg = new LTexture(shadow.quad.fullPath());
					gc.drawImage(simg, shadow.quad.x, shadow.quad.y, sw, sh, x0 - (int) (shadow.transform.offsetX * sx),
							y0 - (int) (shadow.transform.offsetY * sy), (int) (sw * sx), (int) (sh * sy));
				}
			} else {
				int animID = Project.current.animations.getData().get(tile.animation);
				anim = (Animation) Project.current.animations.getTree().get(animID);
			}
			LPoint size = img.getSize();
			int w = size.x;
			int h = size.y;
			float sxa = anim.transform.scaleX / 100f;
			float sya = anim.transform.scaleY / 100f;
			gc.drawImage(img, 0, 0, w, h, x0 - (int) (anim.transform.offsetX * sxa) - oxc,
					y0 - (int) (anim.transform.offsetY * sya) - oyc, (int) (sxc * sxa * w), (int) (syc * sya * h));
		} catch (IndexOutOfBoundsException e) {
			e.printStackTrace();
		} catch (java.lang.IllegalArgumentException e) {
			e.printStackTrace();
		}
	}

	// -------------------------------------------------------------------------------------
	// Field
	// -------------------------------------------------------------------------------------

	public void paintQuad(Quad quad, int x0, int y0, LPainter gc) {
		LTexture bg = new LTexture(quad.fullPath());
		if (bg.isEmpty())
			return;
		LPoint size = bg.getSize();
		int w = Math.min(quad.width - quad.x, size.x);
		int h = Math.min(quad.height - quad.y, size.y);
		gc.drawImage(bg, quad.x, quad.y, w, h, x0, y0, w, h);
	}

	public void paintQuad(Quad quad, int x0, int y0, Transform transform, LPainter gc) {
		LTexture bg = new LTexture(quad.fullPath());
		if (bg.isEmpty())
			return;
		x0 -= (transform.offsetX * transform.scaleX) / 100;
		y0 -= (transform.offsetY * transform.scaleY) / 100;
		int width = (quad.width * transform.scaleX) / 100;
		int height = (quad.height * transform.scaleY) / 100;
		gc.drawImage(bg, quad.x, quad.y, quad.width, quad.height, x0, y0, width, height);
	}

	public LTexture createTileImage(Field field, int x, int y, boolean showGrid, Layer currentLayer,
			CharTile currentChar) {
		LTexture img = new LTexture(imgW, imgH);

		int x0 = imgW / 2;
		int y0 = imgH - FieldHelper.config.grid.tileH;

		int pph = FieldHelper.config.grid.pixelsPerHeight;

		LPainter gc = new LPainter(img) {
			@Override
			public void paint() {
				
			}
		};
		gc.setTransparency(0);
		gc.fillRect(0, 0, imgW, imgH);
		gc.setTransparency(255);
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
			paintRegion(layer, x, y, gc, x0, y0 - (layer.info.height - 1) * pph);
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
		img.correctTransparency();
		return img;
	}

}
