package gui.widgets;

import gui.shell.LuaShell;
import lwt.dialog.LObjectShell;
import lwt.dialog.LShellFactory;
import lwt.widget.LObjectButton;
import lwt.widget.LText;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;

public class LuaButton extends LObjectButton<String> {
	
	private LText pathText;
	
	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public LuaButton(Composite parent, int optional) {
		super(parent, SWT.NONE);
		setShellFactory(new LShellFactory<String>() {
			@Override
			public LObjectShell<String> createShell(Shell parent) {
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