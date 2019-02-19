package gui.helper;

import java.util.HashMap;

import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Display;
import org.eclipse.wb.swt.SWTResourceManager;

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
	
	public static HashMap<String, Image> terrainCache = new HashMap<>();
	public static HashMap<Integer, Image> obstacleCache = new HashMap<>();
	public static HashMap<String, Image> characterCache = new HashMap<>();
	public static HashMap<String, Image> regionCache = new HashMap<>();
	
	public static void reload() {
		reload(terrainCache);
		reload(obstacleCache);
		reload(regionCache);
		reload(characterCache);
		conf = (Config) Project.current.config.getData();
		System.gc();
	}
	
	public static void reload(HashMap<?, Image> map) {
		for(Image img : map.values())
			img.dispose();
		map.clear();
	}
	
	public static Image getTerrainTile(Integer id, boolean full) {
		String key = id + "" + full;
		Image img = terrainCache.get(key);
		if (img != null) return img;
		
		Terrain terrain = (Terrain) Project.current.terrains.getTree().get(id);
		if (terrain == null) return null;
		Animation anim = (Animation) Project.current.animations.getTree().get(terrain.animID);
		if (anim == null) return null;
		Image terrainImg = anim.quad.getImage();
		if (terrainImg == null) return null;
		
		int w = anim.quad.width;
		int h = anim.quad.height;
		if (!full) {
			w /= anim.cols;
			h /= anim.rows;
		}
		int dw = (w * anim.transform.scaleX) / 100;
		int dh = (h * anim.transform.scaleY) / 100;
		img = ImageHelper.newImage(dw, dh);
		GC gc = new GC(img);
		gc.setAlpha(anim.transform.alpha);
		gc.drawImage(terrainImg, anim.quad.x, anim.quad.y, w, h, 0, 0, dw, dh);
		gc.dispose();
		ImageData data = img.getImageData();
		ImageHelper.correctTransparency(data);
		ImageHelper.colorTransform(data,
				anim.transform.red / 255f,
				anim.transform.green / 255f,
				anim.transform.blue / 255f,
				anim.transform.hue, 
				anim.transform.saturation / 100f, 
				anim.transform.brightness / 100f);
		img.dispose();
		img = new Image(Display.getCurrent(), data);
		terrainCache.put(key, img);
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
		int w = rect.width * anim.transform.scaleX / 100;
		int h = rect.height * anim.transform.scaleY / 100;
		img = ImageHelper.newImage(rect.width, rect.height);
		GC gc = new GC(img);
		Image texture = obj.image.getImage();
		Rectangle t = texture.getBounds();
		rect.x = Math.min(rect.x, t.width - 1);
		rect.y = Math.min(rect.y, t.height - 1);
		gc.setAlpha(anim.transform.alpha * obj.transform.alpha / 255);
		gc.drawImage(texture, rect.x, rect.y, 
				Math.min(rect.width, t.width - rect.x),
				Math.min(rect.height, t.height - rect.y),
				0, 0, w, h);
		gc.dispose();
		ImageData data = img.getImageData();
		ImageHelper.correctTransparency(data);
		ImageHelper.colorTransform(img, 
			anim.transform.red / 255f * obj.transform.red / 255f,
			anim.transform.green / 255f * obj.transform.green / 255f,
			anim.transform.blue / 255f * obj.transform.blue / 255f,
			anim.transform.hue + obj.transform.hue, 
			anim.transform.saturation / 100f * obj.transform.saturation / 100f, 
			anim.transform.brightness / 100f * obj.transform.brightness / 100f);
		img.dispose();
		img = new Image(Display.getCurrent(), data);
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
		int col = anim.getFirstFrame();
		img = ImageHelper.newImage(w, h);
		GC gc = new GC(img);
		gc.setAlpha(anim.transform.alpha * c.transform.alpha / 255);
		gc.drawImage(anim.quad.getImage(), 
				anim.quad.x + w * col, anim.quad.y + h * (tile.direction / 45), w, h, 
				0, 0, w, h);
		gc.dispose();
		ImageData data = img.getImageData();
		ImageHelper.colorTransform(data, 
				anim.transform.red / 255f * c.transform.red / 255f,
				anim.transform.green / 255f * c.transform.green / 255f,
				anim.transform.blue / 255f * c.transform.blue / 255f,
				anim.transform.hue + c.transform.hue, 
				anim.transform.saturation / 100f * c.transform.saturation / 100f, 
				anim.transform.brightness / 100f * c.transform.brightness / 100f);
		img.dispose();
		img = new Image(Display.getCurrent(), data);
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
			img = ImageHelper.getStringImage(id + "", conf.grid.tileW + 1, conf.grid.tileH + 1, 
				SWTResourceManager.getColor(r.rgb), true);
		else
			img = ImageHelper.getStringImage(id + "", conf.grid.tileW, conf.grid.tileH, null, false);
		regionCache.put(key, img);
		return img;
	}

}
