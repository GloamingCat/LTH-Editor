package gui.widgets;

import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;

import gui.Vocab;
import lwt.LSoundPlayer;
import lwt.container.LContainer;
import lwt.container.LPanel;
import lwt.widget.LCommandButton;
import project.Project;

public class AudioPlayer extends LPanel {
	
	public String filename;
	public float volume;
	public float pitch;
	public boolean loop = true;
	
	public AudioPlayer(LContainer parent) {
		super(parent, 2, false);

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
		
		addDisposeListener(new DisposeListener() {
			@Override
			public void widgetDisposed(DisposeEvent e) {
				LSoundPlayer.stop();
			}
		});
	}
	
	public void refresh() {
		LSoundPlayer.refresh(volume * 0.01f, pitch * 0.01f);
	}

}
