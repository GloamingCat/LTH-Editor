package gui.views.system;

import gui.Vocab;
import lwt.editor.LViewFolder;

import org.eclipse.swt.widgets.Composite;


public class SystemEditor extends LViewFolder {

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public SystemEditor(Composite parent) {
		super(parent);
		
		ConfigEditor systemEditor = new ConfigEditor(tabFolder);
		addTab(Vocab.instance.GENERAL, systemEditor);

		ListsEditor listsEditor = new ListsEditor(tabFolder);
		addTab(Vocab.instance.LISTS, listsEditor);
		
	}
	
}
