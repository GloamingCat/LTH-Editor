package gui.shell;

import gui.Vocab;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import data.subcontent.Node;
import lwt.dataestructure.LDataTree;
import lwt.dataestructure.LPath;
import lwt.dialog.LObjectShell;
import lwt.editor.LDefaultTreeEditor;

import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.GridData;

public abstract class NodeShell extends LObjectShell<Node> {
	
	private LDefaultTreeEditor<Object> tree;
	private Text txtName;

	public NodeShell(Shell parent) {
		super(parent);
		GridData gridData = (GridData) content.getLayoutData();
		gridData.verticalAlignment = SWT.FILL;
		gridData.grabExcessVerticalSpace = true;
		setMinimumSize(new Point(400, 300));
		
		content.setLayout(new GridLayout(1, false));
		
		Composite name = new Composite(content, SWT.NONE);
		name.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		GridLayout gl_name = new GridLayout(2, false);
		gl_name.marginWidth = 0;
		gl_name.marginHeight = 0;
		name.setLayout(gl_name);
		
		Label lblName = new Label(name, SWT.NONE);
		lblName.setText(Vocab.instance.NAME);
		
		txtName = new Text(name, SWT.BORDER);
		txtName.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		
		tree = new LDefaultTreeEditor<Object>(content, 0) {
			@Override
			public LDataTree<Object> getDataCollection() { return getTree(); }
			@Override
			protected Object createNewData() { return null; }
			@Override
			protected Object duplicateData(Object original) { return null; }
		};
		tree.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		tree.getCollectionWidget().dragEnabled = false;
		
		pack();
	}
	
	public void open(Node initial) {
		super.open(initial);
		tree.onVisible();
		tree.getCollectionWidget().select(null);
		if (initial.id >= 0) {
			LDataTree<Object> node = getTree().findNode(initial.id);
			if (node != null) {
				tree.getCollectionWidget().select(node.toPath());
			}
		}
		txtName.setText(initial.name);
	}

	@Override
	protected Node createResult(Node initial) {
		LPath path = tree.getCollectionWidget().getSelectedPath();
		int id = path == null ? -1 : getTree().getNode(path).id;
		
		if (id == initial.id && txtName.getText().equals(initial.name)) {
			return null;
		} else {
			Node node = new Node();
			node.id = id;
			node.name = txtName.getText();
			return node;
		}
	}
	
	protected abstract LDataTree<Object> getTree();
	
}
