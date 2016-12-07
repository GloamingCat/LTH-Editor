package gui.views.database;

import gui.Vocab;
import gui.views.common.ImageButton;
import gui.views.common.ScriptButton;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Text;

import project.ListSerializer;

import com.google.gson.Gson;

import lwt.action.LActionStack;
import lwt.dataestructure.LDataList;
import lwt.editor.LControlView;
import lwt.editor.LDefaultListEditor;
import lwt.editor.LEditor;
import lwt.editor.LObjectEditor;
import lwt.editor.LView;
import lwt.widget.LControl;
import lwt.widget.LText;

import org.eclipse.swt.widgets.Label;

public abstract class DatabaseTab extends LView {

	protected static Gson gson = new Gson();
	
	protected LDefaultListEditor<Object> listEditor;
	protected LObjectEditor contentEditor;
	protected Group grpGeneral;
	protected Label lblName;
	protected LText txtName;
	
	public DatabaseTab(Composite parent, int style) {
		super(parent, style);
		
		setLayout(new FillLayout());
		
		actionStack = new LActionStack(this);
		
		SashForm sashForm = new SashForm(this, SWT.NONE);
		
		listEditor = new LDefaultListEditor<Object>(sashForm, SWT.NONE) {
			@Override
			public LDataList<Object> getDataCollection() {
				return getSerializer().getList();
			}
			@Override
			public Object createNewData() {
				return gson.fromJson("{}", getSerializer().getType());
			}
			@Override
			public Object duplicateData(Object original) {
				String json = gson.toJson(original, getSerializer().getType());
				return gson.fromJson(json, getSerializer().getType());
			}
		};
		listEditor.getCollectionWidget().setInsertNewEnabled(true);
		listEditor.getCollectionWidget().setEditEnabled(false);
		listEditor.getCollectionWidget().setDuplicateEnabled(true);
		listEditor.getCollectionWidget().setDragEnabled(true);
		listEditor.getCollectionWidget().setDeleteEnabled(true);
		listEditor.getCollectionWidget().setIncludeID(true);
		super.addChild(listEditor);
		
		contentEditor = new LObjectEditor(sashForm, SWT.NONE);
		contentEditor.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		contentEditor.setLayout(new GridLayout(1, false));
		
		grpGeneral = new Group(contentEditor, SWT.NONE);
		grpGeneral.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
		grpGeneral.setLayout(new GridLayout(2, false));
		grpGeneral.setText(Vocab.instance.GENERAL);
		
		lblName = new Label(grpGeneral, SWT.NONE);
		lblName.setText(Vocab.instance.NAME);
		
		txtName = new LText(grpGeneral, SWT.NONE);
		txtName.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		contentEditor.addControl(txtName, "name");
		
		listEditor.addChild(contentEditor);
		
		sashForm.setWeights(new int[] {1, 2});
	}
	
	protected abstract ListSerializer getSerializer();
	
	public void addChild(LEditor editor) {
		contentEditor.addChild(editor);
	}
	
	protected void addControl(LControl control, String attName) {
		contentEditor.addControl(control, attName);
	}
	
	protected void addControl(LControlView view, String attName) {
		contentEditor.addControl(view, attName);
	}
	
	protected void addImageButton(ImageButton button, Label label, String folderName, String attName) {
		button.setLabel(label);
		button.setFolder(folderName);
		addControl(button, attName);
	}
	
	protected void addScriptButton(ScriptButton button, Text pathText, StyledText paramText, String folderName, String attName) {
		button.setPathText(pathText);
		button.setParamText(paramText);
		button.setFolder(folderName);
		addControl(button, attName);
	}

}
