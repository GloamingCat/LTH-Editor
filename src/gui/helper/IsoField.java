package gui.helper;

import java.util.ArrayList;

import org.eclipse.swt.graphics.Point;

public class IsoField extends BaseIsoField {

	public IsoField() {
		super(6);
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
	public int[] autotile(int[][] grid, int x, int y) {
		int[] rows = new int[] {0, 0, 0, 0};
		
		if (sameType(grid, x, y, x + 1, y)) {
			rows[1] = 1;
		}
		if (sameType(grid, x, y, x, y + 1)) {
			rows[3] = 1;
		}
		if (sameType(grid, x, y, x - 1, y)) {
			rows[2] = 1;
		}
		if (sameType(grid, x, y, x, y - 1)) {
			rows[0] = 1;
		}
		
		if (rows[1] > 0 && rows[3] > 0 && sameType(grid, x, y, x + 1, y + 1)) {
			rows[1] = 2;
			rows[3] = 2;
		}
		if (rows[0] > 0 && rows[2] > 0 && sameType(grid, x, y, x - 1, y - 1)) {
			rows[0] = 2;
			rows[2] = 2;
		}
		if (rows[0] > 0 && rows[1] > 0 && sameType(grid, x, y, x + 1, y - 1)) {
			rows[0] += 2;
			rows[1] += 2;
		}
		if (rows[2] > 0 && rows[3] > 0 && sameType(grid, x, y, x - 1, y + 1)) {
			rows[2] += 2;
			rows[3] += 2;
		}
		
		if (rows[0] == 0 && rows[1] == 0 && rows[2] == 0 && rows[3] == 0) {
			rows[0] = rows[1] = rows[2] = rows[3] = 5;
		}

		return rows;
	}

	
}
