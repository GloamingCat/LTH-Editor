package gui.widgets;

import gui.shell.field.PortalDialog;
import lui.container.LContainer;
import lui.dialog.LObjectDialog;
import lui.dialog.LWindow;
import lui.dialog.LWindowFactory;
import lui.widget.LObjectButton;
import lui.widget.LText;

import java.lang.reflect.Type;

import data.field.Transition.Portal;

public class PortalButton extends LObjectButton<Portal> {

	private LText text;

	public PortalButton(LContainer parent, int fieldID) {
		super(parent);
		setShellFactory(new LWindowFactory<>() {
			@Override
			public LObjectDialog<Portal> createWindow(LWindow parent) {
				return new PortalDialog(parent, fieldID);
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
		super.setValue(value);
		if (text != null) {
			if (currentValue != null) {
				text.setValue(currentValue.toString());
				text.setEnabled(true);
			} else {
				text.setValue("");
				text.setEnabled(false);
			}
		}
	}

	@Override
	protected Type getType() {
		return Portal.class;
	}

}