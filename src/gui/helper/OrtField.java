package gui.helper;

import java.util.ArrayList;
import java.util.Iterator;

import lwt.graphics.LPoint;

public class OrtField extends FieldMath {

	public OrtField() {
		super(6, 270);
	}
	
	protected void removePoints(ArrayList<LPoint> p) {
		if (!conf.allNeighbors) {
			LPoint p0 = p.get(0);
			LPoint p1 = p.get(2);
			LPoint p2 = p.get(4);
			LPoint p3 = p.get(6);
			p.remove(p0);
			p.remove(p1);
			p.remove(p2);
			p.remove(p3);
		}
	}
	
	@Override
	public LPoint pixelSize(int sizeX, int sizeY) {
		return new LPoint(sizeX * conf.tileW, sizeY * conf.tileH);
	}
	
	@Override
	public LPoint pixelCenter(int sizeX, int sizeY, int height) {
		return new LPoint((sizeX * conf.tileW) / 2 , 
				(sizeY * conf.tileH + pixelDisplacement(height)) / 2);
	}
	
	@Override
	public int pixelDisplacement(int height) {
		return 0;
	}
	
	@Override
	public int fieldDepth(int sizeX, int sizeY) {
		return sizeY;
	}
	
	@Override
	public LPoint depthLimits(int sizeX, int sizeY, int height) {
		int dpy = conf.depthPerY;
		int pph = conf.pixelsPerHeight;
		int dph = conf.depthPerHeight;
		return new LPoint(
				(int)-Math.ceil(sizeY * dpy + pph* 2 + dph * (height - 1)),
				(int) Math.ceil(pph + dph * (height + 1)));
	}

	@Override
	public int lineWidth(int sizeX, int sizeY) {
		return FieldHelper.config.grid.tileW * sizeX;
	}
	
	@Override
	public LPoint pixel2Tile(float x, float y, float d) {
		float newH = d / conf.pixelsPerHeight;
		int newX = Math.round(x / conf.tileW);
		int newY = Math.round((y + newH) / conf.tileH);
		return new LPoint(newX, newY);
	}

	@Override
	public LPoint tile2Pixel(float x, float y, float h) {		
		float newH = h * conf.pixelsPerHeight;
		int newX = Math.round(x * conf.tileW);
		int newY = Math.round(y * conf.tileH - newH);
		return new LPoint(newX, newY);
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
	
	public Iterator<ArrayList<LPoint>> lineIterator(int sizeX, int sizeY) {
		return new Iterator<ArrayList<LPoint>>() {
			int j = 0;
			@Override
			public ArrayList<LPoint> next() {
				ArrayList<LPoint> list = new ArrayList<>();
				for(int i = 0; i < sizeX; i++) {
					list.add(new LPoint(i, j));
				}
				j++;
				return list;
			}
			@Override
			public boolean hasNext() {
				return j < sizeY;
			}
		};
	}

}
