package gui.shell.database;

import gui.Vocab;
import gui.shell.ObjectShell;
import gui.views.database.subcontent.NeighborEditor;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;

import lwt.container.LFrame;
import lwt.dialog.LShell;
import lwt.widget.LCombo;
import lwt.widget.LLabel;
import lwt.widget.LSpinner;
import data.Obstacle.ObstacleTile;

public class ObstacleTileShell extends ObjectShell<ObstacleTile> {
	
	private LSpinner spnX;
	private LSpinner spnY;
	private LSpinner spnHeight;
	private LCombo cmbMode;
	private NeighborEditor neighborEditor;
	
	public ObstacleTileShell(LShell parent) {
		super(parent);
		
		GridLayout gridLayout = new GridLayout(2, true);
		gridLayout.marginWidth = 0;
		gridLayout.marginHeight = 0;
		contentEditor.setLayout(gridLayout);
		
		LFrame grpGeneral = new LFrame(contentEditor, Vocab.instance.GENERAL, 2, false);
		grpGeneral.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));

		new LLabel(grpGeneral, Vocab.instance.OFFSETX);
		
		spnX = new LSpinner(grpGeneral, SWT.BORDER);
		spnX.setMaximum(100);
		spnX.setMinimum(-100);
		addControl(spnX, "dx");
		
		new LLabel(grpGeneral, Vocab.instance.OFFSETY);
		
		spnY = new LSpinner(grpGeneral, SWT.BORDER);
		spnY.setMaximum(100);
		spnY.setMinimum(-100);
		addControl(spnY, "dy");
		
		new LLabel(grpGeneral, Vocab.instance.HEIGHT);
		
		spnHeight = new LSpinner(grpGeneral, SWT.BORDER);
		spnHeight.setMaximum(100);
		spnHeight.setMinimum(0);
		addControl(spnX, "height");
		
		new LLabel(grpGeneral, Vocab.instance.MODE);
		
		cmbMode = new LCombo(grpGeneral, SWT.NONE | SWT.READ_ONLY);
		cmbMode.setItems(new String[] {
			Vocab.instance.BLOCK,
			Vocab.instance.RAMP,
			Vocab.instance.BRIDGE
		});
		addControl(cmbMode, "mode");
		
		neighborEditor = new NeighborEditor(contentEditor);
		neighborEditor.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));
		addChild(neighborEditor, "neighbors");
		
		pack();
	}
	
}
