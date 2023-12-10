package gui.views.database.subcontent;

import gui.Vocab;
import gui.widgets.IDButton;

import data.Troop.Unit;
import gson.editor.GDefaultObjectEditor;
import project.Project;
import lwt.LFlags;
import lwt.container.LContainer;
import lwt.container.LPanel;
import lwt.dataestructure.LDataTree;
import lwt.widget.LCombo;
import lwt.widget.LLabel;
import lwt.widget.LSpinner;
import lwt.widget.LText;

public class UnitEditor extends GDefaultObjectEditor<Unit> {

	public LPanel gridCanvas = null;
	public LSpinner spnX;
	public LSpinner spnY;
	public LText txtChar;
	public LText txtBattler;
	public LCombo cmbList;
	
	public UnitEditor(LContainer parent) {
		super(parent, 4, false, false);
		
		new LLabel(this, Vocab.instance.KEY);
		
		LText txtKey = new LText(this, 3);
		addControl(txtKey, "key");
		
		new LLabel(this, Vocab.instance.POSITIONX);
		
		spnX = new LSpinner(this);;
		addControl(spnX, "x");
		
		new LLabel(this, Vocab.instance.POSITIONY);
		
		spnY = new LSpinner(this);
		addControl(spnY, "y");
		
		new LLabel(this, Vocab.instance.CHARACTER);
		
		LPanel character = new LPanel(this, 2, false);
		character.setExpand(true, false);
		character.setAlignment(LFlags.CENTER);
		character.setSpread(3, 1);
		
		txtChar = new LText(character, true);		
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
		battler.setExpand(true, false);
		battler.setAlignment(LFlags.CENTER);
		battler.setSpread(3, 1);
		
		txtBattler = new LText(battler, true);
		IDButton btnBattler = new IDButton(battler, true) {
			@Override
			public LDataTree<Object> getDataTree() {
				return Project.current.battlers.getTree();
			}
		};
		addControl(btnBattler, "battlerID");
		btnBattler.setNameWidget(txtBattler);

		new LLabel(this, Vocab.instance.LIST);
		
		cmbList = new LCombo(this, 3);
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

	@Override
	public Class<?> getType() {
		return Unit.class;
	}

}
