package gui.widgets;

import gui.shell.QuadShell;
import gui.views.database.subcontent.TransformEditor;
import lwt.container.LContainer;
import lwt.dialog.LObjectShell;
import lwt.dialog.LShell;
import lwt.dialog.LShellFactory;
import lwt.widget.LImage;
import lwt.widget.LObjectButton;

import org.eclipse.swt.graphics.Image;

import project.Project;
import data.subcontent.Quad;

public class QuadButton extends LObjectButton<Quad> {
	
	private LImage image;
	private TransformEditor transform;
	
	public QuadButton(LContainer parent, boolean optional) {
		super(parent);
		setShellFactory(new LShellFactory<Quad>() {
			@Override
			public LObjectShell<Quad> createShell(LShell parent) {
				return new QuadShell(parent, optional);
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
			button.setEnabled(true);
			Quad s = (Quad) value;
			if (image != null) {
				if (transform != null) {
					transform.updateColorTransform(image);
				}
				if (s.path.isEmpty()) {
					image.setImage((Image) null);
				} else {
					image.setImage(Project.current.imagePath() + s.path, s.getRectangle());
				}
			}
			currentValue = s;
		} else {
			button.setEnabled(false);
			if (image != null) {
				image.setImage((Image) null);
			}
			currentValue = null;
		}
	}
	
}