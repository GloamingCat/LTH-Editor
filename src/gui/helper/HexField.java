package gui.helper;

import java.util.ArrayList;

import lui.base.data.LPoint;

public class HexField extends BaseIsoField {

	public HexField() {
		super(9);
	}
	
	protected void removePoints(ArrayList<LPoint> p) {
		if (!conf.allNeighbors) {
			LPoint p0 = p.get(0);
			LPoint p1 = p.get(4);
			LPoint p2 = p.get(2);
			LPoint p3 = p.get(6);
			if (conf.tileS == 0) {
				// Vertically connected
				p.remove(p0);
				p.remove(p1);
			}
			if (conf.tileB == 0) {
				// Horizontally connected
				p.remove(p2);
				p.remove(p3);
			}
		}
	}

	@Override
	public int[] autotile(int[][] grid, int x, int y) {
		int[] rows = new int[] {0, 0, 0, 0};
		LPoint p = new LPoint(x, y);
		
		if (conf.tileS == 0) {
			// Vertically connected
			if (sameType(grid, p, 1, -1)) {
				rows[0] = rows[0] + 1;
				rows[1] = rows[1] + 1;		  
			}
			if (sameType(grid, p, -1, 1)) {
				rows[2] = rows[2] + 1;
				rows[3] = rows[3] + 1;		  
			}
			
			if (sameType(grid, p, 1, 0)) {
				rows[1] = rows[1] + 2;
				rows[3] = rows[3] + 2;		  
			}
			if (sameType(grid, p, -1, 0)) {
				rows[0] = rows[0] + 2;
				rows[2] = rows[2] + 2;
			}
			
			if (sameType(grid, p, 0, 1)) {
				rows[1] = rows[1] + 4;
				rows[3] = rows[3] + 4;		  
			}
			if (sameType(grid, p, 0, -1)) {
				rows[0] = rows[0] + 4;
				rows[2] = rows[2] + 4;		  
			}
		
		}
		
		if (conf.tileB == 0) {
			// Horizontally connected
			if (sameType(grid, p, -1, -1)) {
				rows[0] += 1;
				rows[2] += 1;		  
			}
			if (sameType(grid, p, 1, 1)) {
				rows[1] += 1;
				rows[3] += 1;		  
			}
		
			if (sameType(grid, p, 1, 0)) {
				rows[1] += 2;
				rows[0] += 2;		  
			}
			if (sameType(grid, p, -1, 0)) {
				rows[3] += 2;
				rows[2] += 2;
			}
			
			if (sameType(grid, p, 0, 1)) {
				rows[3] += 4;
				rows[2] += 4;		  
			}
			if (sameType(grid, p, 0, -1)) {
				rows[1] += 4;
				rows[0] += 4;
			}

		}
		
		if (rows[0] == 0 && rows[1] == 0 && rows[2] == 0 && rows[3] == 0) {
			rows[0] = rows[1] = rows[2] = rows[3] = 8;
		}
		return rows;
	}
	
}
