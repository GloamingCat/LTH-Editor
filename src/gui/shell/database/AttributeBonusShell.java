package gui.shell.database;

import lwt.dialog.LShell;
import lwt.widget.LLabel;
import lwt.widget.LSpinner;
import lwt.widget.LText;
import gui.Vocab;
import gui.shell.ObjectShell;

import data.Item.Attribute;

public class AttributeBonusShell extends ObjectShell<Attribute> {

	public AttributeBonusShell(LShell parent) {
		super(parent, 400, 100);
		contentEditor.setGridLayout(2, false);
		
		new LLabel(contentEditor, Vocab.instance.KEY);
		
		LText txtKey = new LText(contentEditor);
		addControl(txtKey, "key");
		
		new LLabel(contentEditor, Vocab.instance.ATTADD);
		
		LSpinner spnAdd = new LSpinner(contentEditor);
		spnAdd.setMaximum(999999);
		spnAdd.setMinimum(-999999);
		addControl(spnAdd, "add");
		
		new LLabel(contentEditor, Vocab.instance.ATTMUL);
		
		LSpinner spnMul = new LSpinner(contentEditor);
		spnMul.setMaximum(999999);
		spnMul.setMinimum(-999999);
		addControl(spnMul, "mul");
		
		pack();
	}
	
}
