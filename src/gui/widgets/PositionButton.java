package gui.widgets;

import gui.shell.PositionShell;
import lwt.container.LContainer;
import lwt.dialog.LObjectShell;
import lwt.dialog.LShell;
import lwt.dialog.LShellFactory;
import lwt.widget.LObjectButton;
import lwt.widget.LText;

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
		setShellFactory(new LShellFactory<Position>() {
			@Override
			public LObjectShell<Position> createShell(LShell parent) {
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

}