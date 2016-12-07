package gui.shell;

import gui.Vocab;

import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Spinner;

import lwt.dialog.LObjectShell;

import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;

public class ResizeShell extends LObjectShell<Point> {
	
	private Spinner spnH;
	private Spinner spnW;

	public ResizeShell(Shell parent) {
		super(parent);
		
		content.setLayout(new GridLayout(2, false));
		
		Label lblW = new Label(content, SWT.NONE);
		lblW.setText(Vocab.instance.WIDTH);
		
		spnW = new Spinner(content, SWT.BORDER);
		spnW.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label lblH = new Label(content, SWT.NONE);
		lblH.setText(Vocab.instance.HEIGHT);
		
		spnH = new Spinner(content, SWT.BORDER);
		spnH.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		pack();
	}
	
	public void open(Point initial) {
		super.open(initial);
		spnW.setSelection(initial.x);
		spnH.setSelection(initial.y);
	}

	@Override
	protected Point createResult(Point initial) {
		if (initial.x == spnW.getSelection() && initial.y == spnH.getSelection()) {
			return null;
		} else {
			return new Point(spnW.getSelection(), spnH.getSelection());
		}
	}

}
