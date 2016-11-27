package data;

import lwt.dataestructure.LDataList;

public class Field {

	public int id = -1;
	public String name = "New Field";
	public int sizeX = 15;
	public int sizeY = 15;
	public int tilesetID = 0;
	public LDataList<Layer> layers = new LDataList<>();
	public LDataList<Tag> tags = new LDataList<>();
	
	public Field() {}
	
	public Field(int id, int sizeX, int sizeY) {
		this.id = id;
		this.sizeX = sizeX;
		this.sizeY = sizeY;
		layers.add(new Layer(sizeX, sizeY));
	}
	
	public static class Layer {
		
		public String name = "New Layer";
		public int sizeX = 15;
		public int sizeY = 15;
		public int type = 0;
		public int height = 0;
		public boolean visible = true;
		public int[][] grid;
		public LDataList<Tag> tags = new LDataList<>();
		
		public Layer() {}
		
		public Layer(int sizeX, int sizeY) {
			this.sizeX = sizeX;
			this.sizeY = sizeY;
		}
		
		public String toString() {
			return name;
		}
		
	}
	
	public String toString() {
		return name;
	}
	
}
