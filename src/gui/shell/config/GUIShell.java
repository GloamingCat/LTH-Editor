package gui.shell.config;

import gui.Vocab;

import org.eclipse.swt.widgets.Shell;

import data.config.Config.Animations;
import lwt.dialog.LObjectShell;
import lwt.widget.LCombo;

import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.layout.GridData;

import org.eclipse.swt.graphics.Point;

public class GUIShell extends LObjectShell<Animations> {

	private LCombo cmbCursor;
	private LCombo cmbBattleCursor;
	private LCombo cmbTile;
	private LCombo cmbTileHL;
	
	public GUIShell(Shell parent) {
		super(parent);
		setMinimumSize(new Point(320, 39));
		setSize(294, 236);
		setText(Vocab.instance.GUI + " - " + Vocab.instance.PROPERTIES);
		content.setLayout(new GridLayout(2, false));
		
		Label lblCursorAnimation = new Label(content, SWT.NONE);
		lblCursorAnimation.setText(Vocab.instance.CURSORANIMID);
		
		cmbCursor = new LCombo(content, SWT.NONE);
		cmbCursor.setOptional(false);
		cmbCursor.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		Label lblBattleCursorAnim = new Label(content, SWT.NONE);
		lblBattleCursorAnim.setText(Vocab.instance.BATTLECURSORANIMID);
		
		cmbBattleCursor = new LCombo(content, SWT.NONE);
		cmbBattleCursor.setOptional(false);
		cmbBattleCursor.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label lblTileAnimation = new Label(content, SWT.NONE);
		lblTileAnimation.setText(Vocab.instance.TILEANIMID);
		
		cmbTile = new LCombo(content, SWT.NONE);
		cmbTile.setOptional(false);
		cmbTile.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label lblTileHLAnimation = new Label(content, SWT.NONE);
		lblTileHLAnimation.setText(Vocab.instance.TILEHLANIMID);
		
		cmbTileHL = new LCombo(content, SWT.NONE);
		cmbTileHL.setOptional(false);
		cmbTileHL.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		pack();
	}
	
	public void open(Animations initial) {
		super.open(initial);
		//TODO
		//cmbCursor.setItems(Project.current.spritesheets.getList());
		cmbCursor.setValue(initial.cursorID);
		//cmbBattleCursor.setItems(Project.current.spritesheets.getList());
		cmbBattleCursor.setValue(initial.battleCursorID);
		//cmbTile.setItems(Project.current.spritesheets.getList());
		cmbTile.setValue(initial.tileID);
		//cmbTileHL.setItems(Project.current.spritesheets.getList());
		cmbTileHL.setValue(initial.highlightID);
	}

	@Override
	protected Animations createResult(Animations initial) {
		if (cmbCursor.getSelectionIndex() == initial.cursorID &&
				cmbTile.getSelectionIndex() == initial.tileID &&
				cmbTileHL.getSelectionIndex() == initial.highlightID &&
				cmbBattleCursor.getSelectionIndex() == initial.battleCursorID) {
			return null;
		} else {
			Animations g = new Animations();
			g.battleCursorID = cmbBattleCursor.getSelectionIndex();
			g.tileID = cmbTile.getSelectionIndex();
			g.cursorID = cmbCursor.getSelectionIndex();
			g.highlightID = cmbTileHL.getSelectionIndex();
			return g;
		}
	}
	
}
