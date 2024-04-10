package gui.widgets;

import gui.shell.PositionDialog;
import lui.container.LContainer;
import lui.dialog.LObjectDialog;
import lui.dialog.LWindow;
import lui.dialog.LWindowFactory;
import lui.widget.LObjectButton;
import lui.widget.LText;

import java.lang.reflect.Type;

import data.subcontent.Position;

public class PositionButton extends LObjectButton<Position> {

	private LText text;

	public PositionButton(LContainer parent, int fieldID) {
		super(parent);
		setShellFactory(new LWindowFactory<>() {
			@Override
			public LObjectDialog<Position> createWindow(LWindow parent) {
				return new PositionDialog(parent, fieldID);
			}
		});
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
		return Position.class;
	}

}