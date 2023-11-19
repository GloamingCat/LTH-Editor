package gui.views.database.subcontent;

import gui.shell.database.EquipStatusShell;
import gui.widgets.SimpleEditableList;

import data.Item.EquipStatus;
import lwt.container.LContainer;
import lwt.dialog.LObjectShell;
import lwt.dialog.LShell;
import lwt.dialog.LShellFactory;

public class EquipStatusList extends SimpleEditableList<EquipStatus> {
	
	public EquipStatusList(LContainer parent) {
		super(parent);
		type = EquipStatus.class;
		setIncludeID(false);
		setShellFactory(new LShellFactory<EquipStatus>() {
			@Override
			public LObjectShell<EquipStatus> createShell(LShell parent) {
				return new EquipStatusShell(parent);
			}
		});
	}

}
