package gui.views.database.subcontent;

import gui.Vocab;
import gui.shell.TagDialog;
import gui.widgets.SimpleEditableList;

import data.subcontent.Tag;
import lui.container.LContainer;
import lui.dialog.LObjectDialog;
import lui.dialog.LWindow;
import lui.dialog.LWindowFactory;

public class TagList extends SimpleEditableList<Tag> {

	private String shellTitle;
	
	public TagList(LContainer parent, String title) {
		super(parent);
		type = Tag.class;
		shellTitle = title;
		setIncludeID(false);
		setShellFactory(new LWindowFactory<Tag>() {
			@Override
			public LObjectDialog<Tag> createWindow(LWindow parent) {
				return new TagDialog(parent, shellTitle);
			}
		});
	}

	public TagList(LContainer parent) {
		this(parent, Vocab.instance.TAGSHELL);
	}

	public void setTitle(String title) {
		shellTitle = title;
	}

}
