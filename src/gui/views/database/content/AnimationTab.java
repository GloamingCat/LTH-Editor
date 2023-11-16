package gui.views.database.content;

import gson.project.GObjectTreeSerializer;
import gui.Vocab;
import gui.shell.AudioShell;
import gui.views.database.DatabaseTab;
import gui.views.database.subcontent.TransformEditor;
import gui.widgets.LuaButton;
import gui.widgets.QuadButton;
import gui.widgets.SimpleEditableList;
import lwt.dialog.LObjectShell;
import lwt.dialog.LShellFactory;
import lwt.event.LControlEvent;
import lwt.event.listener.LControlListener;
import lwt.widget.LActionButton;
import lwt.widget.LImage;
import lwt.widget.LLabel;
import lwt.widget.LSpinner;
import lwt.widget.LText;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.layout.FillLayout;

import project.Project;
import data.Animation;
import data.subcontent.Audio;

public class AnimationTab extends DatabaseTab<Animation> {

	private LSpinner spnCols;
	private LSpinner spnRows;
	
	/**
	 * Create the composite.
	 * @param parent
	 */
	public AnimationTab(Composite parent) {
		super(parent);
		
		GridData gridData = new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1);
		gridData.widthHint = 240;
		grpGeneral.setLayoutData(gridData);
		
		// Script
		
		new LLabel(grpGeneral, Vocab.instance.SCRIPT);
		
		Composite script = new Composite(grpGeneral,  SWT.NONE);
		script.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		GridLayout gl_script = new GridLayout(2, false);
		gl_script.marginWidth = 0;
		gl_script.marginHeight = 0;
		script.setLayout(gl_script);
		
		LText txtScript = new LText(script, true);
		txtScript.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		LuaButton btnScript = new LuaButton(script, 1);
		btnScript.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		btnScript.setPathWidget(txtScript);
		addControl(btnScript, "script");

		// Size
		
		Composite size = new Composite(grpGeneral, SWT.NONE);
		size.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 2, 1));
		GridLayout gl_size = new GridLayout(4, false);
		gl_size.marginWidth = 0;
		gl_size.marginHeight = 0;
		size.setLayout(gl_size);
		
		new LLabel(size, Vocab.instance.COLUMNS);
		
		spnCols = new LSpinner(size, SWT.NONE);
		spnCols.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		addControl(spnCols, "cols");
		
		new LLabel(size, Vocab.instance.ROWS);
		
		spnRows = new LSpinner(size, SWT.NONE);
		spnRows.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		addControl(spnRows, "rows");
		
		// Transform
		
		Group grpTransform = new Group(right, SWT.NONE);
		grpTransform.setText(Vocab.instance.TRANSFORM);
		grpTransform.setLayout(new FillLayout());
		grpTransform.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));
		
		TransformEditor transformEditor = new TransformEditor(grpTransform, SWT.NONE);
		addChild(transformEditor, "transform");
		
		// Audio
		
		Group grpAudio = new Group(right, SWT.NONE);
		grpAudio.setLayout(new FillLayout(SWT.HORIZONTAL));
		grpAudio.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		grpAudio.setText(Vocab.instance.SOUND);
		
		SimpleEditableList<Audio> lstAudio = new SimpleEditableList<Audio>(grpAudio, SWT.NONE);
		lstAudio.type = Audio.class;
		lstAudio.setIncludeID(false);
		lstAudio.setShellFactory(new LShellFactory<Audio>() {
			@Override
			public LObjectShell<Audio> createShell(Shell parent) {
				return new AudioShell(parent, 0);
			}
		});
		addChild(lstAudio, "audio");
		
		// Intro
		
		Group grpIntro = new Group(left, SWT.NONE);
		grpIntro.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));
		grpIntro.setLayout(new GridLayout(3, false));
		grpIntro.setText(Vocab.instance.INTRO);
		
		new LLabel(grpIntro, Vocab.instance.PATTERN);
		
		LText txtIntroPattern = new LText(grpIntro);
		txtIntroPattern.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		addControl(txtIntroPattern, "introPattern");
		
		LActionButton btnIntroPattern = new LActionButton(grpIntro, Vocab.instance.DEFAULT);
		
		new LLabel(grpIntro, Vocab.instance.DURATION);

		LText txtIntroDuration = new LText(grpIntro);
		txtIntroDuration.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		addControl(txtIntroDuration, "introDuration");
		
		LActionButton btnIntroDuration = new LActionButton(grpIntro, Vocab.instance.DEFAULT);
		
		// Loop
		
		Group grpLoop = new Group(left, SWT.NONE);
		grpLoop.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));
		grpLoop.setLayout(new GridLayout(3, false));
		grpLoop.setText(Vocab.instance.LOOP);
		
		new LLabel(grpLoop, Vocab.instance.PATTERN);
		
		LText txtLoopPattern = new LText(grpLoop);
		txtLoopPattern.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		addControl(txtLoopPattern, "loopPattern");
		
		LActionButton btnLoopPattern = new LActionButton(grpLoop, Vocab.instance.DEFAULT);
		
		new LLabel(grpLoop, Vocab.instance.DURATION);

		LText txtLoopDuration = new LText(grpLoop);
		txtLoopDuration.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		addControl(txtLoopDuration, "loopDuration");
		
		LActionButton btnLoopDuration = new LActionButton(grpLoop, Vocab.instance.DEFAULT);
		
		// Graphics
		
		Group grpImg = new Group(left, SWT.NONE);
		grpImg.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		grpImg.setLayout(new GridLayout(1, false));
		grpImg.setText(Vocab.instance.GRAPHICS);
		
		LImage image = new LImage(grpImg, SWT.NONE);
		GridData gd_image = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		gd_image.widthHint = 150;
		image.setLayoutData(gd_image);
		
		QuadButton btnImage = new QuadButton(grpImg, 1);
		btnImage.setLayoutData(new GridData(SWT.LEFT, SWT.TOP, false, false, 1, 1));
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
