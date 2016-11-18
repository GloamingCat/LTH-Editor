package gui.views.database;

import gui.Vocab;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;

import project.ListSerializer;

import com.google.gson.Gson;

import lwt.action.LActionStack;
import lwt.dataestructure.LDataList;
import lwt.editor.LDefaultListEditor;
import lwt.editor.LObjectEditor;
import lwt.editor.LView;
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
			public LDataList<Object> getList() {
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
			@Override
			public void onVisible() {
				super.onVisible();
				collection.refreshSelection();
			}
		};
		listEditor.setInsertNewEnabled(true);
		listEditor.setEditEnabled(false);
		listEditor.setDuplicateEnabled(true);
		listEditor.setDragEnabled(true);
		listEditor.setDeleteEnabled(true);
		listEditor.setIncludeID(true);
		addChild(listEditor);
		
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

}
