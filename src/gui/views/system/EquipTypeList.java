package gui.views.system;

import gui.shell.system.EquipTypeShell;
import gui.widgets.SimpleEditableList;

import data.config.EquipType;
import lui.container.LContainer;
import lui.dialog.LObjectWindow;
import lui.dialog.LWindow;
import lui.dialog.LWindowFactory;

public class EquipTypeList extends SimpleEditableList<EquipType> {

	public EquipTypeList(LContainer parent) {
		super(parent);
		type = EquipType.class;
		setIncludeID(true);
		setShellFactory(new LWindowFactory<EquipType>() {
			@Override
			public LObjectWindow<EquipType> createWindow(LWindow parent) {
				return new EquipTypeShell(parent);
			}
		});
	}

}
