package gui.views.database.subcontent;

import gui.shell.database.EquipShell;
import gui.widgets.SimpleEditableList;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;

import data.Battler.Equip;
import lwt.dialog.LObjectShell;
import lwt.dialog.LShellFactory;

public class EquipList extends SimpleEditableList<Equip> {
	
	public EquipList(Composite parent, int style) {
		super(parent, style);
		type = Equip.class;
		attributeName = "equip";
		setIncludeID(false);
		setShellFactory(new LShellFactory<Equip>() {
			@Override
			public LObjectShell<Equip> createShell(Shell parent) {
				return new EquipShell(parent);
			}
		});
	}

}
