package gui.widgets;

import gui.shell.LuaShell;
import lwt.dialog.LObjectShell;
import lwt.dialog.LShellFactory;
import lwt.widget.LObjectButton;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class LuaButton extends LObjectButton<String> {
	
	private Text pathText;
	
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
	
	public void setPathText(Text text) {
		pathText = text;
	}

	@Override
	public void setValue(Object value) {
		if (value != null) {
			button.setEnabled(true);
			String s = (String) value;
			if (pathText != null) {
				pathText.setText(s);
			}
			currentValue = s;
		} else {
			button.setEnabled(false);
			if (pathText != null) {
				pathText.setText("");
			}
			currentValue = null;
		}
	}

}