package gui.views.fieldTree.action;

import data.field.CharTile;
import gui.views.fieldTree.FieldCanvas;
import lbase.action.LAction;

public class CharAction implements LAction {

	private final int x;
	private final int y;
	private final int h;
	private final int x0;
	private final int y0;
	private final int h0;
	private final CharTile tile;

	private final FieldCanvas canvas;
	
	public CharAction(FieldCanvas canvas, CharTile tile, int x, int y, int h) {
		x0 = tile.x; y0 = tile.y; h0 = tile.h;
		this.x = x;
		this.y = y;
		this.h = h;
		this.tile = tile;
		this.canvas = canvas;
	}
	
	@Override
	public void undo() {
		canvas.moveCharacter(tile, x0, y0, h0);
	}

	@Override
	public void redo() {
		canvas.moveCharacter(tile, x, y, h);
	}

}
