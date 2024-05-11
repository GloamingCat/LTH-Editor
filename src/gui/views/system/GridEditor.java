package gui.views.system;

import data.config.Config;
import gui.Tooltip;
import gui.Vocab;
import lui.base.LFlags;
import lui.container.LContainer;
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

		setGridLayout(3);
		new LLabel(this, Vocab.instance.TILEWIDTH, Tooltip.instance.TILEWIDTH);
		LSpinner spnTileW = new LSpinner(this);
		spnTileW.getCellData().setExpand(true, false);
		spnTileW.getCellData().setSpread(2, 1);
		addControl(spnTileW, "tileW");

		new LLabel(this, Vocab.instance.TILEHEIGHT, Tooltip.instance.TILEHEIGHT);
		LSpinner spnTileH = new LSpinner(this);
		spnTileH.getCellData().setExpand(true, false);
		spnTileH.getCellData().setSpread(2, 1);
		addControl(spnTileH, "tileH");

		new LLabel(this, Vocab.instance.TILEBASE, Tooltip.instance.TILEBASE);
		LSpinner spnTileB = new LSpinner(this);
		spnTileB.getCellData().setExpand(true, false);
		spnTileB.getCellData().setSpread(2, 1);
		addControl(spnTileB, "tileB");

		new LLabel(this, Vocab.instance.TILESIDE, Tooltip.instance.TILESIDE);
		LSpinner spnTileS = new LSpinner(this);
		spnTileS.getCellData().setExpand(true, false);
		spnTileS.getCellData().setSpread(2, 1);
		addControl(spnTileS, "tileS");

		new LLabel(this, Vocab.instance.PIXELHEIGHT, Tooltip.instance.PIXELHEIGHT);
		LSpinner spnPixelsPerHeight = new LSpinner(this);
		spnPixelsPerHeight.getCellData().setExpand(true, false);
		spnPixelsPerHeight.getCellData().setSpread(2, 1);
		addControl(spnPixelsPerHeight, "pixelsPerHeight");

		new LLabel(this, Vocab.instance.DEPTHHEIGHT, Tooltip.instance.DEPTHHEIGHT);
		LSpinner spnDepthPerHeight = new LSpinner(this);
		spnDepthPerHeight.getCellData().setExpand(true, false);
		spnDepthPerHeight.getCellData().setSpread(2, 1);
		addControl(spnDepthPerHeight, "depthPerHeight");

		new LLabel(this, Vocab.instance.DEPTHY, Tooltip.instance.DEPTHY);
		LSpinner spnDepthPerY = new LSpinner(this);
		spnDepthPerY.getCellData().setExpand(true, false);
		spnDepthPerY.getCellData().setSpread(2, 1);
		addControl(spnDepthPerY, "depthPerY");

		LPanel gridOptions = new LPanel(this);
		gridOptions.setSequentialLayout(true);
		gridOptions.getCellData().setAlignment(LFlags.CENTER);
		gridOptions.getCellData().setExpand(true, false);
		gridOptions.getCellData().setSpread(3, 1);

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
