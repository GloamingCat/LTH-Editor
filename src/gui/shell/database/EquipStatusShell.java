package gui.shell.database;

import gui.Vocab;
import gui.shell.ObjectShell;

import data.Item.EquipStatus;
import lwt.dialog.LShell;
import lwt.widget.LCheckBox;
import lwt.widget.LNodeSelector;

import project.Project;

public class EquipStatusShell extends ObjectShell<EquipStatus> {

	public EquipStatusShell(LShell parent) {
		super(parent);
		setMinimumSize(300, 250);
		contentEditor.setGridLayout(1, false);
		
		LNodeSelector<Object> tree = new LNodeSelector<Object>(contentEditor, false);
		tree.setExpand(true, true);
		tree.setCollection(Project.current.status.getTree());
		addControl(tree, "id");
		
		LCheckBox btnBattle = new LCheckBox(contentEditor);
		btnBattle.setText(Vocab.instance.ADDONBATTLE);
		addControl(btnBattle, "battle");
		
		pack();
	}
	
}
