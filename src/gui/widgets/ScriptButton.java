package gui.widgets;

import gui.shell.ScriptShell;
import lui.container.LContainer;
import lui.dialog.LObjectWindow;
import lui.dialog.LWindow;
import lui.dialog.LWindowFactory;
import lui.widget.LObjectButton;
import lui.widget.LText;

import java.lang.reflect.Type;

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
		setShellFactory(new LWindowFactory<Script>() {
			@Override
			public LObjectWindow<Script> createWindow(LWindow parent) {
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

	@Override
	protected Type getType() {
		return Script.class;
	}

}