package gui.views.dialogue;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;

import project.Project;
import data.Node;
import lwt.action.LActionStack;
import lwt.dataestructure.LDataTree;
import lwt.dataestructure.LPath;
import lwt.editor.LDefaultTreeEditor;
import lwt.editor.LView;
import lwt.event.LSelectionEvent;
import lwt.event.listener.LSelectionListener;

public class DialogueTreeEditor extends LView {

	protected LDefaultTreeEditor<Node> treeEditor;
	protected DialogueEditor dialogueEditor;
	
	public DialogueTreeEditor(Composite parent, int style) {
		super(parent, style);
		setLayout(new FillLayout());
		
		actionStack = new LActionStack(this);
		
		SashForm sashForm = new SashForm(this, SWT.NONE);
		
		treeEditor = new LDefaultTreeEditor<Node>(sashForm, SWT.NONE) {
			@Override
			public LDataTree<Node> getDataCollection() {
				return Project.current.dialogueTree.getData().root;
			}
			@Override
			public Node createNewData() {
				return Project.current.dialogueTree.newNode();
			}
			@Override
			public Node duplicateData(Node original) {
				return Project.current.dialogueTree.duplicateNode(original);
			}
			@Override
			public void forceFirstSelection() {
				if (getDataCollection() != null) {
					LDataTree<Node> tree = getDataCollection();
					getCollectionWidget().setItems(tree);
					LPath lastPath = Project.current.dialogueTree.getData().last;
					getCollectionWidget().forceSelection(lastPath);
				} else {
					getCollectionWidget().setItems(null);
					getCollectionWidget().forceSelection(null);
				}
			}
		};
		treeEditor.getCollectionWidget().setInsertNewEnabled(true);
		treeEditor.getCollectionWidget().setEditEnabled(false);
		treeEditor.getCollectionWidget().setDuplicateEnabled(true);
		treeEditor.getCollectionWidget().setDragEnabled(true);
		treeEditor.getCollectionWidget().setDeleteEnabled(true);
		treeEditor.getCollectionWidget().addSelectionListener(new LSelectionListener() {
			@Override
			public void onSelect(LSelectionEvent event) {
				Project.current.dialogueTree.getData().last = event.path;
			}
		});
		addChild(treeEditor);
		
		dialogueEditor = new DialogueEditor(sashForm, SWT.NONE);
		treeEditor.addChild(dialogueEditor);
		treeEditor.setActionStack(getActionStack());
		
		sashForm.setWeights(new int[] {1, 2});
	}

}
