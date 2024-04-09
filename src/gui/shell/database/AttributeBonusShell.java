package gui.shell.database;

import lwt.dialog.LWindow;
import lwt.widget.LLabel;
import lwt.widget.LSpinner;
import lwt.widget.LText;
import gui.Tooltip;
import gui.Vocab;
import gui.shell.ObjectShell;

import data.Item.Attribute;

public class AttributeBonusShell extends ObjectShell<Attribute> {

	public AttributeBonusShell(LWindow parent) {
		super(parent, Vocab.instance.BUFFSHELL);
	}
	
	@Override
	protected void createContent(int style) {
		super.createContent(style);
		contentEditor.setGridLayout(2);
		
		new LLabel(contentEditor, Vocab.instance.KEY, Tooltip.instance.ATTKEY);
		
		LText txtKey = new LText(contentEditor);
		addControl(txtKey, "key");
		
		new LLabel(contentEditor, Vocab.instance.ATTADD, Tooltip.instance.ATTADD);
		
		LSpinner spnAdd = new LSpinner(contentEditor);
		spnAdd.setMaximum(999999);
		spnAdd.setMinimum(-999999);
		addControl(spnAdd, "add");
		
		new LLabel(contentEditor, Vocab.instance.ATTMUL, Tooltip.instance.ATTMUL);
		
		LSpinner spnMul = new LSpinner(contentEditor);
		spnMul.setMaximum(999999);
		spnMul.setMinimum(-999999);
		addControl(spnMul, "mul");

		setMinimumSize(400, 100);
		pack();
	}
	
}
