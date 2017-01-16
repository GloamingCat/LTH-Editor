package gui.views.common;

import gui.shell.PlayerShell;
import lwt.dialog.LObjectShell;
import lwt.dialog.LShellFactory;
import lwt.widget.LObjectButton;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;

import data.Player;

public class PlayerButton extends LObjectButton<Player> {

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public PlayerButton(Composite parent, int style) {
		super(parent, style);
		setShellFactory(new LShellFactory<Player>() {
			@Override
			public LObjectShell<Player> createShell(Shell parent) {
				return new PlayerShell(parent);
			}
		});
	}

}