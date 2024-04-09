package gui.views.fieldTree.subcontent;

import gui.shell.ScriptShell;
import gui.widgets.SimpleEditableList;

import data.subcontent.Script;
import lwt.container.LContainer;
import lwt.dialog.LObjectWindow;
import lwt.dialog.LWindow;
import lwt.dialog.LWindowFactory;

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
