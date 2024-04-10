package gui.views.fieldTree.subcontent;

import gui.shell.ScriptShell;
import gui.widgets.SimpleEditableList;

import data.subcontent.Script;
import lui.container.LContainer;
import lui.dialog.LObjectWindow;
import lui.dialog.LWindow;
import lui.dialog.LWindowFactory;

public class ScriptList extends SimpleEditableList<Script> {
	
	public ScriptList(LContainer parent, int style) {
		super(parent);
		type = Script.class;
		setIncludeID(false);
		setShellFactory(new LWindowFactory<>() {
			@Override
			public LObjectWindow<Script> createWindow(LWindow parent) {
				return new ScriptShell(parent, style);
			}
		});
	}

}
