package gui.widgets;

import gui.shell.ScriptDialog;
import gui.views.ScriptEditor;
import lui.container.LContainer;
import lui.dialog.LObjectDialog;
import lui.dialog.LWindow;
import lui.dialog.LWindowFactory;
import lui.widget.LObjectButton;
import lui.widget.LText;

import java.lang.reflect.Type;

import data.subcontent.Script;

public class ScriptButton extends LObjectButton<Script> {
	
	private LText pathText;

	public ScriptButton(LContainer parent, boolean optional, boolean onLoad, boolean scope) {
		super(parent);
		int trigger = onLoad ? ScriptEditor.ONLOAD : ScriptEditor.ONEXIT;
		if (scope)
			trigger = trigger | ScriptEditor.SCOPE;
		final int style = optional ? trigger | ScriptEditor.OPTIONAL : trigger;
		setShellFactory(new LWindowFactory<>() {
			@Override
			public LObjectDialog<Script> createWindow(LWindow parent) {
				return new ScriptDialog(parent, style);
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
			if (pathText != null) {
				pathText.setValue(value.toString());
			}
			currentValue = (Script) value;
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