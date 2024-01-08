package gui.views.database.content;

import gson.project.GObjectTreeSerializer;
import gui.Tooltip;
import gui.Vocab;
import gui.shell.AudioPlayShell;
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
		
		LLabel lblScript = new LLabel(grpGeneral, Vocab.instance.SCRIPT, Tooltip.instance.SCRIPT);
		LPanel script = new LPanel(grpGeneral);
		script.setGridLayout(2);
		script.setAlignment(LFlags.CENTER);
		
		LText txtScript = new LText(script, true);
		LuaButton btnScript = new LuaButton(script, Vocab.instance.ANIMSCRIPTSHELL, true);
		btnScript.setPathWidget(txtScript);
		btnScript.addMenu(lblScript);
		btnScript.addMenu(txtScript);
		addControl(btnScript, "script");

		// Size
		
		LLabel lblSize = new LLabel(grpGeneral, Vocab.instance.SIZE, Tooltip.instance.SIZE);
		lblSize.setHoverText(Tooltip.instance.SIZE);
		LPanel size = new LPanel(grpGeneral);
		size.setGridLayout(4);
		size.setExpand(false, false);
		
		LLabel lblCols = new LLabel(size, Vocab.instance.COLUMNS, Tooltip.instance.COLUMNS);
		spnCols = new LSpinner(size);
		spnCols.addMenu(lblCols);
		addControl(spnCols, "cols");
		
		LLabel lblRows = new LLabel(size, Vocab.instance.ROWS, Tooltip.instance.ROWS);
		spnRows = new LSpinner(size);
		spnRows.addMenu(lblRows);
		addControl(spnRows, "rows");
		
		// Transform
		
		LFrame grpTransform = new LFrame(right, (String) Vocab.instance.TRANSFORM);
		grpTransform.setFillLayout(true);
		grpTransform.setHoverText(Tooltip.instance.TRANSFORM);
		grpTransform.setExpand(true, false);
		TransformEditor transformEditor = new TransformEditor(grpTransform);
		transformEditor.addMenu(grpTransform);
		addChild(transformEditor, "transform");
		
		// Audio
		
		LFrame grpAudio = new LFrame(right, (String) Vocab.instance.SOUND);
		grpAudio.setFillLayout(true);
		grpAudio.setHoverText(Tooltip.instance.SOUND);
		grpAudio.setExpand(true, true);
		SimpleEditableList<Audio> lstAudio = new SimpleEditableList<Audio>(grpAudio);
		lstAudio.type = Audio.class;
		lstAudio.setIncludeID(false);
		lstAudio.setShellFactory(new LShellFactory<Audio>() {
			@Override
			public LObjectShell<Audio> createShell(LShell parent) {
				return new AudioPlayShell(parent, AudioPlayShell.TIMED);
			}
		});
		lstAudio.addMenu(grpAudio);
		addChild(lstAudio, "audio");
		
		// Intro
		
		LFrame grpIntro = new LFrame(left, Vocab.instance.INTRO);
		grpIntro.setGridLayout(3);
		grpIntro.setHoverText(Tooltip.instance.INTRO);
		grpIntro.setExpand(true, false);
		
		LLabel lblIntroPattern = new LLabel(grpIntro, Vocab.instance.PATTERN, Tooltip.instance.PATTERN);
		LText txtIntroPattern = new LText(grpIntro);
		txtIntroPattern.addMenu(lblIntroPattern);
		addControl(txtIntroPattern, "introPattern");
		LActionButton btnIntroPattern = new LActionButton(grpIntro, Vocab.instance.DEFAULT);
		btnIntroPattern.setHoverText(Tooltip.instance.PATTERNDEFAULT);
		
		LLabel lblIntroDuration = new LLabel(grpIntro, Vocab.instance.DURATION, Tooltip.instance.DURATION);
		LText txtIntroDuration = new LText(grpIntro);
		txtIntroDuration.addMenu(lblIntroDuration);
		addControl(txtIntroDuration, "introDuration");
		LActionButton btnIntroDuration = new LActionButton(grpIntro, Vocab.instance.DEFAULT);
		btnIntroDuration.setHoverText(Tooltip.instance.DURATIONDEFAULT);
		
		// Loop
		
		LFrame grpLoop = new LFrame(left, Vocab.instance.LOOP);
		grpLoop.setGridLayout(3);
		grpLoop.setHoverText(Tooltip.instance.LOOP);
		grpLoop.setExpand(true, false);
		
		LLabel lblLoopPattern = new LLabel(grpLoop, Vocab.instance.PATTERN, Tooltip.instance.PATTERN);
		LText txtLoopPattern = new LText(grpLoop);
		txtLoopPattern.addMenu(lblLoopPattern);
		addControl(txtLoopPattern, "loopPattern");
		LActionButton btnLoopPattern = new LActionButton(grpLoop, Vocab.instance.DEFAULT);
		btnLoopPattern.setHoverText(Tooltip.instance.PATTERNDEFAULT);
		
		LLabel lblLoopDuration = new LLabel(grpLoop, Vocab.instance.DURATION, Tooltip.instance.DURATION);
		LText txtLoopDuration = new LText(grpLoop);
		txtLoopDuration.addMenu(lblLoopDuration);
		addControl(txtLoopDuration, "loopDuration");
		LActionButton btnLoopDuration = new LActionButton(grpLoop, Vocab.instance.DEFAULT);
		btnLoopDuration.setHoverText(Tooltip.instance.DURATIONDEFAULT);
		
		// Graphics
		
		LFrame grpImg = new LFrame(left, Vocab.instance.GRAPHICS);
		grpImg.setGridLayout(1);
		grpImg.setHoverText(Tooltip.instance.GRAPHICS);
		grpImg.setExpand(true, true);
		
		LImage image = new LImage(grpImg);
		image.setExpand(true, true);
		image.setMinimumWidth(150);
		image.setAlignment(LFlags.TOP & LFlags.LEFT);
		
		QuadButton btnImage = new QuadButton(grpImg, true);
		btnImage.addMenu(image);
		btnImage.addMenu(grpImg);
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
