package gui.views.common;

import gui.shell.PositionShell;
import lwt.dialog.LObjectShell;
import lwt.dialog.LShellFactory;
import lwt.widget.LObjectButton;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import data.Position;

public class PositionButton extends LObjectButton<Position> {

	private Text text;
	
	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public PositionButton(Composite parent, int style) {
		super(parent, style);
		setShellFactory(new LShellFactory<Position>() {
			@Override
			public LObjectShell<Position> createShell(Shell parent) {
				return new PositionShell(parent);
			}
		});
	}
	
	public void setText(Text text) {
		this.text = text;
	}

	@Override
	public void setValue(Object value) {
		if (value != null) {
			button.setEnabled(true);
			Position s = (Position) value;
			if (text != null) {
				text.setText(s.toString());
			}
			currentValue = s;
		} else {
			button.setEnabled(false);
			if (text != null) {
				text.setText("");
			}
			currentValue = null;
		}
	}

}