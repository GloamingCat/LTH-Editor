package gui.widgets;

import gui.shell.IconShell;
import gui.views.database.subcontent.TransformEditor;
import lwt.container.LContainer;
import lwt.container.LImage;
import lwt.dialog.LWindow;
import lwt.dialog.LWindowFactory;
import lwt.widget.LObjectButton;
import lwt.widget.LText;

import java.lang.reflect.Type;

import data.Animation;
import data.subcontent.Icon;

public class IconButton extends LObjectButton<Icon> {
	
	private LImage image;
	private LText text;
	private TransformEditor transform;
	
	public IconButton(LContainer parent, boolean optional) {
		super(parent);
		setShellFactory(new LWindowFactory<Icon>() {
			@Override
			public IconShell createWindow(LWindow parent) {
				return new IconShell(parent, IconShell.OPTIONAL);
			}
		});
	}

	public void setImageWidget(LImage image) {
		this.image = image;
	}
	
	public void setTransform(TransformEditor transform) {
		this.transform = transform;
	}

	@Override
	public void setValue(Object value) {
		if (value != null) {
			setEnabled(true);
			Icon i = (Icon) value;
			if (image != null) {
				if (transform != null) {
					transform.updateColorTransform(image);
				}
				Animation anim = i.getAnimation();
				if (anim != null && !anim.quad.path.isEmpty())
					image.setImage(anim.quad.fullPath(), i.getRectangle());
				else
					image.setImage((String) null);
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

	@Override
	protected Type getType() {
		return Icon.class;
	}
	
}