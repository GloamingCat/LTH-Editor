package gui.shell;

import gui.Vocab;
import gui.views.ScriptEditor;
import lui.dialog.LWindow;
import lui.gson.GObjectDialog;

import data.subcontent.Script;

public class ScriptDialog extends GObjectDialog<Script> {

	private ScriptEditor objectEditor;

	public ScriptDialog(LWindow parent, int style) {
		super(parent, style, Vocab.instance.SCRIPTSHELL);
	}
	
	@Override
	protected void createContent(int style) {
		super.createContent(0);
		content.setFillLayout(true);
		objectEditor = new ScriptEditor(content, style, false);
	}
	
	public void open(Script initial) {
		objectEditor.onVisible();
		objectEditor.setObject(initial.clone());
		super.open(initial);
	}

	@Override
	protected Script createResult(Script initial) {
		return objectEditor.getObject();
	}

}
