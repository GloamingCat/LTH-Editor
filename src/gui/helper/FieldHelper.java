package gui.helper;

import data.config.Config;
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
	
}
