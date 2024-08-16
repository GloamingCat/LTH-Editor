package gui.views.system;

import data.config.Config;
import gui.Tooltip;
import gui.Vocab;
import gui.widgets.CheckBoxPanel;
import lui.base.LPrefs;
import lui.container.LContainer;
import lui.container.LPanel;
import lui.gson.GDefaultObjectEditor;
import lui.widget.LCheckBox;
import lui.widget.LLabel;
import lui.widget.LSeparator;
import lui.widget.LSpinner;

import java.lang.reflect.Type;

public class GridEditor extends GDefaultObjectEditor<Config.Grid> {
    public GridEditor(LContainer parent, boolean doubleBuffered) {
        super(parent, doubleBuffered);
    }

    @Override
    protected void createContent(int style) {
		setGridLayout(4);

		new LLabel(this, Vocab.instance.PIXELHEIGHT, Tooltip.instance.PIXELHEIGHT)
				.getCellData().setRequiredSize(LPrefs.LABELWIDTH, LPrefs.WIDGETHEIGHT);
		LSpinner spnPixelsPerHeight = new LSpinner(this);
		spnPixelsPerHeight.getCellData().setExpand(true, false);
		addControl(spnPixelsPerHeight, "pixelsPerHeight");

		new LLabel(this, Vocab.instance.DEPTHHEIGHT, Tooltip.instance.DEPTHHEIGHT)
				.getCellData().setRequiredSize(LPrefs.LABELWIDTH, LPrefs.WIDGETHEIGHT);
		LSpinner spnDepthPerHeight = new LSpinner(this);
		spnDepthPerHeight.getCellData().setExpand(true, false);
		addControl(spnDepthPerHeight, "depthPerHeight");

		new LLabel(this, Vocab.instance.DEPTHY, Tooltip.instance.DEPTHY);
		LSpinner spnDepthPerY = new LSpinner(this);
		spnDepthPerY.getCellData().setExpand(true, false);
		addControl(spnDepthPerY, "depthPerY");
		new LLabel(this, 2, 1);

		LSeparator tile = new LSeparator(this, Vocab.instance.TILESHAPE, Tooltip.instance.TILESHAPE);
		tile.getCellData().setSpread(4, 1);
		tile.getCellData().setExpand(true, false);

		new LLabel(this, Vocab.instance.TILEWIDTH, Tooltip.instance.TILEWIDTH).getCellData().setTargetSize(LPrefs.LABELWIDTH / 2, -1);
		LSpinner spnTileW = new LSpinner(this);
		spnTileW.getCellData().setExpand(true, false);
		addControl(spnTileW, "tileW");

		new LLabel(this, Vocab.instance.TILEBASE, Tooltip.instance.TILEBASE).getCellData().setTargetSize(LPrefs.LABELWIDTH / 2, -1);
		LSpinner spnTileB = new LSpinner(this);
		spnTileB.getCellData().setExpand(true, false);
		addControl(spnTileB, "tileB");

		new LLabel(this, Vocab.instance.TILEHEIGHT, Tooltip.instance.TILEHEIGHT).getCellData().setTargetSize(LPrefs.LABELWIDTH / 2, -1);
		LSpinner spnTileH = new LSpinner(this);
		spnTileH.getCellData().setExpand(true, false);
		addControl(spnTileH, "tileH");

		new LLabel(this, Vocab.instance.TILESIDE, Tooltip.instance.TILESIDE);
		LSpinner spnTileS = new LSpinner(this);
		spnTileS.getCellData().setExpand(true, false);
		addControl(spnTileS, "tileS");

		new LSeparator(this, true).getCellData().setSpread(4, 1);

		LPanel gridOptions = new CheckBoxPanel(this);
		gridOptions.getCellData().setSpread(4, 1);
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

		new LLabel(this, 4, 1).getCellData().setExpand(false, true);
    }

    @Override
    public Type getType() {
        return Config.Grid.class;
    }
}
