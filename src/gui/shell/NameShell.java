package gui.shell;

import gui.Tooltip;
import gui.Vocab;

import lwt.dialog.LObjectShell;
import lwt.dialog.LShell;
import lwt.widget.LLabel;
import lwt.widget.LText;

public class NameShell extends LObjectShell<String> {
	
	private LText txtName;

	public NameShell(LShell parent, String title) {
		super(parent, title);
		setMinimumSize(300, 120);
	}
	
	@Override
	protected void createContent(int style) {
		super.createContent(style);
		content.setGridLayout(2);
		new LLabel(content, Vocab.instance.NAME, Tooltip.instance.NAME);
		txtName = new LText(content);
		pack();
	}
	
	public void open(String initial) {
		super.open(initial);
		txtName.setValue(initial);
	}

	@Override
	protected String createResult(String initial) {
		return txtName.getValue();
	}

}
