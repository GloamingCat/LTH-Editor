package gui.shell;

import gui.Vocab;
import gui.views.fieldTree.*;
import lui.dialog.LObjectDialog;
import lui.dialog.LWindow;
import data.subcontent.Position;

public class PositionDialog extends LObjectDialog<Position> {
	
	private PositionEditor objectEditor;

	public PositionDialog(LWindow parent, int fieldId) {
		super(parent, fieldId, Vocab.instance.POSITIONSHELL);
	}
	
	@Override
	protected void createContent(int id) {
		super.createContent(0);
		content.setFillLayout(true);
		objectEditor = new PositionEditor(content, id, false);
	}
	
	public void open(Position initial) {
		objectEditor.onVisible();
		objectEditor.setObject(initial.clone());
		super.open(initial);
	}

	@Override
	protected Position createResult(Position initial) {
		return objectEditor.getObject();
	}

}
