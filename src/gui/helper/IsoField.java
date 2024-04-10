package gui.helper;

import java.util.ArrayList;

import lui.base.data.LPoint;

public class IsoField extends BaseIsoField {

	public IsoField() {
		super(6);
		initialDirection = 315;
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
