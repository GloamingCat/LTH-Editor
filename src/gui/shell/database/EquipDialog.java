package gui.shell.database;

import data.Item;
import gui.Tooltip;
import gui.Vocab;
import lui.gson.GObjectDialog;
import gui.widgets.IDButton;

import data.Battler.Equip;
import lui.base.data.LDataTree;
import lui.container.LFrame;
import lui.dialog.LWindow;
import lui.widget.LCombo;
import lui.widget.LLabel;
import lui.widget.LNodeSelector;
import lui.widget.LText;

import project.Project;

public class EquipDialog extends GObjectDialog<Equip> {

	public EquipDialog(LWindow parent) {
		super(parent, 400, 300, Vocab.instance.EQUIPSHELL);
	}
	
	@Override
	protected void createContent(int style) {
		super.createContent(style);		
		contentEditor.setGridLayout(2);
		
		new LLabel(contentEditor, Vocab.instance.KEY, Tooltip.instance.KEY);
		LText txtKey = new LText(contentEditor);
		txtKey.getCellData().setExpand(true, false);
		addControl(txtKey, "key");
		
		new LLabel(contentEditor, Vocab.instance.STATE, Tooltip.instance.STATE);
		LCombo cmbState = new LCombo(contentEditor, true);
		cmbState.getCellData().setExpand(true, false);
		cmbState.setIncludeID(false);
		cmbState.setOptional(false);
		cmbState.setItems(new String[] {
				Vocab.instance.FREE, Vocab.instance.NOTEMPTY,
				Vocab.instance.ALLEQUIPED, Vocab.instance.UNCHANGABLE,
				Vocab.instance.INVISIBLE
		});
		addControl(cmbState, "state");
		
		LFrame item = new LFrame(contentEditor, Vocab.instance.EQUIPITEM);
		item.setFillLayout(true);
		item.setHoverText(Tooltip.instance.EQUIPITEM);
		item.getCellData().setExpand(true, true);
		item.getCellData().setSpread(2, 1);

		LNodeSelector<Object> selItem = new LNodeSelector<>(item, true);
		selItem.setCollection(Project.current.items.getTree());
		addControl(selItem, "id");
	}
	
}
