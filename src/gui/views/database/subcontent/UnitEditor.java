package gui.views.database.subcontent;

import gui.Vocab;
import gui.widgets.IDButton;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

import project.Project;
import lwt.dataestructure.LDataTree;
import lwt.editor.LObjectEditor;
import lwt.widget.LCombo;
import lwt.widget.LLabel;
import lwt.widget.LSpinner;
import lwt.widget.LText;
import org.eclipse.swt.widgets.Label;

public class UnitEditor extends LObjectEditor {

	public UnitEditor(Composite parent, int style) {
		super(parent, style);
		
		GridLayout gridLayout = new GridLayout(7, false);
		gridLayout.marginHeight = 0;
		gridLayout.marginWidth = 0;
		setLayout(gridLayout);
		
		new LLabel(this, Vocab.instance.KEY);
		
		LText txtKey = new LText(this);
		txtKey.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 3, 1));
		addControl(txtKey, "key");
		
		new LLabel(this, Vocab.instance.CHARACTER);
		
		LText txtChar = new LText(this, true);
		txtChar.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		IDButton btnChar = new IDButton(this, 0) {
			@Override
			public LDataTree<Object> getDataTree() {
				return Project.current.characters.getTree();
			}
		};
		btnChar.setNameWidget(txtChar);
		addControl(btnChar, "charID");
		
		new LLabel(this, Vocab.instance.POSITIONX);
		
		LSpinner spnX = new LSpinner(this, SWT.NONE);
		spnX.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		addControl(spnX, "x");
		
		new LLabel(this, Vocab.instance.POSITIONY);
		
		LSpinner spnY = new LSpinner(this, SWT.NONE);
		spnY.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		addControl(spnY, "y");
		
		new LLabel(this, Vocab.instance.CHARBATTLER);
		
		LText txtBattler = new LText(this, true);
		txtBattler.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		IDButton btnBattler = new IDButton(this, 1) {
			@Override
			public LDataTree<Object> getDataTree() {
				return Project.current.battlers.getTree();
			}
		};
		addControl(btnBattler, "battlerID");
		btnBattler.setNameWidget(txtBattler);

		new LLabel(this, Vocab.instance.LIST);
		
		LCombo cmbList = new LCombo(this, SWT.NONE);
		cmbList.setOptional(false);
		cmbList.setIncludeID(false);
		cmbList.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		cmbList.setItems(new String[] {
				Vocab.instance.CURRENT,
				Vocab.instance.BACKUP,
				Vocab.instance.HIDDEN
		});
		addControl(cmbList, "list");
		new Label(this, SWT.NONE);
		
	}

}
