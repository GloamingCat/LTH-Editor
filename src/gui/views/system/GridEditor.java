package gui.views.system;

import data.config.Config;
import gui.Tooltip;
import gui.Vocab;
import gui.widgets.CheckBoxPanel;
import lui.base.LPrefs;
import lui.container.LContainer;
import lui.container.LFrame;
import lui.container.LPanel;
import lui.gson.GDefaultObjectEditor;
import lui.widget.LCheckBox;
import lui.widget.LLabel;
import lui.widget.LSpinner;

import java.lang.reflect.Type;

public class GridEditor extends GDefaultObjectEditor<Config.Grid> {
    public GridEditor(LContainer parent, boolean doubleBuffered) {
        super(parent, doubleBuffered);
    }

    @Override
    protected void createContent(int style) {
		setGridLayout(2);

		new LLabel(this, Vocab.instance.PIXELHEIGHT, Tooltip.instance.PIXELHEIGHT);
		LSpinner spnPixelsPerHeight = new LSpinner(this);
		spnPixelsPerHeight.getCellData().setExpand(true, false);
		addControl(spnPixelsPerHeight, "pixelsPerHeight");

		new LLabel(this, Vocab.instance.DEPTHHEIGHT, Tooltip.instance.DEPTHHEIGHT);
		LSpinner spnDepthPerHeight = new LSpinner(this);
		spnDepthPerHeight.getCellData().setExpand(true, false);
		addControl(spnDepthPerHeight, "depthPerHeight");

		new LLabel(this, Vocab.instance.DEPTHY, Tooltip.instance.DEPTHY);
		LSpinner spnDepthPerY = new LSpinner(this);
		spnDepthPerY.getCellData().setExpand(true, false);
		addControl(spnDepthPerY, "depthPerY");

		LFrame tile = new LFrame(this, Vocab.instance.TILESHAPE);
		tile.setHoverText(Tooltip.instance.TILESHAPE);
		tile.setGridLayout(4);
		tile.getCellData().setSpread(2, 1);
		tile.getCellData().setExpand(true, true);

		new LLabel(tile, Vocab.instance.TILEWIDTH, Tooltip.instance.TILEWIDTH).getCellData().setTargetSize(LPrefs.LABELWIDTH / 2, -1);
		LSpinner spnTileW = new LSpinner(tile);
		spnTileW.getCellData().setExpand(true, false);
		addControl(spnTileW, "tileW");

		new LLabel(tile, Vocab.instance.TILEHEIGHT, Tooltip.instance.TILEHEIGHT).getCellData().setTargetSize(LPrefs.LABELWIDTH / 2, -1);
		LSpinner spnTileH = new LSpinner(tile);
		spnTileH.getCellData().setExpand(true, false);
		addControl(spnTileH, "tileH");

		new LLabel(tile, Vocab.instance.TILEBASE, Tooltip.instance.TILEBASE).getCellData().setTargetSize(LPrefs.LABELWIDTH / 2, -1);
		LSpinner spnTileB = new LSpinner(tile);
		spnTileB.getCellData().setExpand(true, false);
		addControl(spnTileB, "tileB");

		new LLabel(tile, Vocab.instance.TILESIDE, Tooltip.instance.TILESIDE);
		LSpinner spnTileS = new LSpinner(tile);
		spnTileS.getCellData().setExpand(true, false);
		addControl(spnTileS, "tileS");

		LPanel gridOptions = new CheckBoxPanel(this);
		gridOptions.getCellData().setSpread(2, 1);
		gridOptions.getCellData().setExpand(true, true);

		LCheckBox btnAllNeighbors = new LCheckBox(gridOptions);
		btnAllNeighbors.setText(Vocab.instance.ALLNEIGHBORS);
		btnAllNeighbors.setHoverText(Tooltip.instance.ALLNEIGHBORS);
		addControl(btnAllNeighbors, "allNeighbors");

		LCheckBox btnOverpassAllies = new LCheckBox(gridOptions);
		btnOverpassAllies.setText(Vocab.instance.OVERPASSALLIES);
		btnOverpassAllies.setHoverText(Tooltip.instance.OVERPASSALLIES);
		addControl(btnOverpassAllies, "overpassAllies");

		LCheckBox btnOverpassDeads = new LCheckBox(gridOptions);
		btnOverpassDeads.setText(Vocab.instance.OVERPASSDEADS);
		btnOverpassDeads.setHoverText(Tooltip.instance.OVERPASSDEADS);
		addControl(btnOverpassDeads, "overpassDeads");

    }

    @Override
    public Type getType() {
        return Config.Grid.class;
    }
}
