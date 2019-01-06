package gui.views.config;

import gui.shell.config.EquipTypeShell;
import gui.widgets.SimpleEditableList;

import org.eclipse.swt.widgets.Composite;

import data.config.EquipType;
import lwt.dialog.LObjectShell;
import lwt.dialog.LShellFactory;

public class EquipTypeList extends SimpleEditableList<EquipType> {

	public EquipTypeList(Composite parent, int style) {
		super(parent, style);
		type = EquipType.class;
		setIncludeID(true);
		setShellFactory(new LShellFactory<EquipType>() {
			@Override
			public LObjectShell<EquipType> createShell(
					org.eclipse.swt.widgets.Shell parent) {
				return new EquipTypeShell(parent);
			}
		});
	}

}
