package gui.shell.database;

import gui.Vocab;
import lwt.dialog.LObjectShell;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Spinner;

import data.subcontent.Transform;

import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.graphics.Point;

public class TransformShell extends LObjectShell<Transform> {

	private Spinner spnOffsetX;
	private Spinner spnOffsetY;
	private Spinner spnOffsetDepth;
	private Spinner spnScaleX;
	private Spinner spnScaleY;
	private Spinner spnRotation;
	private Spinner spnRed;
	private Spinner spnGreen;
	private Spinner spnBlue;
	private Spinner spnAlpha;
	
	public TransformShell(Shell parent) {
		super(parent);
		setMinimumSize(new Point(300, 39));
		setSize(369, 300);
		content.setLayout(new GridLayout(4, false));

		Label lblOffsetX = new Label(content, SWT.NONE);
		lblOffsetX.setText(Vocab.instance.OFFSETX);
		
		spnOffsetX = new Spinner(content, SWT.BORDER);
		spnOffsetX.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label lblRotation = new Label(content, SWT.NONE);
		lblRotation.setText(Vocab.instance.ROTATION);
		
		spnRotation = new Spinner(content, SWT.BORDER);
		spnRotation.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label lblOffsetY = new Label(content, SWT.NONE);
		lblOffsetY.setText(Vocab.instance.OFFSETY);
		
		spnOffsetY = new Spinner(content, SWT.BORDER);
		spnOffsetY.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label lblRed = new Label(content, SWT.NONE);
		lblRed.setText(Vocab.instance.RED);
		
		spnRed = new Spinner(content, SWT.BORDER);
		spnRed.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label lblOffsetDepth = new Label(content, SWT.NONE);
		lblOffsetDepth.setText(Vocab.instance.OFFSETDEPTH);
		
		spnOffsetDepth = new Spinner(content, SWT.BORDER);
		spnOffsetDepth.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label lblGreen = new Label(content, SWT.NONE);
		lblGreen.setText(Vocab.instance.GREEN);
		
		spnGreen = new Spinner(content, SWT.BORDER);
		spnGreen.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label lblScaleX = new Label(content, SWT.NONE);
		lblScaleX.setText(Vocab.instance.SCALEX);
		
		spnScaleX = new Spinner(content, SWT.BORDER);
		spnScaleX.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label lblBlue = new Label(content, SWT.NONE);
		lblBlue.setText(Vocab.instance.BLUE);
		
		spnBlue = new Spinner(content, SWT.BORDER);
		spnBlue.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label lblScaleY = new Label(content, SWT.NONE);
		lblScaleY.setText(Vocab.instance.SCALEY);
		
		spnScaleY = new Spinner(content, SWT.BORDER);
		spnScaleY.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label lblAlpha = new Label(content, SWT.NONE);
		lblAlpha.setText(Vocab.instance.ALPHA);
		
		spnAlpha = new Spinner(content, SWT.BORDER);
		spnAlpha.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		pack();
	}
	
	public void open(Transform initial) {
		super.open(initial);
		spnAlpha.setSelection(initial.alpha);
		spnBlue.setSelection(initial.blue);
		spnGreen.setSelection(initial.green);
		spnRed.setSelection(initial.red);
		spnOffsetDepth.setSelection(initial.offsetDepth);
		spnOffsetX.setSelection(initial.offsetX);
		spnOffsetY.setSelection(initial.offsetY);
		spnScaleX.setSelection(initial.scaleX);
		spnScaleY.setSelection(initial.scaleY);
		spnRotation.setSelection(initial.rotation);
	}

	@Override
	protected Transform createResult(Transform initial) {
		if (spnOffsetDepth.getSelection() == initial.offsetDepth &&
				spnOffsetX.getSelection() == initial.offsetX &&
				spnOffsetY.getSelection() == initial.offsetY &&
				spnRed.getSelection() == initial.red &&
				spnGreen.getSelection() == initial.green &&
				spnBlue.getSelection() == initial.blue &&
				spnAlpha.getSelection() == initial.alpha &&
				spnScaleX.getSelection() == initial.scaleX &&
				spnScaleY.getSelection() == initial.scaleY &&
				spnRotation.getSelection() == initial.rotation) {
			return null;
		} else {
			Transform t = new Transform();
			t.alpha = spnAlpha.getSelection();
			t.blue = spnBlue.getSelection();
			t.green = spnGreen.getSelection();
			t.red = spnRed.getSelection();
			t.scaleX = spnScaleX.getSelection();
			t.scaleY = spnScaleY.getSelection();
			t.rotation = spnRotation.getSelection();
			t.offsetX = spnOffsetX.getSelection();
			t.offsetY = spnOffsetY.getSelection();
			t.offsetDepth = spnOffsetDepth.getSelection();
			return t;
		}
	}

}
