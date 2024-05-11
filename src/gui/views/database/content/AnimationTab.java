package gui.views.database.content;

import gui.Tooltip;
import gui.Vocab;
import gui.shell.AudioPlayDialog;
import gui.views.database.DatabaseTab;
import gui.views.database.subcontent.TransformEditor;
import gui.widgets.LuaButton;
import gui.widgets.QuadButton;
import gui.widgets.SimpleEditableList;
import lui.base.LFlags;
import lui.container.LContainer;
import lui.container.LFrame;
import lui.container.LImage;
import lui.container.LPanel;
import lui.dialog.LObjectDialog;
import lui.dialog.LWindow;
import lui.dialog.LWindowFactory;
import lui.widget.LActionButton;
import lui.widget.LLabel;
import lui.widget.LSpinner;
import lui.widget.LText;

import project.Project;
import data.Animation;
import data.subcontent.Audio;
import gson.GObjectTreeSerializer;

public class AnimationTab extends DatabaseTab<Animation> {

	private LSpinner spnCols;

    /**
	 * @wbp.parser.constructor
	 * @wbp.eval.method.parameter parent new lwt.dialog.LShell()
	 */
	public AnimationTab(LContainer parent) {
		super(parent);
	}

	@Override
	protected void createContent() {
		// Script
		
		LLabel lblScript = new LLabel(contentEditor.grpGeneral, Vocab.instance.SCRIPT, Tooltip.instance.SCRIPT);
		LPanel script = new LPanel(contentEditor.grpGeneral);
		script.setGridLayout(2);
		script.getCellData().setAlignment(LFlags.CENTER);
		script.getCellData().setExpand(true, false);
		
		LText txtScript = new LText(script, true);
		txtScript.getCellData().setExpand(true, false);
		LuaButton btnScript = new LuaButton(script, Vocab.instance.ANIMSCRIPTSHELL, true);
		btnScript.setPathWidget(txtScript);
		btnScript.addMenu(lblScript);
		btnScript.addMenu(txtScript);
		addControl(btnScript, "script");

		// Size
		
		LLabel lblSize = new LLabel(contentEditor.grpGeneral, Vocab.instance.SIZE, Tooltip.instance.SIZE);
		lblSize.setHoverText(Tooltip.instance.SIZE);
		LPanel size = new LPanel(contentEditor.grpGeneral);
		size.setGridLayout(4);
		size.getCellData().setExpand(true, false);
		
		LLabel lblCols = new LLabel(size, Vocab.instance.COLUMNS, Tooltip.instance.COLUMNS);
		spnCols = new LSpinner(size);
		spnCols.getCellData().setExpand(true, false);
		spnCols.addMenu(lblCols);
		addControl(spnCols, "cols");

		LLabel lblRows = new LLabel(size, Vocab.instance.ROWS, Tooltip.instance.ROWS);
        LSpinner spnRows = new LSpinner(size);
		spnRows.getCellData().setExpand(true, false);
		spnRows.addMenu(lblRows);
		addControl(spnRows, "rows");
		
		// Transform
		
		LFrame grpTransform = new LFrame(contentEditor.right, Vocab.instance.TRANSFORM);
		grpTransform.setFillLayout(true);
		grpTransform.setHoverText(Tooltip.instance.TRANSFORM);
		grpTransform.getCellData().setExpand(true, false);
		TransformEditor transformEditor = new TransformEditor(grpTransform);
		transformEditor.addMenu(grpTransform);
		addChild(transformEditor, "transform");
		
		// Audio
		
		LFrame grpAudio = new LFrame(contentEditor.right, Vocab.instance.SOUND);
		grpAudio.setFillLayout(true);
		grpAudio.setHoverText(Tooltip.instance.SOUND);
		grpAudio.getCellData().setExpand(true, true);
		SimpleEditableList<Audio> lstAudio = new SimpleEditableList<>(grpAudio);
		lstAudio.type = Audio.class;
		lstAudio.setIncludeID(false);
		lstAudio.setShellFactory(new LWindowFactory<>() {
			@Override
			public LObjectDialog<Audio> createWindow(LWindow parent) {
				return new AudioPlayDialog(parent, AudioPlayDialog.TIMED);
			}
		});
		lstAudio.addMenu(grpAudio);
		addChild(lstAudio, "audio");
		
		// Intro
		
		LFrame grpIntro = new LFrame(contentEditor.left, Vocab.instance.INTRO);
		grpIntro.setGridLayout(3);
		grpIntro.setHoverText(Tooltip.instance.INTRO);
		grpIntro.getCellData().setExpand(true, false);
		
		LLabel lblIntroPattern = new LLabel(grpIntro, Vocab.instance.PATTERN, Tooltip.instance.PATTERN);
		LText txtIntroPattern = new LText(grpIntro);
		txtIntroPattern.getCellData().setExpand(true, false);
		txtIntroPattern.addMenu(lblIntroPattern);
		addControl(txtIntroPattern, "introPattern");
		LActionButton btnIntroPattern = new LActionButton(grpIntro, Vocab.instance.DEFAULT);
		btnIntroPattern.setHoverText(Tooltip.instance.PATTERNDEFAULT);
		
		LLabel lblIntroDuration = new LLabel(grpIntro, Vocab.instance.DURATION, Tooltip.instance.DURATION);
		LText txtIntroDuration = new LText(grpIntro);
		txtIntroDuration.getCellData().setExpand(true, false);
		txtIntroDuration.addMenu(lblIntroDuration);
		addControl(txtIntroDuration, "introDuration");
		LActionButton btnIntroDuration = new LActionButton(grpIntro, Vocab.instance.DEFAULT);
		btnIntroDuration.setHoverText(Tooltip.instance.DURATIONDEFAULT);
		
		// Loop
		
		LFrame grpLoop = new LFrame(contentEditor.left, Vocab.instance.LOOP);
		grpLoop.setGridLayout(3);
		grpLoop.setHoverText(Tooltip.instance.LOOP);
		grpLoop.getCellData().setExpand(true, false);
		
		LLabel lblLoopPattern = new LLabel(grpLoop, Vocab.instance.PATTERN, Tooltip.instance.PATTERN);
		LText txtLoopPattern = new LText(grpLoop);
		txtLoopPattern.getCellData().setExpand(true, false);
		txtLoopPattern.addMenu(lblLoopPattern);
		addControl(txtLoopPattern, "loopPattern");
		LActionButton btnLoopPattern = new LActionButton(grpLoop, Vocab.instance.DEFAULT);
		btnLoopPattern.setHoverText(Tooltip.instance.PATTERNDEFAULT);
		
		LLabel lblLoopDuration = new LLabel(grpLoop, Vocab.instance.DURATION, Tooltip.instance.DURATION);
		LText txtLoopDuration = new LText(grpLoop);
		txtLoopDuration.getCellData().setExpand(true, false);
		txtLoopDuration.addMenu(lblLoopDuration);
		addControl(txtLoopDuration, "loopDuration");
		LActionButton btnLoopDuration = new LActionButton(grpLoop, Vocab.instance.DEFAULT);
		btnLoopDuration.setHoverText(Tooltip.instance.DURATIONDEFAULT);
		
		// Graphics
		
		LFrame grpImg = new LFrame(contentEditor.left, Vocab.instance.GRAPHICS);
		grpImg.setGridLayout(1);
		grpImg.setHoverText(Tooltip.instance.GRAPHICS);
		grpImg.getCellData().setExpand(true, true);
		
		LImage image = new LImage(grpImg);
		image.getCellData().setExpand(true, true);
		image.setAlignment(LFlags.TOP | LFlags.LEFT);
		
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
		button.addModifyListener(e -> {
            int cols = spnCols.getValue();
            StringBuilder s = new StringBuilder("1");
            for (int i = 2; i <= cols; i++) {
                s.append(" ").append(i);
            }
            text.modify(s.toString());
        });
	}
	
	private void addDurationListener(LActionButton button, LText text) {
		button.addModifyListener(e -> {
            if (text.getValue().isEmpty())
                return;
            String[] times = text.getValue().split("\\s+");
            if (times.length > 0) {
                int cols = spnCols.getValue();
                int time = Integer.parseInt(times[0]) / cols;
                StringBuilder s = new StringBuilder(time + "");
                for (;cols > 1; cols--)
                    s.append(" ").append(time);
                text.modify(s.toString());
            }
        });
	}

	@Override
	protected GObjectTreeSerializer getSerializer() {
		return Project.current.animations;
	}

}
