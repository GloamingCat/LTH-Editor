package gui.helper;

import data.config.Config;
import lbase.data.LPoint;
import project.Project;

public class FieldHelper {

	public static FieldMath math;
	public static Config config;
	
	public static void reloadMath() {
		config = Project.current.config.getData();
		if (config.grid.tileW == config.grid.tileB || config.grid.tileH == config.grid.tileS) {
			math = new OrtField();
		} else if (config.grid.tileB == 0 && config.grid.tileS == 0) {
			math = new IsoField();
		} else {
			math = new HexField();
		}
	}
	
	public static int[] getTilePolygon(int x0, int y0, float scale) {
		LPoint[] shift = math.vertexShift;
		int[] p = new int[shift.length * 2];
		for(int i = 0; i < shift.length; i++) {
			p[i * 2] = Math.round((shift[i].x * scale + x0));
			p[i * 2 + 1] = Math.round((shift[i].y * scale + y0));
		}
		return p;
	}
	
}
