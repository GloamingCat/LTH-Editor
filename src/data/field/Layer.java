package data.field;

import data.subcontent.Tag;
import lui.base.data.LDataList;

public class Layer {
	
	public static class Info {
		public String name = "New Layer";
		public int height = 1;
		public boolean noAuto = false;
		public LDataList<Tag> tags = new LDataList<>();
	}
	
	public Info info = new Info();
	public boolean visible = true;
	public int[][] grid;
	
	public Layer() {}
	
	public Layer(int sizeX, int sizeY) {
		this.grid = new int[sizeX][sizeY];
		for (int i = 0; i < sizeX; i++) {
			for (int j = 0; j < sizeY; j ++) {
				grid[i][j] = -1;
			}
		}
	}
	
	public Layer(Layer original) {
		int sizeX = original.grid.length;
		int sizeY = original.grid[0].length;
		grid = new int[sizeX][sizeY];
		for (int i = 0; i < sizeX; i++) {
            System.arraycopy(original.grid[i], 0, grid[i], 0, sizeY);
		}
		info.name = original.info.name;
		info.height = original.info.height;
		info.noAuto = original.info.noAuto;
		info.tags = new LDataList<>(original.info.tags);
	}

	public Layer(Layer original, int width, int height, int x0, int y0) {
		this(width, height);
		int oldW = original.grid.length;
		int oldH = original.grid[0].length;
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				int x = x0 + i, y = y0 + j;
				if (x < oldW && y < oldH && x >= 0 && y >= 0) {
					grid[i][j] = original.grid[x][y];
				} else {
					grid[i][j] = -1;
				}
			}
		}
		info = original.info;
		visible = original.visible;
	}

	public String toString() {
		return info.name;
	}
	
}