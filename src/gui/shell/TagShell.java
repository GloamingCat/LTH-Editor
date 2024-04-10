package gui.shell;

import gui.Tooltip;
import gui.Vocab;
import lui.base.LFlags;
import data.subcontent.Tag;
import lui.dialog.LWindow;
import lui.widget.LLabel;
import lui.widget.LText;
import lui.widget.LTextBox;

public class TagShell extends ObjectShell<Tag> {
	
	private LText txtKey;
	private LTextBox txtValue;

	public TagShell(LWindow parent) {
		super(parent, Vocab.instance.TAGSHELL);
		setMinimumSize(400, 300);
	}
	
	@Override
	protected void createContent(int style) {
		super.createContent(style);

		contentEditor.setGridLayout(2);
		
		new LLabel(contentEditor, Vocab.instance.NAME, Tooltip.instance.KEY);
		
		txtKey = new LText(contentEditor);
		addControl(txtKey, "key");
		
		new LLabel(contentEditor, LFlags.TOP, Vocab.instance.VALUE, Tooltip.instance.JSON);
		
		txtValue = new LTextBox(contentEditor);
		txtValue.getCellData().setMinimumSize(170, 75);
		addControl(txtValue, "value");
		
		pack();
	}
	
}
