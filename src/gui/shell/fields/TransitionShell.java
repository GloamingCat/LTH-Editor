package gui.shell.fields;

import gui.Vocab;
import gui.views.PositionButton;

import org.eclipse.swt.widgets.Shell;

import data.Transition;
import lwt.dialog.LObjectShell;

import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Text;

public class TransitionShell extends LObjectShell<Transition> {
	
	private PositionButton btnOrig;
	private PositionButton btnDest;
	private Button btnFadeOut;
	private Text txtOrig;
	private Text txtDest;

	public TransitionShell(Shell parent) {
		super(parent);
		content.setLayout(new GridLayout(3, false));

		Label lblOrig = new Label(content, SWT.NONE);
		lblOrig.setText(Vocab.instance.ORIGIN);
		
		txtOrig = new Text(content, SWT.BORDER | SWT.READ_ONLY);

		btnOrig = new PositionButton(content, SWT.NONE);
		btnOrig.setText(txtOrig);
		
		Label lblDest = new Label(content, SWT.NONE);
		lblDest.setText(Vocab.instance.DESTINATION);
		
		txtDest = new Text(content, SWT.BORDER | SWT.READ_ONLY);

		btnDest = new PositionButton(content, SWT.NONE);
		btnDest.setText(txtDest);
		
		Label lblFadeOut = new Label(content, SWT.NONE);
		lblFadeOut.setText(Vocab.instance.FADEOUT);

		btnFadeOut = new Button(content, SWT.CHECK);
		btnFadeOut.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1));
		
		pack();
	}
	
	public void open(Transition initial) {
		super.open(initial);
		btnOrig.setValue(initial.origin);
		btnDest.setValue(initial.destination);
		btnFadeOut.setSelection(initial.fadeOut);
	}

	@Override
	protected Transition createResult(Transition initial) {
		if (btnFadeOut.getSelection() == initial.fadeOut &&
				btnOrig.getValue().equals(initial.origin) && 
				btnDest.getValue().equals(initial.destination)) {
			return null;
		} else {
			Transition t = new Transition();
			t.origin = btnOrig.getValue();
			t.destination = btnDest.getValue();
			t.fadeOut = btnFadeOut.getSelection();
			return t;
		}
	}
	
}
