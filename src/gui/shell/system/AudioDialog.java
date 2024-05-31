package gui.shell.system;

import data.subcontent.Audio;
import gui.Tooltip;
import gui.Vocab;
import lui.gson.GObjectDialog;
import gui.widgets.AudioPlayer;
import lui.base.LFlags;
import lui.base.LPrefs;
import lui.container.LPanel;
import lui.container.LFlexPanel;
import lui.dialog.LWindow;
import lui.widget.LFileSelector;
import lui.widget.LCheckBox;
import lui.widget.LLabel;
import lui.widget.LSpinner;
import lui.widget.LText;

import project.Project;

import data.subcontent.AudioPlay;

public class AudioDialog extends GObjectDialog<Audio> {
	
	protected LFileSelector selFile;
	protected AudioPlayer reproduction;
	protected LSpinner spnVolume;
	protected LSpinner spnPitch;
	
	public static final int OPTIONAL = 0x01;
	public static final int TIMED = 0x02;
	public static final int BGM = 0x04;
	
	public AudioDialog(LWindow parent, int style) {
		super(parent, 400, 400, style, Vocab.instance.AUDIOFILESHELL);
	}
	
	@Override
	protected void createContent(int style) {
		super.createContent(style);
		contentEditor.setFillLayout(true);
		contentEditor.setMargins(LPrefs.FRAMEMARGIN, LPrefs.FRAMEMARGIN);
		LFlexPanel form = new LFlexPanel(contentEditor, true);
		selFile = new LFileSelector(form, (style & OPTIONAL) > 0);
		selFile.addFileRestriction( (f) -> { 
			String name = f.getName();
			return name.endsWith(".ogg") || name.endsWith(".mp3") || name.endsWith(".wav");
		} );
		selFile.setFolder(Project.current.audioPath());

		LPanel composite = new LPanel(form);
		composite.setGridLayout(2);
		composite.getCellData().setExpand(true, true);

		new LLabel(composite, Vocab.instance.KEY, Tooltip.instance.KEY);
		LText txtKey = new LText(composite);
		txtKey.getCellData().setExpand(true, false);
		addControl(txtKey, "key");
		
		new LLabel(composite, Vocab.instance.VOLUME, Tooltip.instance.VOLUME);
		spnVolume = new LSpinner(composite);
		spnVolume.getCellData().setExpand(true, false);
		spnVolume.setMaximum(1000);
		addControl(spnVolume, "volume");

		new LLabel(composite, Vocab.instance.PITCH, Tooltip.instance.PITCH);
		spnPitch = new LSpinner(composite);
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

		reproduction = new AudioPlayer(composite);
		reproduction.getCellData().setExpand(false, true);
		reproduction.getCellData().setSpread(2, 1);
		reproduction.getCellData().setAlignment(LFlags.RIGHT | LFlags.TOP);
		reproduction.loop = (style & BGM) > 0;
		
		selFile.addModifyListener(event -> {
            String file = selFile.getFile(event.newValue);
            reproduction.filename = file != null ? file : "";
        });
		spnVolume.addModifyListener(event -> {
            reproduction.volume = event.newValue;
            reproduction.refresh();
        });
		spnPitch.addModifyListener(event -> {
            reproduction.pitch = event.newValue;
            reproduction.refresh();
        });
		LCheckBox loop = new LCheckBox(reproduction);
		loop.setText(Vocab.instance.LOOP);
		loop.setHoverText(Tooltip.instance.LOOPAUDIO);
		loop.getCellData().setSpread(2, 1);
		loop.getCellData().setAlignment(LFlags.RIGHT);
		loop.setValue(true);
		loop.addModifyListener(event -> reproduction.loop = event.newValue);
		
		form.setWeights(1, 1);
	}
	
	public void open(Audio initial) {
		selFile.setSelectedFile(initial.name);
		reproduction.volume = spnVolume.getValue();
		reproduction.pitch = spnPitch.getValue();
		super.open(initial);
	}

	@Override
	protected Audio createResult(Audio initial) {
		AudioPlay audio = contentEditor.getObject();
		audio.name = selFile.getSelectedFile();
		if (audio.name == null) {
			audio.name = "";
		}
		return super.createResult(initial);
	}
	
}
