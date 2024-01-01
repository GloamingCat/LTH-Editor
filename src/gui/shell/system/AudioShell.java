package gui.shell.system;

import gui.Tooltip;
import gui.Vocab;
import gui.shell.ObjectShell;
import gui.widgets.AudioPlayer;
import lwt.LFlags;
import lwt.container.LPanel;
import lwt.container.LSashPanel;
import lwt.dialog.LShell;
import lwt.event.LControlEvent;
import lwt.event.LSelectionEvent;
import lwt.event.listener.LControlListener;
import lwt.event.listener.LSelectionListener;
import lwt.widget.LFileSelector;
import lwt.widget.LCheckBox;
import lwt.widget.LLabel;
import lwt.widget.LSpinner;
import lwt.widget.LText;

import project.Project;

import data.subcontent.Audio;

public class AudioShell extends ObjectShell<Audio.Node> {
	
	protected LFileSelector selFile;
	protected AudioPlayer reproduction;
	protected LSpinner spnVolume;
	protected LSpinner spnPitch;
	
	public static final int OPTIONAL = 0x01;
	public static final int TIMED = 0x02;
	public static final int BGM = 0x04;
	
	public AudioShell(LShell parent, int style) {
		super(parent, 400, 400);
		contentEditor.setFillLayout(true);
		LSashPanel form = new LSashPanel(contentEditor, true);
		selFile = new LFileSelector(form, (style & OPTIONAL) > 0);
		selFile.addFileRestriction( (f) -> { 
			String name = f.getName();
			return name.endsWith(".ogg") || name.endsWith(".mp3") || name.endsWith(".wav");
		} );
		selFile.setFolder(Project.current.audioPath());

		LPanel composite = new LPanel(form, 2, false);
		composite.setExpand(true, true);

		new LLabel(composite, Vocab.instance.KEY, Tooltip.instance.KEY);
		LText txtKey = new LText(composite);
		addControl(txtKey, "key");
		
		new LLabel(composite, Vocab.instance.VOLUME, Tooltip.instance.VOLUME);
		spnVolume = new LSpinner(composite);
		spnVolume.setMaximum(1000);
		addControl(spnVolume, "volume");
		
		new LLabel(composite, Vocab.instance.PITCH, Tooltip.instance.PITCH);
		spnPitch = new LSpinner(composite);
		spnPitch.setMaximum(1000);
		spnPitch.setMinimum(1);
		addControl(spnPitch, "pitch");
		
		if ((style & TIMED) > 0) {
			new LLabel(composite, Vocab.instance.TIME, Tooltip.instance.TIME);
			LSpinner spnTime = new LSpinner(composite);
			spnTime.setMaximum(10000);
			spnTime.setMinimum(0);
			addControl(spnTime, "time");
		}
		
		new LLabel(composite, 1, 1);
		LCheckBox loop = new LCheckBox(composite);
		loop.setText(Vocab.instance.LOOP);
		loop.setHoverText(Tooltip.instance.LOOPAUDIO);
		loop.setValue(true);
		
		reproduction = new AudioPlayer(composite);
		reproduction.setExpand(false, true);
		reproduction.setSpread(2, 1);
		reproduction.setAlignment(LFlags.RIGHT | LFlags.BOTTOM);
		reproduction.loop = (style & BGM) > 0;
		
		selFile.addSelectionListener(new LSelectionListener() {
			@Override
			public void onSelect(LSelectionEvent event) {
				String file = selFile.getSelectedFile();
				reproduction.filename = file != null ? file : "";
			}
		});
		spnVolume.addModifyListener(new LControlListener<Integer>() {
			@Override
			public void onModify(LControlEvent<Integer> event) {
				reproduction.volume = event.newValue;
				reproduction.refresh();
			}
		});
		spnPitch.addModifyListener(new LControlListener<Integer>() {
			@Override
			public void onModify(LControlEvent<Integer> event) {
				reproduction.pitch = event.newValue;
				reproduction.refresh();
			}
		});
		loop.addModifyListener(new LControlListener<Boolean>() {
			@Override
			public void onModify(LControlEvent<Boolean> event) {
				reproduction.loop = event.newValue;
			}
		});
		
		form.setWeights(new int[] { 1, 1 });
		
		pack();
	}
	
	public void open(Audio.Node initial) {
		super.open(initial);
		selFile.setSelectedFile(initial.name);
		reproduction.volume = spnVolume.getValue();
		reproduction.pitch = spnPitch.getValue();
	}

	@Override
	protected Audio.Node createResult(Audio.Node initial) {
		Audio audio = (Audio) contentEditor.getObject();
		audio.name = selFile.getSelectedFile();
		if (audio.name == null) {
			audio.name = "";
		}
		return super.createResult(initial);
	}
	
}
