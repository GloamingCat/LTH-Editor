package gui.shell.database;

import gui.Vocab;
import gui.views.database.subcontent.NeighborEditor;

import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Spinner;

import lwt.dialog.LObjectShell;
import lwt.widget.LCombo;

import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;

import project.Project;
import data.Obstacle.Tile;

public class ObstacleTileShell extends LObjectShell<Tile> {
	
	private LCombo cmbRamp;
	private Spinner spnX;
	private Spinner spnY;
	private NeighborEditor neighborEditor;
	
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
		
		Label lblID = new Label(grpGeneral, SWT.NONE);
		lblID.setText(Vocab.instance.RAMP);
		
		cmbRamp = new LCombo(grpGeneral, SWT.NONE);
		cmbRamp.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		cmbRamp.setIncludeID(true);
		cmbRamp.setOptional(true);

		Label lblX = new Label(grpGeneral, SWT.NONE);
		lblX.setText(Vocab.instance.OFFSETX);
		
		spnX = new Spinner(grpGeneral, SWT.BORDER);
		spnX.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		
		Label lblY = new Label(grpGeneral, SWT.NONE);
		lblY.setText(Vocab.instance.OFFSETY);
		
		spnY = new Spinner(grpGeneral, SWT.BORDER);
		spnY.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		
		neighborEditor = new NeighborEditor(content, SWT.NONE);
		neighborEditor.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));
		
		pack();
	}

	public void open(Tile initial) {
		super.open(initial);
		cmbRamp.setItems(Project.current.ramps.getList());
		cmbRamp.setValue(initial.rampID);
		neighborEditor.setObject(initial.neighbors);
		spnX.setSelection(initial.dx);
		spnY.setSelection(initial.dy);
	}
	
	private boolean changed(Tile initial) {
		if (initial.dx != spnX.getSelection() || initial.dy != spnY.getSelection()
				|| initial.rampID != cmbRamp.getSelectionIndex()) {
			return true;
		}
		boolean[] newValues = neighborEditor.getValues();
		for(int i = 0; i < 8; i++) {
			if(initial.neighbors[i] != newValues[i])
				return true;
		}
		return false;
	}

	@Override
	protected Tile createResult(Tile initial) {
		if (changed(initial)) {
			Tile t = new Tile();
			t.dx = spnX.getSelection();
			t.dy = spnY.getSelection();
			t.rampID = cmbRamp.getSelectionIndex();
			t.neighbors = neighborEditor.getValues();
			return t;
		} else {
			return null;
		}
	}
	
}
