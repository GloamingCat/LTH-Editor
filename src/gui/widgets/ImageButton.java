package gui.widgets;

import lwt.container.LContainer;
import lwt.container.LImage;
import lwt.dataestructure.LDataTree;
import data.Animation;
import gui.Vocab;
import project.Project;

public class ImageButton extends IDButton {
	
	protected LImage label;
	
	public ImageButton(LContainer parent, boolean optional) {
		super(parent, Vocab.instance.ANIMSHELL, optional);
	}
	
	public void setLabel(LImage label) {
		this.label = label;
	}
	
	@Override
	public void setValue(Object value) {
		if (value != null) {
			setEnabled(true);
			int id = (int) value;
			if (label != null) {
				if (id >= 0) {
					Animation anim = (Animation) getDataTree().get(id);
					label.setImage(anim.quad.fullPath(), anim.quad.getRect());
				} else {
					label.setImage((String) null);
				}
			}
		} else {
			setEnabled(false);
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