package gui.views.database.subcontent;

import gui.widgets.AudioButton;

import java.lang.reflect.Type;

import data.subcontent.Audio;
import gson.editor.GDefaultObjectEditor;
import lwt.container.LContainer;
import lwt.widget.LText;

public class AudioEditor extends GDefaultObjectEditor<Audio> {

	AudioButton btnSelectSound;
	
	public AudioEditor(LContainer parent) {
		super(parent, 2, false, false);
		LText txtSource = new LText(this, true);		
		btnSelectSound = new AudioButton(this, true);
		btnSelectSound.setTextWidget(txtSource);
		addControl(btnSelectSound, "audio");
		
	}

	@Override
	public Type getType() {
		return Audio.class;
	}
	
}
