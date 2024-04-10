package gui.views.database.subcontent;

import gui.shell.database.EquipShell;
import gui.widgets.SimpleEditableList;

import data.Battler.Equip;
import lui.container.LContainer;
import lui.dialog.LObjectWindow;
import lui.dialog.LWindow;
import lui.dialog.LWindowFactory;

public class EquipList extends SimpleEditableList<Equip> {
	
	public EquipList(LContainer parent) {
		super(parent);
		type = Equip.class;
		setIncludeID(false);
		setShellFactory(new LWindowFactory<Equip>() {
			@Override
			public LObjectWindow<Equip> createWindow(LWindow parent) {
				return new EquipShell(parent);
			}
		});
	}

}
