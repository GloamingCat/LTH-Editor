package gui.views;

import gui.Vocab;
import gui.views.database.content.BattlerTab;
import gui.views.database.content.ItemTab;
import gui.views.database.content.ObstacleTab;
import gui.views.database.content.RampTab;
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
		
		BattlerTab battlerTab = new BattlerTab(tabFolder, SWT.NONE);
		addTab(Vocab.instance.BATTLERS, battlerTab);
		
		ObstacleTab obstacleTab = new ObstacleTab(tabFolder, SWT.NONE);
		addTab(Vocab.instance.OBSTACLES, obstacleTab);
		
		RampTab rampTab = new RampTab(tabFolder, SWT.NONE);
		addTab(Vocab.instance.RAMPS, rampTab);
		
		actionStack = itemTab.getActionStack();
	}

}
