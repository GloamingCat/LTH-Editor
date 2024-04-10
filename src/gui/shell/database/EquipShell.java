package gui.shell.database;

import gui.Tooltip;
import gui.Vocab;
import gui.shell.ObjectShell;
import gui.widgets.IDButton;

import data.Battler.Equip;
import lui.base.data.LDataTree;
import lui.dialog.LWindow;
import lui.widget.LCombo;
import lui.widget.LLabel;
import lui.widget.LText;

import project.Project;

public class EquipShell extends ObjectShell<Equip> {

	public EquipShell(LWindow parent) {
		super(parent, Vocab.instance.EQUIPSHELL);
	}
	
	@Override
	protected void createContent(int style) {
		super.createContent(style);		
		contentEditor.setGridLayout(3);
		
		new LLabel(contentEditor, Vocab.instance.KEY, Tooltip.instance.KEY);

		LText txtKey = new LText(contentEditor);
		txtKey.getCellData().setSpread(2, 1);
		addControl(txtKey, "key");
		
		new LLabel(contentEditor, Vocab.instance.STATE, Tooltip.instance.STATE);
		
		LCombo cmbState = new LCombo(contentEditor, true);
		cmbState.getCellData().setSpread(2, 1);
		cmbState.setIncludeID(false);
		cmbState.setOptional(false);
		cmbState.setItems(new String[] {
				Vocab.instance.FREE, Vocab.instance.NOTEMPTY,
				Vocab.instance.ALLEQUIPED, Vocab.instance.UNCHANGABLE,
				Vocab.instance.INVISIBLE
		});
		addControl(cmbState, "state");
		
		new LLabel(contentEditor, Vocab.instance.EQUIPITEM, Tooltip.instance.EQUIPITEM);
		
		LText txtItem = new LText(contentEditor, true);
		IDButton btnItem = new IDButton(contentEditor, Vocab.instance.ITEMSHELL, true) {
			public LDataTree<Object> getDataTree() {
				return Project.current.items.getTree();
			}
		};
		btnItem.setNameWidget(txtItem);
		addControl(btnItem, "id");
		
		pack();
	}
	
}
