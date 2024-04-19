package gui.views.database;

import java.lang.reflect.Type;
import java.util.LinkedList;

import gson.GObjectTreeSerializer;
import gui.Tooltip;
import gui.Vocab;
import gui.views.database.subcontent.TagList;
import lui.base.LFlags;
import lui.base.event.listener.LCollectionListener;

import com.google.gson.Gson;

import lui.container.LContainer;
import lui.container.LControlView;
import lui.container.LFrame;
import lui.container.LPanel;
import lui.container.LFlexPanel;
import lui.container.LView;
import lui.base.data.LDataTree;
import lui.editor.LEditor;
import lui.gson.GDefaultObjectEditor;
import lui.gson.GDefaultTreeEditor;
import lui.base.event.LInsertEvent;
import lui.widget.LControlWidget;
import lui.widget.LLabel;
import lui.widget.LText;

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

	protected static final int LABELWIDTH = 80;
	protected static final int TAGSHEIGHT = 140;

	/**
	 * @wbp.parser.constructor
	 * @wbp.eval.method.parameter parent new lwt.dialog.LShell(800, 600)
	 */
	public DatabaseTab(LContainer parent) {
		super(parent, true);
		
		setFillLayout(true);
		
		createMenuInterface();
		
		LFlexPanel sashForm = new LFlexPanel(this, true);
		
		listEditor = new DatabaseTreeEditor(sashForm);
		listEditor.getCollectionWidget().setInsertNewEnabled(true);
		listEditor.getCollectionWidget().setEditEnabled(false);
		listEditor.getCollectionWidget().setDuplicateEnabled(true);
		listEditor.getCollectionWidget().setDragEnabled(true);
		listEditor.getCollectionWidget().setDeleteEnabled(true);
		listEditor.getCollectionWidget().setCopyEnabled(true);
		listEditor.getCollectionWidget().setPasteEnabled(true);
		listEditor.getCollectionWidget().setIncludeID(true);
		super.addChild(listEditor);
		
		contentEditor = new DatabaseContentEditor(sashForm, true);
		contentEditor.setGridLayout(2);
		contentEditor.setEqualCells(true, false);
		
		left = new LPanel(contentEditor);
		left.setGridLayout(1);
		left.getCellData().setExpand(true, true);
		right = new LPanel(contentEditor);
		right.setGridLayout(1);
		right.getCellData().setExpand(true, true);
		
		grpGeneral = new LFrame(left, Vocab.instance.GENERAL);
		grpGeneral.setGridLayout(2);
		grpGeneral.setHoverText(Tooltip.instance.GENERAL);
		grpGeneral.getCellData().setExpand(true, false);
		
		LPanel compID = new LPanel(grpGeneral);
		compID.setGridLayout(3);
		compID.getCellData().setExpand(true, false);
		compID.getCellData().setSpread(2, 1);
		lblID = new LLabel(compID, LFlags.EXPAND, "", Tooltip.instance.ID);
		lblKey = new LLabel(compID, Vocab.instance.KEY, Tooltip.instance.KEY);
		txtKey = new LText(compID);
		txtKey.addModifyListener(event -> {
            LDataTree<Object> node = listEditor.getDataCollection().getNode(listEditor.getCollectionWidget().getSelectedPath());
            listEditor.getDataCollection().setKeyID(event.newValue, node.id);
        });
		txtKey.getCellData().setExpand(true, false);
		contentEditor.addControl(txtKey, "key");
		
		lblName = new LLabel(grpGeneral, Vocab.instance.NAME, Tooltip.instance.NAME);
		lblName.getCellData().setMinimumSize(LABELWIDTH, 0);

		txtName = new LText(grpGeneral);
		txtName.getCellData().setExpand(true, false);
		contentEditor.addControl(txtName, "name");

		// Tags

		grpTags = new LFrame(right, Vocab.instance.TAGS);
		grpTags.setFillLayout(true);
		grpTags.getCellData().setExpand(true, false);
		grpTags.getCellData().setMinimumSize(0, TAGSHEIGHT);
		grpTags.getCellData().setPreferredSize(-1, TAGSHEIGHT);
		TagList lstTags = new TagList(grpTags);
		addChild(lstTags, "tags");
		
		listEditor.addChild(contentEditor);
		listEditor.getCollectionWidget().addSelectionListener(event -> {
            if (event.data != null) {
                LDataTree<Object> node = getSerializer().getTree().getNode(event.path);
                lblID.setText("ID " + node.id);
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
		
		sashForm.setWeights(1, 3);
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

	private class DatabaseTreeEditor extends GDefaultTreeEditor<Object> {
		public DatabaseTreeEditor(LContainer parent) {
			super(parent);
		}
		@Override
		public LDataTree<Object> getDataCollection() {
			return getSerializer().getTree();
		}
		@Override
		public Class<?> getType() {
			return (Class<?>) getSerializer().getDataType();
		}
		@Override
		public void setChecked(Object c, boolean checked) {}
	}
	
	private class DatabaseContentEditor extends GDefaultObjectEditor<T> {
		public DatabaseContentEditor(LContainer parent, boolean doubleBuffered) {
			super(parent, doubleBuffered);
		}
		@Override
		public Type getType() {
			return getSerializer().getDataType();
		}
	}

}
