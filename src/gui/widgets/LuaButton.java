package gui.widgets;

import gui.shell.LuaShell;
import lwt.container.LContainer;
import lwt.dialog.LObjectShell;
import lwt.dialog.LShell;
import lwt.dialog.LShellFactory;
import lwt.widget.LObjectButton;
import lwt.widget.LText;

public class LuaButton extends LObjectButton<String> {
	
	private LText pathText;
	
	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public LuaButton(LContainer parent, boolean optional) {
		super(parent);
		setShellFactory(new LShellFactory<String>() {
			@Override
			public LObjectShell<String> createShell(LShell parent) {
				return new LuaShell(parent, optional);
			}
		});
	}

	public void setPathWidget(LText text) {
		pathText = text;
	}

	@Override
	public void setValue(Object value) {
		if (value != null) {
			button.setEnabled(true);
			String s = (String) value;
			if (pathText != null) {
				pathText.setValue(s);
			}
			currentValue = s;
		} else {
			button.setEnabled(false);
			if (pathText != null) {
				pathText.setValue("");
			}
			currentValue = null;
		}
	}

}