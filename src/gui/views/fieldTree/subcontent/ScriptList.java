package gui.views.fieldTree.subcontent;

import gui.shell.ScriptDialog;
import gui.widgets.SimpleEditableList;

import data.subcontent.Script;
import lui.base.event.LInsertEvent;
import lui.base.event.listener.LCollectionListener;
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
		getCollectionWidget().addInsertListener(new LCollectionListener<>() {
			@Override
			public void onInsert(LInsertEvent<Script> e) {
				if (allTriggers)
					e.node.data.scope = 2; // Character
				else
					e.node.data.scope = 1; // Field
			}
		});
	}

}
