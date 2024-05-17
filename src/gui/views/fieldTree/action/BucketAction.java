package gui.views.fieldTree.action;

import java.util.ArrayList;

import gui.views.fieldTree.FieldCanvas;
import lui.base.action.LAction;
import lui.base.data.LPoint;

public class BucketAction implements LAction {

	private final int[][] grid;
	private final FieldCanvas canvas;
	private final ArrayList<LPoint> tiles;
	private final int newID;
	private final int oldID;
	
	public BucketAction(int[][] grid, int newID, int oldID, ArrayList<LPoint> modified, FieldCanvas canvas) {
		this.grid = grid;
		this.tiles = modified;
		this.canvas = canvas;
		this.newID = newID;
		this.oldID = oldID;
	}
	
	@Override
	public void undo() {
		for(LPoint p : tiles) {
			grid[p.x][p.y] = oldID;
		}
		canvas.onTileChange(tiles);
		canvas.refreshBuffer(false);
	}

	@Override
	public void redo() {
		for(LPoint p : tiles) {
			grid[p.x][p.y] = newID;
		}
		canvas.onTileChange(tiles);
		canvas.refreshBuffer(false);
	}

}
