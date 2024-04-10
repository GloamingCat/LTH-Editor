package gui.views.fieldTree.action;

import gui.views.fieldTree.FieldCanvas;
import lui.base.action.LAction;

public class PencilAction implements LAction {

	private final int x;
	private final int y;
	private final int[][] oldValues;
	private final int[][] newValues;
	private final int[][] grid;
	private final FieldCanvas canvas;

	public PencilAction(FieldCanvas canvas, int[][] grid, int x, int y, int[][] oldValues, int[][] newValues) {
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
					canvas.onTileChange(x + i,  y + j);
					needsRedraw = true;
				}
			}
		}
		if (needsRedraw) {
			canvas.redrawBuffer();
			canvas.redraw();
		}
		return needsRedraw;
	}

}
