package gui.shell;

import data.subcontent.Audio;
import gui.Tooltip;
import gui.Vocab;
import gui.widgets.AudioPlayer;
import lui.base.LFlags;

import java.util.ArrayList;

import lui.container.LPanel;
import lui.container.LFlexPanel;
import lui.dialog.LWindow;
import lui.gson.GObjectDialog;
import lui.widget.LFileSelector;
import lui.widget.LCombo;
import lui.widget.LLabel;
import lui.widget.LSpinner;

import project.Project;

import data.subcontent.AudioPlay;

public class AudioPlayDialog extends GObjectDialog<AudioPlay> {
	
	protected LCombo cmbSound;
	protected LFileSelector selFile;
	protected AudioPlay comboAudio = null;
	
	public static final int OPTIONAL = 0x01;
	public static final int TIMED = 0x02;
	public static final int BGM = 0x04;
	
	protected boolean optional = false;

	/**
	 * @wbp.parser.constructor
	 * @wbp.eval.method.parameter parent new lwt.dialog.LShell(800, 600)
	 */
	public AudioPlayDialog(LWindow parent, int style) {
		super(parent, 400, 400, style, Vocab.instance.AUDIOSHELL);
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
		cmbSound.getCellData().setExpand(true, false);
		cmbSound.setOptional(true);
		cmbSound.setIncludeID(false);
		cmbSound.addModifyListener(event -> {
            selFile.setValue(-1);
            ArrayList<Audio> list = Project.current.config.getData().sounds;
            comboAudio = list.get(event.newValue);
        });
		selFile.addModifyListener(event -> cmbSound.setValue(-1));

		LPanel composite = new LPanel(form);
		composite.setGridLayout(2);
		composite.getCellData().setExpand(true, true);
		
		new LLabel(composite, Vocab.instance.VOLUME, Tooltip.instance.VOLUME);
		LSpinner spnVolume = new LSpinner(composite);
		spnVolume.getCellData().setExpand(true, false);
		spnVolume.setMaximum(1000);
		addControl(spnVolume, "volume");

		new LLabel(composite, Vocab.instance.PITCH, Tooltip.instance.PITCH);
		LSpinner spnPitch = new LSpinner(composite);
		spnPitch.getCellData().setExpand(true, false);
		spnPitch.setMaximum(1000);
		spnPitch.setMinimum(1);
		addControl(spnPitch, "pitch");
		
		if ((style & TIMED) > 0) {
			new LLabel(composite, Vocab.instance.TIME, Tooltip.instance.TIME);
			LSpinner spnTime = new LSpinner(composite);
			spnTime.getCellData().setExpand(true, false);
			spnTime.setMaximum(10000);
			spnTime.setMinimum(0);
			addControl(spnTime, "time");
		}
		
		AudioPlayer reproduction = new AudioPlayer(composite);
		reproduction.getCellData().setExpand(false, true);
		reproduction.getCellData().setSpread(2, 1);
		reproduction.getCellData().setAlignment(LFlags.RIGHT | LFlags.TOP);
		reproduction.loop = (style & BGM) > 0;
		
		selFile.addModifyListener(event -> reproduction.filename =
                comboAudio != null ? comboAudio.name :
                event.newValue != null ? event.newValue.toString() :
                "");
		spnVolume.addModifyListener(event -> {
            reproduction.volume = event.newValue *
                    (comboAudio == null ? 1 : comboAudio.volume * 0.01f);
            reproduction.refresh();
        });
		spnPitch.addModifyListener(event -> {
            reproduction.pitch = event.newValue *
                    (comboAudio == null ? 1 : comboAudio.pitch * 0.01f);
            reproduction.refresh();
        });
		
		form.setWeights(1, 1);
	}
	
	public void open(AudioPlay initial) {
		ArrayList<Audio> list = Project.current.config.getData().sounds;
		cmbSound.setItems(list);
		selFile.setSelectedFile(initial.name);
		var item = list.stream().filter(a -> a.key.equals(initial.name)).findAny();
		if (item.isPresent())
			cmbSound.setValue(list.indexOf(item.get()));
		else
			cmbSound.setValue(-1);
		super.open(initial);
	}

	@Override
	protected AudioPlay createResult(AudioPlay initial) {
		AudioPlay audio = contentEditor.getObject();
		audio.name = selFile.getSelectedFile();
		if (audio.name == null)
			audio.name = "";
		int i = cmbSound.getValue();
		if (i >= 0) {
			Audio node = Project.current.config.getData().sounds.get(i);
			audio.name = node.key;
		}
		return super.createResult(initial);
	}
	
}
