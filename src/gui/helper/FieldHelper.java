package gui.helper;

import data.Config;
import project.Project;

public class FieldHelper {

	public static FieldMath math;
	public static Config config;
	
	public static void reloadMath() {
		config = (Config) Project.current.config.getData();
		if (config.tileW == config.tileB || config.tileH == config.tileS) {
			math = new OrtField();
		} else if (config.tileB == 0 && config.tileS == 0) {
			math = new IsoField();
		} else {
			math = new HexField();
		}
	}
	
}
