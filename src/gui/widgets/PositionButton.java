package gui.widgets;

import gui.shell.PositionShell;
import lwt.dialog.LObjectShell;
import lwt.dialog.LShellFactory;
import lwt.widget.LObjectButton;
import lwt.widget.LText;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;

import data.subcontent.Position;

public class PositionButton extends LObjectButton<Position> {

	private LText text;
	
	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public PositionButton(Composite parent, int fieldID) {
		super(parent, SWT.NONE);
		setShellFactory(new LShellFactory<Position>() {
			@Override
			public LObjectShell<Position> createShell(Shell parent) {
				return new PositionShell(parent, fieldID);
			}
		});
	}
	
	public PositionButton(Composite parent) {
		this(parent, -1);
	}
	
	public void setTextWidget(LText text) {
		this.text = text;
	}

	@Override
	public void setValue(Object value) {
		if (value != null) {
			button.setEnabled(true);
			Position s = (Position) value;
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