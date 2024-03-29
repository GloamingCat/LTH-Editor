package gui.widgets;

import gui.shell.IconShell;
import gui.views.database.subcontent.TransformEditor;
import lwt.dialog.LShellFactory;
import lwt.widget.LImage;
import lwt.widget.LObjectButton;
import lwt.widget.LText;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;

import project.Project;
import data.Animation;
import data.subcontent.Icon;

public class IconButton extends LObjectButton<Icon> {
	
	private LImage image;
	private LText text;
	private TransformEditor transform;
	
	public IconButton(Composite parent, int style) {
		super(parent, SWT.NONE);
		setShellFactory(new LShellFactory<Icon>() {
			@Override
			public IconShell createShell(Shell parent) {
				return new IconShell(parent, style);
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
			button.setEnabled(true);
			Icon i = (Icon) value;
			if (image != null) {
				if (transform != null) {
					transform.updateColorTransform(image);
				}
				Animation anim = i.getAnimation();
				if (anim != null && !anim.quad.path.isEmpty())
					image.setImage(Project.current.imagePath() + anim.quad.path, i.getRectangle());
				else
					image.setImage((Image) null);
			}
			currentValue = i;
		} else {
			button.setEnabled(false);
			if (image != null) {
				image.setImage((Image) null);
			}
			if (text != null) {
				text.setValue("");
			}
			currentValue = null;
		}
	}
	
}