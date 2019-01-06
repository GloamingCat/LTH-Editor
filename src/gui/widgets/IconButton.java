package gui.widgets;

import gui.shell.IconShell;
import lwt.dialog.LShellFactory;
import lwt.widget.LImage;
import lwt.widget.LObjectButton;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import project.Project;
import data.Animation;
import data.subcontent.Icon;

public class IconButton extends LObjectButton<Icon> {
	
	private LImage image;
	private Text text;
	
	public IconButton(Composite parent, int style) {
		super(parent, SWT.NONE);
		setShellFactory(new LShellFactory<Icon>() {
			@Override
			public IconShell createShell(Shell parent) {
				return new IconShell(parent, style);
			}
		});
	}

	public void setImage(LImage image) {
		this.image = image;
	}
	
	public void setText(Text text) {
		this.text = text;
	}
	
	@Override
	public void setValue(Object value) {
		if (value != null) {
			button.setEnabled(true);
			Icon i = (Icon) value;
			if (image != null) {
				Animation anim = i.getAnimation();
				if (anim != null)
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
				text.setText("");
			}
			currentValue = null;
		}
	}
	
}