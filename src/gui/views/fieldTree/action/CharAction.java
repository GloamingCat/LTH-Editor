package gui.views.fieldTree.action;

import data.field.CharTile;
import gui.views.fieldTree.FieldEditor;
import gui.views.fieldTree.FieldSideEditor;
import lwt.action.LAction;

public class CharAction implements LAction {

	private int x;
	private int y;
	private int h;
	private int x0;
	private int y0;
	private int h0;
	private CharTile tile;
	
	public CharAction(CharTile tile, int x, int y, int h) {
		x0 = tile.x; y0 = tile.y; h0 = tile.h;
		this.x = x;
		this.y = y;
		this.h = h;
		this.tile = tile;
	}
	
	@Override
	public void undo() {
		tile.x = x0;
		tile.y = y0;
		tile.h = h0;
		FieldEditor.instance.canvas.updateTileImage(x0 - 1, y0 - 1);
		FieldEditor.instance.canvas.updateTileImage(x - 1, y - 1);
		FieldSideEditor.instance.onMoveCharacter(tile);
	}

	@Override
	public void redo() {
		tile.x = x;
		tile.y = y;
		tile.h = h;
		FieldEditor.instance.canvas.updateTileImage(x0 - 1, y0 - 1);
		FieldEditor.instance.canvas.updateTileImage(x - 1, y - 1);
		FieldSideEditor.instance.onMoveCharacter(tile);
	}

}
