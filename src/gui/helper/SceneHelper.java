package gui.helper;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import batching.Quad;
import batching.Scene;
import batching.Transform;
import data.Animation;
import data.GameCharacter;
import data.Obstacle;
import data.Terrain;
import data.config.Config;
import data.config.Region;
import data.field.CharTile;
import data.field.Field;
import data.field.Layer;
import data.subcontent.Icon;
import lui.graphics.LColor;
import lui.base.data.LPoint;
import lui.graphics.LTexture;
import project.Project;
import rendering.*;

public class SceneHelper {
	
	public static Context context = null;
	private static final HashMap<String, Texture> loadedTextures = new HashMap<>();
	private static Config conf;
	private static Texture whiteTexture;

	public static boolean initContext() {
		try {
			if (context == null)
				context = new Context(1, 1);
			else if (context.isInitialized())
				return true;
			context.init();
		} catch (UnsatisfiedLinkError | ExceptionInInitializerError | OpenGLError e) {
			return false;
		}
		whiteTexture = Texture.white(255);
		return true;
	}
	
	public static void terminateContext() {
		if (context.isInitialized())
			context.dispose();
		reload();
	}

	public static void reload() {
		conf = Project.current.config.getData();
		for (Texture texture : loadedTextures.values())
			texture.dispose();
		loadedTextures.clear();
	}
	
	public static void createTileTextures(Renderer renderer, ShaderProgram shader) {
		if (loadedTextures.containsKey("?g"))
			return;
		var regions = Project.current.regions.getList();
		for (int i = 0; i < regions.size(); i++) {
			String key = "?" + i;
			Texture texture = createRegionTexture(renderer, i, 1, shader);
			Texture old = loadedTextures.get(key);
			if (old != null)
				old.dispose();
			loadedTextures.put(key, texture);
		}
		Texture cell = createTileTexture(renderer, 0.95f, 64, shader);
		Texture old = loadedTextures.get("?g");
		if (old != null)
			old.dispose();
		loadedTextures.put("?g", cell);
	}
	
	//////////////////////////////////////////////////
	// {{ Scene
	
	public static Scene createScene(Field field, int x0, int y0, boolean showGrid,
			Layer currentLayer, CharTile currentChar) {
		LPoint depthLimits = FieldHelper.math.depthLimits(field.sizeX, field.sizeY, field.prefs.maxHeight);
		Scene scene = new Scene(depthLimits.first(), depthLimits.second());
		Iterator<ArrayList<LPoint>> it = FieldHelper.math.lineIterator(field.sizeX, field.sizeY);
		while (it.hasNext()) {
			ArrayList<LPoint> tiles = it.next();
			for (LPoint tile : tiles) {
				int charHeight = currentChar == null ? -1 : currentChar.h;
				for (int i = 0; i < field.layers.terrain.size(); i++) {
					Layer layer = field.layers.terrain.get(i);
					if (!layer.visible)
						continue;
					addTerrain(layer, tile, scene, x0, y0, field.layers.terrain.size() - i);
					if (showGrid && currentLayer == layer) {
						addTile(tile, layer.info.height, scene, x0, y0, "?g");
					}
				}
				for (Layer layer : field.layers.region) {
					if (!layer.visible || layer != currentLayer)
						continue;
					addRegion(layer, tile, scene, x0, y0);
					if (showGrid) {
						addTile(tile, layer.info.height, scene, x0, y0, "?g");
					}
				}
				for (Layer layer : field.layers.obstacle) {
					if (!layer.visible)
						continue;
					if (showGrid && currentLayer == layer) {
						addTile(tile, layer.info.height, scene, x0, y0, "?g");
					}
					addObstacle(layer, tile, scene, x0, y0);
				}
				for (CharTile ctile : field.characters) {
					if (showGrid && ctile.h == charHeight) {
						addTile(tile, ctile.h, scene, x0, y0, "?g");
						charHeight = -1;
					}
					if (!ctile.visible || ctile.x - 1 != tile.x || ctile.y - 1 != tile.y)
						continue;
					addCharacter(ctile, tile, scene, x0, y0);
				}
			}
		}
		return scene;
	}
	
	public static void addTerrain(Layer layer, LPoint tile, Scene scene, int x0, int y0, int order) {
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
		
		LPoint pos = FieldHelper.math.tile2Pixel(tile.x, tile.y, layer.info.height - 1);
		int depth = FieldHelper.math.pixelDepth(pos.x, pos.y, layer.info.height - 1);
		int dTop = depth + anim.transform.offsetDepth + order;
		int dBottom = dTop + conf.grid.depthPerY / 2;
		
		int w = dw / 2;
		int h = dh / 2;
		
		Quad tl = new Quad(anim.quad.path, 0, 0, w, h);
		batching.Transform ttl = anim.transform.convert();
		
		tl.x = anim.quad.x;
		tl.y = anim.quad.y + dh*rows[0];
		scene.add(tl, ttl, pos.x + x0, pos.y + y0, w, h, dTop);
		
		Quad tr = tl.clone();
		tr.x = anim.quad.x + w;
		tr.y = anim.quad.y + dh*rows[1];
		var ttr = ttl.clone();
		ttr.offsetX -= w;
		scene.add(tr, ttr, pos.x + x0, pos.y + y0, w, h, dTop);
		
		Quad bl = tl.clone();
		bl.x = anim.quad.x;
		bl.y = anim.quad.y + h + dh*rows[2];
		var tbl = ttl.clone();
		tbl.offsetY -= h;
		scene.add(bl, tbl, pos.x + x0, pos.y + y0, w, h, dBottom);
		
		Quad br = tl.clone();
		br.x = anim.quad.x + w;
		br.y = anim.quad.y + h + dh*rows[3];
		var tbr = ttl.clone();
		tbr.offsetX -= w;
		tbr.offsetY -= h;
		scene.add(br, tbr, pos.x + x0, pos.y + y0, w, h, dBottom);
	}
	
	public static void addRegion(Layer layer, LPoint tile, Scene scene, int x0, int y0) {
		int id = layer.grid[tile.x][tile.y];
		if (id < 0)
			return;
		addTile(tile, layer.info.height, scene, x0, y0, "?" + id);
	}
	
	public static void addTile(LPoint tile, int height, Scene scene, int x0, int y0, String tex) {
		LPoint pos = FieldHelper.math.tile2Pixel(tile.x, tile.y, height - 1);
		int depth = FieldHelper.math.pixelDepth(pos.x, pos.y, height - 1);
		Texture texture = loadedTextures.get(tex);
		Quad quad = new Quad(tex, 0, 0, texture.width, texture.height);
		Transform transform = new Transform();
		transform.offsetX = texture.width / 2;
		transform.offsetY = texture.height / 2;
		scene.add(quad, transform, pos.x + x0, pos.y + y0, quad.width, quad.height, depth);
	}
	
	public static void addObstacle(Layer layer, LPoint tile, Scene scene, int x0, int y0) {
		int id = layer.grid[tile.x][tile.y];
		if (id < 0)
			return;
		Obstacle obj = (Obstacle) Project.current.obstacles.getTree().get(id);
		if (obj == null)
			return;
		LPoint pos = FieldHelper.math.tile2Pixel(tile.x, tile.y, layer.info.height - 1);
		int depth = FieldHelper.math.pixelDepth(pos.x, pos.y, layer.info.height - 1);
		addIcon(obj.image, obj.transform, pos, scene, x0, y0, depth);
	}
	
	public static void addCharacter(CharTile info, LPoint tile, Scene scene, int x0, int y0) {
		GameCharacter c = (GameCharacter) Project.current.characters.getTree().get(info.charID);
		Icon icon = new Icon();
		if (c != null) {
			icon.id = c.findAnimation(info.animation);
		} else if (!info.animation.isEmpty()) {
			icon.id = Project.current.animations.getData().get(info.animation);
		}
		if (icon.id == -1)
			return;
		Animation anim = (Animation) Project.current.animations.getTree().get(icon.id);
		icon.col = anim.getFrame(info.frame - 1);
		icon.row = info.direction / 45;
		LPoint pos = FieldHelper.math.tile2Pixel(tile.x, tile.y, info.h - 1);
		int depth = FieldHelper.math.pixelDepth(pos.x, pos.y, info.h - 1);
		if (c != null) {
			addIcon(icon, c.transform, pos, scene, x0, y0, depth - 1);
			if (c.shadowID >= 0) {
				icon.id = c.shadowID;
				icon.col = 0;
				icon.row = 0;
				addIcon(icon, null, pos, scene, x0, y0, depth);
			}
		} else {
			addIcon(icon, null, pos, scene, x0, y0, depth);			
		}
	}
	
	public static void addIcon(Icon icon, data.subcontent.Transform transform, LPoint pos, Scene scene, int x0, int y0, int depth) {
		Animation anim = (Animation) Project.current.animations.getTree().get(icon.id);
		if (anim == null)
			return;
		int w = anim.quad.width / anim.cols;
		int h = anim.quad.height / anim.rows;
		Quad quad = new Quad(anim.quad.path, anim.quad.x + w * icon.col, anim.quad.y + h * icon.row, w, h);
		if (transform != null) {
			transform = transform.clone().combine(anim.transform);
		} else {
			transform = anim.transform;
		}
		depth += transform.offsetDepth;
		scene.add(quad, transform.convert(), pos.x + x0, pos.y + y0, w, h, depth);
	}
	
	// }}
	
	//////////////////////////////////////////////////
	// {{ Texture
	
	public static Texture getTexture(String path) {
		Texture texture = loadedTextures.get(path);
		if (texture == null) {
			LTexture image = new LTexture(Project.current.imagePath() + path);
			if (image.isEmpty())
				return null;
			texture = SceneHelper.toTexture(image);
			loadedTextures.put(path, texture);
		}
		return texture;
	}
	
	public static LTexture toImage(Texture texture) {
		ByteBuffer buffer = texture.toBuffer(4);
		return new LTexture(buffer, texture.width, texture.height, texture.channels);
	}
	
	public static Texture toTexture(LTexture image) {
		LPoint size = image.getSize();
		return new Texture(size.x, size.y, 4, image.toBuffer());
	}
	
	// }}

	//////////////////////////////////////////////////
	// {{ Tile texture
	
	public static LTexture createTileImage(Renderer renderer, float scale, LColor color, ShaderProgram shader) {
		renderer.setPencilColor(color.red, color.green, color.blue, color.alpha);
		Texture texture = createTileTexture(renderer, scale, 255, shader);
		LTexture img = toImage(texture);
		texture.dispose();
		return img;
	}
	
	private static Texture createTileTexture(Renderer renderer, float scale, int alpha, ShaderProgram shader) {
		int w = (int)Math.ceil(conf.grid.tileW * scale) + 6;
		int h = (int)Math.ceil(conf.grid.tileH * scale) + 6;
		Screen cellBuffer = new Screen(w, h, false);
		VertexArray array = VertexArray.octagon(w / 2.0f, h / 2.0f,
				conf.grid.tileW * scale, conf.grid.tileH * scale,
				conf.grid.tileB * scale, conf.grid.tileS * scale,
				0, 0, 0, alpha);
		array.initVAO(shader.attributes, shader.vertexSize);
		cellBuffer.bind(shader);
		whiteTexture.bind();
		renderer.drawPath(array.getVaoId(), array.vertices.size());
		array.dispose();
		cellBuffer.dispose(true);
		return cellBuffer.texture;
	}
	
	private static Texture createRegionTexture(Renderer renderer, int id, float scale, ShaderProgram shader) {
		Region r = (Region) Project.current.regions.getData().get(id);
		int w = (int)Math.ceil(conf.grid.tileW * scale) + 6;
		int h = (int)Math.ceil(conf.grid.tileH * scale) + 6;
		Screen cellBuffer = new Screen(w, h, false);
		VertexArray array = VertexArray.octagon(w / 2.0f, h / 2.0f,
				conf.grid.tileW * scale, conf.grid.tileH * scale,
				conf.grid.tileB * scale, conf.grid.tileS * scale,
				r.color.red, r.color.green, r.color.blue, r.color.alpha);
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
