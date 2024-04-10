package gui.widgets;

import java.lang.reflect.Type;

import gui.shell.LuaShell;
import lui.container.LContainer;
import lui.dialog.LObjectWindow;
import lui.dialog.LWindow;
import lui.dialog.LWindowFactory;
import lui.widget.LObjectButton;
import lui.widget.LText;

public class LuaButton extends LObjectButton<String> {
	
	private LText pathText;
	
	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public LuaButton(LContainer parent, String title, boolean optional) {
		super(parent);
		setShellFactory(new LWindowFactory<String>() {
			@Override
			public LObjectWindow<String> createWindow(LWindow parent) {
				return new LuaShell(parent, title, optional ? LuaShell.OPTIONAL : 0);
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
			String s = (String) value;
			if (pathText != null) {
				pathText.setValue(s);
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

	@Override
	protected Type getType() {
		return String.class;
	}

}