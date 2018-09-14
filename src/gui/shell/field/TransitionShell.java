package gui.shell.field;

import gui.Vocab;
import gui.views.PositionButton;

import org.eclipse.swt.widgets.Shell;

import data.subcontent.Transition;
import lwt.dialog.LObjectShell;

import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Text;

public class TransitionShell extends LObjectShell<Transition> {
	
	private PositionButton btnDest;
	private Spinner spnFade;
	private Combo cmbSide;
	private Text txtDest;

	public TransitionShell(Shell parent) {
		super(parent);
		setSize(309, 198);
		content.setLayout(new GridLayout(3, false));

		Label lblOrig = new Label(content, SWT.NONE);
		lblOrig.setText(Vocab.instance.ORIGIN);
		
		cmbSide = new Combo(content, SWT.BORDER | SWT.READ_ONLY);
		cmbSide.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 2, 1));
		cmbSide.setItems(new String[] {Vocab.instance.NORTH, Vocab.instance.EAST, 
				Vocab.instance.SOUTH, Vocab.instance.WEST});
		
		Label lblDest = new Label(content, SWT.NONE);
		lblDest.setText(Vocab.instance.DESTINATION);
		
		txtDest = new Text(content, SWT.BORDER | SWT.READ_ONLY);
		GridData gd_txtDest = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_txtDest.widthHint = 105;
		txtDest.setLayoutData(gd_txtDest);

		btnDest = new PositionButton(content, SWT.NONE);
		btnDest.setText(txtDest);
		
		Label lblFadeOut = new Label(content, SWT.NONE);
		lblFadeOut.setText(Vocab.instance.FADEOUT);

		spnFade = new Spinner(content, SWT.BORDER);
		spnFade.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 2, 1));
		
		pack();
	}
	
	public void open(Transition initial) {
		super.open(initial);
		btnDest.setValue(initial.destination);
		spnFade.setSelection(initial.fade);
	}

	@Override
	protected Transition createResult(Transition initial) {
		if (spnFade.getSelection() == initial.fade &&
				cmbSide.getSelectionIndex() == initial.side && 
				btnDest.getValue().equals(initial.destination)) {
			return null;
		} else {
			Transition t = new Transition();
			t.side = cmbSide.getSelectionIndex();
			t.destination = btnDest.getValue();
			t.fade = spnFade.getSelection();
			return t;
		}
	}
	
}
