package gui.widgets;

import gui.shell.QuadShell;
import gui.views.database.subcontent.TransformEditor;
import lwt.dialog.LObjectShell;
import lwt.dialog.LShellFactory;
import lwt.widget.LImage;
import lwt.widget.LObjectButton;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import project.Project;
import data.subcontent.Quad;

public class QuadButton extends LObjectButton<Quad> {
	
	private String folder = "";
	private LImage image;
	private TransformEditor transform;
	private Text text;
	
	public QuadButton(Composite parent, int style) {
		super(parent, style);
		setShellFactory(new LShellFactory<Quad>() {
			@Override
			public LObjectShell<Quad> createShell(Shell parent) {
				return new QuadShell(parent, folder, style != 0);
			}
		});
	}
	
	public void setFolder(String f) {
		this.folder = f;
	}

	public void setImage(LImage image) {
		this.image = image;
	}
	
	public void setTransform(TransformEditor transform) {
		this.transform = transform;
	}
	
	public void setText(Text text) {
		this.text = text;
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
				if (!s.path.isEmpty())
					image.setImage(Project.current.imagePath() + s.path, s.getRectangle());
			}
			currentValue = s;
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