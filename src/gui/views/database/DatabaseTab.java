package gui.views.database;

import java.lang.reflect.Type;
import java.util.LinkedList;

import gson.editor.GDefaultObjectEditor;
import gson.editor.GDefaultTreeEditor;
import gson.project.GObjectTreeSerializer;
import gui.Vocab;
import gui.views.database.subcontent.TagList;
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
import lwt.editor.LView;
import lwt.event.LControlEvent;
import lwt.event.LInsertEvent;
import lwt.event.LSelectionEvent;
import lwt.event.listener.LCollectionListener;
import lwt.event.listener.LControlListener;
import lwt.event.listener.LSelectionListener;
import lwt.widget.LControlWidget;
import lwt.widget.LImage;
import lwt.widget.LLabel;
import lwt.widget.LText;

public abstract class DatabaseTab<T> extends LView {

	protected static Gson gson = new Gson();
	
	protected GDefaultTreeEditor<Object> listEditor;
	protected GDefaultObjectEditor<T> contentEditor;
	protected Group grpGeneral, grpTags;
	protected LLabel lblID;
	protected LLabel lblKey;
	protected LText txtKey;
	protected LLabel lblName;
	protected LText txtName;
	protected Composite left, right;
	
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
		
		contentEditor = new GDefaultObjectEditor<T>(sashForm, SWT.NONE);
		contentEditor.setLayout(new GridLayout(2, true));
		
		left = new Composite(contentEditor, SWT.NONE);
		left.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		GridLayout gl_left = new GridLayout();
		gl_left.marginWidth = 0;
		gl_left.marginHeight = 0;
		left.setLayout(gl_left);
		right = new Composite(contentEditor, SWT.NONE);
		right.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		GridLayout gl_right = new GridLayout();
		gl_right.marginWidth = 0;
		gl_right.marginHeight = 0;
		right.setLayout(gl_right);
		
		grpGeneral = new Group(left, SWT.NONE);
		grpGeneral.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
		GridLayout gl_general = new GridLayout(2, false);
		grpGeneral.setLayout(gl_general);
		grpGeneral.setText(Vocab.instance.GENERAL);
		
		Composite compID = new Composite(grpGeneral, SWT.NONE);
		GridLayout gl_compID = new GridLayout(3, false);
		gl_compID.marginWidth = 0;
		gl_compID.marginHeight = 0;
		compID.setLayout(gl_compID);
		compID.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		
		lblID = new LLabel(compID, "");
		lblID.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		lblKey = new LLabel(compID, Vocab.instance.KEY);
		
		txtKey = new LText(compID);
		txtKey.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		txtKey.addModifyListener(new LControlListener<String>() {
			@Override
			public void onModify(LControlEvent<String> event) {
				LDataTree<Object> node = listEditor.getDataCollection().getNode(listEditor.getCollectionWidget().getSelectedPath());
				listEditor.getDataCollection().setKeyID(event.newValue, node.id);
			}
			
		});
		contentEditor.addControl(txtKey, "key");
		
		lblName = new LLabel(grpGeneral, Vocab.instance.NAME);
		GridData gd_name = new GridData(SWT.LEFT, SWT.CENTER, false, false);
		gd_name.minimumWidth = 72;
		gd_name.widthHint = 72;
		lblName.setLayoutData(gd_name);
		
		txtName = new LText(grpGeneral);
		txtName.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		contentEditor.addControl(txtName, "name");
		
		// Tags
		
		grpTags = new Group(right, SWT.NONE);
		GridData gd_tags = new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1);
		gd_tags.minimumHeight = 140;
		gd_tags.heightHint = 140;
		grpTags.setLayoutData(gd_tags);
		FillLayout fl_tags = new FillLayout(SWT.HORIZONTAL);
		fl_tags.marginWidth = gl_general.marginWidth;
		fl_tags.marginHeight = gl_general.marginHeight;
		grpTags.setLayout(fl_tags);
		grpTags.setText(Vocab.instance.TAGS);
		
		TagList lstTags = new TagList(grpTags, SWT.NONE);
		addChild(lstTags, "tags");
		
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
	
	protected void addControl(LControlWidget<?> control, String attName) {
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
