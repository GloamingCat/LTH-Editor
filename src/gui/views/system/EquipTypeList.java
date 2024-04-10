package gui.views.system;

import gui.shell.system.EquipTypeDialog;
import gui.widgets.SimpleEditableList;

import data.config.EquipType;
import lui.container.LContainer;
import lui.dialog.LObjectDialog;
import lui.dialog.LWindow;
import lui.dialog.LWindowFactory;

public class EquipTypeList extends SimpleEditableList<EquipType> {

	public EquipTypeList(LContainer parent) {
		super(parent);
		type = EquipType.class;
		setIncludeID(true);
		setShellFactory(new LWindowFactory<EquipType>() {
			@Override
			public LObjectDialog<EquipType> createWindow(LWindow parent) {
				return new EquipTypeDialog(parent);
			}
		});
	}

}
