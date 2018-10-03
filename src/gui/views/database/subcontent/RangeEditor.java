package gui.views.database.subcontent;

import gui.Vocab;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import lwt.editor.LObjectEditor;
import lwt.widget.LSpinner;

public class RangeEditor extends LObjectEditor {
	
	public String attributeName = "transform";

	public RangeEditor(Composite parent, int style) {
		super(parent, style);
		
		setLayout(new GridLayout(4, false));

		Label lblRadius = new Label(this, SWT.NONE);
		lblRadius.setAlignment(SWT.CENTER);
		lblRadius.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 2, 1));
		lblRadius.setText("Radius");
		
		Label lblHeightDifference = new Label(this, SWT.NONE);
		lblHeightDifference.setAlignment(SWT.CENTER);
		lblHeightDifference.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 2, 1));
		lblHeightDifference.setText("Height Difference");
		
		Label lblNear = new Label(this, SWT.NONE);
		lblNear.setText(Vocab.instance.NEAR);
		
		LSpinner spnNear = new LSpinner(this, SWT.NONE);
		spnNear.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		addControl(spnNear, "near");
		
		Label lblMinH = new Label(this, SWT.NONE);
		lblMinH.setText(Vocab.instance.MIN);
		
		LSpinner spnMinH = new LSpinner(this, SWT.NONE);
		spnMinH.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		addControl(spnMinH, "minh");
		
		Label lblEffectFar = new Label(this, SWT.NONE);
		lblEffectFar.setText(Vocab.instance.FAR);
		
		LSpinner spnFar = new LSpinner(this, SWT.NONE);
		spnFar.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		addControl(spnFar, "far");
		
		Label lblMaxH = new Label(this, SWT.NONE);
		lblMaxH.setText(Vocab.instance.MAX);
		
		LSpinner spnMaxH = new LSpinner(this, SWT.NONE);
		spnMaxH.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		addControl(spnMaxH, "maxh");
		
	}
	
	public void setObject(Object obj) {
		Object value = obj == null ? null : getFieldValue(obj, attributeName);
		super.setObject(value);
	}

}
