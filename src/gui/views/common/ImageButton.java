package gui.views.common;

import lwt.widget.LStringButton;

import org.eclipse.swt.widgets.Composite;

import project.Project;

public class ImageButton extends LStringButton {
	
	public ImageButton(Composite parent, int style) {
		super(parent, style);
	}
	
	@Override
	protected String getImagePath() {
		return Project.current.imagePath();
	}
	
}