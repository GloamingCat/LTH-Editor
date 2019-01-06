package gui.shell.config;

import gui.Vocab;
import gui.shell.ObjectShell;
import gui.widgets.PositionButton;

import org.eclipse.swt.widgets.Shell;

import data.config.Config.Player;
import lwt.widget.LSpinner;

import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Text;

public class PlayerShell extends ObjectShell<Player> {

	public PlayerShell(Shell parent) {
		super(parent);
		setText(Vocab.instance.PLAYER + " - " + Vocab.instance.PROPERTIES);
		contentEditor.setLayout(new GridLayout(3, false));
		
		Label lblWalkSpeed = new Label(contentEditor, SWT.NONE);
		lblWalkSpeed.setText(Vocab.instance.WALKSPEED);
		
		LSpinner spnWalk = new LSpinner(contentEditor);
		spnWalk.setMaximum(999);
		spnWalk.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		addControl(spnWalk, "walkSpeed");
		
		Label lblDashSpeed = new Label(contentEditor, SWT.NONE);
		lblDashSpeed.setText(Vocab.instance.DASHSPEED);
		
		LSpinner spnDash = new LSpinner(contentEditor);
		spnDash.setMaximum(999);
		spnDash.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		addControl(spnDash, "dashSpeed");
		
		Label lblStartPos = new Label(contentEditor, SWT.NONE);
		lblStartPos.setText(Vocab.instance.STARTPOS);
		
		Text txtStartpos = new Text(contentEditor, SWT.BORDER | SWT.READ_ONLY);
		txtStartpos.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		PositionButton btnStartPos = new PositionButton(contentEditor, SWT.NONE);
		btnStartPos.setText(txtStartpos);
		addControl(btnStartPos, "startPos");

		pack();
	}
	
}
