package gui.widgets;

import gui.shell.IDShell;
import lwt.dataestructure.LDataTree;
import lwt.dialog.LObjectShell;
import lwt.dialog.LShellFactory;
import lwt.widget.LImage;
import lwt.widget.LObjectButton;
import lwt.widget.LText;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;

import project.Project;
import data.Animation;

public abstract class IDButton extends LObjectButton<Integer> {
	
	protected LText txtName;
	protected LImage image;

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public IDButton(Composite parent, int style) {
		super(parent, SWT.NONE);
		setShellFactory(new LShellFactory<Integer>() {
			@Override
			public LObjectShell<Integer> createShell(Shell parent) {
				IDShell shell = new IDShell(parent, style) {
					protected LDataTree<Object> getTree() { 
						return getDataTree(); 
					}
				};
				return shell;
			}
		});
	}
	
	public abstract LDataTree<Object> getDataTree();
	
	public void setNameWidget(LText text) {
		txtName = text;
	}
	
	public void setImage(LImage img) {
		image = img;
	}

	@Override
	public void setValue(Object value) {
		if (value != null) {
			button.setEnabled(true);
			Integer id = (Integer) value;
			if (txtName != null) {
				if (id < 0)
					txtName.setValue("");
				else {
					Object obj = getDataTree().get(id);
					if (obj != null)
						txtName.setValue(obj.toString());
					else 
						txtName.setValue("");
				}
			}
			if (image != null) {
				Animation anim = (Animation) Project.current.animations.getTree().get(id);
				if (anim == null)
					image.setImage((Image) null); 
				else {
					image.setImage(anim.quad.getImage(), anim.quad.getRectangle());
				}
			}
			currentValue = id;
		} else {
			button.setEnabled(false);
			if (txtName != null) {
				txtName.setValue("");
			}
			currentValue = null;
		}
	}

}