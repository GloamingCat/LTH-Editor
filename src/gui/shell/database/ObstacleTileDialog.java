package gui.shell.database;

import gui.Tooltip;
import gui.Vocab;
import lui.gson.GObjectDialog;
import gui.views.database.subcontent.NeighborEditor;

import lui.container.LFrame;
import lui.dialog.LWindow;
import lui.widget.LCombo;
import lui.widget.LLabel;
import lui.widget.LSpinner;
import data.Obstacle.ObstacleTile;

public class ObstacleTileDialog extends GObjectDialog<ObstacleTile> {

    public ObstacleTileDialog(LWindow parent) {
		super(parent, 400, 240, Vocab.instance.OBSTACLESHELL);
	}
	
	@Override
	protected void createContent(int style) {
		super.createContent(style);

		contentEditor.setGridLayout(2);
		
		LFrame grpGeneral = new LFrame(contentEditor, Vocab.instance.GENERAL);
		grpGeneral.setGridLayout(2);
		grpGeneral.setHoverText(Tooltip.instance.GENERAL);
		grpGeneral.getCellData().setExpand(true, true);

		new LLabel(grpGeneral, Vocab.instance.OFFSETX, Tooltip.instance.TILEX);
        LSpinner spnX = new LSpinner(grpGeneral);
		spnX.getCellData().setExpand(true, false);
		spnX.setMaximum(100);
		spnX.setMinimum(-100);
		addControl(spnX, "dx");
		
		new LLabel(grpGeneral, Vocab.instance.OFFSETY, Tooltip.instance.TILEY);
        LSpinner spnY = new LSpinner(grpGeneral);
		spnY.getCellData().setExpand(true, false);
		spnY.setMaximum(100);
		spnY.setMinimum(-100);
		addControl(spnY, "dy");
		
		new LLabel(grpGeneral, Vocab.instance.HEIGHT, Tooltip.instance.TILEH);
        LSpinner spnHeight = new LSpinner(grpGeneral);
		spnHeight.getCellData().setExpand(true, false);
		spnHeight.setMaximum(100);
		spnHeight.setMinimum(0);
		addControl(spnX, "height");
		
		new LLabel(grpGeneral, Vocab.instance.MODE, Tooltip.instance.MODE);
        LCombo cmbMode = new LCombo(grpGeneral, LCombo.READONLY);
		cmbMode.getCellData().setExpand(true, false);
		cmbMode.setItems(new String[] {
			Vocab.instance.BLOCK,
			Vocab.instance.RAMP,
			Vocab.instance.BRIDGE
		});
		addControl(cmbMode, "mode");

        NeighborEditor neighborEditor = new NeighborEditor(contentEditor);
		neighborEditor.getCellData().setExpand(true, true);
		addChild(neighborEditor, "neighbors");

	}
	
}
