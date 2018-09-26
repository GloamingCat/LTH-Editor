package gui.widgets;

import gui.shell.QuadShell;
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
	
	private String folder;
	private LImage image;
	private Text text;
	public boolean optional = false;
	
	public QuadButton(Composite parent, int style) {
		super(parent, style);
		setShellFactory(new LShellFactory<Quad>() {
			@Override
			public LObjectShell<Quad> createShell(Shell parent) {
				return new QuadShell(parent, folder, optional);
			}
		});
	}
	
	public void setFolder(String f) {
		this.folder = f;
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
			Quad s = (Quad) value;
			if (image != null) {
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