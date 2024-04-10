package gui.widgets;

import gui.shell.QuadDialog;
import gui.views.database.subcontent.TransformEditor;
import lui.container.LContainer;
import lui.container.LImage;
import lui.dialog.LObjectDialog;
import lui.dialog.LWindow;
import lui.dialog.LWindowFactory;
import lui.widget.LObjectButton;

import java.lang.reflect.Type;

import data.subcontent.Quad;

public class QuadButton extends LObjectButton<Quad> {
	
	private LImage image;
	private TransformEditor transform;
	
	public QuadButton(LContainer parent, boolean optional) {
		super(parent);
		setShellFactory(new LWindowFactory<Quad>() {
			@Override
			public LObjectDialog<Quad> createWindow(LWindow parent) {
				return new QuadDialog(parent, optional ? QuadDialog.OPTIONAL : 0);
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