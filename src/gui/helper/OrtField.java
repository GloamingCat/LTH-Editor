package gui.helper;

import java.util.ArrayList;

import org.eclipse.swt.graphics.Point;

public class OrtField extends FieldMath {

	public OrtField() {
		super(6, 270);
	}
	
	protected ArrayList<Point> createNeighborShift() {
		ArrayList<Point> p = fullNeighborShift();
		removePoints(p);
		return p;
	}

	@Override
	protected ArrayList<Point> createVertexShift() {
		ArrayList<Point> p = fullVertexShift();
		removePoints(p);
		return p;
	}
	
	protected void removePoints(ArrayList<Point> p) {
		if (!conf.allNeighbors) {
			Point p0 = p.get(1);
			Point p1 = p.get(5);
			Point p2 = p.get(3);
			Point p3 = p.get(7);
			p.remove(p0);
			p.remove(p1);
			p.remove(p2);
			p.remove(p3);
		}
	}
	
	@Override
	public Point pixelSize(int sizeX, int sizeY) {
		return new Point(sizeX * conf.tileW, sizeY * conf.tileH);
	}
	
	@Override
	public Point pixelCenter(int sizeX, int sizeY, int height) {
		return new Point((sizeX * conf.tileW) / 2 , 
				(sizeY * conf.tileH + pixelDisplacement(height)) / 2);
	}
	
	@Override
	public int pixelDisplacement(int height) {
		return height * conf.pixelsPerHeight;
	}

	@Override
	public Point pixel2Tile(float x, float y, float d) {
		float newH = d / conf.pixelsPerHeight;
		int newX = Math.round(x);
		int newY = Math.round(y + newH);	
		return new Point(newX, newY);
	}

	@Override
	public Point tile2Pixel(float x, float y, float h) {		
		float newH = h * conf.pixelsPerHeight;
		int newX = Math.round(x * conf.tileW);
		int newY = Math.round(y * conf.tileW - newH);
		return new Point(newX, newY);
	}

	@Override
	public int[] autotile(int[][] grid, int x, int y) {
		int[] rows = new int[] {0, 0, 0, 0};
		
		if (sameType(grid, x, y, x, y - 1)) {
			rows[0] = 1;
			rows[1] = 1;
		}
		if (sameType(grid, x, y, x, y + 1)) {
			rows[2] = 1;
			rows[3] = 1;
		}
		if (sameType(grid, x, y, x + 1, y)) {
			rows[1] += 2;
			rows[3] += 2;
		}
		if (sameType(grid, x, y, x - 1, y)) {
			rows[0] += 2;
			rows[2] += 2;
		}
		
		if (rows[3] >= 3 && sameType(grid, x, y, x + 1, y + 1)) {
			rows[3] = 4;
		}
		if (rows[0] >= 3 && sameType(grid, x, y, x - 1, y - 1)) {
			rows[0] = 4;
		}
		if (rows[1] >= 3 && sameType(grid, x, y, x + 1, y - 1)) {
			rows[1] = 4;
		}
		if (rows[2] >= 3 && sameType(grid, x, y, x - 1, y + 1)) {
			rows[2] = 4;
		}
		
		if (rows[0] == 0 && rows[1] == 0 && rows[2] == 0 && rows[3] == 0) {
			rows[0] = rows[1] = rows[2] = rows[3] = 5;
		}

		return rows;
	}

}
