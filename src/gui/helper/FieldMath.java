package gui.helper;

import java.util.ArrayList;

import org.eclipse.swt.graphics.Point;

import data.config.Config.Grid;
import data.field.Layer;

public abstract class FieldMath {

	public Point[] neighborShift;
	public Point[] vertexShift;
	public int autoTileRows;
	public int initialDirection;
	protected final Grid conf;
	
	public FieldMath(int autoTileRows, int initialDirection) {
		this.autoTileRows = autoTileRows;
		this.initialDirection = initialDirection;
		this.conf = FieldHelper.config.grid;
		ArrayList<Point> neighbors = createNeighborShift();
		neighborShift = new Point[neighbors.size()];
		for (int i = 0; i < neighborShift.length; i++) {
			neighborShift[i] = neighbors.get(i);
		}
		ArrayList<Point> vertex = createVertexShift();
		vertexShift = new Point[vertex.size()];
		for (int i = 0; i < vertexShift.length; i++) {
			vertexShift[i] = vertex.get(i);
		}
	}
	
	public ArrayList<Point> fullNeighborShift() {
		ArrayList<Point> p = new ArrayList<>();
		p.add(new Point(1, 0));
		p.add(new Point(1, 1));
		p.add(new Point(0, 1));
		p.add(new Point(-1, 1));
		p.add(new Point(-1, 0));
		p.add(new Point(-1, -1));
		p.add(new Point(0, -1));
		p.add(new Point(1, -1));
		return p;
	}
	
	public ArrayList<Point> fullVertexShift() {
		ArrayList<Point> p = new ArrayList<>();
		p.add(new Point(conf.tileB / 2, -conf.tileH / 2));
		p.add(new Point(conf.tileW / 2, -conf.tileS / 2));
		p.add(new Point(conf.tileW / 2, conf.tileS / 2));
		p.add(new Point(conf.tileB / 2, conf.tileH / 2));
		p.add(new Point(-conf.tileB / 2, conf.tileH / 2));
		p.add(new Point(-conf.tileW / 2, conf.tileS / 2));
		p.add(new Point(-conf.tileW / 2, -conf.tileS / 2));
		p.add(new Point(-conf.tileB / 2, -conf.tileH / 2));
		return p;
	}
	
	protected abstract ArrayList<Point> createNeighborShift();
	protected abstract ArrayList<Point> createVertexShift();
	
	public abstract Point pixelSize(int sizeX, int sizeY);
	public abstract Point pixelCenter(int sizeX, int sizeY, int height);
	public abstract int pixelDisplacement(int height);
	public abstract Point pixel2Tile(float x, float y, float d);
	public abstract Point tile2Pixel(float x, float y, float h);
	public abstract int[] autotile(int[][] grid, int x, int y);
	
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
	
	protected static boolean sameType(int[][] grid, Point p, Point shift) {
		return sameType(grid, p.x, p.y, p.x + shift.x, p.y + shift.y);
	}
	
}
