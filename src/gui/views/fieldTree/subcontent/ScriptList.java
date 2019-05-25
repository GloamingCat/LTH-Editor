package gui.views.fieldTree.subcontent;

import gui.shell.ScriptShell;
import gui.widgets.SimpleEditableList;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;

import data.subcontent.Script;
import lwt.dialog.LObjectShell;
import lwt.dialog.LShellFactory;

public class ScriptList extends SimpleEditableList<Script> {
	
	public ScriptList(Composite parent, int style) {
		super(parent, style);
		type = Script.class;
		setIncludeID(false);
		setShellFactory(new LShellFactory<Script>() {
			@Override
			public LObjectShell<Script> createShell(Shell parent) {
				return new ScriptShell(parent, style);
			}
		});
	}

}
