package gui.views.fieldTree.subcontent;

import gui.shell.field.FieldImageShell;
import gui.widgets.SimpleEditableList;

import data.field.FieldImage;
import lwt.container.LContainer;
import lwt.dialog.LObjectShell;
import lwt.dialog.LShell;
import lwt.dialog.LShellFactory;

public class FieldImageList extends SimpleEditableList<FieldImage> {
	
	public FieldImageList(LContainer parent) {
		super(parent);
		type = FieldImage.class;
		setIncludeID(false);
		setShellFactory(new LShellFactory<FieldImage>() {
			@Override
			public LObjectShell<FieldImage> createShell(LShell parent) {
				return new FieldImageShell(parent);
			}
		});
	}

}
