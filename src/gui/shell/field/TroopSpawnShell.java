
package gui.shell.field;

import gui.Tooltip;
import gui.Vocab;
import gui.shell.ObjectShell;

import data.field.Party.TroopSpawn;
import lwt.dialog.LShell;
import lwt.widget.LLabel;
import lwt.widget.LNodeSelector;
import lwt.widget.LSpinner;
import project.Project;

public class TroopSpawnShell extends ObjectShell<TroopSpawn> {

	public TroopSpawnShell(LShell parent) {
		super(parent, Vocab.instance.TROOPSHELL);
		setMinimumSize(360, 320);
	}
	
	@Override
	protected void createContent(int style) {
		super.createContent(style);
		contentEditor.setGridLayout(2);
		
		new LLabel(contentEditor, Vocab.instance.MINLEVEL, Tooltip.instance.MINLEVEL);
		LSpinner spnMin = new LSpinner(contentEditor);
		spnMin.setMinimum(0);
		spnMin.setMaximum(100000);
		spnMin.setValue(1);
		addControl(spnMin, "minLevel");
		
		new LLabel(contentEditor, Vocab.instance.MAXLEVEL, Tooltip.instance.MAXLEVEL);
		LSpinner spnMax = new LSpinner(contentEditor);
		spnMax.setMinimum(0);
		spnMax.setMaximum(100000);
		spnMax.setValue(100);
		addControl(spnMax, "maxLevel");
		
		LNodeSelector<Object> tree = new LNodeSelector<Object>(contentEditor, false);
		tree.setSpread(2, 1);
		tree.setExpand(true, true);
		tree.setCollection(Project.current.troops.getTree());
		addControl(tree, "id");
		
		pack();
	}
	
}
