package gui.views.database.subcontent;

import gui.shell.TagDialog;
import gui.widgets.SimpleEditableList;

import data.subcontent.Tag;
import lui.container.LContainer;
import lui.dialog.LObjectDialog;
import lui.dialog.LWindow;
import lui.dialog.LWindowFactory;

public class TagList extends SimpleEditableList<Tag> {
	
	public TagList(LContainer parent) {
		super(parent);
		type = Tag.class;
		setIncludeID(false);
		setShellFactory(new LWindowFactory<Tag>() {
			@Override
			public LObjectDialog<Tag> createWindow(LWindow parent) {
				return new TagDialog(parent);
			}
		});
	}

}
