package gui.views.system;

import data.config.Config;
import gui.Tooltip;
import gui.Vocab;
import gui.widgets.IDButton;
import lui.container.LContainer;
import lui.container.LPanel;
import lui.gson.GDefaultObjectEditor;
import lui.widget.LLabel;
import lui.widget.LSpinner;
import lui.widget.LText;

import java.lang.reflect.Type;

public class TroopEditor extends GDefaultObjectEditor<Config.Troop> {

    public IDButton btnInitialTroop;

    public TroopEditor(LContainer parent, boolean doubleBuffered) {
        super(parent, doubleBuffered);
    }

    @Override
    protected void createContent(int style) {

		setGridLayout(3);
		new LLabel(this, Vocab.instance.INITIALTROOP, Tooltip.instance.INITIALTROOP);
		LText txtInitialTroop = new LText(this, true);
		txtInitialTroop.getCellData().setExpand(true, false);
		btnInitialTroop = new IDButton(this, Vocab.instance.TROOPSHELL, false);
		btnInitialTroop.setNameWidget(txtInitialTroop);
		addControl(btnInitialTroop, "initialTroopID");

		new LLabel(this, Vocab.instance.MAXMEMBERS, Tooltip.instance.MAXMEMBERS);
		LSpinner spnMaxMembers = new LSpinner(this);
		spnMaxMembers.getCellData().setExpand(true, false);
		spnMaxMembers.getCellData().setSpread(2, 1);
		addControl(spnMaxMembers, "maxMembers");

		new LLabel(this, Vocab.instance.TROOPSIZE, Tooltip.instance.TROOPSIZE);
		LPanel troopSize = new LPanel(this);
		troopSize.setGridLayout(3);
		troopSize.getCellData().setExpand(true, false);
		troopSize.getCellData().setSpread(2, 1);

		LSpinner spnWidth = new LSpinner(troopSize);
		spnWidth.getCellData().setExpand(true, false);
		addControl(spnWidth, "width");

		new LLabel(troopSize, "x");

		LSpinner spnHeight = new LSpinner(troopSize);
		spnHeight.getCellData().setExpand(true, false);
		addControl(spnHeight, "height");

		new LLabel(this, 3, 1).getCellData().setExpand(false, true);

    }

    @Override
    public Type getType() {
        return Config.Troop.class;
    }
}
