package gui.views.fieldTree.subcontent;

import gui.shell.field.FieldImageShell;
import gui.widgets.SimpleEditableList;

import data.field.FieldImage;
import lwt.container.LContainer;
import lwt.dialog.LObjectWindow;
import lwt.dialog.LWindow;
import lwt.dialog.LWindowFactory;

public class FieldImageList extends SimpleEditableList<FieldImage> {
	
	public FieldImageList(LContainer parent) {
		super(parent);
		type = FieldImage.class;
		setIncludeID(false);
		setShellFactory(new LWindowFactory<FieldImage>() {
			@Override
			public LObjectWindow<FieldImage> createWindow(LWindow parent) {
				return new FieldImageShell(parent);
			}
		});
	}

}
