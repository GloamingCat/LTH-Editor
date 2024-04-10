package gui.widgets;

import gui.Vocab;
import lui.LSoundPlayer;
import lui.container.LContainer;
import lui.container.LPanel;
import lui.widget.LCommandButton;
import project.Project;

public class AudioPlayer extends LPanel {
	
	public String filename;
	public float volume;
	public float pitch;
	public boolean loop = true;
	
	public AudioPlayer(LContainer parent) {
		super(parent);
		setGridLayout(2);

		new LCommandButton(this, Vocab.instance.PLAY) {
			@Override
			protected void execute() {
				if (filename == null)
					return;
				if (filename.isEmpty()) {
					LSoundPlayer.stop();
					return;
				}
				String path = Project.current.audioPath() + filename;
				if (loop)
					LSoundPlayer.playBGM(path, volume * 0.01f, pitch * 0.01f);
				else
					LSoundPlayer.playSFX(path, volume * 0.01f, pitch * 0.01f);
			}
		};
		
		new LCommandButton(this, Vocab.instance.STOP) {
			@Override
			protected void execute() {
				LSoundPlayer.stop();
			}
		};
		
	}
	
	public void refresh() {
		LSoundPlayer.refresh(volume * 0.01f, pitch * 0.01f);
	}
	
	public void dispose() {
		super.dispose();
		LSoundPlayer.stop();
	}

}
