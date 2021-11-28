package gui.views.database.subcontent;

import gui.Vocab;
import gui.widgets.IDButton;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Text;

import project.Project;
import lwt.dataestructure.LDataTree;
import lwt.editor.LObjectEditor;
import lwt.widget.LCombo;
import lwt.widget.LLabel;
import lwt.widget.LSpinner;
import lwt.widget.LText;

public class UnitEditor extends LObjectEditor {

	public UnitEditor(Composite parent, int style) {
		super(parent, style);
		
		GridLayout gridLayout = new GridLayout(5, false);
		gridLayout.marginHeight = 0;
		gridLayout.marginWidth = 0;
		setLayout(gridLayout);
		
		new LLabel(this, Vocab.instance.KEY);
		
		LText txtKey = new LText(this, SWT.NONE);
		txtKey.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 3, 1));
		addControl(txtKey, "key");
		
		Group grpEquip = new Group(this, SWT.NONE);
		grpEquip.setLayout(new FillLayout(SWT.HORIZONTAL));
		GridData gd_grpEquip = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 4);
		gd_grpEquip.widthHint = 200;
		grpEquip.setLayoutData(gd_grpEquip);
		grpEquip.setText(Vocab.instance.EQUIP);
		
		EquipList lstEquip = new EquipList(grpEquip, SWT.NONE);
		addChild(lstEquip, "equip");
		
		new LLabel(this, Vocab.instance.POSITIONX);
		
		LSpinner spnX = new LSpinner(this, SWT.NONE);
		spnX.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		addControl(spnX, "x");
		
		new LLabel(this, Vocab.instance.POSITIONY);
		
		LSpinner spnY = new LSpinner(this, SWT.NONE);
		spnY.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		addControl(spnY, "y");
		
		new LLabel(this, Vocab.instance.LIST);
		
		LCombo cmbList = new LCombo(this, SWT.NONE);
		cmbList.setOptional(false);
		cmbList.setIncludeID(false);
		cmbList.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		cmbList.setItems(new String[] {
				Vocab.instance.CURRENT,
				Vocab.instance.BACKUP,
				Vocab.instance.HIDDEN
		});
		addControl(cmbList, "list");
		
		Composite compIDs = new Composite(this, SWT.NONE);
		compIDs.setLayoutData(new GridData(SWT.FILL, SWT.TOP, false, false, 4, 1));
		GridLayout gl_compIDs = new GridLayout(3, false);
		gl_compIDs.marginWidth = 0;
		gl_compIDs.marginHeight = 0;
		compIDs.setLayout(gl_compIDs);
		
		new LLabel(compIDs, Vocab.instance.CHARACTER);
		
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
		
		new LLabel(compIDs, Vocab.instance.CHARBATTLER);
		
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
		
	}

}
