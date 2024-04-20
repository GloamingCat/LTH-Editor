package gui.shell.database;

import gui.Tooltip;
import gui.Vocab;
import gui.shell.ObjectEditorDialog;

import data.Item.EquipStatus;
import lui.dialog.LWindow;
import lui.widget.LCheckBox;
import lui.widget.LNodeSelector;

import project.Project;

public class EquipStatusDialog extends ObjectEditorDialog<EquipStatus> {

	public EquipStatusDialog(LWindow parent) {
		super(parent, Vocab.instance.STATUSSHELL);
		setMinimumSize(300, 250);
		setSize(300, 250);
	}
	
	@Override
	protected void createContent(int style) {
		super.createContent(style);
		contentEditor.setGridLayout(1);
		
		LNodeSelector<Object> tree = new LNodeSelector<Object>(contentEditor, false);
		tree.getCellData().setExpand(true, true);
		tree.setCollection(Project.current.status.getTree());
		addControl(tree, "id");
		
		LCheckBox btnBattle = new LCheckBox(contentEditor);
		btnBattle.setText(Vocab.instance.ADDONBATTLE);
		btnBattle.setHoverText(Tooltip.instance.ADDONBATTLE);
		addControl(btnBattle, "battle");
	}
	
}
