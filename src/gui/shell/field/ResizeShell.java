package gui.shell.field;

import gui.Vocab;
import gui.shell.ObjectShell;

import org.eclipse.swt.widgets.Shell;

import lwt.widget.LCombo;
import lwt.widget.LSpinner;

import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;

public class ResizeShell extends ObjectShell<Rectangle> {

	public ResizeShell(Shell parent) {
		super(parent);
		setMinimumSize(new Point(240, 0));
		
		contentEditor.setLayout(new GridLayout(2, false));
		
		Label lblW = new Label(contentEditor, SWT.NONE);
		lblW.setText(Vocab.instance.SIZEX);
		
		LSpinner spnW = new LSpinner(contentEditor, SWT.NONE);
		spnW.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		addControl(spnW, "width");
		
		Label lblH = new Label(contentEditor, SWT.NONE);
		lblH.setText(Vocab.instance.SIZEY);
		
		LSpinner spnH = new LSpinner(contentEditor, SWT.NONE);
		spnH.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		addControl(spnH, "height");
		
		Label lblX = new Label(contentEditor, SWT.NONE);
		lblX.setText(Vocab.instance.ALIGNX);
		
		LCombo cmbX = new LCombo(contentEditor);
		cmbX.setOptional(false);
		cmbX.setIncludeID(false);
		cmbX.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		cmbX.setItems(new String[] { Vocab.instance.LEFT, Vocab.instance.CENTER, Vocab.instance.RIGHT });
		addControl(cmbX, "x");
		
		Label lblY = new Label(contentEditor, SWT.NONE);
		lblY.setText(Vocab.instance.ALIGNY);
		
		LCombo cmbY = new LCombo(contentEditor);
		cmbY.setIncludeID(false);
		cmbY.setOptional(false);
		cmbY.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		cmbY.setItems(new String[] { Vocab.instance.TOP, Vocab.instance.CENTER, Vocab.instance.BOTTOM });
		addControl(cmbY, "y");
		
		pack();
	}

}
