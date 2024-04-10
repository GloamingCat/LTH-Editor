package gui.shell.system;

import gui.Tooltip;
import gui.Vocab;
import gui.shell.ObjectEditorDialog;

import data.config.EquipType;
import lui.dialog.LWindow;
import lui.widget.LCombo;
import lui.widget.LLabel;
import lui.widget.LSpinner;
import lui.widget.LText;

public class EquipTypeDialog extends ObjectEditorDialog<EquipType> {

	public EquipTypeDialog(LWindow parent) {
		super(parent, Vocab.instance.EQUIPTYPESHELL);
		
		setTitle(Vocab.instance.EQUIP);
	}
	
	@Override
	protected void createContent(int style) {
		super.createContent(style);
		contentEditor.setGridLayout(2);
		
		new LLabel(contentEditor, Vocab.instance.NAME, Tooltip.instance.NAME);

		LText txtName = new LText(contentEditor);
		addControl(txtName, "name");
		
		new LLabel(contentEditor, Vocab.instance.KEY, Tooltip.instance.KEY);

		LText txtKey = new LText(contentEditor);
		addControl(txtKey, "key");
		
		new LLabel(contentEditor, Vocab.instance.STATE, Tooltip.instance.STATE);
		
		LCombo cmbState = new LCombo(contentEditor, true);
		cmbState.setIncludeID(false);
		cmbState.setOptional(false);
		cmbState.setItems(new String[] {
				Vocab.instance.FREE, Vocab.instance.NOTEMPTY,
				Vocab.instance.ALLEQUIPED, Vocab.instance.UNCHANGABLE,
				Vocab.instance.INVISIBLE
		});
		addControl(cmbState, "state");
		
		new LLabel(contentEditor, Vocab.instance.COUNT, Tooltip.instance.SLOTSIZE);
		
		LSpinner spnCount = new LSpinner(contentEditor);
		spnCount.setMinimum(1);
		spnCount.setMaximum(99);
		addControl(spnCount, "count");
		
		pack();
	}
	
}
