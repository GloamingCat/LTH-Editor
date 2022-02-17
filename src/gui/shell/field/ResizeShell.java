package gui.shell.field;

import gui.Vocab;
import gui.shell.ObjectShell;

import org.eclipse.swt.widgets.Shell;

import lwt.widget.LCombo;
import lwt.widget.LLabel;
import lwt.widget.LSpinner;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;

public class ResizeShell extends ObjectShell<Rectangle> {

	public ResizeShell(Shell parent) {
		super(parent);
		setMinimumSize(240, 0);
		
		contentEditor.setLayout(new GridLayout(2, false));
		
		new LLabel(contentEditor, Vocab.instance.SIZEX);
		
		LSpinner spnW = new LSpinner(contentEditor, SWT.NONE);
		spnW.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		addControl(spnW, "width");
		
		new LLabel(contentEditor, Vocab.instance.SIZEY);
		
		LSpinner spnH = new LSpinner(contentEditor, SWT.NONE);
		spnH.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		addControl(spnH, "height");
		
		new LLabel(contentEditor, Vocab.instance.ALIGNX);
		
		LCombo cmbX = new LCombo(contentEditor);
		cmbX.setOptional(false);
		cmbX.setIncludeID(false);
		cmbX.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		cmbX.setItems(new String[] { Vocab.instance.LEFT, Vocab.instance.CENTER, Vocab.instance.RIGHT });
		addControl(cmbX, "x");
		
		new LLabel(contentEditor, Vocab.instance.ALIGNY);
		
		LCombo cmbY = new LCombo(contentEditor);
		cmbY.setIncludeID(false);
		cmbY.setOptional(false);
		cmbY.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		cmbY.setItems(new String[] { Vocab.instance.TOP, Vocab.instance.CENTER, Vocab.instance.BOTTOM });
		addControl(cmbY, "y");
		
		pack();
	}

}
