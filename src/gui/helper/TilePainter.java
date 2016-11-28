package gui.helper;

import java.util.HashMap;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Display;
import org.eclipse.wb.swt.SWTResourceManager;

import data.Animation;
import data.Config;
import data.Field;
import data.Field.Layer;
import data.GameCharacter;
import data.Obstacle;
import data.Ramp;
import data.Ramp.PointSet;
import data.Terrain;
import data.Tileset;
import data.Tileset.CharTile;
import project.Project;

public class TilePainter {

	private static int scale = 1;
	private static Image gridTile = null;
	private static boolean showGrid = true;
	private static HashMap<Integer, Image> terrainCache = new HashMap<>();
	private static HashMap<Integer, Image> obstacleCache = new HashMap<>();
	private static HashMap<String, Image> characterCache = new HashMap<>();
	private static HashMap<Integer, Image> regionCache = new HashMap<>();
	
	public static void setScale(int i) {
		scale = i;
	}
	
	public static void reload() {
		scale = 1;
		gridTile = createGridTile();
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
	
	public static Image createGridTile() {
		Config conf = FieldHelper.config;
		int w = conf.tileW;
		int h = conf.tileH;
		
		Image img = new Image(Display.getCurrent(), w, h);

		GC gc = new GC(gridTile);
		paintEdges(gc, w / 2, h / 2);
		gc.dispose();
		
		ImageData data = gridTile.getImageData();
		data.transparentPixel = -256;
		
		return new Image(img.getDevice(), data, data.getTransparencyMask());
	}
	
	public static void paintEdges(GC gc, int x0, int y0) {
		Point[] shift = FieldHelper.math.vertexShift;
		int[] p = new int[shift.length * 2];
		for(int i = 0; i < shift.length; i++) {
			p[i * 2] = (shift[i].x + x0) * scale;
			p[i * 2 + 1] = (shift[i].y + y0) * scale;
		}
		gc.drawPolygon(p);
	}
	
	public static void paintRamp(GC gc, int x0, int y0, PointSet points, int height) {
		Config conf = FieldHelper.config;
		paintEdges(gc, x0, y0 + conf.pixelsPerHeight);
		Point b1 = new Point((points.b1x + x0) * scale, (points.b1y + y0 + conf.pixelsPerHeight * height) * scale);
		Point t1 = new Point((points.t1x + x0) * scale, (points.t1y + y0) * scale);
		Point b2 = new Point((points.b2x + x0) * scale, (points.b2y + y0 + conf.pixelsPerHeight * height) * scale);
		Point t2 = new Point((points.t2x + x0) * scale, (points.t2y + y0) * scale);
		Point t1_ = new Point(t1.x, t1.y + conf.pixelsPerHeight * height * scale);
		Point t2_ = new Point(t2.x, t2.y + conf.pixelsPerHeight * height * scale);
		int[] p = new int[] {b1.x, b1.y, t1.x, t1.y, t2.x, t2.y, b2.x, b2.y};
		gc.drawPolygon(p);
		
		p = new int[] {t1.x, t1.y, t1_.x, t1_.y, b1.x, b1.y};
		gc.drawPolyline(p);
		p = new int[] {t2.x, t2.y, t2_.x, t2_.y, b2.x, b2.y};
		gc.drawPolyline(p);
		gc.drawLine(t1_.x, t1_.y, t2_.x, t2_.y);
	}
	
	public static void paintRamp(Image image, int x0, int y0, int id) {
		Ramp ramp = (Ramp) Project.current.ramps.getList().get(id);
		Config conf = FieldHelper.config;
		GC gc = new GC(image);
		paintRamp(gc, x0 - conf.tileW / 2, y0 - conf.tileH / 2, ramp);
		gc.dispose();
	}
	
	public static void paintRamp(GC gc, int x0, int y0, Ramp ramp) {
		gc.setBackground(new Color(Display.getCurrent(), new RGB(127, 127, 127)));
		paintRamp(gc, x0, y0, ramp.points, ramp.height);
	}
	
	public static void paintTerrain(int tilesetID, Layer layer, int x, int y, GC gc, int x0, int y0) {
		Tileset tileset = (Tileset) Project.current.tilesets.getList().get(tilesetID);
		int id = tileset.terrains.get(layer.grid[x][y]).id;
		Terrain terrain = (Terrain) Project.current.terrains.getList().get(id);
		Image img = terrainCache.get(id);
		if (img == null) {
			img = SWTResourceManager.getImage(Project.current.imagePath() + terrain.imagePath);
			terrainCache.put(id, img);
		}
		int[] rows = FieldHelper.math.autotile(layer.grid, x, y);
		int tw = img.getBounds().width / terrain.frameCount;
		int th = img.getBounds().height / FieldHelper.math.rowCount;
		gc.drawImage(img, 
				0, th * rows[0], tw / 2, th / 2, 
				x0 - tw / 2, y0 - th / 2, tw / 2, th / 2);
		gc.drawImage(img, 
				tw / 2, th * rows[1], tw / 2, th / 2, 
				x0, y0 - th / 2, tw / 2, th / 2);
		gc.drawImage(img, 
				0, th / 2 + th * rows[2], tw / 2, th / 2, 
				x0 - tw / 2, y0, tw / 2, th / 2);
		gc.drawImage(img, 
				tw / 2, th / 2 + th * rows[3], tw / 2, th / 2, 
				x0, y0, tw / 2, th / 2);
	}
	
	public static void paintObstacle(int tilesetID, Layer layer, int x, int y, GC gc, int x0, int y0) {
		Tileset tileset = (Tileset) Project.current.tilesets.getList().get(tilesetID);
		int id = tileset.obstacles.get(layer.grid[x][y]).id;
		Obstacle obj = (Obstacle) Project.current.obstacles.getList().get(id);
		Image img = obstacleCache.get(id);
		if (img == null) {
			img = SWTResourceManager.getImage(Project.current.imagePath() + obj.quad.imagePath);
			obstacleCache.put(id, img);
		}
		gc.drawImage(img, obj.quad.x, obj.quad.y, obj.quad.width, obj.quad.height,
				x0 - img.getBounds().width / 2 + obj.transform.offsetX, 
				y0 - img.getBounds().height + obj.transform.offsetY, obj.quad.width, obj.quad.height);
		if (showGrid && obj.rampID >= 0) {
			Ramp ramp = (Ramp) Project.current.ramps.getList().get(obj.rampID);
			paintRamp(gc, x0, y0, ramp);
		}
	}
	
	public static void paintCharacter(int tilesetID, Layer layer, int x, int y, GC gc, int x0, int y0) {
		Tileset tileset = (Tileset) Project.current.tilesets.getList().get(tilesetID);
		CharTile tile = tileset.characters.get(layer.grid[x][y]);
		String key = tile.getKey();
		
		GameCharacter c = (GameCharacter) Project.current.characters.getList().get(tile.id);
		Animation anim = (Animation) Project.current.animCharacter.getList().get(c.animations.get(tile.animID).id);
		
		Image img = characterCache.get(key);
		if (img == null) {
			img = SWTResourceManager.getImage(Project.current.imagePath() + anim.imagePath);
			characterCache.put(key, img);
		}
		
		int w = img.getBounds().width / anim.cols;
		int h = img.getBounds().height / anim.rows;
		
		gc.drawImage(img, 0, h * tile.direction / 45, w, h, 
				x0 - w / 2 + anim.transform.offsetX, y0 - h + anim.transform.offsetY, w, h);
	}
	
	public static void paintRegion(int tilesetID, Layer layer, int x, int y, GC gc, int x0, int y0) {
		Tileset tileset = (Tileset) Project.current.tilesets.getList().get(tilesetID);
		int id = tileset.regions.get(layer.grid[x][y]).id;
		int w = FieldHelper.config.tileW;
		int h = FieldHelper.config.tileH;
		Image img = regionCache.get(id);
		if (img == null) {
			img = ImageHelper.getStringImage(id + "", w, h);
			regionCache.put(id, img);
		}
		gc.drawImage(img, 0, 0, w, h, x0 - w / 2, y0 - h / 2, w, h);
	}
	
	public static Image createTileImage(int x, int y, int imgW, int imgH, int h, Field field) {
		Image src = new Image(Display.getCurrent(), imgW, imgH);        
	    ImageData imageData = src.getImageData();
	    imageData.transparentPixel = imageData.getPixel(0, 0);
	    src.dispose();
	    Image img = new Image(Display.getCurrent(), imageData);
	    
	    int x0 = FieldHelper.config.tileW + FieldHelper.config.tileW / 2;
	    int y0 = imgH - FieldHelper.config.tileH / 2;

	    int pph = FieldHelper.config.pixelsPerHeight;
	    
		GC gc = new GC(img);
		for(int i = 0; i < field.layers.size(); i++) {
			Layer layer = field.layers.get(i);
			if (layer.visible && layer.grid[x][y] >= 0) {
				if (layer.type == 0) {
					paintTerrain(field.tilesetID, layer, x, y, gc, x0, y0 - layer.height * pph);
					if (showGrid && layer.height == h) {
						h = -1;
						paintEdges(gc, x0, y0 - layer.height * pph);
					}
				} else if (layer.type == 1)
					paintObstacle(field.tilesetID, layer, x, y, gc, x0, y0 - layer.height * pph);
				else if (layer.type == 2)
					paintCharacter(field.tilesetID, layer, x, y, gc, x0, y0 - layer.height * pph);
				else if (layer.type == 3 && layer.height == h) {
					paintRegion(field.tilesetID, layer, x, y, gc, x0, y0 - layer.height * pph);
				}
			}
		}
		gc.dispose();
		return img;
	}

}

