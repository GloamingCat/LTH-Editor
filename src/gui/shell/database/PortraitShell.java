package gui.shell.database;

import gui.Vocab;
import lwt.dataestructure.LDataTree;
import lwt.dataestructure.LPath;
import lwt.dialog.LObjectShell;
import lwt.editor.LDefaultTreeEditor;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Shell;

import data.GameCharacter.Portrait;

import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.layout.GridData;

import project.Project;

import org.eclipse.swt.graphics.Point;

public class PortraitShell extends LObjectShell<Portrait> {
	
	private LDefaultTreeEditor<Object> tree;
	private Text txtName;
	private Spinner spnCol;
	private Spinner spnRow;
	
	public PortraitShell(Shell parent) {
		super(parent);
		GridData gridData = (GridData) content.getLayoutData();
		gridData.verticalAlignment = SWT.FILL;
		gridData.grabExcessVerticalSpace = true;
		setMinimumSize(new Point(400, 300));

		content.setLayout(new GridLayout(4, false));
		
		Label lblName = new Label(content, SWT.NONE);
		lblName.setText(Vocab.instance.NAME);
		
		txtName = new Text(content, SWT.BORDER);
		txtName.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 3, 1));
		
		Label lblCol = new Label(content, SWT.NONE);
		lblCol.setText(Vocab.instance.COLUMN);
		
		spnCol = new Spinner(content, SWT.BORDER);
		spnCol.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label lblRow = new Label(content, SWT.NONE);
		lblRow.setText(Vocab.instance.ROW);
		
		spnRow = new Spinner(content, SWT.BORDER);
		spnRow.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		tree = new LDefaultTreeEditor<Object>(content, 0) {
			@Override
			public LDataTree<Object> getDataCollection() { 
				return Project.current.animations.getTree(); }
			@Override
			protected Object createNewData() { return null; }
			@Override
			protected Object duplicateData(Object original) { return null; }
		};
		tree.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 4, 1));
		
		pack();
	}
	
	public void open(Portrait initial) {
		super.open(initial);
		tree.onVisible();
		tree.getCollectionWidget().select(null);
		if (initial.id >= 0) {
			LDataTree<Object> node = Project.current.animations.
					getTree().findNode(initial.id);
			if (node != null) {
				tree.getCollectionWidget().select(node.toPath());
			}
		}
		txtName.setText(initial.name);
		spnCol.setSelection(initial.col);
		spnRow.setSelection(initial.row);
	}
	
	@Override
	protected Portrait createResult(Portrait initial) {
		int id = -1;
		LPath path = tree.getCollectionWidget().getSelectedPath();
		if (path == null)
			id = initial.id;
		else
			id = Project.current.animations.getTree().getNode(path).id;
		Portrait p = new Portrait();
		p.id = id;
		p.col = spnCol.getSelection();
		p.row = spnRow.getSelection();
		p.name = txtName.getText();
		return p;
	}
	
}
