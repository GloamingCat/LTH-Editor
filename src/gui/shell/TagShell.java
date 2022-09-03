package gui.shell;

import gui.Vocab;

import org.eclipse.swt.widgets.Shell;

import data.subcontent.Tag;
import lwt.widget.LLabel;
import lwt.widget.LText;
import lwt.widget.LTextBox;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.GridData;

public class TagShell extends ObjectShell<Tag> {
	
	private LText txtKey;
	private LTextBox txtValue;

	public TagShell(Shell parent) {
		super(parent);
		setMinimumSize(400, 300);
		contentEditor.setLayout(new GridLayout(2, false));
		
		new LLabel(contentEditor, Vocab.instance.NAME);
		
		txtKey = new LText(contentEditor);
		txtKey.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		addControl(txtKey, "key");
		
		new LLabel(contentEditor, Vocab.instance.VALUE).setLayoutData(new GridData(SWT.LEFT, SWT.FILL, false, false, 1, 1));
		
		txtValue = new LTextBox(contentEditor);
		GridData gd_txtValue = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		gd_txtValue.widthHint = 170;
		gd_txtValue.heightHint = 75;
		txtValue.setLayoutData(gd_txtValue);
		addControl(txtValue, "value");
		
		pack();
	}
	
}
