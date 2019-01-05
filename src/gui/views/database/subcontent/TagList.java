package gui.views.database.subcontent;

import gui.shell.TagShell;
import gui.widgets.SimpleEditableList;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;

import data.subcontent.Tag;
import lwt.dialog.LObjectShell;
import lwt.dialog.LShellFactory;

public class TagList extends SimpleEditableList<Tag> {
	
	public TagList(Composite parent, int style) {
		super(parent, style);
		type = Tag.class;
		setIncludeID(false);
		setShellFactory(new LShellFactory<Tag>() {
			@Override
			public LObjectShell<Tag> createShell(Shell parent) {
				return new TagShell(parent);
			}
		});
	}

}
