package gui.shell;

import gui.Vocab;
import gui.views.database.subcontent.EquipList;
import gui.widgets.IDButton;
import lwt.dataestructure.LDataList;
import lwt.dataestructure.LDataTree;
import lwt.dialog.LObjectShell;

import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;

import data.Battler.Equip;
import data.subcontent.Unit;

import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Spinner;

import project.Project;

import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.graphics.Point;

public class UnitShell extends LObjectShell<Unit> {
	
	private Text txtKey;
	private IDButton btnChar;
	private IDButton btnBattler;
	private EquipList lstEquip;
	
	private LDataList<Equip> equip = new LDataList<>();
	private Spinner spnX;
	private Spinner spnY;
	
	/**
	 * Create the shell.
	 * @param display
	 */
	public UnitShell(Shell parent) {
		super(parent);
		setMinimumSize(new Point(480, 0));
		
		content.setLayout(new GridLayout(5, false));
		
		Label lblKey = new Label(content, SWT.NONE);
		lblKey.setText(Vocab.instance.KEY);
		
		txtKey = new Text(content, SWT.BORDER);
		txtKey.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 3, 1));
		
		Group grpEquip = new Group(content, SWT.NONE);
		grpEquip.setLayout(new FillLayout(SWT.HORIZONTAL));
		grpEquip.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 3));
		grpEquip.setText(Vocab.instance.EQUIP);
		
		lstEquip = new EquipList(grpEquip, SWT.NONE);
		
		Label lblPositionX = new Label(content, SWT.NONE);
		lblPositionX.setText(Vocab.instance.POSITIONX);
		
		spnX = new Spinner(content, SWT.BORDER);
		spnX.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label lblPositionY = new Label(content, SWT.NONE);
		lblPositionY.setText(Vocab.instance.POSITIONY);
		
		spnY = new Spinner(content, SWT.BORDER);
		spnY.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Composite compIDs = new Composite(content, SWT.NONE);
		compIDs.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 4, 1));
		GridLayout gl_compIDs = new GridLayout(3, false);
		gl_compIDs.marginWidth = 0;
		gl_compIDs.marginHeight = 0;
		compIDs.setLayout(gl_compIDs);
		
		Label lblChar = new Label(compIDs, SWT.NONE);
		lblChar.setText(Vocab.instance.CHARACTER);
		
		Text txtChar = new Text(compIDs, SWT.BORDER | SWT.READ_ONLY);
		txtChar.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		btnChar = new IDButton(compIDs, SWT.NONE) {
			@Override
			public LDataTree<Object> getDataTree() {
				return Project.current.characters.getTree();
			}
		};
		btnChar.optional = false;
		btnChar.setNameText(txtChar);
		
		Label lblBattler = new Label(compIDs, SWT.NONE);
		lblBattler.setText(Vocab.instance.CHARBATTLER);
		
		Text txtBattler = new Text(compIDs, SWT.BORDER | SWT.READ_ONLY);
		txtBattler.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		btnBattler = new IDButton(compIDs, SWT.NONE) {
			@Override
			public LDataTree<Object> getDataTree() {
				return Project.current.battlers.getTree();
			}
		};
		btnBattler.optional = true;
		btnBattler.setNameText(txtBattler);

		pack();
	}
	
	public void open(Unit initial) {
		super.open(initial);
		txtKey.setText(initial.key);
		btnChar.setValue(initial.charID);
		btnBattler.setValue(initial.battlerID);
		spnX.setSelection(initial.x);
		spnY.setSelection(initial.y);
		for (Equip e : initial.equip) {
			equip.add(e.clone());
		}
		lstEquip.setObject(equip);
	}
	
	@Override
	protected Unit createResult(Unit initial) {
		Unit u = new Unit();
		u.key = txtKey.getText();
		u.x = spnX.getSelection();
		u.y = spnY.getSelection();
		u.equip = equip;
		u.charID = btnChar.getValue();
		u.battlerID = btnBattler.getValue();
		return u;
	}

}
