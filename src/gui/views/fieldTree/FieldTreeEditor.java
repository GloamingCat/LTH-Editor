package gui.views.fieldTree;

import java.util.LinkedList;

import gui.helper.FieldHelper;
import gui.helper.TilePainter;
import gui.shell.field.FieldPrefShell;

import project.Project;

import com.google.gson.Gson;

import data.field.Field;
import data.field.Field.Prefs;
import data.field.FieldNode;
import lwt.LGlobals;
import lwt.container.LContainer;
import lwt.container.LSashPanel;
import lwt.container.LView;
import lwt.dataestructure.LDataTree;
import lwt.dataestructure.LPath;
import lwt.dialog.LObjectShell;
import lwt.dialog.LShell;
import lwt.dialog.LShellFactory;
import lwt.editor.LTreeEditor;
import lwt.event.LEditEvent;
import lwt.event.LInsertEvent;
import lwt.event.LSelectionEvent;
import lwt.event.listener.LCollectionListener;
import lwt.event.listener.LSelectionListener;
import lwt.widget.LTree;

public class FieldTreeEditor extends LView {

	protected static class FieldTree extends LTreeEditor<FieldNode, Prefs> {
		
		private FieldTree(LContainer parent) {
			super(parent);
		}

		@Override
		public LDataTree<FieldNode> getDataCollection() {
			return Project.current.fieldTree.getData();
		}

		@Override
		public FieldNode createNewElement() {
			return Project.current.fieldTree.newNode();
		}

		@Override
		public FieldNode duplicateElement(FieldNode original) {
			return Project.current.fieldTree.duplicateNode(original);
		}
		
		@Override
		protected String encodeElement(FieldNode data) {
			Field field = Project.current.fieldTree.loadField(data);
			return LGlobals.gson.toJson(field);
		}

		@Override
		protected FieldNode decodeElement(String str) {
			Field field = LGlobals.gson.fromJson(str, Field.class);
			return Project.current.fieldTree.addField(field);
		}

		@Override
		public boolean canDecode(String str) {
			return true;
		}
		
		@Override
		protected Prefs getEditableData(LPath path) {
			return Project.current.fieldTree.loadField(path).prefs;
		}

		@Override
		protected void setEditableData(LPath path, Prefs newData) {
			Project.current.fieldTree.loadField(path).prefs = newData;
		}

		@Override
		public void forceFirstSelection() {
			if (getDataCollection() != null) {
				LDataTree<FieldNode> tree = getDataCollection().toTree();
				getCollectionWidget().setItems(tree);
				int lastField = Project.current.fieldTree.getData().lastField;
				LDataTree<FieldNode> lastNode = Project.current.fieldTree.getData().findNode(lastField);
				getCollectionWidget().forceSelection(lastNode == null ? null : lastNode.toPath());
			} else {
				getCollectionWidget().setItems(null);
				getCollectionWidget().forceSelection(null);
			}
		}

	}

	public static FieldTreeEditor instance;
	protected static Gson gson = new Gson();
	
	public LTree<FieldNode, Field.Prefs> fieldTree;

	/**
	 * @wbp.parser.constructor
	 * @wbp.eval.method.parameter parent new lwt.dialog.LShell()
	 */
	public FieldTreeEditor(LContainer parent) {
		super(parent, true, true);
		FieldTreeEditor.instance = this;
		
		createMenuInterface();
		
		LSashPanel sashForm = new LSashPanel(this, true);
		
		LTreeEditor<FieldNode, Field.Prefs> treeEditor = new FieldTree(sashForm);
		treeEditor.getCollectionWidget().setInsertNewEnabled(true);
		treeEditor.getCollectionWidget().setEditEnabled(true);
		treeEditor.getCollectionWidget().setDuplicateEnabled(true);
		treeEditor.getCollectionWidget().setDragEnabled(true);
		treeEditor.getCollectionWidget().setDeleteEnabled(true);
		treeEditor.getCollectionWidget().setCopyEnabled(true);
		treeEditor.getCollectionWidget().setPasteEnabled(true);
		treeEditor.setShellFactory(new LShellFactory<Prefs>() {
			@Override
			public LObjectShell<Prefs> createShell(LShell parent) {
				FieldNode n = treeEditor.getCollectionWidget().getSelectedObject();
				FieldPrefShell shell = new FieldPrefShell(parent);
				int id = Project.current.fieldTree.getData().findNode(n).id;
				shell.setTitle(String.format("[%03d] ", id) + n.name);
				return shell;
			}
		});
		addChild(treeEditor);
		fieldTree = treeEditor.getCollectionWidget();

		FieldEditor fieldEditor = new FieldEditor(sashForm);		
		treeEditor.addChild(fieldEditor);
		
		FieldSideEditor sideEditor = new FieldSideEditor(sashForm);
		fieldEditor.addChild(sideEditor);
		
		treeEditor.getCollectionWidget().addSelectionListener(new LSelectionListener() {
			@Override
			public void onSelect(LSelectionEvent event) {
				Project.current.fieldTree.getData().lastField = event.id;
				System.gc();
			}
		});
		treeEditor.getCollectionWidget().addEditListener(new LCollectionListener<Prefs>() {
			public void onEdit(LEditEvent<Prefs> e) {
				LDataTree<FieldNode> node = Project.current.fieldTree.getData().getNode(e.path);
				node.data.name = e.newData.name;
				treeEditor.getCollectionWidget().refreshObject(e.path);
				fieldEditor.canvas.redraw();
			}
		});
		treeEditor.getCollectionWidget().addInsertListener(new LCollectionListener<FieldNode>() {
			@Override
			public void onInsert(LInsertEvent<FieldNode> event) {
				LinkedList<LDataTree<FieldNode>> nodes = new LinkedList <>();
				nodes.add(event.node);
				while (!nodes.isEmpty()) {
					int id = treeEditor.getDataCollection().findID();
					nodes.peek().initID(id);
					for (LDataTree<FieldNode> child : nodes.poll().children) {
						nodes.add(child);
					}
				}
			}
		});
		
		sashForm.setWeights(new int[] {1, 3, 1});
	}
	
	public void onVisible() {
		TilePainter.reload();
		FieldHelper.reloadMath();
		super.onVisible();
	}

}
