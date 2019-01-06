package gui.shell;

import gui.Vocab;

import org.eclipse.swt.widgets.Shell;

import data.subcontent.Tag;
import lwt.widget.LText;
import lwt.widget.LTextBox;

import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.GridData;

public class TagShell extends ObjectShell<Tag> {
	
	private LText txtKey;
	private LTextBox txtValue;

	public TagShell(Shell parent) {
		super(parent);
		contentEditor.setLayout(new GridLayout(2, false));
		
		Label lblKey = new Label(contentEditor, SWT.NONE);
		lblKey.setText(Vocab.instance.NAME);
		
		txtKey = new LText(contentEditor, SWT.NONE);
		txtKey.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		addControl(txtKey, "key");
		
		Label lblValue = new Label(contentEditor, SWT.NONE);
		lblValue.setLayoutData(new GridData(SWT.LEFT, SWT.FILL, false, false, 1, 1));
		lblValue.setText(Vocab.instance.VALUE);
		
		txtValue = new LTextBox(contentEditor, SWT.NONE);
		GridData gd_txtValue = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		gd_txtValue.widthHint = 170;
		gd_txtValue.heightHint = 75;
		txtValue.setLayoutData(gd_txtValue);
		addControl(txtValue, "value");
		
		pack();
	}
	
}
