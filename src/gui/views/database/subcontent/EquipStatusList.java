package gui.views.database.subcontent;

import gui.shell.database.EquipStatusDialog;
import gui.widgets.SimpleEditableList;

import data.Item.EquipStatus;
import lui.container.LContainer;
import lui.dialog.LObjectDialog;
import lui.dialog.LWindow;
import lui.dialog.LWindowFactory;

public class EquipStatusList extends SimpleEditableList<EquipStatus> {
	
	public EquipStatusList(LContainer parent) {
		super(parent);
		type = EquipStatus.class;
		setIncludeID(false);
		setShellFactory(new LWindowFactory<EquipStatus>() {
			@Override
			public LObjectDialog<EquipStatus> createWindow(LWindow parent) {
				return new EquipStatusDialog(parent);
			}
		});
	}

}
