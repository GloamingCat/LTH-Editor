package gui.shell.database;

import gui.Tooltip;
import gui.Vocab;
import lui.base.LFlags;
import lui.gson.GObjectDialog;

import data.Item.EquipStatus;
import lui.dialog.LWindow;
import lui.widget.LCheckBox;
import lui.widget.LNodeSelector;

import project.Project;

public class EquipStatusDialog extends GObjectDialog<EquipStatus> {

	public EquipStatusDialog(LWindow parent) {
		super(parent, 300, 250, Vocab.instance.STATUSSHELL);
	}
	
	@Override
	protected void createContent(int style) {
		super.createContent(style);
		contentEditor.setGridLayout(1);
		
		LNodeSelector<Object> tree = new LNodeSelector<>(contentEditor, 0);
		tree.getCellData().setExpand(true, true);
		tree.setCollection(Project.current.status.getTree());
		addControl(tree, "id");
		
		LCheckBox btnBattle = new LCheckBox(contentEditor);
		btnBattle.getCellData().setAlignment(LFlags.LEFT);
		btnBattle.setText(Vocab.instance.ADDONBATTLE);
		btnBattle.setHoverText(Tooltip.instance.ADDONBATTLE);
		addControl(btnBattle, "battle");
	}
	
}
