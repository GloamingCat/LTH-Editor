package gui.shell;

import gui.Vocab;
import gui.views.database.subcontent.EquipList;
import gui.widgets.IDButton;
import lwt.dataestructure.LDataTree;
import lwt.widget.LSpinner;
import lwt.widget.LText;

import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;

import data.subcontent.Unit;

import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.layout.GridData;

import project.Project;

import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.graphics.Point;

public class UnitShell extends ObjectShell<Unit> {
	
	/**
	 * Create the shell.
	 * @param display
	 */
	public UnitShell(Shell parent) {
		super(parent);
		setMinimumSize(new Point(480, 0));
		
		contentEditor.setLayout(new GridLayout(5, false));
		
		Label lblKey = new Label(contentEditor, SWT.NONE);
		lblKey.setText(Vocab.instance.KEY);
		
		LText txtKey = new LText(contentEditor, SWT.NONE);
		txtKey.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 3, 1));
		addControl(txtKey, "key");
		
		Group grpEquip = new Group(contentEditor, SWT.NONE);
		grpEquip.setLayout(new FillLayout(SWT.HORIZONTAL));
		grpEquip.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 3));
		grpEquip.setText(Vocab.instance.EQUIP);
		
		EquipList lstEquip = new EquipList(grpEquip, SWT.NONE);
		addChild(lstEquip, "equip");
		
		Label lblPositionX = new Label(contentEditor, SWT.NONE);
		lblPositionX.setText(Vocab.instance.POSITIONX);
		
		LSpinner spnX = new LSpinner(contentEditor, SWT.NONE);
		spnX.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		addControl(spnX, "x");
		
		Label lblPositionY = new Label(contentEditor, SWT.NONE);
		lblPositionY.setText(Vocab.instance.POSITIONY);
		
		LSpinner spnY = new LSpinner(contentEditor, SWT.NONE);
		spnY.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		addControl(spnY, "y");
		
		Composite compIDs = new Composite(contentEditor, SWT.NONE);
		compIDs.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 4, 1));
		GridLayout gl_compIDs = new GridLayout(3, false);
		gl_compIDs.marginWidth = 0;
		gl_compIDs.marginHeight = 0;
		compIDs.setLayout(gl_compIDs);
		
		Label lblChar = new Label(compIDs, SWT.NONE);
		lblChar.setText(Vocab.instance.CHARACTER);
		
		Text txtChar = new Text(compIDs, SWT.BORDER | SWT.READ_ONLY);
		txtChar.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		IDButton btnChar = new IDButton(compIDs, 0) {
			@Override
			public LDataTree<Object> getDataTree() {
				return Project.current.characters.getTree();
			}
		};
		btnChar.setNameText(txtChar);
		addControl(btnChar, "charID");
		
		Label lblBattler = new Label(compIDs, SWT.NONE);
		lblBattler.setText(Vocab.instance.CHARBATTLER);
		
		Text txtBattler = new Text(compIDs, SWT.BORDER | SWT.READ_ONLY);
		txtBattler.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		IDButton btnBattler = new IDButton(compIDs, 1) {
			@Override
			public LDataTree<Object> getDataTree() {
				return Project.current.battlers.getTree();
			}
		};
		btnBattler.setNameText(txtBattler);
		addControl(btnBattler, "battlerID");
		
		pack();
	}

}
