package gui.views.dialog;

import gui.Vocab;
import gui.views.common.SimpleEditableList;

import org.eclipse.swt.widgets.Composite;

import lwt.editor.LObjectEditor;
import lwt.widget.LText;

import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Group;

import data.Dialog.Speech;

import org.eclipse.swt.layout.FillLayout;

public class DialogEditor extends LObjectEditor {
	
	private LText txtName;

	public DialogEditor(Composite parent, int style) {
		super(parent, style);
		setLayout(new GridLayout(2, false));
		
		Label lblName = new Label(this, SWT.NONE);
		lblName.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblName.setText(Vocab.instance.NAME);
		
		txtName = new LText(this, SWT.NONE);
		txtName.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		addControl(txtName, "name");
		
		Group grpSpeeches = new Group(this, SWT.NONE);
		grpSpeeches.setLayout(new FillLayout(SWT.HORIZONTAL));
		grpSpeeches.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, true, 2, 1));
		grpSpeeches.setText(Vocab.instance.SPEECHES);
		
		SashForm sashForm = new SashForm(grpSpeeches, SWT.VERTICAL);

		SimpleEditableList<Speech> lstSpeeches = new SimpleEditableList<>(sashForm, SWT.NONE);
		lstSpeeches.getCollectionWidget().setEditEnabled(false);
		lstSpeeches.setIncludeID(true);
		lstSpeeches.type = Speech.class;
		lstSpeeches.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		addChild(lstSpeeches, "speeches");
		
		SpeechEditor speechEditor = new SpeechEditor(sashForm, SWT.NONE);
		speechEditor.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		lstSpeeches.addChild(speechEditor);
		
		speechEditor.setObject(null);
		sashForm.setWeights(new int[] {1, 2});
	}
	
}
