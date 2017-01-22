package gui.views.config;

import gui.shell.config.BattlerTypeShell;
import gui.views.SimpleEditableList;

import org.eclipse.swt.widgets.Composite;

import data.BattlerType;
import lwt.dialog.LObjectShell;
import lwt.dialog.LShellFactory;

public class BattlerTypeList extends SimpleEditableList<BattlerType> {

	public BattlerTypeList(Composite parent, int style) {
		super(parent, style);
		attributeName = "battlerTypes";
		setIncludeID(true);

		setShellFactory(new LShellFactory<BattlerType>() {
			@Override
			public LObjectShell<BattlerType> createShell(
					org.eclipse.swt.widgets.Shell parent) {
				return new BattlerTypeShell(parent);
			}
		});
	}

}
