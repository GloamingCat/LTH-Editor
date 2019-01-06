package gui.shell.config;

import gui.Vocab;
import gui.shell.ObjectShell;
import gui.widgets.IDButton;

import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import data.config.Config.Animations;
import lwt.dataestructure.LDataTree;

import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.graphics.Point;

import project.Project;

public class GUIShell extends ObjectShell<Animations> {
	
	public GUIShell(Shell parent) {
		super(parent);
		setMinimumSize(new Point(320, 39));
		setSize(294, 236);
		setText(Vocab.instance.GUI + " - " + Vocab.instance.PROPERTIES);
		contentEditor.setLayout(new GridLayout(3, false));
		
		Label lblCursorAnimation = new Label(contentEditor, SWT.NONE);
		lblCursorAnimation.setText(Vocab.instance.CURSORANIMID);
		
		Text txtCursor = new Text(contentEditor, SWT.BORDER);
		txtCursor.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		IDButton btnCursor = new IDButton(contentEditor, 0) {
			@Override
			public LDataTree<Object> getDataTree() {
				return Project.current.animations.getTree();
			}
		};
		btnCursor.setNameText(txtCursor);
		btnCursor.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		addControl(btnCursor, "cursorID");

		Label lblBattleCursorAnim = new Label(contentEditor, SWT.NONE);
		lblBattleCursorAnim.setText(Vocab.instance.BATTLECURSORANIMID);
		
		Text txtBattleCursor = new Text(contentEditor, SWT.BORDER);
		txtBattleCursor.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		IDButton btnBattleCursor = new IDButton(contentEditor, 0) {
			@Override
			public LDataTree<Object> getDataTree() {
				return Project.current.animations.getTree();
			}
		};
		btnBattleCursor.setNameText(txtBattleCursor);
		btnBattleCursor.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		addControl(btnBattleCursor, "battleCursorID");
		
		Label lblTileAnimation = new Label(contentEditor, SWT.NONE);
		lblTileAnimation.setText(Vocab.instance.TILEANIMID);
		
		Text txtTile = new Text(contentEditor, SWT.BORDER);
		txtTile.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		IDButton btnTile = new IDButton(contentEditor, 0) {
			@Override
			public LDataTree<Object> getDataTree() {
				return Project.current.animations.getTree();
			}
		};
		btnTile.setNameText(txtTile);
		btnTile.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		addControl(btnTile, "tileID");
		
		Label lblTileHLAnimation = new Label(contentEditor, SWT.NONE);
		lblTileHLAnimation.setText(Vocab.instance.TILEHLANIMID);
		
		Text txtTileHL = new Text(contentEditor, SWT.BORDER);
		txtTileHL.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		IDButton btnTileHL = new IDButton(contentEditor, 0) {
			@Override
			public LDataTree<Object> getDataTree() {
				return Project.current.animations.getTree();
			}
		};
		btnTileHL.setNameText(txtTileHL);
		btnTileHL.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		addControl(btnTileHL, "highlightID");
		
		pack();
	}
	
}
