package gui.shell;

import org.eclipse.swt.widgets.Shell;

import lwt.dataestructure.LDataTree;

import org.eclipse.swt.layout.GridLayout;

import project.Project;

public class ImageShell extends IDShell {
	
	public ImageShell(Shell parent) {
		super(parent);
		
		content.setLayout(new GridLayout(2, true));		
		//LImage image = new LImage(content, SWT.NONE);

		pack();
	}
	
	protected LDataTree<Object> getTree() { 
		return Project.current.animations.getTree(); 
	}
	
}
