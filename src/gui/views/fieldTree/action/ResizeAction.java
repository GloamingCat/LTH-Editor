package gui.views.fieldTree.action;

import gui.views.fieldTree.FieldToolBar;
import lui.graphics.LRect;
import lui.base.action.LAction;
import data.field.Field;

public class ResizeAction implements LAction {

	private final FieldToolBar toolBar;
	private final LRect oldSize;
	private final LRect newSize;

	private final Field.Layers oldLayers;
	private final Field.Layers newLayers;
	
	public ResizeAction (FieldToolBar toolBar, LRect oldSize, Field.Layers oldLayers, LRect newSize, Field.Layers newLayers) {
		this.toolBar = toolBar;
		this.oldSize = oldSize;
		this.oldLayers = oldLayers;
		this.newSize = newSize;
		this.newLayers = newLayers;
	}

	public void redo() {
		toolBar.resizeField(newSize, newLayers);
	}
	
	public void undo() {
		toolBar.resizeField(oldSize, oldLayers);
	}
	
}
