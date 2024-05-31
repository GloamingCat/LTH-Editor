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
	
	public QuadButton(LContainer parent) {
		super(parent);
		setShellFactory(new LWindowFactory<>() {
			@Override
			public LObjectDialog<Quad> createWindow(LWindow parent) {
				return new QuadDialog(parent, QuadDialog.OPTIONAL);
			}
		});
	}

	public void setImageWidget(LImage image) {
		this.image = image;
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