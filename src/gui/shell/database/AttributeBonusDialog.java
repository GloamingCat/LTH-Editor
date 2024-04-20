package gui.shell.database;

import lui.base.data.LPoint;
import lui.dialog.LWindow;
import lui.widget.LLabel;
import lui.widget.LSpinner;
import lui.widget.LText;
import gui.Tooltip;
import gui.Vocab;
import gui.shell.ObjectEditorDialog;

import data.Item.Attribute;

public class AttributeBonusDialog extends ObjectEditorDialog<Attribute> {

	public AttributeBonusDialog(LWindow parent) {
		super(parent, Vocab.instance.BUFFSHELL);
		LPoint size = getTargetSize();
		setMinimumSize(200, size.y);
		setSize(200, size.y);
	}
	
	@Override
	protected void createContent(int style) {
		super.createContent(style);
		contentEditor.setGridLayout(2);
		
		new LLabel(contentEditor, Vocab.instance.KEY, Tooltip.instance.ATTKEY);
		LText txtKey = new LText(contentEditor);
		txtKey.getCellData().setExpand(true, false);
		addControl(txtKey, "key");

		new LLabel(contentEditor, Vocab.instance.ATTADD, Tooltip.instance.ATTADD);
		LSpinner spnAdd = new LSpinner(contentEditor);
		spnAdd.getCellData().setExpand(true, false);
		spnAdd.setMaximum(999999);
		spnAdd.setMinimum(-999999);
		addControl(spnAdd, "add");

		new LLabel(contentEditor, Vocab.instance.ATTMUL, Tooltip.instance.ATTMUL);
		LSpinner spnMul = new LSpinner(contentEditor);
		spnMul.getCellData().setExpand(true, false);
		spnMul.setMaximum(999999);
		spnMul.setMinimum(-999999);
		addControl(spnMul, "mul");
	}
	
}
