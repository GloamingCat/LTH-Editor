package gui.shell;

import gui.Tooltip;
import gui.Vocab;
import gui.widgets.AudioPlayer;
import lui.base.LFlags;
import lui.base.event.listener.LControlListener;
import lui.base.event.listener.LSelectionListener;

import java.util.ArrayList;

import lui.container.LPanel;
import lui.container.LFlexPanel;
import lui.dialog.LWindow;
import lui.base.event.LControlEvent;
import lui.base.event.LSelectionEvent;
import lui.widget.LFileSelector;
import lui.widget.LCombo;
import lui.widget.LLabel;
import lui.widget.LSpinner;

import project.Project;

import data.subcontent.Audio;

public class AudioPlayDialog extends ObjectEditorDialog<Audio> {
	
	protected LCombo cmbSound;
	protected LFileSelector selFile;
	protected Audio comboAudio = null;
	
	public static final int OPTIONAL = 0x01;
	public static final int TIMED = 0x02;
	public static final int BGM = 0x04;
	
	protected boolean optional = false;

	/**
	 * @wbp.parser.constructor
	 * @wbp.eval.method.parameter parent new lwt.dialog.LShell(800, 600)
	 */
	public AudioPlayDialog(LWindow parent, int style) {
		super(parent, style, Vocab.instance.AUDIOSHELL);
		setMinimumSize(400, 400);
	}
	
	@Override
	protected void createContent(int style) {
		super.createContent(style);
		contentEditor.setFillLayout(true);
		LFlexPanel form = new LFlexPanel(contentEditor, true);
		LPanel sound = new LPanel(form);
		sound.setGridLayout(1);
		selFile = new LFileSelector(sound, (style & OPTIONAL) > 0);
		selFile.addFileRestriction( (f) -> { 
			String name = f.getName();
			return name.endsWith(".ogg") || name.endsWith(".mp3") || name.endsWith(".wav");
		} );
		selFile.setFolder(Project.current.audioPath());
		selFile.getCellData().setExpand(true, true);
		cmbSound = new LCombo(sound, true);
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

		LPanel composite = new LPanel(form);
		composite.setGridLayout(2);
		composite.getCellData().setExpand(true, true);
		
		new LLabel(composite, Vocab.instance.VOLUME, Tooltip.instance.VOLUME);
		LSpinner spnVolume = new LSpinner(composite);
		spnVolume.setMaximum(1000);
		addControl(spnVolume, "volume");
		
		new LLabel(composite, Vocab.instance.PITCH, Tooltip.instance.PITCH);
		LSpinner spnPitch = new LSpinner(composite);
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
		
		AudioPlayer reproduction = new AudioPlayer(composite);
		reproduction.getCellData().setExpand(false, true);
		reproduction.getCellData().setSpread(2, 1);
		reproduction.getCellData().setAlignment(LFlags.RIGHT | LFlags.BOTTOM);
		reproduction.loop = (style & BGM) > 0;
		
		selFile.addSelectionListener(new LSelectionListener() {
			@Override
			public void onSelect(LSelectionEvent event) {
				reproduction.filename = 
						comboAudio != null ? comboAudio.name :
						event.data != null ? event.data.toString() : 
						"";
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
		
		form.setWeights(1, 1);
		
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
