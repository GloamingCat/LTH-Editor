package gui.shell.database;

import gui.Vocab;
import gui.shell.ObjectShell;

import lwt.dialog.LShell;
import lwt.widget.LLabel;
import lwt.widget.LSpinner;

import data.subcontent.Tile;

public class CharTileShell extends ObjectShell<Tile> {
	
	public CharTileShell(LShell parent) {
		super(parent, 200, 160);
		
		contentEditor.setGridLayout(2, false);

		new LLabel(contentEditor, Vocab.instance.OFFSETX);
		
		LSpinner spnX = new LSpinner(contentEditor);
		spnX.setMaximum(10);
		spnX.setMinimum(-10);
		addControl(spnX, "dx");
		
		new LLabel(contentEditor, Vocab.instance.OFFSETY);
		
		LSpinner spnY = new LSpinner(contentEditor);
		spnY.setMaximum(10);
		spnY.setMinimum(-10);
		addControl(spnY, "dy");
		
		new LLabel(contentEditor, Vocab.instance.HEIGHT);
		
		LSpinner spnHeight = new LSpinner(contentEditor);
		spnHeight.setMaximum(20);
		addControl(spnHeight, "height");

		pack();
	}
	
}
