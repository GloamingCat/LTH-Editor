package gui.views.gamegui;

import gui.shell.GUIShell;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;

import project.Project;
import data.GUI;
import lwt.action.LActionStack;
import lwt.dataestructure.LDataTree;
import lwt.dataestructure.LPath;
import lwt.dialog.LObjectShell;
import lwt.dialog.LShellFactory;
import lwt.editor.LTreeEditor;
import lwt.editor.LView;
import lwt.event.LSelectionEvent;
import lwt.event.listener.LSelectionListener;

public class GUITreeEditor extends LView {

	protected LTreeEditor<GUI, GUI.Prefs> treeEditor;
	
	public GUITreeEditor(Composite parent, int style) {
		super(parent, style);
		setLayout(new FillLayout());
		
		actionStack = new LActionStack(this);
		
		SashForm sashForm = new SashForm(this, SWT.NONE);
		
		treeEditor = new LTreeEditor<GUI, GUI.Prefs>(sashForm, SWT.NONE) {
			@Override
			public LDataTree<GUI> getDataCollection() {
				return Project.current.gameGUIs.getTree();
			}
			@Override
			public GUI createNewData() {
				return Project.current.gameGUIs.newGUI();
			}
			@Override
			public GUI duplicateData(GUI original) {
				return Project.current.gameGUIs.duplicateGUI(original);
			}
			@Override
			protected GUI.Prefs getEditableData(LPath path) {
				return Project.current.gameGUIs.getTree().getNode(path).data.prefs;
			}
			@Override
			protected void setEditableData(LPath path, GUI.Prefs newData) {
				Project.current.gameGUIs.getTree().getNode(path).data.prefs = newData;
			}
			@Override
			public void forceFirstSelection() {
				if (getDataCollection() != null) {
					LDataTree<GUI> tree = getDataCollection();
					getCollectionWidget().setItems(tree);
					LPath lastPath = Project.current.gameGUIs.getLast();
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
		treeEditor.setShellFactory(new LShellFactory<GUI.Prefs>() {
			@Override
			public LObjectShell<GUI.Prefs> createShell(Shell parent) {
				GUI n = treeEditor.getCollectionWidget().getSelectedObject();
				LObjectShell<GUI.Prefs> shell = new GUIShell(parent);
				shell.setText(n.prefs.name);
				return shell;
			}
		});
		treeEditor.getCollectionWidget().addSelectionListener(new LSelectionListener() {
			@Override
			public void onSelect(LSelectionEvent event) {
				Project.current.gameGUIs.setLast(event.path);
			}
		});
		addChild(treeEditor);
		
		GUIEditor contentEditor = new GUIEditor(sashForm, SWT.NONE);
		
		treeEditor.addChild(contentEditor);
		treeEditor.setActionStack(getActionStack());
		
		sashForm.setWeights(new int[] {1, 2});
	}

}
