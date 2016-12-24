package gui.views.dialog;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;

import project.Project;
import data.DialogNode;
import lwt.action.LActionStack;
import lwt.dataestructure.LDataTree;
import lwt.dataestructure.LPath;
import lwt.editor.LDefaultTreeEditor;
import lwt.editor.LView;
import lwt.event.LSelectionEvent;
import lwt.event.listener.LSelectionListener;

public class DialogTreeEditor extends LView {

	protected LDefaultTreeEditor<DialogNode> treeEditor;
	
	public DialogTreeEditor(Composite parent, int style) {
		super(parent, style);
		setLayout(new FillLayout());
		
		actionStack = new LActionStack(this);
		
		SashForm sashForm = new SashForm(this, SWT.NONE);
		
		treeEditor = new LDefaultTreeEditor<DialogNode>(sashForm, SWT.NONE) {
			@Override
			public LDataTree<DialogNode> getDataCollection() {
				return Project.current.dialogs.getTree();
			}
			@Override
			public DialogNode createNewData() {
				return Project.current.dialogs.newNode();
			}
			@Override
			public DialogNode duplicateData(DialogNode original) {
				return Project.current.dialogs.duplicateNode(original);
			}
			@Override
			public void forceFirstSelection() {
				if (getDataCollection() != null) {
					LDataTree<DialogNode> tree = getDataCollection();
					getCollectionWidget().setItems(tree);
					LPath lastPath = Project.current.dialogs.getLast();
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
				Project.current.dialogs.setLast(event.path);
			}
		});
		addChild(treeEditor);
		
		DialogEditor contentEditor = new DialogEditor(sashForm, SWT.NONE);
		
		treeEditor.addChild(contentEditor);
		treeEditor.setActionStack(getActionStack());
		
		sashForm.setWeights(new int[] {1, 2});
	}

}
