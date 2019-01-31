package gui.views.system;

import gui.shell.ConstantShell;
import gui.widgets.SimpleEditableList;

import org.eclipse.swt.widgets.Composite;

import data.subcontent.Constant;
import lwt.dialog.LObjectShell;
import lwt.dialog.LShellFactory;

public class ConstantList extends SimpleEditableList<Constant> {

	public ConstantList(Composite parent, int style) {
		super(parent, style);
		type = Constant.class;
		setIncludeID(false);
		setShellFactory(new LShellFactory<Constant>() {
			@Override
			public LObjectShell<Constant> createShell(
					org.eclipse.swt.widgets.Shell parent) {
				return new ConstantShell(parent);
			}
		});
	}

}
