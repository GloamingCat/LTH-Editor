package gui.views.database.subcontent;

import data.Data;
import data.subcontent.Tag;
import gui.Vocab;
import gui.shell.DataDialog;
import gui.shell.TagDialog;
import gui.widgets.DirectionCombo;
import gui.widgets.SimpleEditableList;
import lui.container.LContainer;
import lui.dialog.LObjectDialog;
import lui.dialog.LWindow;
import lui.dialog.LWindowFactory;

public class DataList extends SimpleEditableList<Data> {

	public DataList(LContainer parent, String title, boolean editTags) {
		super(parent);
		type = Data.class;
		setIncludeID(true);
		setShellFactory(new LWindowFactory<>() {
			@Override
			public LObjectDialog<Data> createWindow(LWindow parent) {
				return new DataDialog(parent, title, editTags ? DataDialog.EDITTAGS : 0);
			}
		});
	}

}
