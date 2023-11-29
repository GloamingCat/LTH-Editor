package gui.widgets;

import lwt.container.LContainer;
import lwt.dataestructure.LDataTree;
import lwt.widget.LImage;

import data.Animation;
import project.Project;

public class ImageButton extends IDButton {
	
	protected LImage label;
	
	public ImageButton(LContainer parent, boolean optional) {
		super(parent, optional);
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
					label.setImage(anim.quad.fullPath(), anim.quad.getRectangle());
				} else {
					label.setImage((String) null);
				}
			}
		} else {
			button.setEnabled(false);
			if (label != null) {
				label.setImage((String) null);
			}
		}
		super.setValue(value);
	}

	@Override
	public LDataTree<Object> getDataTree() {
		return Project.current.animations.getTree();
	}
	
}