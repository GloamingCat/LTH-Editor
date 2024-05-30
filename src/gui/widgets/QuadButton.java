package gui.widgets;

import data.subcontent.Transform;
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
	private Transform[] transforms = new Transform[0];
	
	public QuadButton(LContainer parent, boolean optional) {
		super(parent);
		setShellFactory(new LWindowFactory<>() {
			@Override
			public LObjectDialog<Quad> createWindow(LWindow parent) {
				return new QuadDialog(parent, optional ? QuadDialog.OPTIONAL : 0);
			}
		});
	}

	public void setImageWidget(LImage image) {
		this.image = image;
	}
	
	public void setTransforms(Transform[] transforms) {
		this.transforms = transforms;
		if (image != null)
			applyTransforms();
	}
	
	@Override
	public void setValue(Object value) {
		if (value != null) {
			setEnabled(true);
			Quad s = (Quad) value;
			if (image != null) {
				if (s.path.isEmpty()) {
					image.setImage((String) null);
				} else {
					image.setImage(s.fullPath(), s);
				}
				applyTransforms();
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

	private void applyTransforms() {
		image.resetTransform();
		for (Transform t : transforms)
			t.applyTo(image);
		image.repaint();
	}

	@Override
	protected Type getType() {
		return Quad.class;
	}

}