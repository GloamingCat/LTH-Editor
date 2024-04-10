package gui.views.fieldTree.subcontent;

import gui.shell.field.FieldImageShell;
import gui.widgets.SimpleEditableList;

import data.field.FieldImage;
import lui.container.LContainer;
import lui.dialog.LObjectWindow;
import lui.dialog.LWindow;
import lui.dialog.LWindowFactory;

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
