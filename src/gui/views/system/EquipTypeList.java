package gui.views.system;

import gui.shell.system.EquipTypeShell;
import gui.widgets.SimpleEditableList;

import data.config.EquipType;
import lwt.container.LContainer;
import lwt.dialog.LObjectShell;
import lwt.dialog.LShell;
import lwt.dialog.LShellFactory;

public class EquipTypeList extends SimpleEditableList<EquipType> {

	public EquipTypeList(LContainer parent) {
		super(parent);
		type = EquipType.class;
		setIncludeID(true);
		setShellFactory(new LShellFactory<EquipType>() {
			@Override
			public LObjectShell<EquipType> createShell(LShell parent) {
				return new EquipTypeShell(parent);
			}
		});
	}

}
