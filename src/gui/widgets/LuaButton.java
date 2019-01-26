package gui.widgets;

import gui.shell.LuaShell;
import lwt.dialog.LObjectShell;
import lwt.dialog.LShellFactory;
import lwt.widget.LObjectButton;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import data.subcontent.Script;

public class LuaButton extends LObjectButton<Script> {
	
	private String folder;
	private Text pathText;
	
	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public LuaButton(Composite parent, int optional) {
		super(parent, SWT.NONE);
		setShellFactory(new LShellFactory<Script>() {
			@Override
			public LObjectShell<Script> createShell(Shell parent) {
				return new LuaShell(parent, folder, optional);
			}
		});
	}
	
	public void setPathText(Text text) {
		pathText = text;
	}
	
	public void setFolder(String folder) {
		this.folder = folder;
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