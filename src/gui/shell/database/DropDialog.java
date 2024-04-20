package gui.shell.database;

import gui.Tooltip;
import gui.Vocab;
import gui.shell.ObjectEditorDialog;

import lui.dialog.LWindow;
import lui.widget.LLabel;
import lui.widget.LNodeSelector;
import lui.widget.LSpinner;

import project.Project;
import data.Battler.Drop;

public class DropDialog extends ObjectEditorDialog<Drop> {

	public DropDialog(LWindow parent) {
		super(parent, Vocab.instance.DROPSHELL);
		setMinimumSize(400, 300);
		setSize(400, 300);
	}
	
	@Override
	protected void createContent(int style) {
		super.createContent(style);
		contentEditor.setGridLayout(2);
		
		new LLabel(contentEditor, Vocab.instance.CHANCE, Tooltip.instance.CHANCE);
		LSpinner spnChance = new LSpinner(contentEditor);
		spnChance.getCellData().setExpand(true, false);
		spnChance.setMinimum(0);
		spnChance.setMaximum(100);
		addControl(spnChance, "value");

		new LLabel(contentEditor, Vocab.instance.COUNT, Tooltip.instance.COUNT);
		LSpinner spnCount = new LSpinner(contentEditor);
		spnCount.getCellData().setExpand(true, false);
		spnCount.setMaximum(999999999);
		spnCount.setMinimum(1);
		addControl(spnCount, "count");
		
		LNodeSelector<Object> tree = new LNodeSelector<>(contentEditor, false);
		tree.getCellData().setExpand(true, true);
		tree.getCellData().setSpread(2, 1);
		tree.setCollection(Project.current.items.getTree());
		addControl(tree, "id");
	}
	
}
