package gui.views.config;

import gui.shell.config.PartyShell;
import lwt.dialog.LObjectShell;
import lwt.dialog.LShellFactory;
import lwt.widget.LObjectButton;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;

import data.Config.Party;

public class PartyButton extends LObjectButton<Party> {

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public PartyButton(Composite parent, int style) {
		super(parent, style);
		setShellFactory(new LShellFactory<Party>() {
			@Override
			public LObjectShell<Party> createShell(Shell parent) {
				return new PartyShell(parent);
			}
		});
	}

}