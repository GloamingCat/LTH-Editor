package gui.helper;

import java.util.HashMap;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import org.eclipse.wb.swt.SWTResourceManager;

import data.Animation;
import data.GameCharacter;
import data.Obstacle;
import data.Terrain;
import data.config.Config;
import data.config.Region;
import project.Project;

public class TilePainter {

	private static Config conf;
	
	public static HashMap<Integer, Image> terrainTiles = new HashMap<>();
	public static HashMap<Integer, Image> obstacleTiles = new HashMap<>();
	public static HashMap<String, Image> characterTiles = new HashMap<>();
	public static HashMap<Integer, Image> regionTiles = new HashMap<>();
	
	public static void reloadConfig() {
		conf = (Config) Project.current.config.getData();
	}
	
	public static Image getTerrainTile(int terrainID) {
		int w = conf.grid.tileW;
		int h = conf.grid.tileH;
		Image subImage = new Image(Display.getCurrent(), w, h);
		
		Terrain terrain = (Terrain) Project.current.terrains.getTree().get(terrainID);
		Animation anim = (Animation) Project.current.animations.getTree().get(terrain.animID);
		if (anim != null) {
			String path = anim.quad.path;
			Image image = SWTResourceManager.getImage(Project.current.imagePath() + path);
			
			int srcX = ((anim.quad.width / anim.cols) - w) / 2 + anim.quad.x;
			int srcY = ((anim.quad.height / FieldHelper.math.autoTileRows) - h) / 2 + anim.quad.y;
			
			if (srcX < 0) srcX = 0;
			if (srcY < 0) srcY = 0;
			if (image.getBounds().width < w) w = image.getBounds().width;
			if (image.getBounds().height < h) h = image.getBounds().height;
			
			//
			GC gc = new GC(subImage);
			gc.drawImage(image, srcX, srcY, w, h, 0, 0, w, h);
			gc.dispose();
			//
		}
		return subImage;
	}
	
	public static Image getObstacleTile(int id) {
		Obstacle obj = (Obstacle) Project.current.obstacles.getTree().get(id);
		Animation anim = (Animation) Project.current.animations.getTree().get(obj.image.id);
		String path = anim.quad.path;
		Image image = SWTResourceManager.getImage(Project.current.imagePath() + path);
		
		int w = conf.grid.tileW;
		int h = conf.grid.tileH;
		Image subImage = new Image(Display.getCurrent(), w, h);
		
		int srcX = (image.getBounds().width - w) / 2 + obj.transform.offsetX;
		int srcY = image.getBounds().height - obj.transform.offsetY - h / 2;
		
		if (srcX < 0) srcX = 0;
		if (srcY < 0) srcY = 0;
		if (image.getBounds().width - srcX < w) w = image.getBounds().width - srcX;
		if (image.getBounds().height - srcY < h) h = image.getBounds().height - srcY;
		
		//
		GC gc = new GC(subImage);
		gc.drawImage(image, srcX, srcY, w, h, 0, 0, w, h);
		gc.dispose();
		//
		return subImage;
	}
	
	public static Image getCharacterTile(int id, String animKey, int direction) {
		GameCharacter c = (GameCharacter) Project.current.characters.getTree().get(id);
		Animation anim = (Animation) Project.current.animations.getTree().
				get(c.findAnimation(animKey));
		Image image = SWTResourceManager.getImage(Project.current.imagePath() + anim.quad.path);
		
		int w = conf.grid.tileW;
		int h = conf.grid.tileH;
		Image subImage = new Image(Display.getCurrent(), w, h);
		
		int fw = image.getBounds().width / anim.cols;
		int fh = image.getBounds().height / anim.rows;
		
		int srcX = (fw - w) / 2;
		int srcY = fh * (direction / 45) + (fh - h) / 2;
		
		if (srcX < 0) srcX = 0;
		if (srcY < 0) srcY = 0;
		if (image.getBounds().width - srcX < w) w = image.getBounds().width - srcX;
		if (image.getBounds().height - srcY < h) h = image.getBounds().height - srcY;
		
		//
		GC gc = new GC(subImage);
		gc.drawImage(image, srcX, srcY, w, h, 0, 0, w, h);
		gc.dispose();
		//
		return subImage;
	}
	
	public static Image getRegionTile(int id) {
		Region r = (Region) Project.current.regions.getData().get(id);
		Image str = ImageHelper.getStringImage(id + "", conf.grid.tileW, conf.grid.tileH, 
				new Color(Display.getCurrent(),  r.rgb), true);
		return str;
	}
	
	public static Image getPartyTile(int id) {
		Image str = ImageHelper.getStringImage(id + "", conf.grid.tileW, conf.grid.tileH, null, true);
		return str;
	}
	
}
