package gui.shell.config;

import gui.Vocab;

import org.eclipse.swt.widgets.Shell;

import data.Config.GUI;
import lwt.dialog.LObjectShell;
import lwt.widget.LCombo;

import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.layout.GridData;

import project.Project;
import org.eclipse.swt.graphics.Point;

public class GUIShell extends LObjectShell<GUI> {

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
	
	public void open(GUI initial) {
		super.open(initial);
		cmbCursor.setItems(Project.current.animOther.getList());
		cmbCursor.setValue(initial.cursorAnimID);
		cmbBattleCursor.setItems(Project.current.animOther.getList());
		cmbBattleCursor.setValue(initial.battleCursorAnimID);
		cmbTile.setItems(Project.current.animOther.getList());
		cmbTile.setValue(initial.tileAnimID);
		cmbTileHL.setItems(Project.current.animOther.getList());
		cmbTileHL.setValue(initial.tileHLAnimID);
	}

	@Override
	protected GUI createResult(GUI initial) {
		if (cmbCursor.getSelectionIndex() == initial.cursorAnimID &&
				cmbTile.getSelectionIndex() == initial.tileAnimID &&
				cmbTileHL.getSelectionIndex() == initial.tileHLAnimID &&
				cmbBattleCursor.getSelectionIndex() == initial.battleCursorAnimID) {
			return null;
		} else {
			GUI g = new GUI();
			g.battleCursorAnimID = cmbBattleCursor.getSelectionIndex();
			g.tileAnimID = cmbTile.getSelectionIndex();
			g.cursorAnimID = cmbCursor.getSelectionIndex();
			g.tileHLAnimID = cmbTileHL.getSelectionIndex();
			return g;
		}
	}
	
}
