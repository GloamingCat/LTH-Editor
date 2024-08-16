package gui.views.database.subcontent;

import data.Data;
import gui.shell.DataDialog;
import gui.widgets.SimpleEditableList;
import lui.container.LContainer;
import lui.dialog.LObjectDialog;
import lui.dialog.LWindow;
import lui.dialog.LWindowFactory;

public class DataList extends SimpleEditableList<Data> {

	public DataList(LContainer parent, String title, String tagTitle, boolean editKey) {
		super(parent);
		type = Data.class;
		setIncludeID(true);
		int keyFlag = editKey ? DataDialog.KEY : 0;
		setShellFactory(new LWindowFactory<>() {
			@Override
			public LObjectDialog<Data> createWindow(LWindow parent) {
				return new DataDialog(parent, title, tagTitle, keyFlag | DataDialog.NAME);
			}
		});
	}

}
