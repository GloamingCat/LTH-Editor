package data.field;

import data.subcontent.Tag;
import lui.base.data.LDataList;

public class Layer {
	
	public static class Info {
		public String name = "New Layer";
		public int height = 1;
		public boolean noAuto = false;
		public LDataList<Tag> tags = new LDataList<>();
		public Info() {}
		public Info(Info original) {
			name = original.name;
			height = original.height;
			noAuto = original.noAuto;
			tags = new LDataList<>(original.tags);
		}
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
		info = new Info(original.info);
		visible = original.visible;
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

	public void transpose() {
		int[][] newGrid = new int[grid[0].length][grid.length];
		for (int i = 0; i < newGrid.length; i++) {
			for (int j = 0; j < newGrid[0].length; j++) {
				newGrid[i][j] = grid[j][i];
			}
		}
		grid = newGrid;
	}

	public void invertX() {
		int[][] newGrid = new int[grid.length][grid[0].length];
		for (int i = 0; i < newGrid.length; i++) {
            System.arraycopy(grid[newGrid.length - i - 1], 0, newGrid[i], 0, newGrid[0].length);
		}
		grid = newGrid;
	}

	public void invertY() {
		int[][] newGrid = new int[grid.length][grid[0].length];
		for (int i = 0; i < newGrid.length; i++) {
			for (int j = 0; j < newGrid[0].length; j++) {
				newGrid[i][j] = grid[i][newGrid[0].length - j - 1];
			}
		}
		grid = newGrid;
	}

	public void rotate90() {
		transpose();
		invertX();
	}

	public void rotate270() {
		transpose();
		invertY();
	}

}