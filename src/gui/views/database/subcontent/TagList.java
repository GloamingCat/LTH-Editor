package gui.views.database.subcontent;

import gui.shell.TagShell;
import gui.views.common.SimpleEditableList;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;

import data.Tag;
import lwt.dialog.LObjectShell;
import lwt.dialog.LShellFactory;

public class TagList extends SimpleEditableList<Tag> {
	
	public TagList(Composite parent, int style) {
		super(parent, style);
		type = Tag.class;
		attributeName = "tags";
		setIncludeID(false);
		setShellFactory(new LShellFactory<Tag>() {
			@Override
			public LObjectShell<Tag> createShell(Shell parent) {
				return new TagShell(parent);
			}
		});
	}

}
