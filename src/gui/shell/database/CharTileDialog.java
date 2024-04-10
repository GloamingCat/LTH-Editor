package gui.shell.database;

import gui.Tooltip;
import gui.Vocab;
import gui.shell.ObjectEditorDialog;

import lui.dialog.LWindow;
import lui.widget.LLabel;
import lui.widget.LSpinner;

import data.subcontent.Tile;

public class CharTileDialog extends ObjectEditorDialog<Tile> {
	
	public CharTileDialog(LWindow parent) {
		super(parent, Vocab.instance.CHARTILESHELL);
		setMinimumSize(200, 160);
	}
	
	@Override
	protected void createContent(int style) {
		super.createContent(style);		
		contentEditor.setGridLayout(2);

		new LLabel(contentEditor, Vocab.instance.OFFSETX, Tooltip.instance.TILEX);
		
		LSpinner spnX = new LSpinner(contentEditor);
		spnX.setMaximum(10);
		spnX.setMinimum(-10);
		addControl(spnX, "dx");
		
		new LLabel(contentEditor, Vocab.instance.OFFSETY, Tooltip.instance.TILEY);
		
		LSpinner spnY = new LSpinner(contentEditor);
		spnY.setMaximum(10);
		spnY.setMinimum(-10);
		addControl(spnY, "dy");
		
		new LLabel(contentEditor, Vocab.instance.HEIGHT, Tooltip.instance.TILEH);
		
		LSpinner spnHeight = new LSpinner(contentEditor);
		spnHeight.setMaximum(20);
		addControl(spnHeight, "height");

		pack();
	}
	
}
