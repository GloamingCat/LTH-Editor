package gui.widgets;

import gui.shell.field.PortalShell;
import lwt.container.LContainer;
import lwt.dialog.LObjectShell;
import lwt.dialog.LShell;
import lwt.dialog.LShellFactory;
import lwt.widget.LObjectButton;
import lwt.widget.LText;

import data.field.Transition.Portal;

public class PortalButton extends LObjectButton<Portal> {

	private LText text;
	
	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public PortalButton(LContainer parent, int fieldID) {
		super(parent);
		setShellFactory(new LShellFactory<Portal>() {
			@Override
			public LObjectShell<Portal> createShell(LShell parent) {
				return new PortalShell(parent, fieldID);
			}
		});
	}
	
	public PortalButton(LContainer parent) {
		this(parent, -1);
	}
	
	public void setTextWidget(LText text) {
		this.text = text;
	}

	@Override
	public void setValue(Object value) {
		if (value != null) {
			setEnabled(true);
			Portal s = (Portal) value;
			if (text != null) {
				text.setValue(s.toString());
			}
			currentValue = s;
		} else {
			setEnabled(false);
			if (text != null) {
				text.setValue("");
			}
			currentValue = null;
		}
	}

}