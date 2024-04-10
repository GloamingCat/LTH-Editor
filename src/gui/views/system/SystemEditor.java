package gui.views.system;

import gui.Vocab;
import lui.container.LContainer;
import lui.container.LViewFolder;

public class SystemEditor extends LViewFolder {

	/**
	 * @wbp.parser.constructor
	 * @wbp.eval.method.parameter parent new lwt.dialog.LShell()
	 */
	public SystemEditor(LContainer parent) {
		super(parent, false);	
		
		ConfigEditor systemEditor = new ConfigEditor(this);
		addTab(Vocab.instance.GENERAL, systemEditor);

		ListsEditor listsEditor = new ListsEditor(this);
		addTab(Vocab.instance.LISTS, listsEditor);
		
	}
	
}
