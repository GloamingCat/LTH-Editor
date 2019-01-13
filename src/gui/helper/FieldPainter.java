package gui.helper;

import java.util.HashMap;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
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

	private static HashMap<Integer, Image> terrainCache = new HashMap<>();
	private static HashMap<Integer, Image> obstacleCache = new HashMap<>();
	private static HashMap<String, Image> characterCache = new HashMap<>();
	private static HashMap<Integer, Image> regionCache = new HashMap<>();
	private static HashMap<Integer, Image> partyCache = new HashMap<>();
	
	public float scale = 1;
	public boolean showGrid = true;
	
	public FieldPainter(float scale, boolean showGrid) {
		this.scale = scale;
		this.showGrid = showGrid;
	}
	
	public static void reload() {
		for(Image img : terrainCache.values()) {
			img.dispose();
		}
		for(Image img : obstacleCache.values()) {
			img.dispose();
		}
		for(Image img : characterCache.values()) {
			img.dispose();
		}
		for(Image img : regionCache.values()) {
			img.dispose();
		}
		terrainCache.clear();
		obstacleCache.clear();
		characterCache.clear();
		regionCache.clear();
	}
	
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
		int[] p = getTilePolygon(x0, y0);
		gc.drawPolygon(p);
	}
	
	public void paintHex(GC gc, int x0, int y0) {
		int[] p = getTilePolygon(x0, y0);
		gc.fillPolygon(p);
	}

	public void paintTerrain(Layer layer, int x, int y, GC gc, int x0, int y0) {
		try {
			int id = layer.grid[x][y];
			Terrain terrain = (Terrain) Project.current.terrains.getTree().get(id);
			Animation anim = (Animation) Project.current.animations.getTree().get(terrain.animID);
			if (anim != null) {
				Image img = terrainCache.get(id);
				if (img == null) {
					img = anim.quad.getImage();
					terrainCache.put(id, img);
				}
				int[] rows = FieldHelper.math.autotile(layer.grid, x, y);
				int tw = anim.quad.width / anim.cols;
				int th = anim.quad.height / FieldHelper.math.autoTileRows;
				gc.drawImage(img, 
						anim.quad.x, anim.quad.y + th * rows[0], tw / 2, th / 2, 
						x0 - tw / 2, y0 - th / 2, tw / 2, th / 2);
				gc.drawImage(img, 
						anim.quad.x + tw / 2, anim.quad.y + th * rows[1], tw / 2, th / 2, 
						x0, y0 - th / 2, tw / 2, th / 2);
				gc.drawImage(img, 
						anim.quad.x, th / 2 + anim.quad.y + th * rows[2], tw / 2, th / 2, 
						x0 - tw / 2, y0, tw / 2, th / 2);
				gc.drawImage(img, 
						anim.quad.x + tw / 2, anim.quad.y + th / 2 + th * rows[3], tw / 2, th / 2, 
						x0, y0, tw / 2, th / 2);
			}
		} catch(IndexOutOfBoundsException e) {
			e.printStackTrace();
		}
	}
	
	public void paintObstacle(Layer layer, int x, int y, GC gc, int x0, int y0) {
		try {
			int id = layer.grid[x][y];
			Obstacle obj = (Obstacle) Project.current.obstacles.getTree().get(id);
			Animation anim = (Animation) Project.current.animations.getTree().get(obj.image.id);
			Image img = obstacleCache.get(id);
			if (img == null) {
				img = SWTResourceManager.getImage(Project.current.imagePath() + anim.quad.path);
				obstacleCache.put(id, img);
			}
			Rectangle rect = obj.image.getRectangle();
			gc.drawImage(img, rect.x, rect.y, rect.width, rect.height,
					x0 - img.getBounds().width / 2 + obj.transform.offsetX, 
					y0 - img.getBounds().height + obj.transform.offsetY, 
					rect.width, rect.height);
		} catch (IndexOutOfBoundsException e) {
			e.printStackTrace();
		}
	}
	
	public void paintCharacter(CharTile tile, GC gc, int x0, int y0) {
		try {
			String key = tile.getKey();
			
			GameCharacter c = (GameCharacter) Project.current.characters.getTree().get(tile.id);
			int animID = c.findAnimation(tile.anim);
			Animation anim = (Animation) Project.current.animations.getTree().get(animID);
			
			Image img = characterCache.get(key);
			if (img == null) {
				img = SWTResourceManager.getImage(Project.current.imagePath() + anim.quad.path);
				characterCache.put(key, img);
			}
			
			int w = img.getBounds().width / anim.cols;
			int h = img.getBounds().height / anim.rows;
			
			gc.drawImage(img, 0, h * tile.direction / 45, w, h, 
					x0 - w / 2 + anim.transform.offsetX, y0 - h + anim.transform.offsetY, w, h);
		} catch (IndexOutOfBoundsException e) {
			e.printStackTrace();
		}
	}
	
	public void paintRegion(Layer layer, int x, int y, GC gc, int x0, int y0) {
		try {
			int id = layer.grid[x][y];
			int w = FieldHelper.config.grid.tileW;
			int h = FieldHelper.config.grid.tileH;
			Image img = regionCache.get(id);
			if (img == null) {
				Region r = (Region) Project.current.regions.getData().get(id);
				img = new Image(Display.getCurrent(), w, h);
				Image str = ImageHelper.getStringImage(id + "", w, h, null);
				GC strGC = new GC(img);
				strGC.setBackground(new Color(Display.getCurrent(), r.rgb));
				strGC.fillPolygon(getTilePolygon(0, 0));
				strGC.drawImage(str, 0, 0);
				strGC.dispose();
				regionCache.put(id, img);
			}
			gc.drawImage(img, 0, 0, w, h, x0 - w / 2, y0 - h / 2, w, h);
		} catch (IndexOutOfBoundsException e) {
			e.printStackTrace();
		}
	}
	
	public void paintParty(Layer layer, int x, int y, GC gc, int x0, int y0) {
		int id = layer.grid[x][y];
		int w = FieldHelper.config.grid.tileW;
		int h = FieldHelper.config.grid.tileH;
		Image img = partyCache.get(id);
		if (img == null) {
			img = ImageHelper.getStringImage("" + id, w, h, null);
			partyCache.put(id, img);
		}
		gc.drawImage(img, 0, 0, w, h, x0 - w / 2, y0 - h / 2, w, h);
	}
	
	public Image createTileImage(int x, int y, int imgW, int imgH, Layer currentLayer, Field field) {
		Image src = new Image(Display.getCurrent(), imgW, imgH);        
	    ImageData imageData = src.getImageData();
	    imageData.transparentPixel = imageData.getPixel(0, 0);
	    src.dispose();
	    Image img = new Image(Display.getCurrent(), imageData);
	    
	    int x0 = FieldHelper.config.grid.tileW + FieldHelper.config.grid.tileW / 2;
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
		// Party Layers
		for (Layer layer : field.layers.party) {
			if (!layer.visible || layer != currentLayer)
				continue;
			paintParty(layer, x, y, 
					gc, x0, y0 - layer.info.height * pph);
			if (showGrid) {
				paintEdges(gc, x0, y0 - layer.info.height * pph);
			}
			break;
		}
		// Characters
		for (CharTile tile : field.characters) {
			if (tile.x != x || tile.y != y)
				continue;
			paintCharacter(tile, gc, x0, y0 - tile.h * pph);
		}
		gc.dispose();
		return img;
	}
	
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

}

