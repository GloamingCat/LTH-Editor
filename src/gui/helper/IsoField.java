package gui.helper;

import java.util.ArrayList;

import org.eclipse.swt.graphics.Point;

public class IsoField extends FieldMath {

	public IsoField() {
		super(true);
	}
	
	protected ArrayList<Point> createNeighborShift() {
		ArrayList<Point> p = new ArrayList<>();
		p.add(new Point(1, 0));
		p.add(new Point(1, 1));
		p.add(new Point(0, 1));
		p.add(new Point(-1, 1));
		p.add(new Point(-1, 0));
		p.add(new Point(-1, -1));
		p.add(new Point(0, -1));
		p.add(new Point(1, -1));
		removePoints(p);
		return p;
	}

	@Override
	protected ArrayList<Point> createVertexShift() {
		ArrayList<Point> p = new ArrayList<>();
		p.add(new Point(conf.tileB / 2, -conf.tileH / 2));
		p.add(new Point(conf.tileW / 2, -conf.tileS / 2));
		p.add(new Point(conf.tileW / 2, conf.tileS / 2));
		p.add(new Point(conf.tileB / 2, conf.tileH / 2));
		p.add(new Point(-conf.tileB / 2, conf.tileH / 2));
		p.add(new Point(-conf.tileW / 2, conf.tileS / 2));
		p.add(new Point(-conf.tileW / 2, -conf.tileS / 2));
		p.add(new Point(-conf.tileB / 2, -conf.tileH / 2));
		removePoints(p);
		return p;
	}
	
	private void removePoints(ArrayList<Point> p) {
		if (!conf.allNeighbors) {
			Point p0 = p.get(1);
			Point p1 = p.get(5);
			Point p2 = p.get(3);
			Point p3 = p.get(7);
			if (conf.tileS == 0) {
				p.remove(p0);
				p.remove(p1);
			}
			if (conf.tileB == 0) {
				p.remove(p2);
				p.remove(p3);
			}
		}
	}
	
	@Override
	public Point pixelSize(int sizeX, int sizeY) {
		return new Point((sizeX + sizeY - 1) * (conf.tileW + conf.tileB) / 2 + (conf.tileW - conf.tileB) / 2,
						 (sizeX + sizeY) * (conf.tileH + conf.tileS) / 2 + (conf.tileH - conf.tileS) / 2);
	}
	
	@Override
	public int pixelDisplacement(int height) {
		return height * (conf.tileH + conf.tileS) / 2 + (conf.tileH - conf.tileS) / 2;
	}

	@Override
	public Point pixel2Tile(int x, int y, int d) {
		int newH = d * conf.pixelsPerHeight;
		float sxy = x * 2 / (conf.tileW + conf.tileB);			// sum
		float dyx = (y + newH) * 2 / (conf.tileH + conf.tileS); // dif
		int newX = Math.round((sxy - dyx) / 2);
		int newY = Math.round((sxy + dyx) / 2);
		return new Point(newX, newY);
	}

	@Override
	public Point tile2Pixel(int x, int y, int h) {
		int newHeight = h * conf.pixelsPerHeight;
		int newX = (x + y) * (conf.tileW + conf.tileB) / 2;
		int newY = (y - x) * (conf.tileH + conf.tileS) / 2 - newHeight;
		return new Point(newX, newY);
	}

	@Override
	public int[] autotile(int[][] grid, int x, int y) {
		int[] rows = new int[] {0, 0, 0, 0};
		int step1 = conf.tileS > 0 ? 2 : 1;
		int step2 = conf.tileB > 0 ? 2 : 1;
		int shiftCount = neighborShift.length;
		Point p = new Point(x, y);
		int n;
		for(int i = -1; i <= 1; i++) {
			n = (i + shiftCount) % shiftCount;
			if (sameType(grid, p, neighborShift[n])) {
				rows[1] += Math.pow(2, 1 + i);
			}
			n = (step1 + (i + 3) % 3 - 1) % shiftCount;
			if (sameType(grid, p, neighborShift[n])) {
				rows[3] += Math.pow(2, 1 + i);
			}
			n = (i + step1 + step2) % shiftCount;
			if (sameType(grid, p, neighborShift[n])) {
				rows[2] += Math.pow(2, 1 + i);
			}
			n = (step1 + step2 + step1 + (i + 3) % 3 - 1) % shiftCount;
			if (sameType(grid, p, neighborShift[n])) {
				rows[0] += Math.pow(2, 1 + i);
			}
		}
		return rows;
	}
	
	private static boolean sameType(int[][] grid, Point p, Point shift) {
		return sameType(grid, p.x, p.y, p.x + shift.x, p.y + shift.y);
	}
	
}
