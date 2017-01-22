package gui.views.database.subcontent;

import gui.shell.ScriptShell;
import gui.views.SimpleEditableList;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;

import data.Script;
import lwt.dialog.LObjectShell;
import lwt.dialog.LShellFactory;

public class ScriptList extends SimpleEditableList<Script> {

	public String folderName = "";
	public boolean optional = false;
	
	public ScriptList(Composite parent, int style) {
		super(parent, style);
		type = Script.class;
		setIncludeID(false);
		setShellFactory(new LShellFactory<Script>() {
			@Override
			public LObjectShell<Script> createShell(Shell parent) {
				return new ScriptShell(parent, folderName, optional);
			}
		});
	}

}
