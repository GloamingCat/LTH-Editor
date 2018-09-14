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

public class CharTileShell extends LObjectShell<Tile> {
	
	private Spinner spnX;
	private Spinner spnY;
	private Spinner spnHeight;
	
	public CharTileShell(Shell parent) {
		super(parent);
		
		GridLayout gridLayout = new GridLayout(2, true);
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
		lblHeight.setText(Vocab.instance.OFFSETY);
		
		spnHeight = new Spinner(content, SWT.BORDER);
		spnHeight.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		pack();
	}

	public void open(Tile initial) {
		super.open(initial);
		spnX.setSelection(initial.dx);
		spnY.setSelection(initial.dy);
	}

	@Override
	protected Tile createResult(Tile initial) {
		if (initial.dx != spnX.getSelection() || initial.dy != spnY.getSelection()) {
			Tile t = new Tile();
			t.dx = spnX.getSelection();
			t.dy = spnY.getSelection();
			return t;
		} else {
			return null;
		}
	}
	
}
