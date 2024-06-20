package gui.widgets;

import gui.shell.AudioPlayDialog;
import lui.container.LContainer;
import lui.dialog.LObjectDialog;
import lui.dialog.LWindow;
import lui.dialog.LWindowFactory;
import lui.widget.LObjectButton;
import lui.widget.LText;

import java.lang.reflect.Type;

import data.subcontent.AudioPlay;

public class AudioButton extends LObjectButton<AudioPlay> {
	
	private LText text;
	
	public AudioButton(LContainer parent, boolean optional) {
		super(parent);
		setShellFactory(new LWindowFactory<AudioPlay>() {
			@Override
			public LObjectDialog<AudioPlay> createWindow(LWindow parent) {
				return new AudioPlayDialog(parent, AudioPlayDialog.OPTIONAL);
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
			AudioPlay s = (AudioPlay) value;
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
		return AudioPlay.class;
	}

}