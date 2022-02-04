package gui.widgets;

import gui.shell.field.PortalShell;
import lwt.dialog.LObjectShell;
import lwt.dialog.LShellFactory;
import lwt.widget.LObjectButton;
import lwt.widget.LText;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;

import data.field.Transition.Portal;

public class PortalButton extends LObjectButton<Portal> {

	private LText text;
	
	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public PortalButton(Composite parent, int fieldID) {
		super(parent, SWT.NONE);
		setShellFactory(new LShellFactory<Portal>() {
			@Override
			public LObjectShell<Portal> createShell(Shell parent) {
				return new PortalShell(parent, fieldID);
			}
		});
	}
	
	public PortalButton(Composite parent) {
		this(parent, -1);
	}
	
	public void setTextWidget(LText text) {
		this.text = text;
	}

	@Override
	public void setValue(Object value) {
		if (value != null) {
			button.setEnabled(true);
			Portal s = (Portal) value;
			if (text != null) {
				text.setValue(s.toString());
			}
			currentValue = s;
		} else {
			button.setEnabled(false);
			if (text != null) {
				text.setValue("");
			}
			currentValue = null;
		}
	}

}