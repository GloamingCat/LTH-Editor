package gui.helper;

import java.util.ArrayList;

import org.eclipse.swt.graphics.Point;

import data.Config;

public abstract class FieldMath {

	public final boolean isIso;
	public final int rowCount;
	public Point[] neighborShift;
	public Point[] vertexShift;
	protected final Config conf;
	
	public FieldMath(boolean iso) {
		this.isIso = iso;
		this.rowCount = iso ? 8 : 4;
		this.conf = FieldHelper.config;
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
	
	public abstract ArrayList<Point> fullNeighborShift();
	public abstract ArrayList<Point> fullVertexShift();
	
	protected abstract ArrayList<Point> createNeighborShift();
	protected abstract ArrayList<Point> createVertexShift();
	
	public abstract Point pixelSize(int sizeX, int sizeY);
	public abstract int pixelDisplacement(int height);
	public abstract Point pixel2Tile(int x, int y, int d);
	public abstract Point tile2Pixel(int x, int y, int h);
	public abstract int[] autotile(int[][] grid, int x, int y);
	
	protected static boolean sameType(int[][] grid, int x1, int y1, int x2, int y2) {
		if (x1 < 0 || x1 >= grid.length || x2 < 0 || x2 >= grid.length)
			return true;
		if (y1 < 0 || y1 >= grid[0].length || y2 < 0 || y2 >= grid[0].length)
			return true;
		int t1 = grid[x1][y1];
		int t2 = grid[x2][y2];
		return t1 == t2;
	}
	
}
