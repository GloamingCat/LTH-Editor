package gui.widgets;

import gui.shell.PositionShell;
import lui.container.LContainer;
import lui.dialog.LObjectWindow;
import lui.dialog.LWindow;
import lui.dialog.LWindowFactory;
import lui.widget.LObjectButton;
import lui.widget.LText;

import java.lang.reflect.Type;

import data.subcontent.Position;

public class PositionButton extends LObjectButton<Position> {

	private LText text;
	
	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public PositionButton(LContainer parent) {
		super(parent);
		setShellFactory(new LWindowFactory<Position>() {
			@Override
			public LObjectWindow<Position> createWindow(LWindow parent) {
				return new PositionShell(parent);
			}
		});
	}
	
	public void setTextWidget(LText text) {
		this.text = text;
	}

	@Override
	public void setValue(Object value) {
		if (value != null) {
			setEnabled(true);
			Position s = (Position) value;
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
		return Position.class;
	}

}