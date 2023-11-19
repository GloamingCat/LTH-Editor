package gui.views.fieldTree.subcontent;

import gui.shell.ScriptShell;
import gui.widgets.SimpleEditableList;

import data.subcontent.Script;
import lwt.container.LContainer;
import lwt.dialog.LObjectShell;
import lwt.dialog.LShell;
import lwt.dialog.LShellFactory;

public class ScriptList extends SimpleEditableList<Script> {
	
	public ScriptList(LContainer parent, int style) {
		super(parent);
		type = Script.class;
		setIncludeID(false);
		setShellFactory(new LShellFactory<Script>() {
			@Override
			public LObjectShell<Script> createShell(LShell parent) {
				return new ScriptShell(parent, style);
			}
		});
	}

}
