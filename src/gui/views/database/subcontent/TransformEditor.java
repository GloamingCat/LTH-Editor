package gui.views.database.subcontent;

import gui.Vocab;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;

import lwt.editor.LObjectEditor;
import lwt.widget.LSpinner;

public class TransformEditor extends LObjectEditor {

	public TransformEditor(Composite parent, int style) {
		super(parent, style);
		setLayout(new FillLayout(SWT.HORIZONTAL));
		
		Group grpTransform = new Group(this, SWT.NONE);
		grpTransform.setText(Vocab.instance.TRANSFORM);
		grpTransform.setLayout(new GridLayout(4, false));
		
		Label lblOffsetX = new Label(grpTransform, SWT.NONE);
		lblOffsetX.setText(Vocab.instance.OFFSETX);
		
		LSpinner spnOffsetX = new LSpinner(grpTransform, SWT.BORDER);
		spnOffsetX.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		addControl(spnOffsetX, "offsetX");
		
		Label lblRotation = new Label(grpTransform, SWT.NONE);
		lblRotation.setText(Vocab.instance.ROTATION);
		
		LSpinner spnRotation = new LSpinner(grpTransform, SWT.BORDER);
		spnRotation.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		addControl(spnRotation, "rotation");
		
		Label lblOffsetY = new Label(grpTransform, SWT.NONE);
		lblOffsetY.setText(Vocab.instance.OFFSETY);
		
		LSpinner spnOffsetY = new LSpinner(grpTransform, SWT.BORDER);
		spnOffsetY.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		addControl(spnOffsetY, "offsetY");
		
		Label lblRed = new Label(grpTransform, SWT.NONE);
		lblRed.setText(Vocab.instance.RED);
		
		LSpinner spnRed = new LSpinner(grpTransform, SWT.BORDER);
		spnRed.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		addControl(spnRed, "red");
		
		Label lblOffsetDepth = new Label(grpTransform, SWT.NONE);
		lblOffsetDepth.setText(Vocab.instance.OFFSETDEPTH);
		
		LSpinner spnOffsetDepth = new LSpinner(grpTransform, SWT.BORDER);
		spnOffsetDepth.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		addControl(spnOffsetDepth, "offsetDepth");
		
		Label lblGreen = new Label(grpTransform, SWT.NONE);
		lblGreen.setText(Vocab.instance.GREEN);
		
		LSpinner spnGreen = new LSpinner(grpTransform, SWT.BORDER);
		spnGreen.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		addControl(spnGreen, "green");
		
		Label lblScaleX = new Label(grpTransform, SWT.NONE);
		lblScaleX.setText(Vocab.instance.SCALEX);
		
		LSpinner spnScaleX = new LSpinner(grpTransform, SWT.BORDER);
		spnScaleX.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		addControl(spnScaleX, "scaleX");
		
		Label lblBlue = new Label(grpTransform, SWT.NONE);
		lblBlue.setText(Vocab.instance.BLUE);
		
		LSpinner spnBlue = new LSpinner(grpTransform, SWT.BORDER);
		spnBlue.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		addControl(spnBlue, "blue");
		
		Label lblScaleY = new Label(grpTransform, SWT.NONE);
		lblScaleY.setText(Vocab.instance.SCALEY);
		
		LSpinner spnScaleY = new LSpinner(grpTransform, SWT.BORDER);
		spnScaleY.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		addControl(spnScaleY, "scaleY");
		
		Label lblAlpha = new Label(grpTransform, SWT.NONE);
		lblAlpha.setText(Vocab.instance.ALPHA);
		
		LSpinner spnAlpha = new LSpinner(grpTransform, SWT.BORDER);
		spnAlpha.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		addControl(spnAlpha, "alpha");
	}
	
	public void setObject(Object obj) {
		Object value = getFieldValue(obj, "transform");
		super.setObject(value);
	}

}
