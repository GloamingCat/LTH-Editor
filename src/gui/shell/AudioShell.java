package gui.shell;

import gui.Vocab;
import gui.widgets.AudioPlayer;
import gui.widgets.FileSelector;

import java.io.File;
import java.util.ArrayList;

import lwt.LFlags;
import lwt.container.LPanel;
import lwt.container.LSashPanel;
import lwt.dialog.LShell;
import lwt.event.LControlEvent;
import lwt.event.LSelectionEvent;
import lwt.event.listener.LControlListener;
import lwt.event.listener.LSelectionListener;
import lwt.widget.LCombo;
import lwt.widget.LLabel;
import lwt.widget.LSpinner;

import project.Project;

import data.subcontent.Audio;

public class AudioShell extends ObjectShell<Audio> {
	
	protected LCombo cmbSound;
	protected FileSelector selFile;
	protected Audio comboAudio = null;
	
	public static final int BGM = 0x01;
	

	public AudioShell(LShell parent, boolean isBGM) {
		super(parent);

		setMinimumSize(400, 400);
		contentEditor.setFillLayout(true);
		LSashPanel form = new LSashPanel(contentEditor, true);
		selFile = new FileSelector(form, false) {
			@Override
			protected boolean isValidFile(File f) {
				String name = f.getName();
				return name.endsWith(".ogg") || name.endsWith(".mp3") || name.endsWith(".wav");
			}
		};
		selFile.setFolder(Project.current.audioPath());
		cmbSound = new LCombo(selFile);
		cmbSound.setOptional(true);
		cmbSound.setIncludeID(false);
		selFile.addModifyListener(new LControlListener<Integer>() {
			@Override
			public void onModify(LControlEvent<Integer> event) {
				cmbSound.setValue(-1);
			}
		});
		cmbSound.addModifyListener(new LControlListener<Integer>() {
			@Override
			public void onModify(LControlEvent<Integer> event) {
				selFile.setValue(-1);
				ArrayList<Audio.Node> list = Project.current.config.getData().sounds;
				comboAudio = list.get(cmbSound.getValue());
			}
		});

		LPanel composite = new LPanel(form, 2, false);
		composite.setExpand(true, true);
		
		new LLabel(composite, Vocab.instance.VOLUME);
		
		LSpinner spnVolume = new LSpinner(composite);
		spnVolume.setMaximum(1000);
		addControl(spnVolume, "volume");
		
		new LLabel(composite, Vocab.instance.PITCH);
		
		LSpinner spnPitch = new LSpinner(composite);
		spnPitch.setMaximum(1000);
		spnPitch.setMinimum(1);
		addControl(spnPitch, "pitch");
		
		new LLabel(composite, Vocab.instance.TIME);
		
		LSpinner spnTime = new LSpinner(composite);
		spnTime.setMaximum(10000);
		spnTime.setMinimum(0);
		addControl(spnTime, "time");
		
		AudioPlayer reproduction = new AudioPlayer(composite);
		reproduction.setExpand(false, true);
		reproduction.setSpread(2, 1);
		reproduction.setAlignment(LFlags.RIGHT | LFlags.BOTTOM);
		reproduction.loop = isBGM;
		
		selFile.addSelectionListener(new LSelectionListener() {
			@Override
			public void onSelect(LSelectionEvent event) {
				reproduction.filename = comboAudio == null ? 
						event.data.toString() : comboAudio.name;
			}
		});
		spnVolume.addModifyListener(new LControlListener<Integer>() {
			@Override
			public void onModify(LControlEvent<Integer> event) {
				reproduction.volume = event.newValue *
						(comboAudio == null ? 1 : comboAudio.volume * 0.01f);
				reproduction.refresh();
			}
		});
		spnPitch.addModifyListener(new LControlListener<Integer>() {
			@Override
			public void onModify(LControlEvent<Integer> event) {
				reproduction.pitch = event.newValue *
						(comboAudio == null ? 1 : comboAudio.pitch * 0.01f);
				reproduction.refresh();
			}
		});
		
		form.setWeights(new int[] { 1, 1 });
		
		pack();
	}
	
	public void open(Audio initial) {
		super.open(initial);
		ArrayList<Audio.Node> list = Project.current.config.getData().sounds;
		cmbSound.setItems(list);
		selFile.setSelectedFile(initial.name);
		if (selFile.getValue() == null) {
			for (int i = 0; i < list.size(); i++) {
				if (list.get(i).key.equals(initial.name)) {
					cmbSound.setSelectionIndex(i);
					return;
				}
			}
		}
		cmbSound.setValue(-1);
	}

	@Override
	protected Audio createResult(Audio initial) {
		Audio audio = (Audio) contentEditor.getObject();
		audio.name = selFile.getSelectedFile();
		if (audio.name == null)
			audio.name = "";
		int i = cmbSound.getSelectionIndex();
		if (i >= 0) {
			Audio.Node node = (Audio.Node) Project.current.config.getData().sounds.get(i);
			audio.name = node.key;
		}
		return super.createResult(initial);
	}
	
}
