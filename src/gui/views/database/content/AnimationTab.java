package gui.views.database.content;

import gson.project.GObjectTreeSerializer;
import gui.Vocab;
import gui.shell.AudioShell;
import gui.views.database.DatabaseTab;
import gui.views.database.subcontent.TransformEditor;
import gui.widgets.LuaButton;
import gui.widgets.QuadButton;
import gui.widgets.SimpleEditableList;
import lwt.LFlags;
import lwt.container.LContainer;
import lwt.container.LFrame;
import lwt.container.LImage;
import lwt.container.LPanel;
import lwt.dialog.LObjectShell;
import lwt.dialog.LShell;
import lwt.dialog.LShellFactory;
import lwt.event.LControlEvent;
import lwt.event.listener.LControlListener;
import lwt.widget.LActionButton;
import lwt.widget.LLabel;
import lwt.widget.LSpinner;
import lwt.widget.LText;

import project.Project;
import data.Animation;
import data.subcontent.Audio;

public class AnimationTab extends DatabaseTab<Animation> {

	private LSpinner spnCols;
	private LSpinner spnRows;

	/**
	 * @wbp.parser.constructor
	 * @wbp.eval.method.parameter parent new lwt.dialog.LShell()
	 */
	public AnimationTab(LContainer parent) {
		super(parent);
		
		// Script
		
		new LLabel(grpGeneral, Vocab.instance.SCRIPT);
		LPanel script = new LPanel(grpGeneral, 2, false);
		script.setAlignment(LFlags.CENTER);
		
		LText txtScript = new LText(script, true);
		LuaButton btnScript = new LuaButton(script, true);
		btnScript.setPathWidget(txtScript);
		addControl(btnScript, "script");

		// Size
		
		new LLabel(grpGeneral, Vocab.instance.SIZE);
		
		LPanel size = new LPanel(grpGeneral, 4, false);
		size.setExpand(false, false);
		
		new LLabel(size, Vocab.instance.COLUMNS);
		
		spnCols = new LSpinner(size);
		addControl(spnCols, "cols");
		
		new LLabel(size, Vocab.instance.ROWS);
		
		spnRows = new LSpinner(size);
		addControl(spnRows, "rows");
		
		// Transform
		
		LFrame grpTransform = new LFrame(right, Vocab.instance.TRANSFORM, true, true);
		grpTransform.setExpand(true, false);
		
		TransformEditor transformEditor = new TransformEditor(grpTransform);
		addChild(transformEditor, "transform");
		
		// Audio
		
		LFrame grpAudio = new LFrame(right, Vocab.instance.SOUND, true, true);
		grpAudio.setExpand(true, true);
		SimpleEditableList<Audio> lstAudio = new SimpleEditableList<Audio>(grpAudio);
		lstAudio.type = Audio.class;
		lstAudio.setIncludeID(false);
		lstAudio.setShellFactory(new LShellFactory<Audio>() {
			@Override
			public LObjectShell<Audio> createShell(LShell parent) {
				return new AudioShell(parent, false);
			}
		});
		addChild(lstAudio, "audio");
		
		// Intro
		
		LFrame grpIntro = new LFrame(left, Vocab.instance.INTRO, 3, false);
		grpIntro.initGridData();
		
		new LLabel(grpIntro, Vocab.instance.PATTERN);
		
		LText txtIntroPattern = new LText(grpIntro);
		addControl(txtIntroPattern, "introPattern");
		LActionButton btnIntroPattern = new LActionButton(grpIntro, Vocab.instance.DEFAULT);
		
		new LLabel(grpIntro, Vocab.instance.DURATION);

		LText txtIntroDuration = new LText(grpIntro);
		addControl(txtIntroDuration, "introDuration");
		LActionButton btnIntroDuration = new LActionButton(grpIntro, Vocab.instance.DEFAULT);
		
		// Loop
		
		LFrame grpLoop = new LFrame(left, Vocab.instance.LOOP, 3, false);
		grpLoop.initGridData();
		
		new LLabel(grpLoop, Vocab.instance.PATTERN);
		
		LText txtLoopPattern = new LText(grpLoop);
		addControl(txtLoopPattern, "loopPattern");
		LActionButton btnLoopPattern = new LActionButton(grpLoop, Vocab.instance.DEFAULT);
		
		new LLabel(grpLoop, Vocab.instance.DURATION);

		LText txtLoopDuration = new LText(grpLoop);
		addControl(txtLoopDuration, "loopDuration");
		LActionButton btnLoopDuration = new LActionButton(grpLoop, Vocab.instance.DEFAULT);
		
		// Graphics
		
		LFrame grpImg = new LFrame(left, Vocab.instance.GRAPHICS, 1);
		grpImg.setExpand(true, true);
		
		LImage image = new LImage(grpImg);
		image.setExpand(true, true);
		image.setMinimumWidth(150);
		image.setAlignment(LFlags.TOP & LFlags.LEFT);
		
		QuadButton btnImage = new QuadButton(grpImg, true);
		addControl(btnImage, "quad");
		
		transformEditor.setImage(image);
		btnImage.setImage(image);
		btnImage.setTransform(transformEditor);
		
		addDurationListener(btnIntroDuration, txtIntroDuration);
		addDurationListener(btnLoopDuration, txtLoopDuration);
		addPatternListener(btnIntroPattern, txtIntroPattern);
		addPatternListener(btnLoopPattern, txtLoopPattern);
		
	}
	
	private void addPatternListener(LActionButton button, LText text) {
		button.addModifyListener(new LControlListener<Object>() {
			@Override
			public void onModify(LControlEvent<Object> e) {
				int cols = spnCols.getValue();
				String s = "1";
				for (int i = 2; i <= cols; i++) {
					s += " " + i;
				}
				text.modify(s);
			}
		});
	}
	
	private void addDurationListener(LActionButton button, LText text) {
		button.addModifyListener(new LControlListener<Object>() {
			@Override
			public void onModify(LControlEvent<Object> e) {
				if (text.getValue().isEmpty())
					return;
				String[] times = text.getValue().split("\\s+");
				if (times.length > 0) {
					int cols = spnCols.getValue();
					int time = Integer.parseInt(times[0]) / cols;
					String s = time + "";
					for (;cols > 1; cols--)
						s += " " + time;
					text.modify(s);
				}
			}
		});
	}

	@Override
	protected GObjectTreeSerializer getSerializer() {
		return Project.current.animations;
	}

}
