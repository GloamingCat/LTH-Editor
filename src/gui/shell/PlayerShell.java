package gui.shell;

import gui.Vocab;
import gui.views.common.PositionButton;
import gui.views.common.ScriptButton;

import org.eclipse.swt.widgets.Shell;

import data.Player;
import lwt.dialog.LObjectShell;

import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Composite;

public class PlayerShell extends LObjectShell<Player> {
	
	private Text txtScript;
	private Text txtStartpos;
	private PositionButton btnStartPos;
	private ScriptButton btnScript;
	private Button btnPixelMovement;
	private Button btnStopOnCollision;
	private Spinner spnWalk;
	private Spinner spnDash;
	private Composite composite;

	public PlayerShell(Shell parent) {
		super(parent);
		content.setLayout(new GridLayout(5, false));
		
		Label lblWalkSpeed = new Label(content, SWT.NONE);
		lblWalkSpeed.setText("Walk Speed");
		
		spnWalk = new Spinner(content, SWT.BORDER);
		spnWalk.setMaximum(999);
		GridData gd_spnWalk = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		gd_spnWalk.widthHint = 34;
		spnWalk.setLayoutData(gd_spnWalk);
		
		Label lblScript = new Label(content, SWT.NONE);
		lblScript.setText(Vocab.instance.SCRIPT);
		
		txtScript = new Text(content, SWT.BORDER | SWT.READ_ONLY);
		txtScript.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		btnScript = new ScriptButton(content, SWT.NONE);
		btnScript.setFolder("character");
		btnScript.setPathText(txtScript);
		
		Label lblDashSpeed = new Label(content, SWT.NONE);
		lblDashSpeed.setText("Dash Speed");
		
		spnDash = new Spinner(content, SWT.BORDER);
		spnDash.setMaximum(999);
		spnDash.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label lblStartPos = new Label(content, SWT.NONE);
		lblStartPos.setText(Vocab.instance.STARTPOS);
		
		txtStartpos = new Text(content, SWT.BORDER | SWT.READ_ONLY);
		txtStartpos.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		btnStartPos = new PositionButton(content, SWT.NONE);
		btnStartPos.setText(txtStartpos);
		
		composite = new Composite(content, SWT.NONE);
		composite.setLayout(new GridLayout(2, false));
		composite.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 5, 1));

		btnPixelMovement = new Button(composite, SWT.CHECK);
		btnPixelMovement.setText(Vocab.instance.PIXELMOV);
		
		btnStopOnCollision = new Button(composite, SWT.CHECK);
		btnStopOnCollision.setText(Vocab.instance.STOPCOLL);
		
		pack();
	}
	
	public void open(Player initial) {
		super.open(initial);
		btnStartPos.setValue(initial.startPos);
		spnDash.setSelection(initial.dashSpeed);
		spnWalk.setSelection(initial.walkSpeed);
		btnPixelMovement.setSelection(initial.pixelMovement);
		btnStopOnCollision.setSelection(initial.stopOnCollision);
		btnScript.setValue(initial.script);
	}

	@Override
	protected Player createResult(Player initial) {
		if (btnStartPos.getValue().equals(initial.startPos) && 
				btnScript.getValue().equals(initial.script) &&
				spnDash.getSelection() == initial.dashSpeed &&
				spnWalk.getSelection() == initial.walkSpeed &&
				btnPixelMovement.getSelection() == initial.pixelMovement &&
				btnStopOnCollision.getSelection() == initial.stopOnCollision) {
			return null;
		} else {
			Player player = new Player();
			player.dashSpeed = spnDash.getSelection();
			player.walkSpeed = spnWalk.getSelection();
			player.pixelMovement = btnPixelMovement.getSelection();
			player.stopOnCollision = btnStopOnCollision.getSelection();
			player.startPos = btnStartPos.getValue();
			return player;
		}
	}
	
}
