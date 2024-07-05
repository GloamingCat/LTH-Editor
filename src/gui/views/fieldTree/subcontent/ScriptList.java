package gui.views.fieldTree.subcontent;

import gui.shell.ScriptDialog;
import gui.widgets.SimpleEditableList;

import data.subcontent.Script;
import lui.container.LContainer;
import lui.dialog.LObjectDialog;
import lui.dialog.LWindow;
import lui.dialog.LWindowFactory;

public class ScriptList extends SimpleEditableList<Script> {
	
	public ScriptList(LContainer parent, boolean allTriggers) {
		super(parent);
		type = Script.class;
		setIncludeID(false);
		setShellFactory(new LWindowFactory<>() {
			@Override
			public LObjectDialog<Script> createWindow(LWindow parent) {
				return new ScriptDialog(parent, allTriggers ? 0 : ScriptDialog.ONLOAD | ScriptDialog.ONEXIT);
			}
		});
	}

}
