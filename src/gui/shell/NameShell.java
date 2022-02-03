package gui.shell;

import gui.Vocab;

import lwt.dialog.LObjectShell;
import lwt.widget.LLabel;
import lwt.widget.LText;

import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;

public class NameShell extends LObjectShell<String> {
	
	private LText txtName;

	public NameShell(Shell parent) {
		super(parent);
		setMinimumSize(new Point(300, 120));
		
		content.setLayout(new GridLayout(2, false));
		
		new LLabel(content, Vocab.instance.NAME);
		
		txtName = new LText(content);
		txtName.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
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
