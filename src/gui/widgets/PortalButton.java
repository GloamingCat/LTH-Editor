package gui.widgets;

import gui.shell.field.PortalShell;
import lui.container.LContainer;
import lui.dialog.LObjectWindow;
import lui.dialog.LWindow;
import lui.dialog.LWindowFactory;
import lui.widget.LObjectButton;
import lui.widget.LText;

import java.lang.reflect.Type;

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
		setShellFactory(new LWindowFactory<Portal>() {
			@Override
			public LObjectWindow<Portal> createWindow(LWindow parent) {
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

	@Override
	protected Type getType() {
		return Portal.class;
	}

}