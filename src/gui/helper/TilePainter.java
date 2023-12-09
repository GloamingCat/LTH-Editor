package gui.helper;

import java.util.HashMap;

import data.Animation;
import data.GameCharacter;
import data.Obstacle;
import data.Terrain;
import data.config.Config;
import data.config.Region;
import data.field.CharTile;
import data.subcontent.Transform;
import lwt.graphics.LPainter;
import lwt.graphics.LPoint;
import lwt.graphics.LRect;
import lwt.graphics.LTexture;
import project.Project;

public class TilePainter {

	private static Config conf;
	
	public static HashMap<String, LTexture> terrainCache = new HashMap<>();
	public static HashMap<Integer, LTexture> obstacleCache = new HashMap<>();
	public static HashMap<String, LTexture> characterCache = new HashMap<>();
	public static HashMap<String, LTexture> regionCache = new HashMap<>();
	
	public static void reload() {
		reload(terrainCache);
		reload(obstacleCache);
		reload(regionCache);
		reload(characterCache);
		conf = (Config) Project.current.config.getData();
		System.gc();
	}
	
	public static void reload(HashMap<?, LTexture> map) {
		for(LTexture img : map.values())
			img.dispose();
		map.clear();
	}
	
	public static LTexture getTerrainTile(Integer id, boolean full) {
		String key = id + "" + full;
		LTexture img = terrainCache.get(key);
		if (img != null) return img;
		
		Terrain terrain = (Terrain) Project.current.terrains.getTree().get(id);
		if (terrain == null) return null;
		Animation anim = (Animation) Project.current.animations.getTree().get(terrain.animID);
		if (anim == null) return null;
		LTexture terrainImg = new LTexture(anim.quad.fullPath());
		if (terrainImg.isEmpty())
			return null;
		int w = anim.quad.width / (full ? 1 : anim.cols);
		int h = anim.quad.height / (full ? 1 : anim.rows);
		int dw = (w * anim.transform.scaleX) / 100;
		int dh = (h * anim.transform.scaleY) / 100;
		img = new LTexture(dw, dh);
		LPainter gc = new LPainter(img) {
			@Override
			public void paint() {
				setTransparency(anim.transform.alpha);
				drawImage(terrainImg, anim.quad.x, anim.quad.y, w, h, 0, 0, dw, dh);
				dispose();
			}
		};
		try {
			gc.paint();
			img.colorTransform(
					anim.transform.red / 255f,
					anim.transform.green / 255f,
					anim.transform.blue / 255f,
					anim.transform.alpha / 255f,
					anim.transform.hue, 
					anim.transform.saturation / 100f, 
					anim.transform.brightness / 100f);
			terrainCache.put(key, img);
		} catch (IllegalArgumentException e) {
			System.out.print("Couldn't draw terrain image.");
		}
		return img;
	}
	
	public static LTexture getObstacleTile(Integer id) {
		LTexture img = obstacleCache.get(id);
		if (img != null)
			return img;
		Obstacle obj = (Obstacle) Project.current.obstacles.getTree().get(id);
		if (obj == null)
			return null;
		Animation anim = (Animation) Project.current.animations.getTree().get(obj.image.id);
		if (anim == null)
			return null;
		LRect rect = obj.image.getRectangle();
		int w = Math.round(rect.width * anim.transform.scaleX / 100f * obj.transform.scaleX / 100f);
		int h = Math.round(rect.height * anim.transform.scaleY / 100f * obj.transform.scaleY / 100f);
		img = new LTexture(w, h);
		LTexture texture = new LTexture(obj.image.fullPath());
		if (texture.isEmpty())
			return null;
		LPoint s = texture.getSize();
		rect.x = Math.min(rect.x, s.x - 1);
		rect.y = Math.min(rect.y, s.y - 1);
		LPainter gc = new LPainter(img) {
			@Override
			public void paint() {
				setTransparency(anim.transform.alpha * obj.transform.alpha / 255);
				drawImage(texture, rect.x, rect.y, 
						Math.min(rect.width, s.x - rect.x),
						Math.min(rect.height, s.y - rect.y),
						0, 0, w, h);
			}
		};
		try {
			gc.paint();
			gc.dispose();
			img.colorTransform( 
				anim.transform.red / 255f * obj.transform.red / 255f,
				anim.transform.green / 255f * obj.transform.green / 255f,
				anim.transform.blue / 255f * obj.transform.blue / 255f,
				anim.transform.alpha / 255f * obj.transform.alpha / 255f,
				anim.transform.hue + obj.transform.hue, 
				anim.transform.saturation / 100f * obj.transform.saturation / 100f, 
				anim.transform.brightness / 100f * obj.transform.brightness / 100f);
			obstacleCache.put(id, img);
		} catch (IllegalArgumentException e) {
			System.out.println("Couldn't draw obstacle image: " + id);
		}
		return img;
	}
	
	public static LTexture getCharacterTile(CharTile tile) {
		GameCharacter c = (GameCharacter) Project.current.characters.getTree().get(tile.charID);
		if (c != null) {
			int animID = c.findAnimation(tile.animation);
			return getAnimationTile(tile.charID, animID, tile.direction, tile.frame - 1, c.transform);
		} else if (!tile.animation.isEmpty()) {
			int animID = Project.current.animations.getData().get(tile.animation);
			if (animID == -1)
				return null;
			return getAnimationTile(-1, animID, tile.direction, tile.frame - 1, Transform.neutral);
		}
		return null;
	}
	
	public static LTexture getAnimationTile(int charID, int animID, int direction, int frame, Transform transform) {
		String key = charID + "." + animID + "." + direction + "." + frame;
		LTexture img = characterCache.get(key);
		if (img != null)
			return img;
		Animation anim = (Animation) Project.current.animations.getTree().get(animID);
		if (anim == null || anim.cols == 0 || anim.rows == 0)
			return null;
		int w = anim.quad.width / anim.cols;
		int h = anim.quad.height / anim.rows;
		int col = anim.getFrame(frame);
		int row = (direction / 45);
		img = new LTexture(w, h);
		LTexture quadImg = new LTexture(anim.quad.fullPath());
		LPainter gc = new LPainter(img) {
			@Override
			public void paint() {
				setTransparency(anim.transform.alpha * transform.alpha / 255);
				drawImage(quadImg, // Image
						anim.quad.x + w * col, anim.quad.y + h * row, w, h, // Source
						0, 0, w, h); // Destination
			}
		};
		try {
			gc.dispose();
			img.colorTransform(
					anim.transform.red / 255f * transform.red / 255f,
					anim.transform.green / 255f * transform.green / 255f,
					anim.transform.blue / 255f * transform.blue / 255f,
					anim.transform.alpha / 255f * transform.alpha / 255f,
					anim.transform.hue + transform.hue, 
					anim.transform.saturation / 100f * transform.saturation / 100f, 
					anim.transform.brightness / 100f * transform.brightness / 100f);
			characterCache.put(key, img);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
		return img;
	}

	public static LTexture getRegionTile(int id, boolean rect) {
		String key = id + " " + rect;
		LTexture img = regionCache.get(key);
		if (img != null)
			return img;
		if (id < 0)
			return null;
		Region r = (Region) Project.current.regions.getData().get(id);
		if (rect)
			img = new LTexture(id + "", conf.grid.tileW + 1, conf.grid.tileH + 1, r.color, true);
		else
			img = new LTexture(id + "", conf.grid.tileW, conf.grid.tileH, null, false);
		regionCache.put(key, img);
		return img;
	}

}
