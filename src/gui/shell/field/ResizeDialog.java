package gui.shell.field;

import gui.Tooltip;
import gui.Vocab;
import lui.gson.GObjectDialog;
import lui.dialog.LWindow;
import lui.graphics.LRect;
import lui.widget.LCombo;
import lui.widget.LLabel;
import lui.widget.LSpinner;

public class ResizeDialog extends GObjectDialog<LRect> {

	public ResizeDialog(LWindow parent) {
		super(parent, 250, 0, Vocab.instance.RESIZESHELL);
	}
	
	@Override
	protected void createContent(int style) {
		super.createContent(style);
		contentEditor.setGridLayout(2);
		
		new LLabel(contentEditor, Vocab.instance.SIZEX, Tooltip.instance.SIZEX);
		
		LSpinner spnW = new LSpinner(contentEditor);
		spnW.getCellData().setExpand(true, false);
		addControl(spnW, "width");
		
		new LLabel(contentEditor, Vocab.instance.SIZEY, Tooltip.instance.SIZEY);
		
		LSpinner spnH = new LSpinner(contentEditor);
		spnH.getCellData().setExpand(true, false);
		addControl(spnH, "height");
		
		new LLabel(contentEditor, Vocab.instance.ALIGNX, Tooltip.instance.ALIGNX);
		
		LCombo cmbX = new LCombo(contentEditor, LCombo.READONLY);
		cmbX.getCellData().setExpand(true, false);
		cmbX.setItems(new String[] { Vocab.instance.LEFT, Vocab.instance.CENTER, Vocab.instance.RIGHT });
		addControl(cmbX, "x");
		
		new LLabel(contentEditor, Vocab.instance.ALIGNY, Tooltip.instance.ALIGNY);
		
		LCombo cmbY = new LCombo(contentEditor, LCombo.READONLY);
		cmbY.getCellData().setExpand(true, false);
		cmbY.setItems(new String[] { Vocab.instance.TOP, Vocab.instance.CENTER, Vocab.instance.BOTTOM });
		addControl(cmbY, "y");

	}

	@Override
	public LRect createResult(LRect initial) {
		LRect rect = super.createResult(initial);
		if (rect == null || rect.height == initial.height && rect.width == initial.width)
			return null;
		// X anchor
		if (rect.x == 1) {
			rect.x = (initial.width - rect.width) / 2;
		} else if (rect.x == 2) {
			rect.x = initial.width - rect.width;
		}
		// Y anchor
		if (rect.y == 1) {
			rect.y = (initial.height - rect.height) / 2;
		} else if (rect.y == 2) {
			rect.y = initial.height - rect.height;
		}
		return rect;
	}

}
