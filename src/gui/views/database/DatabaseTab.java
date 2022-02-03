package gui.views.database;

import java.lang.reflect.Type;
import java.util.LinkedList;

import gson.editor.GDefaultTreeEditor;
import gson.project.GObjectTreeSerializer;
import gui.Vocab;
import gui.widgets.ImageButton;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;

import com.google.gson.Gson;

import lwt.dataestructure.LDataTree;
import lwt.editor.LControlView;
import lwt.editor.LEditor;
import lwt.editor.LObjectEditor;
import lwt.editor.LView;
import lwt.event.LInsertEvent;
import lwt.event.LSelectionEvent;
import lwt.event.listener.LCollectionListener;
import lwt.event.listener.LSelectionListener;
import lwt.widget.LControl;
import lwt.widget.LImage;
import lwt.widget.LLabel;
import lwt.widget.LText;

public abstract class DatabaseTab extends LView {

	protected static Gson gson = new Gson();
	
	protected GDefaultTreeEditor<Object> listEditor;
	protected LObjectEditor contentEditor;
	protected Group grpGeneral;
	protected LLabel lblName;
	protected LText txtName;
	protected LLabel lblID;
	
	public DatabaseTab(Composite parent) {
		super(parent);
		
		setLayout(new FillLayout());
		
		createActionStack();
		
		SashForm sashForm = new SashForm(this, SWT.NONE);
		
		listEditor = new GDefaultTreeEditor<Object>(sashForm, SWT.NONE) {
			@Override
			public LDataTree<Object> getDataCollection() {
				return getSerializer().getTree();
			}
			@Override
			public Type getType() {
				return getSerializer().getDataType();
			}
		};
		
		listEditor.getCollectionWidget().setInsertNewEnabled(true);
		listEditor.getCollectionWidget().setEditEnabled(false);
		listEditor.getCollectionWidget().setDuplicateEnabled(true);
		listEditor.getCollectionWidget().setDragEnabled(true);
		listEditor.getCollectionWidget().setDeleteEnabled(true);
		listEditor.getCollectionWidget().setCopyEnabled(true);
		listEditor.getCollectionWidget().setPasteEnabled(true);
		listEditor.getCollectionWidget().setIncludeID(true);
		super.addChild(listEditor);
		
		GridLayout gridLayout = new GridLayout(2, false);
		gridLayout.verticalSpacing = 0;
		contentEditor = new LObjectEditor(sashForm, SWT.NONE);
		contentEditor.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		contentEditor.setLayout(gridLayout);
		
		grpGeneral = new Group(contentEditor, SWT.NONE);
		grpGeneral.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
		grpGeneral.setLayout(new GridLayout(2, false));
		grpGeneral.setText(Vocab.instance.GENERAL);
		
		lblID = new LLabel(grpGeneral, "");
		lblID.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		
		lblName = new LLabel(grpGeneral, Vocab.instance.NAME);
		
		txtName = new LText(grpGeneral);
		txtName.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		contentEditor.addControl(txtName, "name");
		
		listEditor.addChild(contentEditor);
		listEditor.getCollectionWidget().addSelectionListener(new LSelectionListener() {
			@Override
			public void onSelect(LSelectionEvent event) {
				if (event.data != null) {
					LDataTree<Object> node = getSerializer().getTree().getNode(event.path);
					lblID.setText("ID " + node.id);
				}
			}
		});
		listEditor.getCollectionWidget().addInsertListener(new LCollectionListener<Object>() {
			@Override
			public void onInsert(LInsertEvent<Object> event) {
				LinkedList<LDataTree<Object>> nodes = new LinkedList <>();
				nodes.add(event.node);
				while (!nodes.isEmpty()) {
					int id = listEditor.getDataCollection().findID();
					nodes.peek().initID(id);
					for (LDataTree<Object> child : nodes.poll().children) {
						nodes.add(child);
					}
				}
				lblID.setText("ID " + event.node.id);
			}
		});
		
		sashForm.setWeights(new int[] {1, 3});
	}
	
	protected abstract GObjectTreeSerializer getSerializer();
	
	public void addChild(LEditor editor) {
		contentEditor.addChild(editor);
	}
	
	public void addChild(LEditor editor, String key) {
		contentEditor.addChild(editor, key);
	}
	
	protected void addControl(LControl<?> control, String attName) {
		contentEditor.addControl(control, attName);
	}
	
	protected void addControl(LControlView<?> view, String attName) {
		contentEditor.addControl(view, attName);
	}
	
	protected void addImageButton(ImageButton button, LImage label, LText text, String attName) {
		button.setLabel(label);
		button.setNameWidget(text);
		addControl(button, attName);
	}

}
