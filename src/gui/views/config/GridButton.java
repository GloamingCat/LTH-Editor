package gui.views.config;

import gui.shell.config.GridShell;
import lwt.dialog.LObjectShell;
import lwt.dialog.LShellFactory;
import lwt.widget.LObjectButton;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;

import data.config.Config.Grid;

public class GridButton extends LObjectButton<Grid> {

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public GridButton(Composite parent, int style) {
		super(parent, style);
		setShellFactory(new LShellFactory<Grid>() {
			@Override
			public LObjectShell<Grid> createShell(Shell parent) {
				return new GridShell(parent);
			}
		});
	}

}