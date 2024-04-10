package gui.helper;

import java.util.ArrayList;
import java.util.Iterator;

import data.config.Config.Grid;
import data.field.Layer;
import lui.base.data.LPoint;

public abstract class FieldMath {

	public LPoint[] neighborShift;
	public LPoint[] vertexShift;
	public int autoTileRows;
	public int initialDirection;
	protected final Grid conf;
	
	public FieldMath(int autoTileRows, int initialDirection) {
		this.autoTileRows = autoTileRows;
		this.initialDirection = initialDirection;
		this.conf = FieldHelper.config.grid;
		ArrayList<LPoint> neighbors = createNeighborShift();
		neighborShift = new LPoint[neighbors.size()];
		for (int i = 0; i < neighborShift.length; i++) {
			neighborShift[i] = neighbors.get(i);
		}
		ArrayList<LPoint> vertex = createVertexShift();
		vertexShift = new LPoint[vertex.size()];
		for (int i = 0; i < vertexShift.length; i++) {
			vertexShift[i] = vertex.get(i);
		}
	}
	
	public ArrayList<LPoint> fullNeighborShift() {
		ArrayList<LPoint> p = new ArrayList<>();
		p.add(new LPoint(1, 1));
		p.add(new LPoint(1, 0));
		p.add(new LPoint(1, -1));
		p.add(new LPoint(0, -1));
		p.add(new LPoint(-1, -1));
		p.add(new LPoint(-1, 0));
		p.add(new LPoint(-1, 1));
		p.add(new LPoint(0, 1));
		return p;
	}
	
	public ArrayList<LPoint> fullVertexShift() {
		ArrayList<LPoint> p = new ArrayList<>();
		p.add(new LPoint(-conf.tileW / 2, -conf.tileS / 2));
		p.add(new LPoint(-conf.tileB / 2, -conf.tileH / 2));
		p.add(new LPoint(conf.tileB / 2, -conf.tileH / 2));
		p.add(new LPoint(conf.tileW / 2, -conf.tileS / 2));
		p.add(new LPoint(conf.tileW / 2, conf.tileS / 2));
		p.add(new LPoint(conf.tileB / 2, conf.tileH / 2));
		p.add(new LPoint(-conf.tileB / 2, conf.tileH / 2));
		p.add(new LPoint(-conf.tileW / 2, conf.tileS / 2));
		return p;
	}
	
	protected abstract void removePoints(ArrayList<LPoint> p);
	
	protected ArrayList<LPoint> createNeighborShift() {
		ArrayList<LPoint> p = fullNeighborShift();
		removePoints(p);
		return p;
	}

	protected ArrayList<LPoint> createVertexShift() {
		ArrayList<LPoint> p = fullVertexShift();
		removePoints(p);
		return p;
	}
	
	public abstract LPoint pixelSize(int sizeX, int sizeY);
	public abstract LPoint pixelCenter(int sizeX, int sizeY, int height);
	public abstract int pixelDisplacement(int height);
	public abstract int fieldDepth(int sizeX, int sizeY);
	public abstract LPoint depthLimits(int sizeX, int sizeY, int height);
	public abstract int lineWidth(int sizeX, int sizeY);
	public abstract LPoint pixel2Tile(float x, float y, float d);
	public abstract LPoint tile2Pixel(float x, float y, float h);
	public abstract int[] autotile(int[][] grid, int x, int y);
	public abstract Iterator<ArrayList<LPoint>> lineIterator(int sizeX, int sizeY);
	
	public int pixelDepth(int x, int y, int height) {
		int ty = y + height * conf.pixelsPerHeight;
		int d = -ty * conf.depthPerY / conf.tileH;
		return d - height * conf.depthPerHeight;
	}
	
	public int[] autotile(Layer layer, int x, int y) {
		if (layer.info.noAuto) {
			int n = autoTileRows - 2;
			return new int[] {n, n, n, n};
		} else {
			return autotile(layer.grid, x, y);
		}
	}
	
	protected static boolean sameType(int[][] grid, int x1, int y1, int x2, int y2) {
		if (x1 < 0 || x1 >= grid.length || x2 < 0 || x2 >= grid.length)
			return true;
		if (y1 < 0 || y1 >= grid[0].length || y2 < 0 || y2 >= grid[0].length)
			return true;
		int t1 = grid[x1][y1];
		int t2 = grid[x2][y2];
		return t1 == t2;
	}
	
	protected static boolean sameType(int[][] grid, LPoint p, int dx, int dy) {
		return sameType(grid, p.x, p.y, p.x + dx, p.y + dy);
	}

	
}
