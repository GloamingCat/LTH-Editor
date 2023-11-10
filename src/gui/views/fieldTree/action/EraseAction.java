package gui.views.fieldTree.action;

import gui.views.fieldTree.FieldCanvas;
import lwt.action.LAction;

public class EraseAction implements LAction {

	private int x;
	private int y;
	private int id;
	private int[][] grid;
	private FieldCanvas canvas;
	
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
		canvas.redrawBuffer();
		canvas.redraw();
	}

	@Override
	public void redo() {
		grid[x][y] = -1;
		canvas.onTileChange(x, y);
		canvas.redrawBuffer();
		canvas.redraw();
	}

}
