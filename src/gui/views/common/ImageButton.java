package gui.views.common;

import gui.shell.ImageShell;
import lwt.dialog.LObjectShell;
import lwt.dialog.LShellFactory;
import lwt.widget.LStringButton;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;

import project.Project;

public class ImageButton extends LStringButton {
	
	private String folder;
	
	public ImageButton(Composite parent, int style) {
		super(parent, style);
		setShellFactory(new LShellFactory<String>() {
			@Override
			public LObjectShell<String> createShell(Shell parent) {
				return new ImageShell(parent, folder);
			}
		});
	}
	
	public void setFolder(String f) {
		this.folder = f;
	}
	
	@Override
	protected String getImagePath() {
		return Project.current.imagePath();
	}
	
}