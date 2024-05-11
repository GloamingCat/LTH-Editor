package gui.views.database;

import java.util.LinkedList;

import gson.GObjectTreeSerializer;
import lui.base.LPrefs;
import lui.base.event.listener.LCollectionListener;

import com.google.gson.Gson;

import lui.container.LContainer;
import lui.container.LControlView;
import lui.container.LFlexPanel;
import lui.container.LView;
import lui.base.data.LDataTree;
import lui.editor.LEditor;
import lui.gson.GDefaultTreeEditor;
import lui.base.event.LInsertEvent;
import lui.widget.LControlWidget;

public abstract class DatabaseTab<T> extends LView {

	protected static Gson gson = new Gson();
	
	protected GDefaultTreeEditor<Object> listEditor;
	protected DatabaseContentEditor<T> contentEditor;

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
		super.addChild(listEditor);
		
		contentEditor = new DatabaseContentEditor<>(sashForm, this, true);
		contentEditor.setGridLayout(2);
		contentEditor.setMargins(LPrefs.FRAMEMARGIN, LPrefs.FRAMEMARGIN);
		contentEditor.setEqualCells(true, false);

		contentEditor.txtKey.addModifyListener(event -> {
			LDataTree<Object> node = listEditor.getDataCollection().getNode(listEditor.getCollectionWidget().getSelectedPath());
			listEditor.getDataCollection().setKeyID(event.newValue, node.id);
		});

		listEditor.addChild(contentEditor);
		listEditor.getCollectionWidget().addSelectionListener(event -> {
            if (event.data != null) {
                LDataTree<Object> node = getSerializer().getTree().getNode(event.path);
                contentEditor.lblID.setText("ID " + node.id);
            }
        });
		listEditor.getCollectionWidget().addInsertListener(new LCollectionListener<Object>() {
			@Override
			public void onInsert(LInsertEvent<Object> event) {
				LinkedList<LDataTree<Object>> nodes = new LinkedList <>();
				nodes.add(event.node);
				while (nodes.peek() != null) {
					int id = listEditor.getDataCollection().findID();
					nodes.peek().initID(id);
					for (LDataTree<Object> child : nodes.poll().children) {
						nodes.add(child);
					}
				}
				contentEditor.lblID.setText("ID " + event.node.id);
			}
		});

		createContent();
		
		sashForm.setWeights(1, 3);
	}

	protected abstract void createContent();
	
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

}
