package gui.shell.database;

import gui.Vocab;
import gui.views.database.subcontent.NeighborEditor;

import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;

import lwt.dialog.LObjectShell;
import data.Obstacle.ObstacleTile;

public class ObstacleTileShell extends LObjectShell<ObstacleTile> {
	
	private Spinner spnX;
	private Spinner spnY;
	private Spinner spnHeight;
	private NeighborEditor neighborEditor;
	private Combo cmbMode;
	
	public ObstacleTileShell(Shell parent) {
		super(parent);
		
		GridLayout gridLayout = new GridLayout(2, true);
		gridLayout.marginWidth = 0;
		gridLayout.marginHeight = 0;
		content.setLayout(gridLayout);
		
		Group grpGeneral = new Group(content, SWT.NONE);
		grpGeneral.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
		grpGeneral.setText(Vocab.instance.GENERAL);
		grpGeneral.setLayout(new GridLayout(2, false));

		Label lblX = new Label(grpGeneral, SWT.NONE);
		lblX.setText(Vocab.instance.OFFSETX);
		
		spnX = new Spinner(grpGeneral, SWT.BORDER);
		spnX.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		
		Label lblY = new Label(grpGeneral, SWT.NONE);
		lblY.setText(Vocab.instance.OFFSETY);
		
		spnY = new Spinner(grpGeneral, SWT.BORDER);
		spnY.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		
		Label lblHeight = new Label(grpGeneral, SWT.NONE);
		lblHeight.setText(Vocab.instance.HEIGHT);
		
		spnHeight = new Spinner(grpGeneral, SWT.BORDER);
		spnHeight.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		spnHeight.setMinimum(0);
		
		Label lblMode = new Label(grpGeneral, SWT.NONE);
		lblMode.setText(Vocab.instance.MODE);
		
		cmbMode = new Combo(grpGeneral, SWT.NONE | SWT.READ_ONLY);
		cmbMode.setItems(new String[] {
			Vocab.instance.BLOCK,
			Vocab.instance.RAMP,
			Vocab.instance.BRIDGE
		});
		
		neighborEditor = new NeighborEditor(content, SWT.NONE);
		neighborEditor.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));
		
		pack();
	}

	public void open(ObstacleTile initial) {
		super.open(initial);
		neighborEditor.setObject(initial.neighbors);
		spnX.setSelection(initial.dx);
		spnY.setSelection(initial.dy);
		spnHeight.setSelection(initial.height);
		cmbMode.select(initial.mode);
	}

	@Override
	protected ObstacleTile createResult(ObstacleTile initial) {
		ObstacleTile t = new ObstacleTile();
		t.dx = spnX.getSelection();
		t.dy = spnY.getSelection();
		t.height = spnHeight.getSelection();
		t.neighbors = neighborEditor.getValues();
		t.mode = cmbMode.getSelectionIndex();
		return t;
	}
	
}
