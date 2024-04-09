package gui.views.database.subcontent;

import gui.shell.database.EquipStatusShell;
import gui.widgets.SimpleEditableList;

import data.Item.EquipStatus;
import lwt.container.LContainer;
import lwt.dialog.LObjectWindow;
import lwt.dialog.LWindow;
import lwt.dialog.LWindowFactory;

public class EquipStatusList extends SimpleEditableList<EquipStatus> {
	
	public EquipStatusList(LContainer parent) {
		super(parent);
		type = EquipStatus.class;
		setIncludeID(false);
		setShellFactory(new LWindowFactory<EquipStatus>() {
			@Override
			public LObjectWindow<EquipStatus> createWindow(LWindow parent) {
				return new EquipStatusShell(parent);
			}
		});
	}

}
