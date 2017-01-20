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
import data.BattlerType;
import data.Config;
import data.Field;
import data.Layer;
import data.GameCharacter;
import data.Obstacle;
import data.Ramp;
import data.Ramp.PointSet;
import data.Region;
import data.Terrain;
import data.Tileset;
import data.Tileset.CharTile;
import project.Project;

public class FieldPainter {

	private static Image gridTile = null;
	private static HashMap<Integer, Image> terrainCache = new HashMap<>();
	private static HashMap<Integer, Image> obstacleCache = new HashMap<>();
	private static HashMap<String, Image> characterCache = new HashMap<>();
	private static HashMap<Integer, Image> regionCache = new HashMap<>();
	private static HashMap<Integer, Image> typeCache = new HashMap<>();
	private static HashMap<Integer, Image> partyCache = new HashMap<>();
	
	public float scale = 1;
	public boolean showGrid = true;
	
	public FieldPainter(float scale, boolean showGrid) {
		this.scale = scale;
		this.showGrid = showGrid;
	}
	
	public void reload() {
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
		for(Image img : typeCache.values()) {
			img.dispose();
		}
		terrainCache.clear();
		obstacleCache.clear();
		characterCache.clear();
		regionCache.clear();
		typeCache.clear();
	}
	
	public int[] getTilePolygon(int x0, int y0) {
		Point[] shift = FieldHelper.math.vertexShift;
		int[] p = new int[shift.length * 2];
		for(int i = 0; i < shift.length; i++) {
			p[i * 2] = Math.round((shift[i].x + x0) * scale);
			p[i * 2 + 1] = Math.round((shift[i].y + y0) * scale);
		}
		return p;
	}
	
	public Image createGridTile() {
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
	
	public void paintEdges(GC gc, int x0, int y0) {
		int[] p = getTilePolygon(x0, y0);
		gc.drawPolygon(p);
	}
	
	public void paintHex(GC gc, int x0, int y0) {
		int[] p = getTilePolygon(x0, y0);
		gc.fillPolygon(p);
	}
	
	public void paintRamp(GC gc, int x0, int y0, PointSet points, int height) {
		Config conf = FieldHelper.config;
		paintEdges(gc, x0, y0 + conf.pixelsPerHeight);
		Point b1 = new Point(Math.round((points.b1x + x0) * scale), Math.round((points.b1y + y0 + conf.pixelsPerHeight * height) * scale));
		Point t1 = new Point(Math.round((points.t1x + x0) * scale), Math.round((points.t1y + y0) * scale));
		Point b2 = new Point(Math.round((points.b2x + x0) * scale), Math.round((points.b2y + y0 + conf.pixelsPerHeight * height) * scale));
		Point t2 = new Point(Math.round((points.t2x + x0) * scale), Math.round((points.t2y + y0) * scale));
		Point t1_ = new Point(t1.x, Math.round(t1.y + conf.pixelsPerHeight * height * scale));
		Point t2_ = new Point(t2.x, Math.round(t2.y + conf.pixelsPerHeight * height * scale));
		int[] p = new int[] {b1.x, b1.y, t1.x, t1.y, t2.x, t2.y, b2.x, b2.y};
		gc.drawPolygon(p);
		
		p = new int[] {t1.x, t1.y, t1_.x, t1_.y, b1.x, b1.y};
		gc.drawPolyline(p);
		p = new int[] {t2.x, t2.y, t2_.x, t2_.y, b2.x, b2.y};
		gc.drawPolyline(p);
		gc.drawLine(t1_.x, t1_.y, t2_.x, t2_.y);
	}
	
	public void paintRamp(Image image, int x0, int y0, int id) {
		Ramp ramp = (Ramp) Project.current.ramps.getList().get(id);
		Config conf = FieldHelper.config;
		GC gc = new GC(image);
		paintRamp(gc, x0 - conf.tileW / 2, y0 - conf.tileH / 2, ramp);
		gc.dispose();
	}
	
	public void paintRamp(GC gc, int x0, int y0, Ramp ramp) {
		gc.setBackground(new Color(Display.getCurrent(), new RGB(127, 127, 127)));
		paintRamp(gc, x0, y0, ramp.points, ramp.height);
	}
	
	public void paintTerrain(int tilesetID, Layer layer, int x, int y, GC gc, int x0, int y0) {
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
	
	public void paintObstacle(int tilesetID, Layer layer, int x, int y, GC gc, int x0, int y0, boolean paintRamp) {
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
		
		/*if (paintRamp && obj.rampID >= 0) {
			Ramp ramp = (Ramp) Project.current.ramps.getList().get(obj.rampID);
			paintRamp(gc, x0, y0 - FieldHelper.config.tileH / 2, ramp);
		}*/
	}
	
	public void paintCharacter(int tilesetID, Layer layer, int x, int y, GC gc, int x0, int y0) {
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
	
	public void paintRegion(int tilesetID, Layer layer, int x, int y, GC gc, int x0, int y0) {
		Tileset tileset = (Tileset) Project.current.tilesets.getList().get(tilesetID);
		int id = tileset.regions.get(layer.grid[x][y]).id;
		int w = FieldHelper.config.tileW;
		int h = FieldHelper.config.tileH;
		Image img = regionCache.get(id);
		if (img == null) {
			Region r = FieldHelper.config.regions.get(id);
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
	}
	
	public void paintType(Layer layer, int x, int y, GC gc, int x0, int y0) {
		int id = layer.grid[x][y];
		int w = FieldHelper.config.tileW;
		int h = FieldHelper.config.tileH;
		Image img = typeCache.get(id);
		if (img == null) {
			BattlerType type = FieldHelper.config.battlerTypes.get(id);
			img = ImageHelper.getStringImage(type.code, w, h, null);
			typeCache.put(id, img);
		}
		gc.drawImage(img, 0, 0, w, h, x0 - w/ 2, y0 - h / 2, w, h);
	}
	
	public void paintParty(Layer layer, int x, int y, GC gc, int x0, int y0) {
		int id = layer.grid[x][y];
		int w = FieldHelper.config.tileW;
		int h = FieldHelper.config.tileH;
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
	    
	    int x0 = FieldHelper.config.tileW + FieldHelper.config.tileW / 2;
	    int y0 = imgH - FieldHelper.config.tileH;

	    int pph = FieldHelper.config.pixelsPerHeight;
	    
		GC gc = new GC(img);
		for(Layer layer : field.layers) {
			if (layer.visible) {
				switch(layer.info.type) {
				case 0:
					// Terrain Layer
					if (layer.grid[x][y] >= 0) {
						paintTerrain(field.prefs.tilesetID, layer, x, y, gc, x0, y0 - layer.info.height * pph);
					}
					if (showGrid && layer == currentLayer) {
						paintEdges(gc, x0, y0 - layer.info.height * pph);
					}
					break;
				case 1:
					// Obstacle Layer
					if (showGrid && layer == currentLayer) {
						paintEdges(gc, x0, y0 - layer.info.height * pph);
						if (layer.grid[x][y] >= 0) {
							paintObstacle(field.prefs.tilesetID, layer, x, y, gc, x0, y0 - layer.info.height * pph, true);
						}
					} else {
						if (layer.grid[x][y] >= 0) {
							paintObstacle(field.prefs.tilesetID, layer, x, y, gc, x0, y0 - layer.info.height * pph, false);
						}
					}
					break;
				case 2:
					// Character Layer
					if (showGrid && layer == currentLayer) {
						paintEdges(gc, x0, y0 - layer.info.height * pph);
					}
					if (layer.grid[x][y] >= 0) {
						paintCharacter(field.prefs.tilesetID, layer, x, y, gc, x0, y0 - layer.info.height * pph);
					}
					break;
				default:
					if (layer == currentLayer) {
						if (layer.grid[x][y] >= 0) {
							switch(layer.info.type) {
							case 3: // Region Layer
								paintRegion(field.prefs.tilesetID, layer, x, y, 
										gc, x0, y0 - layer.info.height * pph);
								break;
							case 4: // Type Layer
								paintType(layer, x, y, 
										gc, x0, y0 - layer.info.height * pph);
								break;
							case 5: // Party Layer
								paintParty(layer, x, y, 
										gc, x0, y0 - layer.info.height * pph);
								break;
							}
						}
						if (showGrid) {
							paintEdges(gc, x0, y0 - layer.info.height * pph);
						}
					}
					break;
				}
			}
		}
		gc.dispose();
		return img;
	}
	
	public void paintBackground(Field field, int x0, int y0, int maxHeight, GC gc) {
		if (field.prefs.background.isEmpty() == false) {
			Image bg = SWTResourceManager.getImage(Project.current.imagePath() + field.prefs.background);
			Point center = FieldHelper.math.pixelCenter(field.sizeX, field.sizeY, maxHeight);
			gc.drawImage(bg, x0 + center.x - bg.getBounds().width / 2, y0 + center.y - bg.getBounds().height / 2);
		}
	}

}

