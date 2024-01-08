package gui.views.database.subcontent;

import gui.Tooltip;
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
		super(parent, false);
		setGridLayout(4);
		
		LLabel lblKey = new LLabel(this, Vocab.instance.KEY, Tooltip.instance.KEY);
		LText txtKey = new LText(this, 3);
		txtKey.addMenu(lblKey);
		addControl(txtKey, "key");
		
		LLabel lblX = new LLabel(this, Vocab.instance.GRIDX, Tooltip.instance.GRIDX);
		spnX = new LSpinner(this);
		spnX.addMenu(lblX);
		addControl(spnX, "x");
		
		LLabel lblY = new LLabel(this, Vocab.instance.GRIDY, Tooltip.instance.GRIDY);
		spnY = new LSpinner(this);
		spnY.addMenu(lblY);
		addControl(spnY, "y");
		
		LLabel lblChar = new LLabel(this, Vocab.instance.CHARACTER, Tooltip.instance.CHARACTER);
		LPanel character = new LPanel(this);
		character.setGridLayout(2);
		character.setExpand(true, false);
		character.setAlignment(LFlags.CENTER);
		character.setSpread(3, 1);
		txtChar = new LText(character, true);		
		IDButton btnChar = new IDButton(character, Vocab.instance.CHARSHELL, false) {
			@Override
			public LDataTree<Object> getDataTree() {
				return Project.current.characters.getTree();
			}
		};
		btnChar.setNameWidget(txtChar);
		btnChar.addMenu(lblChar);
		addControl(btnChar, "charID");
		
		LLabel lblBattler = new LLabel(this, Vocab.instance.CHARBATTLER, Tooltip.instance.CHARBATTLER);
		LPanel battler = new LPanel(this);
		battler.setGridLayout(2);
		battler.setExpand(true, false);
		battler.setAlignment(LFlags.CENTER);
		battler.setSpread(3, 1);
		txtBattler = new LText(battler, true);
		IDButton btnBattler = new IDButton(battler, Vocab.instance.BATTLERSHELL, true) {
			@Override
			public LDataTree<Object> getDataTree() {
				return Project.current.battlers.getTree();
			}
		};		
		btnBattler.setNameWidget(txtBattler);
		btnBattler.addMenu(lblBattler);
		addControl(btnBattler, "battlerID");

		LLabel lblList = new LLabel(this, Vocab.instance.UNITLIST, Tooltip.instance.UNITLIST);
		cmbList = new LCombo(this, 3);
		cmbList.setOptional(false);
		cmbList.setIncludeID(false);
		cmbList.setItems(new String[] {
				Vocab.instance.CURRENT,
				Vocab.instance.BACKUP,
				Vocab.instance.HIDDEN
		});
		cmbList.addMenu(lblList);
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
