package gui.views.dialogue;

import gui.Vocab;
import gui.views.QuadButton;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;

import lwt.editor.LObjectEditor;
import lwt.widget.LText;
import lwt.widget.LTextBox;

public class SpeechEditor extends LObjectEditor {

	public SpeechEditor(Composite parent, int style) {
		super(parent, style);
		setLayout(new GridLayout(3, false));
		
		Label lblCharname = new Label(this, SWT.NONE);
		lblCharname.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblCharname.setText(Vocab.instance.CHARNAME);
		
		LText txtCharName = new LText(this, SWT.NONE);
		txtCharName.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		addControl(txtCharName, "name");
		
		Group grpPortrait = new Group(this, SWT.NONE);
		grpPortrait.setLayout(new GridLayout(1, false));
		grpPortrait.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 2));
		grpPortrait.setText(Vocab.instance.PORTRAIT);
		
		QuadButton btnQuad = new QuadButton(grpPortrait, SWT.NONE);
		btnQuad.setFolder("Portrait");
		btnQuad.optional = true;
		btnQuad.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));
		
		Label lblPortrait = new Label(grpPortrait, SWT.NONE);
		lblPortrait.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		
		btnQuad.setLabel(lblPortrait);
		addControl(btnQuad, "portrait");
		
		Group grpMessage = new Group(this, SWT.NONE);
		grpMessage.setLayout(new FillLayout(SWT.HORIZONTAL));
		grpMessage.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, true, 2, 1));
		grpMessage.setText(Vocab.instance.MESSAGE);
		
		LTextBox txtMessage = new LTextBox(grpMessage, SWT.NONE);
		addControl(txtMessage, "message");
		
	}

	
}
