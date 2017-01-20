package gui.views.database.subcontent;

import gui.shell.TransitionShell;
import gui.views.common.SimpleEditableList;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;

import data.Transition;
import lwt.dialog.LObjectShell;
import lwt.dialog.LShellFactory;

public class TransitionList extends SimpleEditableList<Transition> {

	public TransitionList(Composite parent, int style) {
		super(parent, style);
		attributeName = "transitions";
		setIncludeID(false);
		setShellFactory(new LShellFactory<Transition>() {
			@Override
			public LObjectShell<Transition> createShell(Shell parent) {
				return new TransitionShell(parent);
			}
		});
	}

}
