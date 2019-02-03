package gui.views.database.subcontent;

import gui.shell.database.EquipStatusShell;
import gui.widgets.SimpleEditableList;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;

import data.Item.EquipStatus;
import lwt.dialog.LObjectShell;
import lwt.dialog.LShellFactory;

public class EquipStatusList extends SimpleEditableList<EquipStatus> {
	
	public EquipStatusList(Composite parent, int style) {
		super(parent, style);
		type = EquipStatus.class;
		setIncludeID(false);
		setShellFactory(new LShellFactory<EquipStatus>() {
			@Override
			public LObjectShell<EquipStatus> createShell(Shell parent) {
				return new EquipStatusShell(parent);
			}
		});
	}

}
