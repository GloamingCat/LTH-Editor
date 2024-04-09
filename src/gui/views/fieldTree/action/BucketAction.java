package gui.views.fieldTree.action;

import java.util.ArrayList;

import gui.views.fieldTree.FieldCanvas;
import lbase.action.LAction;
import lbase.data.LPoint;

public class BucketAction implements LAction {

	private int[][] grid;
	private FieldCanvas canvas;
	private ArrayList<LPoint> tiles;
	private int newID;
	private int oldID;
	
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
		canvas.redrawBuffer();
		canvas.redraw();
	}

	@Override
	public void redo() {
		for(LPoint p : tiles) {
			grid[p.x][p.y] = newID;
		}
		canvas.onTileChange(tiles);
		canvas.redrawBuffer();
		canvas.redraw();
	}

}
