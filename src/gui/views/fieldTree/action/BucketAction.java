package gui.views.fieldTree.action;

import java.util.ArrayList;

import org.eclipse.swt.graphics.Point;

import gui.views.fieldTree.FieldCanvas;
import lwt.action.LAction;

public class BucketAction implements LAction {

	private int[][] grid;
	private FieldCanvas canvas;
	private ArrayList<Point> tiles;
	private int newID;
	private int oldID;
	
	public BucketAction(int[][] grid, int newID, int oldID, ArrayList<Point> tiles, FieldCanvas canvas) {
		this.grid = grid;
		this.tiles = tiles;
		this.canvas = canvas;
		this.newID = newID;
		this.oldID = oldID;
	}
	
	@Override
	public void undo() {
		for(Point p : tiles) {
			grid[p.x][p.y] = oldID;
			canvas.updateTileImage(p.x, p.y);
		}
		canvas.redraw();
	}

	@Override
	public void redo() {
		for(Point p : tiles) {
			grid[p.x][p.y] = newID;
			canvas.updateTileImage(p.x, p.y);
		}
		canvas.redraw();
	}

}
