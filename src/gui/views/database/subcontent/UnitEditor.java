package gui.views.database.subcontent;

import gui.Vocab;
import gui.widgets.IDButton;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;

import data.Troop.Unit;
import gson.editor.GDefaultObjectEditor;
import project.Project;
import lwt.container.LContainer;
import lwt.container.LPanel;
import lwt.dataestructure.LDataTree;
import lwt.widget.LCombo;
import lwt.widget.LLabel;
import lwt.widget.LSpinner;
import lwt.widget.LText;

public class UnitEditor extends GDefaultObjectEditor<Unit> {

	public LPanel gridCanvas = null;
	
	public UnitEditor(LContainer parent) {
		super(parent, 4, false, false);
		
		new LLabel(this, Vocab.instance.KEY);
		
		LText txtKey = new LText(this, 3);
		addControl(txtKey, "key");
		
		new LLabel(this, Vocab.instance.POSITIONX);
		
		LSpinner spnX = new LSpinner(this);;
		addControl(spnX, "x");
		
		new LLabel(this, Vocab.instance.POSITIONY);
		
		LSpinner spnY = new LSpinner(this);
		addControl(spnY, "y");
		
		new LLabel(this, Vocab.instance.CHARACTER);
		
		LPanel character = new LPanel(this, 2, false);
		character.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 3, 1));
		
		LText txtChar = new LText(character, true);		
		IDButton btnChar = new IDButton(character, false) {
			@Override
			public LDataTree<Object> getDataTree() {
				return Project.current.characters.getTree();
			}
		};
		btnChar.setNameWidget(txtChar);
		addControl(btnChar, "charID");
		
		new LLabel(this, Vocab.instance.CHARBATTLER);
		
		LPanel battler = new LPanel(this, 2, false);
		battler.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 3, 1));
		
		LText txtBattler = new LText(battler, true);
		IDButton btnBattler = new IDButton(battler, true) {
			@Override
			public LDataTree<Object> getDataTree() {
				return Project.current.battlers.getTree();
			}
		};
		addControl(btnBattler, "battlerID");
		btnBattler.setNameWidget(txtBattler);

		new LLabel(this, Vocab.instance.LIST);
		
		LCombo cmbList = new LCombo(this, 3);
		cmbList.setOptional(false);
		cmbList.setIncludeID(false);
		cmbList.setItems(new String[] {
				Vocab.instance.CURRENT,
				Vocab.instance.BACKUP,
				Vocab.instance.HIDDEN
		});
		addControl(cmbList, "list");
		
	}
	
	public void refresh() {
		if (gridCanvas != null)
			gridCanvas.redraw();
	}

}
