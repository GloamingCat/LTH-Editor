package gui.widgets;

import lwt.dataestructure.LDataTree;
import lwt.widget.LImage;
import lwt.widget.LText;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;

import data.Animation;
import project.Project;

public class ImageButton extends IDButton {
	
	protected LImage label;
	
	public ImageButton(Composite parent, int style) {
		super(parent, style);
	}

	public void setLabel(LImage label) {
		this.label = label;
	}
	
	@Override
	public void setValue(Object value) {
		if (value != null) {
			button.setEnabled(true);
			int id = (int) value;
			if (label != null) {
				if (id >= 0) {
					Animation anim = (Animation) getDataTree().get(id);
					label.setImage(anim.getImage());
				} else {
					label.setImage((Image) null);
				}
			}
		} else {
			button.setEnabled(false);
			if (label != null) {
				label.setImage((Image) null);
			}
		}
		super.setValue(value);
	}

	@Override
	public LDataTree<Object> getDataTree() {
		return Project.current.animations.getTree();
	}
	
}