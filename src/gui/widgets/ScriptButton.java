package gui.widgets;

import gui.shell.ScriptShell;
import lwt.dialog.LObjectShell;
import lwt.dialog.LShellFactory;
import lwt.widget.LObjectButton;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import data.subcontent.Script;

public class ScriptButton extends LObjectButton<Script> {
	
	private Text pathText;
	
	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public ScriptButton(Composite parent, int optional) {
		super(parent, SWT.NONE);
		setShellFactory(new LShellFactory<Script>() {
			@Override
			public LObjectShell<Script> createShell(Shell parent) {
				return new ScriptShell(parent, optional);
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
			Script s = (Script) value;
			if (pathText != null) {
				pathText.setText(s.name);
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