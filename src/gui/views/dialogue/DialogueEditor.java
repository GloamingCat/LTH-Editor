package gui.views.dialogue;

import gui.Vocab;
import gui.views.SimpleEditableList;
import gui.views.database.subcontent.TagList;

import org.eclipse.swt.widgets.Composite;

import lwt.editor.LObjectEditor;
import lwt.widget.LText;

import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Group;

import data.Dialogue;
import data.Node;
import data.Dialogue.Speech;

import org.eclipse.swt.layout.FillLayout;

import project.Project;

public class DialogueEditor extends LObjectEditor {
	
	private LText txtName;

	public DialogueEditor(Composite parent, int style) {
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
		SashForm sashForm2 = new SashForm(sashForm, SWT.HORIZONTAL);
		
		SimpleEditableList<Speech> lstSpeeches = new SimpleEditableList<>(sashForm2, SWT.NONE);
		lstSpeeches.getCollectionWidget().setEditEnabled(false);
		lstSpeeches.setIncludeID(true);
		lstSpeeches.type = Speech.class;
		lstSpeeches.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		addChild(lstSpeeches, "speeches");
		
		Group grpTags = new Group(sashForm2, SWT.NONE);
		grpTags.setLayout(new FillLayout(SWT.HORIZONTAL));
		grpTags.setText(Vocab.instance.TAGS);
		
		TagList lstTags = new TagList(grpTags, SWT.NONE);
		
		SpeechEditor speechEditor = new SpeechEditor(sashForm, SWT.NONE);
		speechEditor.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		lstSpeeches.addChild(speechEditor);
		speechEditor.addChild(lstTags);
		
		speechEditor.setObject(null);
		sashForm.setWeights(new int[] {1, 2});
	}
	
	@Override
	public void setObject(Object obj) {
		if (obj != null) {
			Node node = (Node) obj;
			Dialogue d = Project.current.dialogueTree.loadData(node);
			super.setObject(d);
		} else {
			super.setObject(null);
		}
	}
	
}
