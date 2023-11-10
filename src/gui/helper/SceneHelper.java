package gui.helper;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.PaletteData;
import org.eclipse.swt.widgets.Display;
import org.eclipse.wb.swt.SWTResourceManager;

import batching.Quad;
import batching.Scene;
import batching.Transform;
import data.Animation;
import data.Obstacle;
import data.Terrain;
import data.config.Config;
import data.config.Region;
import data.field.CharTile;
import data.field.Field;
import data.field.Layer;
import data.subcontent.Point;
import lwt.LImageHelper;
import project.Project;
import rendering.Renderer;
import rendering.Screen;
import rendering.ShaderProgram;
import rendering.Texture;
import rendering.VertexArray;

public class SceneHelper {
	
	private static HashMap<String, Texture> loadedTextures = new HashMap<String, Texture>();
	private static Config conf;
	private static Renderer renderer = new Renderer();
	private static Texture whiteTexture = Texture.white(255);

	public static void reload() {
		conf = (Config) Project.current.config.getData();
		for (Texture texture : loadedTextures.values())
			texture.dispose();
		loadedTextures.clear();
	}
	
	public static void createTileTextures(Renderer renderer, ShaderProgram shader) {
		var regions = Project.current.regions.getList();
		for (int i = 0; i < regions.size(); i++) {
			String key = "?" + i;
			Texture texture = createRegionTexture(i, 1, shader);
			Texture old = loadedTextures.get(key);
			if (old != null)
				old.dispose();
			loadedTextures.put(key, texture);
		}
		Texture cell = createTileTexture(renderer, 0.95f, shader);
		Texture old = loadedTextures.get("?g");
		if (old != null)
			old.dispose();
		loadedTextures.put("?g", cell);
	}
	
	//////////////////////////////////////////////////
	// {{ Scene
	
	public static Scene createScene(Field field, int x0, int y0, boolean showGrid,
			Layer currentLayer, CharTile currentChar) {
		Scene scene = new Scene(FieldHelper.math.fieldDepth(field.sizeX, field.sizeY) * (4 + field.prefs.maxHeight));
		Iterator<ArrayList<Point>> it = FieldHelper.math.lineIterator(field.sizeX, field.sizeY);
		int depth = 0;
		while (it.hasNext()) {
			ArrayList<Point> tiles = it.next();
			for (Point tile : tiles) {
				for (Layer layer : field.layers.terrain) {
					if (!layer.visible)
						continue;
					addTerrain(layer, tile, scene, x0, y0, depth + layer.info.height - 1);
					if (showGrid && currentLayer == layer) {
						Point pos = FieldHelper.math.tile2Pixel(tile.x, tile.y, layer.info.height - 1);
						addTile(pos, scene, x0, y0, depth + layer.info.height - 1, "?g");
					}
				}
				for (Layer layer : field.layers.region) {
					if (!layer.visible)
						continue;
					addRegion(layer, tile, scene, x0, y0, depth + 1 + layer.info.height - 1);
					if (showGrid && currentLayer == layer) {
						Point pos = FieldHelper.math.tile2Pixel(tile.x, tile.y, layer.info.height - 1);
						addTile(pos, scene, x0, y0, depth + 1 + layer.info.height - 1, "?g");
					}
				}
				for (Layer layer : field.layers.obstacle) {
					if (!layer.visible)
						continue;
					addObstacle(layer, tile, scene, x0, y0, depth + 2 + layer.info.height - 1);
					if (showGrid && currentLayer == layer) {
						Point pos = FieldHelper.math.tile2Pixel(tile.x, tile.y, layer.info.height - 1);
						addTile(pos, scene, x0, y0, depth + 2 + layer.info.height - 1, "?g");
					}
				}
				for (CharTile ctile : field.characters) {
					if (ctile.x - 1 != tile.x || ctile.y - 1 != tile.y)
						continue;
					addCharacter(ctile, tile, scene, x0, y0, depth + 3 + ctile.h - 1);
					if (showGrid && currentChar == ctile) {
						Point pos = FieldHelper.math.tile2Pixel(tile.x, tile.y, ctile.h - 1);
						addTile(pos, scene, x0, y0, depth + 3 + ctile.h - 1, "?g");
					}
				}
			}
			depth += 4 + field.prefs.maxHeight;
		}
		return scene;
	}
	
	public static void addTerrain(Layer layer, Point tile, Scene scene, int x0, int y0, int depth) {
		int id = layer.grid[tile.x][tile.y];
		if (id < 0)
			return;
		Terrain terrain = (Terrain) Project.current.terrains.getTree().get(id);
		if (terrain == null)
			return;
		Animation anim = (Animation) Project.current.animations.getTree().get(terrain.animID);
		if (anim == null)
			return;
		if (anim.quad.path.isEmpty())
			return;
		int[] rows = FieldHelper.math.autotile(layer, tile.x, tile.y);
		
		int dw = anim.quad.width / anim.cols;
		int dh = anim.quad.height / anim.rows;
		
		Point pos = FieldHelper.math.tile2Pixel(tile.x, tile.y, layer.info.height - 1);
		int w = dw / 2;
		int h = dh / 2;
		
		Quad tl = new Quad(anim.quad.path, 0, 0, w, h);
		batching.Transform ttl = anim.transform.convert();
		
		tl.x = anim.quad.x;
		tl.y = anim.quad.y + dh*rows[0];
		scene.add(tl, ttl, pos.x + x0, pos.y + y0, w, h, depth);
		
		Quad tr = tl.clone();
		tr.x = anim.quad.x + w;
		tr.y = anim.quad.y + dh*rows[1];
		var ttr = ttl.clone();
		ttr.offsetX -= w;
		scene.add(tr, ttr, pos.x + x0, pos.y + y0, w, h, depth);
		
		Quad bl = tl.clone();
		bl.x = anim.quad.x;
		bl.y = anim.quad.y + h + dh*rows[2];
		var tbl = ttl.clone();
		tbl.offsetY -= h;
		scene.add(bl, tbl, pos.x + x0, pos.y + y0, w, h, depth);
		
		Quad br = tl.clone();
		br.x = anim.quad.x + w;
		br.y = anim.quad.y + h + dh*rows[3];
		var tbr = ttl.clone();
		tbr.offsetX -= w;
		tbr.offsetY -= h;
		scene.add(br, tbr, pos.x + x0, pos.y + y0, w, h, depth);
	}
	
	public static void addRegion(Layer layer, Point tile, Scene scene, int x0, int y0, int depth) {
		int id = layer.grid[tile.x][tile.y];
		if (id < 0)
			return;
		Point pos = FieldHelper.math.tile2Pixel(tile.x, tile.y, layer.info.height - 1);
		addTile(pos, scene, x0, y0, depth, "?" + id);
	}
	
	public static void addTile(Point pos, Scene scene, int x0, int y0, int depth, String tex) {
		Texture texture = loadedTextures.get(tex);
		Quad quad = new Quad(tex, 0, 0, texture.width, texture.height);
		Transform transform = new Transform();
		transform.offsetX = texture.width / 2;
		transform.offsetY = texture.height / 2;
		scene.add(quad, transform, pos.x + x0, pos.y + y0, quad.width, quad.height, depth);
	}
	
	public static void addObstacle(Layer layer, Point tile, Scene scene, int x0, int y0, int depth) {
		int id = layer.grid[tile.x][tile.y];
		if (id < 0)
			return;
		Obstacle obj = (Obstacle) Project.current.obstacles.getTree().get(id);
		if (obj == null)
			return;
		Animation anim = (Animation) Project.current.animations.getTree().get(obj.image.id);
		if (anim == null)
			return;
		//Rectangle rect = obj.image.getRectangle();
		//int w = Math.round(rect.width * anim.transform.scaleX / 100f * obj.transform.scaleX / 100f);
		//int h = Math.round(rect.height * anim.transform.scaleY / 100f * obj.transform.scaleY / 100f);
	}
	
	public static void addCharacter(CharTile info, Point tile, Scene scene, int x0, int y0, int depth) {
		
	}
	
	// }}
	
	//////////////////////////////////////////////////
	// {{ Texture
	
	public static Texture getTexture(String path) {
		Texture texture = loadedTextures.get(path);
		if (texture == null) {
			Image image = SWTResourceManager.getImage(Project.current.imagePath() + path);
			if (image == null)
				return null;
			texture = SceneHelper.toTexture(image);
			loadedTextures.put(path, texture);
		}
		return texture;
	}
	
	public static Image toImage(Texture texture) {
		ByteBuffer buffer = texture.toBuffer(4);
		byte[] bytes;
		if (buffer.hasArray())
			bytes = buffer.array();
		else {
			bytes = new byte[buffer.capacity()];
			buffer.get(bytes);
		}
		ImageData data = new ImageData(
				texture.width, texture.height, 32,
				new PaletteData(0xff000000, 0xff0000, 0xff00), 1,
				bytes);
		LImageHelper.correctTransparency(data);
		return new Image(Display.getCurrent(), data);
	}
	
	public static Texture toTexture(Image image) {
		ImageData data = image.getImageData();
		ByteBuffer buffer = ByteBuffer.allocateDirect(data.width * data.height * 4);
		for (int i = 0; i < data.width * data.height; i++) {
			buffer.put(i*4, data.data[i*3+2]);
			buffer.put(i*4+1, data.data[i*3+1]);
			buffer.put(i*4+2, data.data[i*3]);
			buffer.put(i*4+3, data.alphaData[i]);
		}
		return new Texture(data.width, data.height, 4, buffer);
	}
	
	// }}

	//////////////////////////////////////////////////
	// {{ Tile texture
	
	public static Image createTileImage(Renderer renderer, float scale, Color color, ShaderProgram shader) {
		renderer.setPencilColor(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
		Texture texture = createTileTexture(renderer, scale, shader);
		Image img = toImage(texture);
		texture.dispose();
		return img;
	}
	
	private static Texture createTileTexture(Renderer renderer, float scale, ShaderProgram shader) {
		int w = (int)Math.ceil(conf.grid.tileW * scale) + 6;
		int h = (int)Math.ceil(conf.grid.tileH * scale) + 6;
		Screen cellBuffer = new Screen(w, h, false);
		VertexArray array = VertexArray.octagon(w / 2, h / 2, 
				conf.grid.tileW * scale, conf.grid.tileH * scale,
				conf.grid.tileB * scale, conf.grid.tileS * scale,
				0, 0, 0, 127);
		array.initVAO(shader.attributes, shader.vertexSize);
		cellBuffer.bind(shader);
		whiteTexture.bind();
		renderer.drawPath(array.getVaoId(), array.vertices.size());
		array.dispose();
		cellBuffer.dispose(true);
		return cellBuffer.texture;
	}
	
	private static Texture createRegionTexture(int id, float scale, ShaderProgram shader) {
		Region r = (Region) Project.current.regions.getData().get(id);
		Color color = SWTResourceManager.getColor(r.rgb);
		int w = (int)Math.ceil(conf.grid.tileW * scale) + 6;
		int h = (int)Math.ceil(conf.grid.tileH * scale) + 6;
		Screen cellBuffer = new Screen(w, h, false);
		VertexArray array = VertexArray.octagon(w / 2, h / 2, 
				conf.grid.tileW * scale, conf.grid.tileH * scale,
				conf.grid.tileB * scale, conf.grid.tileS * scale,
				color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
		array.initVAO(shader.attributes, shader.vertexSize);
		cellBuffer.bind(shader);
		whiteTexture.bind();
		renderer.drawPolygon(array.getVaoId(), array.vertices.size());
		array.dispose();
		cellBuffer.dispose(true);
		return cellBuffer.texture;
	}
	
	// }}	
	
}
