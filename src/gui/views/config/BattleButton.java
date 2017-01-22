package gui.views.config;

import gui.shell.config.BattleShell;
import lwt.dialog.LObjectShell;
import lwt.dialog.LShellFactory;
import lwt.widget.LObjectButton;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;

import data.Config.Battle;

public class BattleButton extends LObjectButton<Battle> {

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public BattleButton(Composite parent, int style) {
		super(parent, style);
		setShellFactory(new LShellFactory<Battle>() {
			@Override
			public LObjectShell<Battle> createShell(Shell parent) {
				return new BattleShell(parent);
			}
		});
	}

}