package gui.views.database.subcontent;

import gui.shell.TagShell;
import gui.widgets.SimpleEditableList;

import data.subcontent.Tag;
import lwt.container.LContainer;
import lwt.dialog.LObjectShell;
import lwt.dialog.LShell;
import lwt.dialog.LShellFactory;

public class TagList extends SimpleEditableList<Tag> {
	
	public TagList(LContainer parent) {
		super(parent);
		type = Tag.class;
		setIncludeID(false);
		setShellFactory(new LShellFactory<Tag>() {
			@Override
			public LObjectShell<Tag> createShell(LShell parent) {
				return new TagShell(parent);
			}
		});
	}

}
