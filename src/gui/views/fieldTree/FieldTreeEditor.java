package gui.views.fieldTree;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;

import project.Project;

import com.google.gson.Gson;

import data.Field;
import data.Field.Prefs;
import data.Layer;
import data.Node;
import lwt.action.LActionStack;
import lwt.dataestructure.LDataTree;
import lwt.dataestructure.LPath;
import lwt.editor.LTreeEditor;
import lwt.editor.LView;

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
		};
		treeEditor.getCollectionWidget().setInsertNewEnabled(true);
		treeEditor.getCollectionWidget().setEditEnabled(false);
		treeEditor.getCollectionWidget().setDuplicateEnabled(true);
		treeEditor.getCollectionWidget().setDragEnabled(true);
		treeEditor.getCollectionWidget().setDeleteEnabled(true);
		addChild(treeEditor);
		
		fieldEditor = new FieldEditor(sashForm, SWT.NONE);
		fieldEditor.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		
		sideEditor = new FieldLayerEditor(sashForm, SWT.NONE) {
			public void selectLayer(Layer layer) {
				fieldEditor.selectLayer(layer);
				super.selectLayer(layer);
			}
		};

		treeEditor.addChild(fieldEditor);
		fieldEditor.addChild(sideEditor);
		
		sashForm.setWeights(new int[] {1, 3, 1});
	}

}
