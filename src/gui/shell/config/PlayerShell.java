package gui.shell.config;

import gui.Vocab;
import gui.views.PositionButton;

import org.eclipse.swt.widgets.Shell;

import data.config.Config.Player;
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
	
	private Text txtStartpos;
	private PositionButton btnStartPos;
	private Button btnPixelMovement;
	private Button btnStopOnCollision;
	private Spinner spnWalk;
	private Spinner spnDash;
	private Composite composite;

	public PlayerShell(Shell parent) {
		super(parent);
		setText(Vocab.instance.PLAYER + " - " + Vocab.instance.PROPERTIES);
		content.setLayout(new GridLayout(5, false));
		
		Label lblWalkSpeed = new Label(content, SWT.NONE);
		lblWalkSpeed.setText(Vocab.instance.WALKSPEED);
		
		spnWalk = new Spinner(content, SWT.BORDER);
		spnWalk.setMaximum(999);
		GridData gd_spnWalk = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		gd_spnWalk.widthHint = 47;
		spnWalk.setLayoutData(gd_spnWalk);

		Label lblStartPos = new Label(content, SWT.NONE);
		lblStartPos.setText(Vocab.instance.STARTPOS);
		
		txtStartpos = new Text(content, SWT.BORDER | SWT.READ_ONLY);
		txtStartpos.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		btnStartPos = new PositionButton(content, SWT.NONE);
		btnStartPos.setText(txtStartpos);
		
		Label lblDashSpeed = new Label(content, SWT.NONE);
		lblDashSpeed.setText(Vocab.instance.DASHSPEED);
		
		spnDash = new Spinner(content, SWT.BORDER);
		spnDash.setMaximum(999);
		spnDash.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
	
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
	}

	@Override
	protected Player createResult(Player initial) {
		if (btnStartPos.getValue().equals(initial.startPos) && 
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
