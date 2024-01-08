package gui.shell.database;

import gui.Tooltip;
import gui.Vocab;
import gui.shell.ObjectShell;

import lwt.dialog.LShell;
import lwt.widget.LLabel;
import lwt.widget.LNodeSelector;
import lwt.widget.LSpinner;

import project.Project;
import data.Battler.Drop;

public class DropShell extends ObjectShell<Drop> {

	public DropShell(LShell parent) {
		super(parent, Vocab.instance.DROPSHELL);
		setMinimumSize(400, 200);
	}
	
	@Override
	protected void createContent(int style) {
		super.createContent(style);
		contentEditor.setGridLayout(2);
		
		new LLabel(contentEditor, Vocab.instance.CHANCE, Tooltip.instance.CHANCE);
		LSpinner spnChance = new LSpinner(contentEditor);
		addControl(spnChance, "value");
		
		new LLabel(contentEditor, Vocab.instance.COUNT, Tooltip.instance.COUNT);
		LSpinner spnCount = new LSpinner(contentEditor);
		spnCount.setMaximum(999999999);
		spnCount.setMinimum(1);
		addControl(spnCount, "count");
		
		LNodeSelector<Object> tree = new LNodeSelector<>(contentEditor, false);
		tree.setExpand(true, true);
		tree.setSpread(2, 1);
		tree.setCollection(Project.current.items.getTree());
		addControl(tree, "id");
		
		pack();
	}
	
}
