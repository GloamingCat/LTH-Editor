package data;

import lwt.dataestructure.LDataList;

public class Layer {
	
	public class Info {
		public String name = "New Layer";
		public int type = 0;
		public int height = 0;
		public LDataList<Tag> tags = new LDataList<>();
	}
	
	public Info info = new Info();
	public boolean visible = true;
	public int[][] grid;
	
	public Layer() {}
	
	public Layer(int sizeX, int sizeY) {
		this.grid = new int[sizeX][sizeY];
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
		info.type = original.info.type;
		info.tags = new LDataList<Tag>(original.info.tags);
	}

	public String toString() {
		return info.name;
	}
	
}