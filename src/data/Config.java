package data;

import java.util.HashMap;

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
	
	public Script textVariables = new Script("/gui/Variables.lua", "");
	public Position startPos = new Position();
	public HashMap<String, Font> fonts = new HashMap<>();
	public HashMap<String, Audio> sounds = new HashMap<>();

	// Types
	public LDataList<Attribute> attributes = new LDataList<>();
	public LDataList<String> elements = new LDataList<>();
	public LDataList<String> regions = new LDataList<>();
	
	public static class Font {
		
		public String path = "";
		public int size = 12;
		public String format = "";
		
		public Font() {}
		public Font(String path, int size, String format) {
			this.path = path;
			this.size = size;
			this.format = format;
		}
		
		public String toString() {
			return path.replace("fonts/", "") + " " + size;
		}
	}
	
	public Config() {
		fonts.put("dialog", new Font("Arial", 12, "\\c{#000000}"));
		fonts.put("button", new Font("Arial", 12, "\\c{#000000}"));
		fonts.put("popup", new Font("Arial", 14, "\\c{#0000FF}"));
		sounds.put("cursor", new Audio("cursor.wav", 100, 100, 100));
		sounds.put("confirm", new Audio("confirm.wav", 100, 100, 100));
		sounds.put("cancel", new Audio("cancel.wav", 100, 100, 100));
		sounds.put("dialog", new Audio("cursor.wav", 100, 75, 150));
	}
	
}
