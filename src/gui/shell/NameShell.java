package gui.shell;

import gui.Vocab;

import lwt.dialog.LObjectShell;
import lwt.dialog.LShell;
import lwt.widget.LLabel;
import lwt.widget.LText;

import org.eclipse.swt.layout.GridLayout;

public class NameShell extends LObjectShell<String> {
	
	private LText txtName;

	public NameShell(LShell parent) {
		super(parent);
		setMinimumSize(300, 120);
		
		content.setLayout(new GridLayout(2, false));
		
		new LLabel(content, Vocab.instance.NAME);
		
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
