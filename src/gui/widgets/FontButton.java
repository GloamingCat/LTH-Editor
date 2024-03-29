package gui.widgets;

import gui.shell.system.FontShell;
import lwt.dialog.LObjectShell;
import lwt.dialog.LShellFactory;
import lwt.widget.LObjectButton;
import lwt.widget.LText;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;

import data.subcontent.FontData;

public class FontButton extends LObjectButton<FontData> {
	
	private LText text;
	
	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public FontButton(Composite parent, int style) {
		super(parent, style);
		setShellFactory(new LShellFactory<FontData>() {
			@Override
			public LObjectShell<FontData> createShell(Shell parent) {
				return new FontShell(parent);
			}
		});
	}
	
	public void setPathWidget(LText text) {
		this.text = text;
	}

	@Override
	public void setValue(Object value) {
		if (value != null) {
			button.setEnabled(true);
			FontData s = (FontData) value;
			if (text != null) {
				text.setValue(s.toString());
			}
			currentValue = s;
		} else {
			button.setEnabled(false);
			if (text != null) {
				text.setValue("");
			}
			currentValue = null;
		}
	}

}