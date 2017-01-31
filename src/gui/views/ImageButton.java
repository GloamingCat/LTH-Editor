package gui.views;

import gui.shell.ImageShell;
import lwt.dialog.LObjectShell;
import lwt.dialog.LShellFactory;
import lwt.widget.LObjectButton;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;

import project.Project;

public class ImageButton extends LObjectButton<String> {
	
	private String folder;
	private Label label;
	private Text text;
	public boolean optional = false;
	
	public ImageButton(Composite parent, int style) {
		super(parent, style);
		setShellFactory(new LShellFactory<String>() {
			@Override
			public LObjectShell<String> createShell(Shell parent) {
				return new ImageShell(parent, folder, optional);
			}
		});
	}
	
	public void setFolder(String f) {
		this.folder = f;
	}

	public void setLabel(Label label) {
		this.label = label;
	}
	
	public void setText(Text text) {
		this.text = text;
	}
	
	@Override
	public void setValue(Object value) {
		if (value != null) {
			button.setEnabled(true);
			String s = (String) value;
			if (label != null) {
				try {
				label.setImage(SWTResourceManager.getImage(
						Project.current.imagePath() + s));
				} catch (Exception e) {
					System.out.println("Error with image: " + s);
				}
			}
			if (text != null) {
				text.setText(s);
			}
			currentValue = s;
		} else {
			button.setEnabled(false);
			if (label != null) {
				label.setImage(null);
			}
			if (text != null) {
				text.setText("");
			}
			currentValue = null;
		}
	}
	
}