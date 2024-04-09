package gui.shell;

import gui.Tooltip;
import gui.Vocab;
import lbase.LFlags;
import data.subcontent.Tag;
import lwt.dialog.LWindow;
import lwt.widget.LLabel;
import lwt.widget.LText;
import lwt.widget.LTextBox;

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
