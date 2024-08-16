package gui.views.system;

import data.config.Config;
import gui.Tooltip;
import gui.Vocab;
import gui.widgets.IDButton;
import lui.base.LPrefs;
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
		setGridLayout(4);

		new LLabel(this, Vocab.instance.INITIALTROOP, Tooltip.instance.INITIALTROOP)
				.getCellData().setRequiredSize(LPrefs.LABELWIDTH, LPrefs.WIDGETHEIGHT);
		LPanel playerTroop = new LPanel(this);
		playerTroop.setGridLayout(2);
		playerTroop.getCellData().setSpread(3, 1);
		playerTroop.getCellData().setExpand(true, false);
		LText txtInitialTroop = new LText(playerTroop, true);
		txtInitialTroop.getCellData().setExpand(true, false);
		btnInitialTroop = new IDButton(playerTroop, Vocab.instance.TROOPSHELL, false);
		btnInitialTroop.setNameWidget(txtInitialTroop);
		addControl(btnInitialTroop, "initialTroopID");

		new LLabel(this, Vocab.instance.TROOPSIZE, Tooltip.instance.TROOPSIZE);
		LSpinner spnWidth = new LSpinner(this);
		spnWidth.getCellData().setExpand(true, false);
		addControl(spnWidth, "width");

		new LLabel(this, "x");
		LSpinner spnHeight = new LSpinner(this);
		spnHeight.getCellData().setExpand(true, false);
		addControl(spnHeight, "height");

		new LLabel(this, Vocab.instance.MAXMEMBERS, Tooltip.instance.MAXMEMBERS);
		LSpinner spnMaxMembers = new LSpinner(this);
		spnMaxMembers.getCellData().setExpand(true, false);
		addControl(spnMaxMembers, "maxMembers");
		new LLabel(this, 2, 1);

		new LLabel(this, 4, 1).getCellData().setExpand(false, true);
    }

    @Override
    public Type getType() {
        return Config.Troop.class;
    }
}
