package gui.shell;

import gui.Vocab;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;

import data.subcontent.Node;
import lwt.dataestructure.LDataTree;
import lwt.widget.LNodeSelector;
import lwt.widget.LText;

import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.GridData;

public abstract class NodeShell extends ObjectShell<Node> {

	public NodeShell(Shell parent) {
		super(parent);
		GridData gridData = (GridData) content.getLayoutData();
		gridData.verticalAlignment = SWT.FILL;
		gridData.grabExcessVerticalSpace = true;
		setMinimumSize(new Point(400, 400));
		
		contentEditor.setLayout(new GridLayout(1, false));
		
		Composite name = new Composite(contentEditor, SWT.NONE);
		name.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		GridLayout gl_name = new GridLayout(2, false);
		gl_name.marginWidth = 0;
		gl_name.marginHeight = 0;
		name.setLayout(gl_name);
		
		Label lblName = new Label(name, SWT.NONE);
		lblName.setText(Vocab.instance.NAME);
		
		LText txtName = new LText(name, SWT.NONE);
		txtName.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		addControl(txtName, "name");
		
		LNodeSelector<Object> tree = new LNodeSelector<Object>(contentEditor, SWT.NONE);
		tree.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		tree.setCollection(getTree());
		addControl(tree, "id");
		
		pack();
	}
	
	protected abstract LDataTree<Object> getTree();
	
}
