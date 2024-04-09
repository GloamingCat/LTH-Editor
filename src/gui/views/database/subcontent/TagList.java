package gui.views.database.subcontent;

import gui.shell.TagShell;
import gui.widgets.SimpleEditableList;

import data.subcontent.Tag;
import lwt.container.LContainer;
import lwt.dialog.LObjectWindow;
import lwt.dialog.LWindow;
import lwt.dialog.LWindowFactory;

public class TagList extends SimpleEditableList<Tag> {
	
	public TagList(LContainer parent) {
		super(parent);
		type = Tag.class;
		setIncludeID(false);
		setShellFactory(new LWindowFactory<Tag>() {
			@Override
			public LObjectWindow<Tag> createWindow(LWindow parent) {
				return new TagShell(parent);
			}
		});
	}

}
