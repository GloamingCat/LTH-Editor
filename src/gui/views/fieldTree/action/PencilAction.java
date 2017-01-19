package gui.views.fieldTree.action;

import gui.views.fieldTree.FieldCanvas;
import lwt.action.LAction;

public class PencilAction implements LAction {

	private int x;
	private int y;
	private int[][] oldValues;
	private int[][] newValues;
	private int[][] grid;
	private FieldCanvas canvas;
	
	public PencilAction(int[][] grid, int x, int y, int[][] oldValues, int[][] newValues, FieldCanvas canvas) {
		this.grid = grid;
		this.x = x;
		this.y = y;
		this.oldValues = oldValues;
		this.newValues = newValues;
		this.canvas = canvas;
	}
	
	@Override
	public void undo() {
		apply(oldValues);
	}

	@Override
	public void redo() {
		apply(newValues);
	}
	
	public boolean apply(int[][] selection) {
		boolean needsRedraw = false;
		for(int i = 0; i < selection.length && x + i < grid.length; i++) {
			for(int j = 0; j < selection[i].length && y + j < grid[0].length; j++) {
				if (x + i < 0 || y + j < 0)
					continue;
				if (selection[i][j] != grid[x + i][y + j]) {
					grid[x + i][y + j] = selection[i][j];
					canvas.updateTileImage(x + i,  y + j);
					needsRedraw = true;
				}
			}
		}
		if (needsRedraw) {
			canvas.redraw();
		}
		return needsRedraw;
	}

}
