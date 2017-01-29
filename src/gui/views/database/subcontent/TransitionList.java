package gui.views.database.subcontent;

import gui.shell.field.TransitionShell;
import gui.views.SimpleEditableList;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;

import data.Transition;
import lwt.dialog.LObjectShell;
import lwt.dialog.LShellFactory;

public class TransitionList extends SimpleEditableList<Transition> {

	public TransitionList(Composite parent, int style) {
		super(parent, style);
		type = Transition.class;
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
