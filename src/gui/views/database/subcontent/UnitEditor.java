package gui.views.database.subcontent;

import gui.Vocab;
import gui.widgets.IDButton;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

import data.Troop.Unit;
import gson.editor.GDefaultObjectEditor;
import project.Project;
import lwt.dataestructure.LDataTree;
import lwt.widget.LCombo;
import lwt.widget.LLabel;
import lwt.widget.LSpinner;
import lwt.widget.LText;

public class UnitEditor extends GDefaultObjectEditor<Unit> {

	public UnitEditor(Composite parent, int style) {
		super(parent, style);
		
		GridLayout gridLayout = new GridLayout(4, false);
		gridLayout.marginHeight = 0;
		gridLayout.marginWidth = 0;
		setLayout(gridLayout);
		
		new LLabel(this, Vocab.instance.KEY);
		
		LText txtKey = new LText(this);
		txtKey.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 3, 1));
		addControl(txtKey, "key");
		
		new LLabel(this, Vocab.instance.POSITIONX);
		
		LSpinner spnX = new LSpinner(this, SWT.NONE);
		spnX.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		addControl(spnX, "x");
		
		new LLabel(this, Vocab.instance.POSITIONY);
		
		LSpinner spnY = new LSpinner(this, SWT.NONE);
		spnY.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		addControl(spnY, "y");
		
		new LLabel(this, Vocab.instance.CHARACTER);
		
		Composite character = new Composite(this, 0);
		GridLayout char_gl = new GridLayout(2, false);
		char_gl.marginHeight = 0;
		char_gl.marginWidth = 0;
		character.setLayout(char_gl);
		character.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 3, 1));
		
		LText txtChar = new LText(character, true);
		txtChar.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		IDButton btnChar = new IDButton(character, 0) {
			@Override
			public LDataTree<Object> getDataTree() {
				return Project.current.characters.getTree();
			}
		};
		btnChar.setNameWidget(txtChar);
		addControl(btnChar, "charID");
		
		new LLabel(this, Vocab.instance.CHARBATTLER);
		
		Composite battler = new Composite(this, 0);
		GridLayout battler_gl = new GridLayout(2, false);
		battler_gl.marginHeight = 0;
		battler_gl.marginWidth = 0;
		battler.setLayout(battler_gl);
		battler.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 3, 1));
		
		LText txtBattler = new LText(battler, true);
		txtBattler.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		IDButton btnBattler = new IDButton(battler, 1) {
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
		cmbList.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 3, 1));
		cmbList.setItems(new String[] {
				Vocab.instance.CURRENT,
				Vocab.instance.BACKUP,
				Vocab.instance.HIDDEN
		});
		addControl(cmbList, "list");
		
	}

}
