package gui.helper;

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

public class ImageHelper {

	private static Config conf;
	
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
	
	public static Image getCharacterTile(int id) {
		GameCharacter c = (GameCharacter) Project.current.characters.getList().get(id);
		Animation anim = (Animation) Project.current.animCharacter.getList().get(c.animations.get(0).id);
		String path = anim.imagePath;
		Image image = SWTResourceManager.getImage(Project.current.imagePath() + path);
		
		int w = conf.tileW;
		int h = conf.tileH;
		Image subImage = new Image(Display.getCurrent(), w, h);
		
		int fw = image.getBounds().width / anim.cols;
		int fh = image.getBounds().height / anim.rows;
		
		int srcX = (fw - w) / 2;
		int srcY = fh * (c.direction / 45) + (fh - h) / 2;
		
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
	
	public static Image getObstacleImage(int id) {
		Obstacle obj = (Obstacle) Project.current.obstacles.getList().get(id);
		String path = obj.quad.imagePath;
		Image image = SWTResourceManager.getImage(Project.current.imagePath() + path);
		
		int w = obj.quad.width;
		int h = obj.quad.height;
		Image subImage = new Image(Display.getCurrent(), w, h);
		
		int srcX = obj.quad.x;
		int srcY = obj.quad.y;
		
		//
		GC gc = new GC(subImage);
		gc.drawImage(image, srcX, srcY, w, h, 0, 0, w, h);
		gc.dispose();
		//
		return subImage;
	}
	
	public static Image getAnimationFrame(Animation anim, int col, int row) {
		String path = anim.imagePath;
		Image image = SWTResourceManager.getImage(Project.current.imagePath() + path);
		
		int w = image.getBounds().width / anim.cols;
		int h = image.getBounds().height / anim.rows;
		
		return getImageQuad(image, w * col, h * row, w, h);
	}
	
	public static Image getImageQuad(Image image, int x, int y, int w, int h) {
		try {
			System.out.println(image.getBounds().width + " " + image.getBounds().height);
			System.out.println(w + " " + h);
			Image subImage = new Image(Display.getCurrent(), w, h);
			GC gc = new GC(subImage);
			gc.drawImage(image, x, y, w, h, 0, 0, w, h);
			gc.dispose();
			return subImage;
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
			return SWTResourceManager.getImage("");
		}
	}
	
}
