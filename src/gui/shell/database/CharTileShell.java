package gui.shell.database;

import gui.Vocab;
import gui.shell.ObjectShell;

import org.eclipse.swt.widgets.Shell;

import lwt.widget.LSpinner;

import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;

import data.subcontent.Tile;

import org.eclipse.swt.graphics.Point;

public class CharTileShell extends ObjectShell<Tile> {
	
	public CharTileShell(Shell parent) {
		super(parent);
		setMinimumSize(new Point(200, 39));
		
		GridLayout gridLayout = new GridLayout(2, false);
		gridLayout.marginWidth = 0;
		gridLayout.marginHeight = 0;
		contentEditor.setLayout(gridLayout);

		Label lblX = new Label(contentEditor, SWT.NONE);
		lblX.setText(Vocab.instance.OFFSETX);
		
		LSpinner spnX = new LSpinner(contentEditor, SWT.NONE);
		spnX.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		spnX.setMaximum(10);
		spnX.setMinimum(-10);
		addControl(spnX, "dx");
		
		Label lblY = new Label(contentEditor, SWT.NONE);
		lblY.setText(Vocab.instance.OFFSETY);
		
		LSpinner spnY = new LSpinner(contentEditor, SWT.NONE);
		spnY.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		spnY.setMaximum(10);
		spnY.setMinimum(-10);
		addControl(spnY, "dy");
		
		Label lblHeight = new Label(contentEditor, SWT.NONE);
		lblHeight.setText(Vocab.instance.HEIGHT);
		
		LSpinner spnHeight = new LSpinner(contentEditor, SWT.NONE);
		spnHeight.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		spnHeight.setMaximum(20);
		addControl(spnHeight, "height");

		pack();
	}
	
}
