package gui.widgets;

import gui.shell.IDShell;
import lui.container.LContainer;
import lui.container.LImage;
import lui.base.data.LDataTree;
import lui.dialog.LObjectWindow;
import lui.dialog.LWindow;
import lui.dialog.LWindowFactory;
import lui.widget.LObjectButton;
import lui.widget.LText;

import project.Project;

import java.lang.reflect.Type;

import data.Animation;

public class IDButton extends LObjectButton<Integer> {
	
	public LDataTree<Object> dataTree = null;
	protected LText txtName;
	protected LImage image;

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public IDButton(LContainer parent, String title, boolean optional) {
		super(parent);
		setShellFactory(new LWindowFactory<Integer>() {
			@Override
			public LObjectWindow<Integer> createWindow(LWindow parent) {
				IDShell shell = new IDShell(parent, title, optional ? IDShell.OPTIONAL : 0) {
					protected LDataTree<Object> getTree() { 
						return getDataTree(); 
					}
				};
				return shell;
			}
		});
	}
	
	public LDataTree<Object> getDataTree() { return dataTree; }
	
	public void setNameWidget(LText text) {
		txtName = text;
	}
	
	public void setImage(LImage img) {
		image = img;
	}

	@Override
	public void setValue(Object value) {
		if (value != null) {
			setEnabled(true);
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
					image.setImage((String) null); 
				else {
					image.setImage(anim.quad.fullPath(), anim.quad.getRect());
				}
			}
			currentValue = id;
		} else {
			setEnabled(false);
			if (txtName != null) {
				txtName.setValue("");
			}
			currentValue = null;
		}
	}

	@Override
	protected Type getType() {
		return Integer.class;
	}

}