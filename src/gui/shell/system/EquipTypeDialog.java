package gui.shell.system;

import gui.Tooltip;
import gui.Vocab;
import lui.gson.GObjectDialog;

import data.config.EquipType;
import lui.base.data.LPoint;
import lui.dialog.LWindow;
import lui.widget.LCombo;
import lui.widget.LLabel;
import lui.widget.LSpinner;
import lui.widget.LText;

public class EquipTypeDialog extends GObjectDialog<EquipType> {

	public EquipTypeDialog(LWindow parent) {
		super(parent, 200, 0, Vocab.instance.EQUIPTYPESHELL);
	}
	
	@Override
	protected void createContent(int style) {
		super.createContent(style);
		contentEditor.setGridLayout(2);
		
		new LLabel(contentEditor, Vocab.instance.NAME, Tooltip.instance.NAME);
		LText txtName = new LText(contentEditor);
		txtName.getCellData().setExpand(true, false);
		addControl(txtName, "name");
		
		new LLabel(contentEditor, Vocab.instance.KEY, Tooltip.instance.KEY);
		LText txtKey = new LText(contentEditor);
		txtKey.getCellData().setExpand(true, false);
		addControl(txtKey, "key");
		
		new LLabel(contentEditor, Vocab.instance.STATE, Tooltip.instance.STATE);
		LCombo cmbState = new LCombo(contentEditor, LCombo.READONLY);
		cmbState.getCellData().setExpand(true, false);
		cmbState.setItems(new String[] {
				Vocab.instance.FREE, Vocab.instance.NOTEMPTY,
				Vocab.instance.ALLEQUIPED, Vocab.instance.UNCHANGABLE,
				Vocab.instance.INVISIBLE
		});
		addControl(cmbState, "state");
		
		new LLabel(contentEditor, Vocab.instance.COUNT, Tooltip.instance.SLOTSIZE);
		LSpinner spnCount = new LSpinner(contentEditor);
		spnCount.getCellData().setExpand(true, false);
		spnCount.setMinimum(1);
		spnCount.setMaximum(99);
		addControl(spnCount, "count");
	}
	
}
