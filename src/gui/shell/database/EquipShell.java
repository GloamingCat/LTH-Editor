package gui.shell.database;

import gui.Tooltip;
import gui.Vocab;
import gui.shell.ObjectShell;
import gui.widgets.IDButton;

import data.Battler.Equip;
import lwt.dataestructure.LDataTree;
import lwt.dialog.LShell;
import lwt.widget.LCombo;
import lwt.widget.LLabel;
import lwt.widget.LText;

import project.Project;

public class EquipShell extends ObjectShell<Equip> {

	public EquipShell(LShell parent) {
		super(parent, Vocab.instance.EQUIPSHELL);
	}
	
	@Override
	protected void createContent(int style) {
		super.createContent(style);		
		contentEditor.setGridLayout(3);
		
		new LLabel(contentEditor, Vocab.instance.KEY, Tooltip.instance.KEY);

		LText txtKey = new LText(contentEditor, 2);
		addControl(txtKey, "key");
		
		new LLabel(contentEditor, Vocab.instance.STATE, Tooltip.instance.STATE);
		
		LCombo cmbState = new LCombo(contentEditor, 2);
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
