package gui.views.fieldTree.action;

import gui.views.fieldTree.FieldCanvas;
import lui.base.action.LAction;

public class EraseAction implements LAction {

	private final int x;
	private final int y;
	private final int id;
	private final int[][] grid;
	private final FieldCanvas canvas;
	
	public EraseAction(int[][] grid, int x, int y, int id, FieldCanvas canvas) {
		this.grid = grid;
		this.x = x;
		this.y = y;
		this.id = id;
		this.canvas = canvas;
	}
	
	@Override
	public void undo() {
		grid[x][y] = id;
		canvas.onTileChange(x, y);
		canvas.refreshBuffer(false);
	}

	@Override
	public void redo() {
		grid[x][y] = -1;
		canvas.onTileChange(x, y);
		canvas.refreshBuffer(false);
	}

}
