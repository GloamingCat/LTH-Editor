package gui.views.database;

import java.lang.reflect.Type;
import java.util.LinkedList;

import gson.editor.GDefaultTreeEditor;
import gson.project.GObjectTreeSerializer;
import gui.Vocab;
import gui.widgets.ImageButton;
import gui.widgets.QuadButton;
import gui.widgets.ScriptButton;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Text;

import com.google.gson.Gson;

import lwt.action.LActionStack;
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
import lwt.widget.LText;

import org.eclipse.swt.widgets.Label;

public abstract class DatabaseTab extends LView {

	protected static Gson gson = new Gson();
	
	protected GDefaultTreeEditor<Object> listEditor;
	protected LObjectEditor contentEditor;
	protected Group grpGeneral;
	protected Label lblName;
	protected LText txtName;
	protected Label lblID;
	
	public DatabaseTab(Composite parent, int style) {
		super(parent, style);
		
		setLayout(new FillLayout());
		
		actionStack = new LActionStack(this);
		
		SashForm sashForm = new SashForm(this, SWT.NONE);
		
		listEditor = new GDefaultTreeEditor<Object>(sashForm, SWT.NONE) {
			@Override
			public LDataTree<Object> getDataCollection() {
				return getSerializer().getTree();
			}
			@Override
			public Type getType() {
				System.out.println(getSerializer().getDataType());
				return getSerializer().getDataType();
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
		
		lblID = new Label(grpGeneral, SWT.NONE);
		lblID.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		
		lblName = new Label(grpGeneral, SWT.NONE);
		lblName.setText(Vocab.instance.NAME);
		
		txtName = new LText(grpGeneral, SWT.NONE);
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
	
	protected void addImageButton(ImageButton button, LImage label, Text text, String attName) {
		button.setLabel(label);
		button.setNameText(text);
		addControl(button, attName);
	}
	
	protected void addQuadButton(QuadButton button, LImage image, String folderName, String attName) {
		button.setImage(image);
		button.setFolder(folderName);
		addControl(button, attName);
	}
	
	protected void addScriptButton(ScriptButton button, Text pathText, String folderName, String attName) {
		button.setPathText(pathText);
		button.setFolder(folderName);
		addControl(button, attName);
	}

}
