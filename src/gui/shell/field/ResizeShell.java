package gui.shell.field;

import gui.Tooltip;
import gui.Vocab;
import gui.shell.ObjectShell;
import lwt.dialog.LShell;
import lwt.graphics.LRect;
import lwt.widget.LCombo;
import lwt.widget.LLabel;
import lwt.widget.LSpinner;

public class ResizeShell extends ObjectShell<LRect> {

	public ResizeShell(LShell parent) {
		super(parent, Vocab.instance.RESIZESHELL);
		setMinimumSize(240, 0);
	}
	
	@Override
	protected void createContent(int style) {
		super.createContent(style);
		
		contentEditor.setGridLayout(2);
		
		new LLabel(contentEditor, Vocab.instance.SIZEX, Tooltip.instance.SIZEX);
		
		LSpinner spnW = new LSpinner(contentEditor);
		addControl(spnW, "width");
		
		new LLabel(contentEditor, Vocab.instance.SIZEY, Tooltip.instance.SIZEY);
		
		LSpinner spnH = new LSpinner(contentEditor);
		addControl(spnH, "height");
		
		new LLabel(contentEditor, Vocab.instance.ALIGNX, Tooltip.instance.ALIGNX);
		
		LCombo cmbX = new LCombo(contentEditor, true);
		cmbX.setOptional(false);
		cmbX.setIncludeID(false);
		cmbX.setItems(new String[] { Vocab.instance.LEFT, Vocab.instance.CENTER, Vocab.instance.RIGHT });
		addControl(cmbX, "x");
		
		new LLabel(contentEditor, Vocab.instance.ALIGNY, Tooltip.instance.ALIGNY);
		
		LCombo cmbY = new LCombo(contentEditor, true);
		cmbY.setIncludeID(false);
		cmbY.setOptional(false);
		cmbY.setItems(new String[] { Vocab.instance.TOP, Vocab.instance.CENTER, Vocab.instance.BOTTOM });
		addControl(cmbY, "y");
		
		pack();
	}

}
