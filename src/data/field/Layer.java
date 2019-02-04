package data.field;

import data.subcontent.Tag;
import lwt.dataestructure.LDataList;

public class Layer {
	
	public static class Info {
		public String name = "New Layer";
		public int height = 1;
		public LDataList<Tag> tags = new LDataList<>();
	}
	
	public Info info = new Info();
	public boolean visible = true;
	public int[][] grid;
	
	public Layer() {}
	
	public Layer(int sizeX, int sizeY) {
		this.grid = new int[sizeX][sizeY];
		for(int i = 0; i < sizeX; i++) {
			for(int j = 0; j < sizeY; j ++) {
				grid[i][j] = -1;
			}
		}
	}
	
	public Layer(Layer original) {
		int sizeX = original.grid.length;
		int sizeY = original.grid[0].length;
		grid = new int[sizeX][sizeY];
		for(int i = 0; i < sizeX; i++) {
			for(int j = 0; j < sizeY; j ++) {
				grid[i][j] = original.grid[i][j];
			}
		}
		info.name = original.info.name;
		info.height = original.info.height;
		info.tags = new LDataList<Tag>(original.info.tags);
	}

	public String toString() {
		return info.name;
	}
	
}