package gui.views.fieldTree.action;

import data.field.Field;
import gui.views.fieldTree.FieldToolBar;
import lui.base.action.LAction;
import lui.graphics.LRect;

public class TransformAction implements LAction {

	private final FieldToolBar toolBar;
	private final int op;

	public TransformAction(FieldToolBar toolBar, int op) {
		this.toolBar = toolBar;
		this.op = op;
	}

	public void redo() {
		toolBar.transformField(op);
	}
	
	public void undo() {
		if (op == 3)
			toolBar.transformField(4);
		else if (op == 4)
			toolBar.transformField(3);
		else
			toolBar.transformField(op);
	}
	
}
