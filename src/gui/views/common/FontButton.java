package gui.views.common;

import gui.shell.FontShell;
import lwt.dialog.LObjectShell;
import lwt.dialog.LShellFactory;
import lwt.widget.LObjectButton;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import data.Config;

public class FontButton extends LObjectButton<Config.Font> {
	
	private Text text;
	
	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public FontButton(Composite parent, int style) {
		super(parent, style);
		setShellFactory(new LShellFactory<Config.Font>() {
			@Override
			public LObjectShell<Config.Font> createShell(Shell parent) {
				return new FontShell(parent);
			}
		});
	}
	
	public void setPathText(Text text) {
		this.text = text;
	}

	@Override
	public void setValue(Object value) {
		if (value != null) {
			button.setEnabled(true);
			Config.Font s = (Config.Font) value;
			if (text != null) {
				text.setText(s.toString());
			}
			currentValue = s;
		} else {
			button.setEnabled(false);
			if (text != null) {
				text.setText("");
			}
			currentValue = null;
		}
	}

}