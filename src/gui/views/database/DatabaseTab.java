package gui.views.database;

import java.util.LinkedList;

import gson.editor.GDefaultObjectEditor;
import gson.editor.GDefaultTreeEditor;
import gson.project.GObjectTreeSerializer;
import gui.Vocab;
import gui.views.database.subcontent.TagList;
import gui.widgets.ImageButton;

import com.google.gson.Gson;

import lwt.LFlags;
import lwt.container.LContainer;
import lwt.container.LControlView;
import lwt.container.LFrame;
import lwt.container.LPanel;
import lwt.container.LSashPanel;
import lwt.container.LView;
import lwt.dataestructure.LDataTree;
import lwt.editor.LEditor;
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
	protected LFrame grpGeneral, grpTags;
	protected LLabel lblID;
	protected LLabel lblKey;
	protected LText txtKey;
	protected LLabel lblName;
	protected LText txtName;
	protected LPanel left, right;
	
	public DatabaseTab(LContainer parent) {
		super(parent, true);
		
		setFillLayout(true);
		
		createActionStack();
		
		LSashPanel sashForm = new LSashPanel(this, true);
		
		listEditor = new GDefaultTreeEditor<Object>(sashForm) {
			@Override
			public LDataTree<Object> getDataCollection() {
				return getSerializer().getTree();
			}
			@Override
			public Class<?> getType() {
				return (Class<?>) getSerializer().getDataType();
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
		
		contentEditor = new GDefaultObjectEditor<T>(sashForm, true);
		contentEditor.setGridLayout(2, true);
		
		left = new LPanel(contentEditor, 1);
		left.setExpand(true, true);
		right = new LPanel(contentEditor, 1);
		right.setExpand(true, true);
		
		grpGeneral = new LFrame(left, Vocab.instance.GENERAL, 2, false);
		grpGeneral.setExpand(true, false);
		
		LPanel compID = new LPanel(grpGeneral, 3, false);
		compID.setExpand(true, false);
		compID.setSpread(2, 1);
		lblID = new LLabel(compID, LFlags.EXPAND, "");
		lblKey = new LLabel(compID, Vocab.instance.KEY);
		txtKey = new LText(compID);
		txtKey.addModifyListener(new LControlListener<String>() {
			@Override
			public void onModify(LControlEvent<String> event) {
				LDataTree<Object> node = listEditor.getDataCollection().getNode(listEditor.getCollectionWidget().getSelectedPath());
				listEditor.getDataCollection().setKeyID(event.newValue, node.id);
			}
			
		});
		contentEditor.addControl(txtKey, "key");
		
		lblName = new LLabel(grpGeneral, Vocab.instance.NAME);
		lblName.setMinimumWidth(72);
		
		txtName = new LText(grpGeneral);
		contentEditor.addControl(txtName, "name");
		
		// Tags
		
		grpTags = new LFrame(right, Vocab.instance.TAGS, true, true);
		grpTags.setExpand(true, false);
		grpTags.setMinimumHeight(140);
		TagList lstTags = new TagList(grpTags);
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
