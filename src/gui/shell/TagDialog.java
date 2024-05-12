package gui.shell;

import gui.Tooltip;
import gui.Vocab;
import lui.base.LFlags;
import data.subcontent.Tag;
import lui.dialog.LWindow;
import lui.gson.GObjectDialog;
import lui.widget.LLabel;
import lui.widget.LText;
import lui.widget.LTextBox;

public class TagDialog extends GObjectDialog<Tag> {

    public TagDialog(LWindow parent, String title) {
		super(parent, 400, 200, title);
	}
	
	@Override
	protected void createContent(int style) {
		super.createContent(style);
		contentEditor.setGridLayout(2);
		
		new LLabel(contentEditor, Vocab.instance.NAME, Tooltip.instance.KEY);
        LText txtKey = new LText(contentEditor);
		txtKey.getCellData().setExpand(true, false);
		addControl(txtKey, "key");
		
		new LLabel(contentEditor, LFlags.TOP, Vocab.instance.VALUE, Tooltip.instance.JSON);
        LTextBox txtValue = new LTextBox(contentEditor);
		txtValue.getCellData().setExpand(true, true);
		addControl(txtValue, "value");
	}
	
}
