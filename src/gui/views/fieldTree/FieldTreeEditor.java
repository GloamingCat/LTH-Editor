package gui.views.fieldTree;

import java.util.LinkedList;

import gui.helper.FieldHelper;
import gui.helper.TilePainter;
import gui.shell.field.FieldPrefShell;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;

import project.Project;

import com.google.gson.Gson;

import data.field.Field;
import data.field.Field.Prefs;
import data.field.FieldNode;
import lwt.action.LActionStack;
import lwt.dataestructure.LDataTree;
import lwt.dataestructure.LPath;
import lwt.dialog.LObjectShell;
import lwt.dialog.LShellFactory;
import lwt.editor.LTreeEditor;
import lwt.editor.LView;
import lwt.event.LEditEvent;
import lwt.event.LInsertEvent;
import lwt.event.LSelectionEvent;
import lwt.event.listener.LCollectionListener;
import lwt.event.listener.LSelectionListener;
import lwt.widget.LTree;

public class FieldTreeEditor extends LView {

	public static FieldTreeEditor instance;
	protected static Gson gson = new Gson();
	
	public LTree<FieldNode, Field.Prefs> fieldTree;
	
	public FieldTreeEditor(Composite parent, int style) {
		super(parent, style);
		instance = this;
		
		setLayout(new FillLayout());
		
		actionStack = new LActionStack(this);
		
		SashForm sashForm = new SashForm(this, SWT.NONE);
		
		LTreeEditor<FieldNode, Field.Prefs> treeEditor = new LTreeEditor<FieldNode, Field.Prefs>(sashForm, SWT.NONE) {
			@Override
			public LDataTree<FieldNode> getDataCollection() {
				return Project.current.fieldTree.getData();
			}
			@Override
			public FieldNode createNewData() {
				return Project.current.fieldTree.newNode();
			}
			@Override
			public FieldNode duplicateData(FieldNode original) {
				return Project.current.fieldTree.duplicateNode(original);
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
		};
		treeEditor.getCollectionWidget().setInsertNewEnabled(true);
		treeEditor.getCollectionWidget().setEditEnabled(true);
		treeEditor.getCollectionWidget().setDuplicateEnabled(true);
		treeEditor.getCollectionWidget().setDragEnabled(true);
		treeEditor.getCollectionWidget().setDeleteEnabled(true);
		treeEditor.setShellFactory(new LShellFactory<Prefs>() {
			@Override
			public LObjectShell<Prefs> createShell(Shell parent) {
				FieldNode n = treeEditor.getCollectionWidget().getSelectedObject();
				FieldPrefShell shell = new FieldPrefShell(parent);
				int id = Project.current.fieldTree.getData().findNode(n).id;
				shell.setText(String.format("[%03d] ", id) + n.name);
				return shell;
			}
		});
		addChild(treeEditor);
		fieldTree = treeEditor.getCollectionWidget();

		FieldEditor fieldEditor = new FieldEditor(sashForm, SWT.NONE);		
		treeEditor.addChild(fieldEditor);
		
		FieldSideEditor sideEditor = new FieldSideEditor(sashForm, SWT.NONE);
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
		
		sashForm.setWeights(new int[] {9, 29, 12});
	}
	
	public void onVisible() {
		TilePainter.reload();
		FieldHelper.reloadMath();
		super.onVisible();
	}

}
