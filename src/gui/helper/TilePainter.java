package gui.helper;

import java.util.HashMap;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import org.eclipse.wb.swt.SWTResourceManager;

import data.Animation;
import data.Config;
import data.GameCharacter;
import data.Obstacle;
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
		Terrain terrain = (Terrain) Project.current.terrains.getList().get(terrainID);
		String path = terrain.imagePath;
		Image image = SWTResourceManager.getImage(Project.current.imagePath() + path);
		
		int w = conf.tileW;
		int h = conf.tileH;
		Image subImage = new Image(Display.getCurrent(), w, h);
		
		int srcX = ((image.getBounds().width / terrain.frameCount) - w) / 2;
		int srcY = ((image.getBounds().height / FieldHelper.math.rowCount) - h) / 2;
		
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
		
		int w = conf.tileW;
		int h = conf.tileH;
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
		GameCharacter c = (GameCharacter) Project.current.characters.getList().get(id);
		Animation anim = (Animation) Project.current.animCharacter.getList().get(c.animations.get(animID).id);
		String path = anim.imagePath;
		Image image = SWTResourceManager.getImage(Project.current.imagePath() + path);
		
		int w = conf.tileW;
		int h = conf.tileH;
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
		Image image = ImageHelper.getStringImage(id + "", conf.tileW, conf.tileH);
		//
		GC gc = new GC(image);
		gc.setForeground(Display.getCurrent().getSystemColor(SWT.COLOR_BLACK));
		gc.drawRectangle(2, 2, conf.tileW - 5, conf.tileH - 5);
		gc.dispose();
		//
		return image;
	}
	
}
