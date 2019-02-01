package gui.views.database.content;

import gson.project.GObjectTreeSerializer;
import gui.Vocab;
import gui.shell.AudioShell;
import gui.views.database.DatabaseTab;
import gui.views.database.subcontent.TagList;
import gui.views.database.subcontent.TransformEditor;
import gui.widgets.LuaButton;
import gui.widgets.QuadButton;
import gui.widgets.SimpleEditableList;
import lwt.dialog.LObjectShell;
import lwt.dialog.LShellFactory;
import lwt.widget.LImage;
import lwt.widget.LSpinner;
import lwt.widget.LText;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.layout.FillLayout;

import project.Project;

import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

import data.subcontent.Audio;

public class AnimationTab extends DatabaseTab {

	private LSpinner spnCols;
	private LSpinner spnRows;
	
	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public AnimationTab(Composite parent, int style) {
		super(parent, style);
		
		GridData gridData = new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1);
		gridData.widthHint = 240;
		grpGeneral.setLayoutData(gridData);
		
		// Script
		
		Label lblScript = new Label(grpGeneral,  SWT.NONE);
		lblScript.setText(Vocab.instance.SCRIPT);
		
		Composite script = new Composite(grpGeneral,  SWT.NONE);
		script.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		GridLayout gl_script = new GridLayout(2, false);
		gl_script.marginWidth = 0;
		gl_script.marginHeight = 0;
		script.setLayout(gl_script);
		
		Text txtScript = new Text(script, SWT.BORDER | SWT.READ_ONLY);
		txtScript.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		LuaButton btnScript = new LuaButton(script, 1);
		btnScript.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		btnScript.setPathText(txtScript);
		addControl(btnScript, "script");

		// Size
		
		Composite size = new Composite(grpGeneral, SWT.NONE);
		size.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 2, 1));
		GridLayout gl_size = new GridLayout(4, false);
		gl_size.marginWidth = 0;
		gl_size.marginHeight = 0;
		size.setLayout(gl_size);
		
		Label lblColumns = new Label(size, SWT.NONE);
		lblColumns.setText(Vocab.instance.COLUMNS);
		
		spnCols = new LSpinner(size, SWT.NONE);
		spnCols.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		addControl(spnCols, "cols");
		
		Label lblRows = new Label(size, SWT.NONE);
		lblRows.setText(Vocab.instance.ROWS);
		
		spnRows = new LSpinner(size, SWT.NONE);
		spnRows.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		addControl(spnRows, "rows");

		// Transform
		
		Composite right = new Composite(contentEditor, SWT.NONE);
		GridLayout gl_right = new GridLayout(1, false);
		gl_right.marginWidth = 0;
		gl_right.marginHeight = 0;
		gl_right.verticalSpacing = 0;
		right.setLayout(gl_right);
		GridData gd_right = new GridData(SWT.FILL, SWT.FILL, false, false, 1, 4);
		gd_right.widthHint = 300;
		right.setLayoutData(gd_right);
		
		Group grpTransform = new Group(right, SWT.NONE);
		grpTransform.setText(Vocab.instance.TRANSFORM);
		grpTransform.setLayout(new FillLayout());
		grpTransform.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));
		
		TransformEditor transformEditor = new TransformEditor(grpTransform, SWT.NONE);
		addChild(transformEditor, "transform");
		
		// Graphics
		
		Group grpImg = new Group(right, SWT.NONE);
		grpImg.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		GridLayout gl_grpImg = new GridLayout(1, false);
		gl_grpImg.marginWidth = 0;
		gl_grpImg.marginHeight = 0;
		grpImg.setLayout(gl_grpImg);
		grpImg.setText(Vocab.instance.GRAPHICS);
		
		LImage image = new LImage(grpImg, SWT.NONE);
		GridData gd_image = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		gd_image.widthHint = 150;
		image.setLayoutData(gd_image);
		
		QuadButton btnImage = new QuadButton(grpImg, 0);
		btnImage.setLayoutData(new GridData(SWT.LEFT, SWT.TOP, false, false, 1, 1));
		addControl(btnImage, "quad");
		
		transformEditor.setImage(image);
		btnImage.setImage(image);
		btnImage.setTransform(transformEditor);
		
		// Intro
		
		Group grpIntro = new Group(contentEditor, SWT.NONE);
		grpIntro.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));
		grpIntro.setLayout(new GridLayout(3, false));
		grpIntro.setText(Vocab.instance.INTRO);
		
		Label lblIntroPattern = new Label(grpIntro, SWT.NONE);
		lblIntroPattern.setText(Vocab.instance.PATTERN);
		
		LText txtIntroPattern = new LText(grpIntro, SWT.NONE);
		txtIntroPattern.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		addControl(txtIntroPattern, "introPattern");
		
		Button btnIntroPattern = new Button(grpIntro, SWT.NONE);
		btnIntroPattern.setText(Vocab.instance.DEFAULT);
		
		Label lblIntroDuration = new Label(grpIntro, SWT.NONE);
		lblIntroDuration.setText(Vocab.instance.DURATION);

		LText txtIntroDuration = new LText(grpIntro, SWT.NONE);
		txtIntroDuration.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		addControl(txtIntroDuration, "introDuration");
		
		Button btnIntroDuration = new Button(grpIntro, SWT.NONE);
		btnIntroDuration.setText(Vocab.instance.DEFAULT);
		
		// Loop
		
		Group grpLoop = new Group(contentEditor, SWT.NONE);
		grpLoop.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));
		grpLoop.setLayout(new GridLayout(3, false));
		grpLoop.setText(Vocab.instance.LOOP);
		
		Label lblLoopPattern = new Label(grpLoop, SWT.NONE);
		lblLoopPattern.setText(Vocab.instance.PATTERN);
		
		LText txtLoopPattern = new LText(grpLoop, SWT.NONE);
		txtLoopPattern.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		addControl(txtLoopPattern, "loopPattern");
		
		Button btnLoopPattern = new Button(grpLoop, SWT.NONE);
		btnLoopPattern.setText(Vocab.instance.DEFAULT);
		
		Label lblLoopDuration = new Label(grpLoop, SWT.NONE);
		lblLoopDuration.setText(Vocab.instance.DURATION);

		LText txtLoopDuration = new LText(grpLoop, SWT.NONE);
		txtLoopDuration.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		addControl(txtLoopDuration, "loopDuration");
		
		Button btnLoopDuration = new Button(grpLoop, SWT.NONE);
		btnLoopDuration.setText(Vocab.instance.DEFAULT);
		
		// Audio
		
		Composite bottom = new Composite(contentEditor, SWT.NONE);
		bottom.setLayout(new GridLayout(2, true));
		bottom.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		
		Group grpAudio = new Group(bottom, SWT.NONE);
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
		
		// Tags
		
		Group grpTags = new Group(bottom, SWT.NONE);
		grpTags.setLayout(new FillLayout(SWT.HORIZONTAL));
		grpTags.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		grpTags.setText(Vocab.instance.TAGS);
		
		TagList lstTag = new TagList(grpTags, SWT.NONE);
		addChild(lstTag, "tags");
		
		addDurationListener(btnIntroDuration, txtIntroDuration);
		addDurationListener(btnLoopDuration, txtLoopDuration);
		addPatternListener(btnIntroPattern, txtIntroPattern);
		addPatternListener(btnLoopPattern, txtLoopPattern);
		
	}
	
	private void addPatternListener(Button button, LText text) {
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				int cols = spnCols.getValue();
				String s = "1";
				for (int i = 2; i <= cols; i++) {
					s += " " + i;
				}
				text.modify(s);
			}
		});
	}
	
	private void addDurationListener(Button button, LText text) {
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
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
