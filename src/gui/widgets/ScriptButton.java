package gui.widgets;

import gui.shell.ScriptShell;
import lwt.container.LContainer;
import lwt.dialog.LObjectShell;
import lwt.dialog.LShell;
import lwt.dialog.LShellFactory;
import lwt.widget.LObjectButton;
import lwt.widget.LText;

import data.subcontent.Script;

public class ScriptButton extends LObjectButton<Script> {
	
	private LText pathText;
	
	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public ScriptButton(LContainer parent, int style) {
		super(parent);
		setShellFactory(new LShellFactory<Script>() {
			@Override
			public LObjectShell<Script> createShell(LShell parent) {
				return new ScriptShell(parent, style);
			}
		});
	}
	
	public void setPathWidget(LText text) {
		pathText = text;
	}

	@Override
	public void setValue(Object value) {
		if (value != null) {
			setEnabled(true);
			Script s = (Script) value;
			if (pathText != null) {
				pathText.setValue(s.name);
			}
			currentValue = s;
		} else {
			setEnabled(false);
			if (pathText != null) {
				pathText.setValue("");
			}
			currentValue = null;
		}
	}

}