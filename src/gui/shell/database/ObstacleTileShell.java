package gui.shell.database;

import gui.Tooltip;
import gui.Vocab;
import gui.shell.ObjectShell;
import gui.views.database.subcontent.NeighborEditor;

import lui.container.LFrame;
import lui.dialog.LWindow;
import lui.widget.LCombo;
import lui.widget.LLabel;
import lui.widget.LSpinner;
import data.Obstacle.ObstacleTile;

public class ObstacleTileShell extends ObjectShell<ObstacleTile> {
	
	private LSpinner spnX;
	private LSpinner spnY;
	private LSpinner spnHeight;
	private LCombo cmbMode;
	private NeighborEditor neighborEditor;
	
	public ObstacleTileShell(LWindow parent) {
		super(parent, Vocab.instance.OBSTACLESHELL);
	}
	
	@Override
	protected void createContent(int style) {
		super.createContent(style);

		contentEditor.setGridLayout(2);
		
		LFrame grpGeneral = new LFrame(contentEditor, Vocab.instance.GENERAL);
		grpGeneral.setGridLayout(2);
		grpGeneral.setHoverText(Tooltip.instance.GENERAL);
		grpGeneral.getCellData().setExpand(true, false);

		new LLabel(grpGeneral, Vocab.instance.OFFSETX, Tooltip.instance.TILEX);
		
		spnX = new LSpinner(grpGeneral);
		spnX.setMaximum(100);
		spnX.setMinimum(-100);
		addControl(spnX, "dx");
		
		new LLabel(grpGeneral, Vocab.instance.OFFSETY, Tooltip.instance.TILEY);
		
		spnY = new LSpinner(grpGeneral);
		spnY.setMaximum(100);
		spnY.setMinimum(-100);
		addControl(spnY, "dy");
		
		new LLabel(grpGeneral, Vocab.instance.HEIGHT, Tooltip.instance.TILEH);
		
		spnHeight = new LSpinner(grpGeneral);
		spnHeight.setMaximum(100);
		spnHeight.setMinimum(0);
		addControl(spnX, "height");
		
		new LLabel(grpGeneral, Vocab.instance.MODE, Tooltip.instance.MODE);
		
		cmbMode = new LCombo(grpGeneral, true);
		cmbMode.setItems(new String[] {
			Vocab.instance.BLOCK,
			Vocab.instance.RAMP,
			Vocab.instance.BRIDGE
		});
		addControl(cmbMode, "mode");
		
		neighborEditor = new NeighborEditor(contentEditor);
		neighborEditor.getCellData().setExpand(true, true);
		addChild(neighborEditor, "neighbors");
		
		pack();
	}
	
}
