package gui.views.config;

import gui.shell.config.GUIShell;
import lwt.dialog.LObjectShell;
import lwt.dialog.LShellFactory;
import lwt.widget.LObjectButton;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;

import data.Config.GUI;

public class GUIButton extends LObjectButton<GUI> {

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public GUIButton(Composite parent, int style) {
		super(parent, style);
		setShellFactory(new LShellFactory<GUI>() {
			@Override
			public LObjectShell<GUI> createShell(Shell parent) {
				return new GUIShell(parent);
			}
		});
	}

}