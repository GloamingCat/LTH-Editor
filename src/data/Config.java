package data;

import lwt.dataestructure.LDataList;

public class Config {
	
	// General
	public String name = "New Project";
	
	// Grid
	public int tileW = 36;
	public int tileH = 22;
	public int tileB = 16;
	public int tileS = 0;
	public int pixelsPerHeight = 16;
	
	public boolean allNeighbors = false;
	public boolean pixelMovement = false;

	// Types
	public LDataList<Attribute> attributes = new LDataList<>();
	public LDataList<String> elements = new LDataList<>();
	public LDataList<String> regions = new LDataList<>();
	
}
