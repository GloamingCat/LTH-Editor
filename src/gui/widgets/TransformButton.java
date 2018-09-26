package gui.widgets;

import gui.shell.database.TransformShell;
import lwt.dialog.LObjectShell;
import lwt.dialog.LShellFactory;
import lwt.widget.LObjectButton;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;

import data.subcontent.Transform;

public class TransformButton extends LObjectButton<Transform> {
	
	public TransformButton(Composite parent, int style) {
		super(parent, style);
		setShellFactory(new LShellFactory<Transform>() {
			@Override
			public LObjectShell<Transform> createShell(Shell parent) {
				return new TransformShell(parent);
			}
		});
	}

}