package gui.widgets;

import gui.shell.system.FontShell;
import lwt.container.LContainer;
import lwt.dialog.LObjectWindow;
import lwt.dialog.LWindow;
import lwt.dialog.LWindowFactory;
import lwt.widget.LObjectButton;
import lwt.widget.LText;

import java.lang.reflect.Type;

import data.subcontent.FontData;

public class FontButton extends LObjectButton<FontData> {
	
	private LText text;
	
	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public FontButton(LContainer parent) {
		super(parent);
		setShellFactory(new LWindowFactory<FontData>() {
			@Override
			public LObjectWindow<FontData> createWindow(LWindow parent) {
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
			setEnabled(true);
			FontData s = (FontData) value;
			if (text != null) {
				text.setValue(s.toString());
			}
			currentValue = s;
		} else {
			setEnabled(false);
			if (text != null) {
				text.setValue("");
			}
			currentValue = null;
		}
	}

	@Override
	protected Type getType() {
		return FontData.class;
	}

}