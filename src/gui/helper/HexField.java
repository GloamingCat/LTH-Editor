package gui.helper;

import java.util.ArrayList;

import org.eclipse.swt.graphics.Point;

public class HexField extends BaseIsoField {

	public HexField() {
		super(9);
	}
	
	protected void removePoints(ArrayList<Point> p) {
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
		if (rows[0] == 0 && rows[1] == 0 && rows[2] == 0 && rows[3] == 0) {
			rows[0] = rows[1] = rows[2] = rows[3] = 8;
		}
		return rows;
	}
	
}
