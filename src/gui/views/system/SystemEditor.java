package gui.views.system;

import gui.Vocab;
import lwt.editor.LViewFolder;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;


public class SystemEditor extends LViewFolder {

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public SystemEditor(Composite parent, int style) {
		super(parent, style);
		
		ConfigEditor systemEditor = new ConfigEditor(tabFolder, SWT.NONE);
		addTab(Vocab.instance.GENERAL, systemEditor);

		ListsEditor listsEditor = new ListsEditor(tabFolder, SWT.NONE);
		addTab(Vocab.instance.LISTS, listsEditor);
		
	}
	
}
