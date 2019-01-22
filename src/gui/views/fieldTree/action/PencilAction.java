package gui.views.fieldTree.action;

import gui.views.fieldTree.FieldEditor;
import lwt.action.LAction;

public class PencilAction implements LAction {

	private int x;
	private int y;
	private int[][] oldValues;
	private int[][] newValues;
	private int[][] grid;

	public PencilAction(int[][] grid, int x, int y, int[][] oldValues, int[][] newValues) {
		this.grid = grid;
		this.x = x;
		this.y = y;
		this.oldValues = oldValues;
		this.newValues = newValues;
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
					FieldEditor.instance.canvas.updateTileImage(x + i,  y + j);
					needsRedraw = true;
				}
			}
		}
		if (needsRedraw) {
			FieldEditor.instance.canvas.redraw();
		}
		return needsRedraw;
	}

}
