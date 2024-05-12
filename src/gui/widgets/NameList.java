package gui.widgets;

import lui.container.LContainer;
import lui.dialog.LObjectDialog;
import lui.dialog.LStringDialog;
import lui.dialog.LWindow;
import lui.dialog.LWindowFactory;

public class NameList extends SimpleEditableList<String> {

	public NameList(LContainer parent, String title) {
		super(parent);
		type = String.class;
		setIncludeID(true);
		setShellFactory(new LWindowFactory<String>() {
			@Override
			public LObjectDialog<String> createWindow(LWindow parent) {
				return new LStringDialog(parent, title);
			}
		});
	}

	@Override
	public String createNewElement() {
		return "Empty";
	}

}
