package gui.views.config;

import gui.Vocab;
import lwt.editor.LViewFolder;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;


public class ConfigEditor extends LViewFolder {

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public ConfigEditor(Composite parent, int style) {
		super(parent, style);
		
		SystemEditor systemEditor = new SystemEditor(tabFolder, SWT.NONE);
		addTab(Vocab.instance.GENERAL, systemEditor);

	}
	
}
