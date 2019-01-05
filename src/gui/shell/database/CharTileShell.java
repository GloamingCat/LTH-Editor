package gui.shell.database;

import gui.Vocab;

import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Spinner;

import lwt.dialog.LObjectShell;

import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;

import data.GameCharacter.Tile;
import org.eclipse.swt.graphics.Point;

public class CharTileShell extends LObjectShell<Tile> {
	
	private Spinner spnX;
	private Spinner spnY;
	private Spinner spnHeight;
	
	public CharTileShell(Shell parent) {
		super(parent);
		setMinimumSize(new Point(200, 39));
		
		GridLayout gridLayout = new GridLayout(2, false);
		gridLayout.marginWidth = 0;
		gridLayout.marginHeight = 0;
		content.setLayout(gridLayout);

		Label lblX = new Label(content, SWT.NONE);
		lblX.setText(Vocab.instance.OFFSETX);
		
		spnX = new Spinner(content, SWT.BORDER);
		spnX.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label lblY = new Label(content, SWT.NONE);
		lblY.setText(Vocab.instance.OFFSETY);
		
		spnY = new Spinner(content, SWT.BORDER);
		spnY.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label lblHeight = new Label(content, SWT.NONE);
		lblHeight.setText(Vocab.instance.HEIGHT);
		
		spnHeight = new Spinner(content, SWT.BORDER);
		spnHeight.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		pack();
	}

	public void open(Tile initial) {
		super.open(initial);
		spnX.setSelection(initial.dx);
		spnY.setSelection(initial.dy);
		spnHeight.setSelection(initial.height);
	}

	@Override
	protected Tile createResult(Tile initial) {
		Tile t = new Tile();
		t.dx = spnX.getSelection();
		t.dy = spnY.getSelection();
		t.height = spnHeight.getSelection();
		return t;
	}
	
}
