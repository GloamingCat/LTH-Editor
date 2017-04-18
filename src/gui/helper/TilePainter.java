package gui.helper;

import java.util.HashMap;

import lwt.dataestructure.LDataList;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import org.eclipse.wb.swt.SWTResourceManager;

import data.Animation;
import data.BattlerType;
import data.Config;
import data.GameCharacter;
import data.Obstacle;
import data.Region;
import data.Terrain;
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
		
		LDataList<Object> terrains = Project.current.terrains.getList();
		if (terrainID >= terrains.size()) {
			return subImage;
		}
		Terrain terrain = (Terrain) terrains.get(terrainID);
		String path = terrain.quad.imagePath;
		Image image = SWTResourceManager.getImage(Project.current.imagePath() + path);
		
		int srcX = ((terrain.quad.width / terrain.frameCount) - w) / 2 + terrain.quad.x;
		int srcY = ((terrain.quad.height / FieldHelper.math.autoTileRows) - h) / 2 + terrain.quad.y;
		
		if (srcX < 0) srcX = 0;
		if (srcY < 0) srcY = 0;
		if (image.getBounds().width < w) w = image.getBounds().width;
		if (image.getBounds().height < h) h = image.getBounds().height;
		
		//
		GC gc = new GC(subImage);
		gc.drawImage(image, srcX, srcY, w, h, 0, 0, w, h);
		gc.dispose();
		//
		return subImage;
	}
	
	public static Image getObstacleTile(int id) {
		Obstacle obj = (Obstacle) Project.current.obstacles.getList().get(id);
		String path = obj.quad.imagePath;
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
	
	public static Image getCharacterTile(int id, int animID, int direction) {
		GameCharacter c = (GameCharacter) Project.current.charField.getList().get(id);
		Animation anim = (Animation) Project.current.animCharacter.getList().get(c.animations.get(animID).id);
		String path = anim.imagePath;
		Image image = SWTResourceManager.getImage(Project.current.imagePath() + path);
		
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
		Region r =  conf.regions.get(id);
		Image str = ImageHelper.getStringImage(id + "", conf.grid.tileW, conf.grid.tileH, 
				new Color(Display.getCurrent(),  r.rgb), true);
		return str;
	}

	public static Image getBattlerTypeTile(BattlerType bt) {
		Image str = ImageHelper.getStringImage(bt.code, conf.grid.tileW, conf.grid.tileH, null, true);
		return str;
	}
	
	public static Image getPartyTile(int id) {
		Image str = ImageHelper.getStringImage(id + "", conf.grid.tileW, conf.grid.tileH, null, true);
		return str;
	}
	
}
