package gui.views.fieldTree;

import gui.shell.FieldShell;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;

import project.Project;

import com.google.gson.Gson;

import data.Field;
import data.Field.Prefs;
import data.Node;
import lwt.action.LActionStack;
import lwt.dataestructure.LDataTree;
import lwt.dataestructure.LPath;
import lwt.dialog.LObjectShell;
import lwt.dialog.LShellFactory;
import lwt.editor.LTreeEditor;
import lwt.editor.LView;
import lwt.event.LEditEvent;
import lwt.event.LSelectionEvent;
import lwt.event.listener.LCollectionListener;
import lwt.event.listener.LSelectionListener;

public class FieldTreeEditor extends LView {

	protected static Gson gson = new Gson();
	
	protected LTreeEditor<Node, Field.Prefs> treeEditor;
	protected FieldEditor fieldEditor;
	protected FieldLayerEditor sideEditor;
	
	public FieldTreeEditor(Composite parent, int style) {
		super(parent, style);
		
		setLayout(new FillLayout());
		
		actionStack = new LActionStack(this);
		
		SashForm sashForm = new SashForm(this, SWT.NONE);
		
		treeEditor = new LTreeEditor<Node, Field.Prefs>(sashForm, SWT.NONE) {
			@Override
			public LDataTree<Node> getDataCollection() {
				return Project.current.fieldTree.getData().root;
			}
			@Override
			public Node createNewData() {
				return Project.current.fieldTree.newNode();
			}
			@Override
			public Node duplicateData(Node original) {
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
					LDataTree<Node> tree = getDataCollection().toTree();
					getCollectionWidget().setItems(tree);
					LPath lastPath = Project.current.fieldTree.getData().lastField;
					getCollectionWidget().forceSelection(lastPath);
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
				return new FieldShell(parent);
			}
		});
		treeEditor.getCollectionWidget().addSelectionListener(new LSelectionListener() {
			@Override
			public void onSelect(LSelectionEvent event) {
				Project.current.fieldTree.getData().lastField = event.path;
			}
		});
		treeEditor.getCollectionWidget().addEditListener(new LCollectionListener<Prefs>() {
			public void onEdit(LEditEvent<Prefs> e) {
				LDataTree<Node> node = Project.current.fieldTree.getData().root.getNode(e.path);
				node.data.name = e.newData.name;
				treeEditor.getCollectionWidget().refreshObject(e.path);
			}
		});
		addChild(treeEditor);
		
		fieldEditor = new FieldEditor(sashForm, SWT.NONE);
		fieldEditor.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		
		sideEditor = new FieldLayerEditor(sashForm, SWT.NONE);
		sideEditor.setFieldEditor(fieldEditor);

		treeEditor.addChild(fieldEditor);
		fieldEditor.addChild(sideEditor);
		
		treeEditor.setActionStack(getActionStack());
		
		sashForm.setWeights(new int[] {1, 3, 1});
	}

}
