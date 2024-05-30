package gui.widgets;

import data.subcontent.Transform;
import gui.shell.IconDialog;
import lui.container.LContainer;
import lui.container.LImage;
import lui.dialog.LWindow;
import lui.dialog.LWindowFactory;
import lui.widget.LObjectButton;
import lui.widget.LText;

import java.lang.reflect.Type;

import data.Animation;
import data.subcontent.Icon;

public class IconButton extends LObjectButton<Icon> {
	
	private LImage image;
	private LText text;
	private Transform[] transforms = new Transform[0];
	
	public IconButton(LContainer parent, boolean optional) {
		super(parent);
		setShellFactory(new LWindowFactory<>() {
			@Override
			public IconDialog createWindow(LWindow parent) {
				return new IconDialog(parent, IconDialog.OPTIONAL);
			}
		});
	}

	public void setImageWidget(LImage image) {
		this.image = image;
	}
	
	public void setTransforms(Transform[] transforms) {
		this.transforms = transforms;
		if (image != null && getValue() != null)
			applyTransforms(getValue());
	}

	@Override
	public void setValue(Object value) {
		if (value != null) {
			setEnabled(true);
			Icon i = (Icon) value;
			if (image != null) {
				Animation anim = i.getAnimation();
				if (anim != null && !anim.quad.path.isEmpty())
					image.setImage(anim.quad.fullPath(), i.getRectangle());
				else
					image.setImage((String) null);
				applyTransforms(i);
			}
			currentValue = i;
		} else {
			setEnabled(false);
			if (image != null) {
				image.setImage((String) null);
			}
			if (text != null) {
				text.setValue("");
			}
			currentValue = null;
		}
	}

	private void applyTransforms(Icon icon) {
		image.resetTransform();
		Animation anim = icon.getAnimation();
		if (anim != null)
			anim.transform.applyTo(image);
		for (Transform t : transforms)
			t.applyTo(image);
		image.repaint();
	}

	@Override
	protected Type getType() {
		return Icon.class;
	}
	
}