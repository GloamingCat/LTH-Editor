package gui.views;

import gui.Vocab;
import gui.views.database.tabs.ItemTab;
import lwt.editor.LViewFolder;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

public class DatabaseEditor extends LViewFolder {

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public DatabaseEditor(Composite parent, int style) {
		super(parent, style);

		ItemTab itemTab = new ItemTab(tabFolder, SWT.NONE);
		addTab(Vocab.instance.ITEMS, itemTab);
		
		actionStack = itemTab.getActionStack();
	}

}
