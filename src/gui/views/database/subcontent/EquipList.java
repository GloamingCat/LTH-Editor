package gui.views.database.subcontent;

import gui.shell.database.EquipShell;
import gui.widgets.SimpleEditableList;

import data.Battler.Equip;
import lwt.container.LContainer;
import lwt.dialog.LObjectShell;
import lwt.dialog.LShell;
import lwt.dialog.LShellFactory;

public class EquipList extends SimpleEditableList<Equip> {
	
	public EquipList(LContainer parent) {
		super(parent);
		type = Equip.class;
		setIncludeID(false);
		setShellFactory(new LShellFactory<Equip>() {
			@Override
			public LObjectShell<Equip> createShell(LShell parent) {
				return new EquipShell(parent);
			}
		});
	}

}
