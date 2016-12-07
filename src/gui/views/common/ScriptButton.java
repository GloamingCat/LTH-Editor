package gui.views.common;

import gui.shell.ScriptShell;
import lwt.dialog.LObjectShell;
import lwt.dialog.LShellFactory;
import lwt.widget.LObjectButton;

import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import data.Script;

public class ScriptButton extends LObjectButton<Script> {
	
	private String folder;
	
	private Text pathText;
	private StyledText paramText;
	
	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public ScriptButton(Composite parent, int style) {
		super(parent, style);
		dialog.setFactory(new LShellFactory<Script>() {
			@Override
			public LObjectShell<Script> createShell(Shell parent) {
				return new ScriptShell(parent, folder);
			}
		});
	}
	
	public void setPathText(Text text) {
		pathText = text;
	}
	
	public void setParamText(StyledText text) {
		paramText = text;
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
				pathText.setText(s.path);
			}
			if (paramText != null) {
				paramText.setText(s.param);
			}
		} else {
			button.setEnabled(false);
			if (pathText != null) {
				pathText.setText("");
			}
			if (paramText != null) {
				paramText.setText("");
			}
		}
		currentValue = value;
	}

}
