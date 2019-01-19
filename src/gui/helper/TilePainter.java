package gui.helper;

import java.util.HashMap;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Display;

import data.Animation;
import data.GameCharacter;
import data.Obstacle;
import data.Terrain;
import data.config.Config;
import data.config.Region;
import data.field.CharTile;
import project.Project;

public class TilePainter {

	private static Config conf;
	
	public static HashMap<Integer, Image> terrainCache = new HashMap<>();
	public static HashMap<Integer, Image> obstacleCache = new HashMap<>();
	public static HashMap<String, Image> characterCache = new HashMap<>();
	public static HashMap<String, Image> regionCache = new HashMap<>();
	
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
		conf = (Config) Project.current.config.getData();
	}
	
	public static Image getTerrainTile(Integer id) {
		Image img = terrainCache.get(id);
		if (img != null) {
			return img;
		}
		Terrain terrain = (Terrain) Project.current.terrains.getTree().get(id);
		if (terrain == null)
			return null;
		Animation anim = (Animation) Project.current.animations.getTree().get(terrain.animID);
		if (anim == null)
			return null;
		System.out.println(id + " " + anim.quad.path);
		int w = anim.quad.width / anim.cols;
		int h = anim.quad.height / anim.rows;
		img = ImageHelper.newImage(w, h);
		GC gc = new GC(img);
		gc.drawImage(anim.quad.getImage(), anim.quad.x, anim.quad.y, w, h, 0, 0, w, h);
		gc.dispose();
		img = ImageHelper.correctTransparency(img);
		terrainCache.put(id, img);
		return img;
	}
	
	public static Image getObstacleTile(Integer id) {
		Image img = obstacleCache.get(id);
		if (img != null)
			return img;
		Obstacle obj = (Obstacle) Project.current.obstacles.getTree().get(id);
		if (obj == null)
			return null;
		Animation anim = (Animation) Project.current.animations.getTree().get(obj.image.id);
		if (anim == null)
			return null;
		Rectangle rect = obj.image.getRectangle();
		img = ImageHelper.newImage( rect.width, rect.height);
		GC gc = new GC(img);
		System.out.println(obj.image);
		gc.drawImage(obj.image.getImage(), rect.x, rect.y, rect.width, rect.height,
				0, 0, rect.width, rect.height);
		gc.dispose();
		img = ImageHelper.correctTransparency(img);
		obstacleCache.put(id, img);
		return img;
	}
	
	public static Image getCharacterTile(CharTile tile) {
		String key = tile.charID + "." + tile.animation + "." + tile.direction;
		Image img = characterCache.get(key);
		if (img != null)
			return img;
		GameCharacter c = (GameCharacter) Project.current.characters.getTree().get(tile.charID);
		if (c == null)
			return null;
		Animation anim = (Animation) Project.current.animations.getTree().
				get(c.findAnimation(tile.animation));
		if (anim == null)
			return null;
		int w = anim.quad.width / anim.cols;
		int h = anim.quad.height / anim.rows;
		img = ImageHelper.newImage(w, h);
		GC gc = new GC(img);
		gc.drawImage(anim.quad.getImage(), 0, h * tile.direction / 45, w, h, 
				- w / 2 + anim.transform.offsetX, - h + anim.transform.offsetY, w, h);
		gc.dispose();
		img = ImageHelper.correctTransparency(img);
		characterCache.put(key, img);
		return img;
	}
	
	public static Image getRegionTile(int id, boolean rect) {
		String key = id + " " + rect;
		Image img = regionCache.get(key);
		if (img != null)
			return img;
		if (id < 0)
			return null;
		Region r = (Region) Project.current.regions.getData().get(id);
		if (rect)
			img = ImageHelper.getStringImage(id + "", conf.grid.tileW, conf.grid.tileH, 
				new Color(Display.getCurrent(), r.rgb), true);
		else
			img = ImageHelper.getStringImage(id + "", conf.grid.tileW, conf.grid.tileH, null, false);
		img = ImageHelper.correctTransparency(img);
		regionCache.put(key, img);
		return img;
	}

}
