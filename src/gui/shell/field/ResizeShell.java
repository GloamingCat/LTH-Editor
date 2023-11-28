package gui.shell.field;

import gui.Vocab;
import gui.shell.ObjectShell;

import lwt.dialog.LShell;
import lwt.widget.LCombo;
import lwt.widget.LLabel;
import lwt.widget.LSpinner;

import org.eclipse.swt.graphics.Rectangle;

public class ResizeShell extends ObjectShell<Rectangle> {

	public ResizeShell(LShell parent) {
		super(parent);
		setMinimumSize(240, 0);
		
		contentEditor.setGridLayout(2, false);
		
		new LLabel(contentEditor, Vocab.instance.SIZEX);
		
		LSpinner spnW = new LSpinner(contentEditor);
		addControl(spnW, "width");
		
		new LLabel(contentEditor, Vocab.instance.SIZEY);
		
		LSpinner spnH = new LSpinner(contentEditor);
		addControl(spnH, "height");
		
		new LLabel(contentEditor, Vocab.instance.ALIGNX);
		
		LCombo cmbX = new LCombo(contentEditor);
		cmbX.setOptional(false);
		cmbX.setIncludeID(false);
		cmbX.setItems(new String[] { Vocab.instance.LEFT, Vocab.instance.CENTER, Vocab.instance.RIGHT });
		addControl(cmbX, "x");
		
		new LLabel(contentEditor, Vocab.instance.ALIGNY);
		
		LCombo cmbY = new LCombo(contentEditor);
		cmbY.setIncludeID(false);
		cmbY.setOptional(false);
		cmbY.setItems(new String[] { Vocab.instance.TOP, Vocab.instance.CENTER, Vocab.instance.BOTTOM });
		addControl(cmbY, "y");
		
		pack();
	}

}
