package gui.views;

import gui.helper.ImageHelper;
import gui.shell.QuadShell;
import lwt.dialog.LObjectShell;
import lwt.dialog.LShellFactory;
import lwt.widget.LObjectButton;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import data.Quad;

public class QuadButton extends LObjectButton<Quad> {
	
	private String folder;
	private Label label;
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
			Quad s = (Quad) value;
			if (label != null) {
				label.setImage(ImageHelper.getImageQuad(s));
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