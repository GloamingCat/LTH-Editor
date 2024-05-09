package gui.views.database.subcontent;

import gui.Tooltip;
import gui.Vocab;
import gui.widgets.IDButton;
import lui.base.LFlags;
import data.Troop.Unit;
import lui.container.LCanvas;
import project.Project;
import lui.container.LContainer;
import lui.container.LPanel;
import lui.gson.GDefaultObjectEditor;
import lui.base.data.LDataTree;
import lui.widget.LCombo;
import lui.widget.LLabel;
import lui.widget.LSpinner;
import lui.widget.LText;

public class UnitEditor extends GDefaultObjectEditor<Unit> {

	public LCanvas gridCanvas = null;
	public final LSpinner spnX;
	public final LSpinner spnY;
	public final LText txtChar;
	public final LText txtBattler;
	public final LCombo cmbList;
	
	public UnitEditor(LContainer parent) {
		super(parent, false);
		setGridLayout(4);
		
		LLabel lblKey = new LLabel(this, Vocab.instance.KEY, Tooltip.instance.KEY);
		LText txtKey = new LText(this);
		txtKey.addMenu(lblKey);
		txtKey.getCellData().setExpand(true, false);
		txtKey.getCellData().setSpread(3, 1);
		addControl(txtKey, "key");
		
		LLabel lblX = new LLabel(this, Vocab.instance.GRIDX, Tooltip.instance.GRIDX);
		spnX = new LSpinner(this);
		spnX.getCellData().setExpand(true, false);
		spnX.addMenu(lblX);
		addControl(spnX, "x");
		
		LLabel lblY = new LLabel(this, Vocab.instance.GRIDY, Tooltip.instance.GRIDY);
		spnY = new LSpinner(this);
		spnY.getCellData().setExpand(true, false);
		spnY.addMenu(lblY);
		addControl(spnY, "y");
		
		LLabel lblChar = new LLabel(this, Vocab.instance.CHARACTER, Tooltip.instance.CHARACTER);
		LPanel character = new LPanel(this);
		character.setGridLayout(2);
		character.getCellData().setExpand(true, false);
		character.getCellData().setAlignment(LFlags.MIDDLE);
		character.getCellData().setSpread(3, 1);
		txtChar = new LText(character, true);
		txtChar.getCellData().setExpand(true, false);
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
		battler.getCellData().setExpand(true, false);
		battler.getCellData().setAlignment(LFlags.MIDDLE);
		battler.getCellData().setSpread(3, 1);
		txtBattler = new LText(battler, true);
		txtBattler.getCellData().setExpand(true, false);
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
		cmbList = new LCombo(this, true);
		cmbList.getCellData().setSpread(3, 1);
		cmbList.getCellData().setExpand(true, false);
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
