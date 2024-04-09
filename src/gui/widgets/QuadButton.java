package gui.widgets;

import gui.shell.QuadShell;
import gui.views.database.subcontent.TransformEditor;
import lwt.container.LContainer;
import lwt.container.LImage;
import lwt.dialog.LObjectWindow;
import lwt.dialog.LWindow;
import lwt.dialog.LWindowFactory;
import lwt.widget.LObjectButton;

import java.lang.reflect.Type;

import data.subcontent.Quad;

public class QuadButton extends LObjectButton<Quad> {
	
	private LImage image;
	private TransformEditor transform;
	
	public QuadButton(LContainer parent, boolean optional) {
		super(parent);
		setShellFactory(new LWindowFactory<Quad>() {
			@Override
			public LObjectWindow<Quad> createWindow(LWindow parent) {
				return new QuadShell(parent, optional ? QuadShell.OPTIONAL : 0);
			}
		});
	}

	public void setImage(LImage image) {
		this.image = image;
	}
	
	public void setTransform(TransformEditor transform) {
		this.transform = transform;
	}
	
	@Override
	public void setValue(Object value) {
		if (value != null) {
			setEnabled(true);
			Quad s = (Quad) value;
			if (image != null) {
				if (transform != null) {
					transform.updateColorTransform(image);
				}
				if (s.path.isEmpty()) {
					image.setImage((String) null);
				} else {
					image.setImage(s.fullPath(), s.getRect());
				}
			}
			currentValue = s;
		} else {
			setEnabled(false);
			if (image != null) {
				image.setImage((String) null);
			}
			currentValue = null;
		}
	}

	@Override
	protected Type getType() {
		return Quad.class;
	}

}