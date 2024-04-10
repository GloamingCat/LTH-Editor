package gui.widgets;

import gui.shell.AudioPlayShell;
import lui.container.LContainer;
import lui.dialog.LObjectWindow;
import lui.dialog.LWindow;
import lui.dialog.LWindowFactory;
import lui.widget.LObjectButton;
import lui.widget.LText;

import java.lang.reflect.Type;

import data.subcontent.Audio;

public class AudioButton extends LObjectButton<Audio> {
	
	private LText text;
	
	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public AudioButton(LContainer parent, boolean optional) {
		super(parent);
		setShellFactory(new LWindowFactory<Audio>() {
			@Override
			public LObjectWindow<Audio> createWindow(LWindow parent) {
				return new AudioPlayShell(parent, AudioPlayShell.OPTIONAL);
			}
		});
	}
	
	public void setTextWidget(LText text) {
		this.text = text;
	}
	
	@Override
	public void setValue(Object value) {
		if (value != null) {
			setEnabled(true);
			Audio s = (Audio) value;
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
		return Audio.class;
	}

}