package gui.views.database.content;

import gui.Vocab;
import gui.views.QuadButton;
import gui.views.ScriptButton;
import gui.views.database.DatabaseTab;
import gui.views.database.subcontent.AudioList;
import gui.views.database.subcontent.TagList;
import gui.views.database.subcontent.TransformEditor;
import lwt.widget.LCombo;
import lwt.widget.LImage;
import lwt.widget.LSpinner;
import lwt.widget.LText;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;

import project.GObjectTreeSerializer;
import project.Project;

import org.eclipse.swt.layout.FillLayout;

public class AnimationTab extends DatabaseTab {

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public AnimationTab(Composite parent, int style) {
		super(parent, style);
		
		contentEditor.setLayout(new GridLayout(2, false));
		GridLayout gridLayout = new GridLayout(2, false);
		grpGeneral.setLayout(gridLayout);
		grpGeneral.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));
		
		Label lblColumns = new Label(grpGeneral, SWT.NONE);
		lblColumns.setText(Vocab.instance.COLUMNS);
		
		LSpinner spnCols = new LSpinner(grpGeneral, SWT.NONE);
		spnCols.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		addControl(spnCols, "cols");
		
		Label lblRows = new Label(grpGeneral, SWT.NONE);
		lblRows.setText(Vocab.instance.ROWS);
		
		LSpinner spnRows = new LSpinner(grpGeneral, SWT.NONE);
		spnRows.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		addControl(spnRows, "rows");

		Label lblDuration = new Label(grpGeneral, SWT.NONE);
		lblDuration.setText(Vocab.instance.DURATION);

		LText txtDuration = new LText(grpGeneral, SWT.NONE);
		txtDuration.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		addControl(txtDuration, "duration");

		Label lblLoop = new Label(grpGeneral, SWT.NONE);
		lblLoop.setText(Vocab.instance.LOOP);
		
		LCombo cmbLoop = new LCombo(grpGeneral, SWT.NONE);
		cmbLoop.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		cmbLoop.setIncludeID(false);
		cmbLoop.setOptional(false);
		cmbLoop.setItems(new String[] { Vocab.instance.NOLOOP, Vocab.instance.REPEAT, Vocab.instance.MIRROR });
		addControl(cmbLoop, "loop");
		
		Label lblPattern = new Label(grpGeneral, SWT.NONE);
		lblPattern.setText(Vocab.instance.PATTERN);
		
		LText txtPattern = new LText(grpGeneral, SWT.NONE);
		txtPattern.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		addControl(txtPattern, "pattern");

		Label lblScript = new Label(grpGeneral,  SWT.NONE);
		lblScript.setText(Vocab.instance.SCRIPT);

		Composite script = new Composite(grpGeneral,  SWT.NONE);
		script.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		GridLayout gl_script = new GridLayout(2, false);
		gl_script.marginWidth = 0;
		gl_script.marginHeight = 0;
		script.setLayout(gl_script);
		
		Text txtScript = new Text(script, SWT.BORDER | SWT.READ_ONLY);
		txtScript.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		
		ScriptButton btnScript = new ScriptButton(script, SWT.NONE);
		btnScript.optional = true;
		btnScript.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		addScriptButton(btnScript, txtScript, "animation", "animation");
		
		TransformEditor transformTab = new TransformEditor(contentEditor, SWT.NONE);
		GridData gd_transformTab = new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1);
		gd_transformTab.widthHint = 300;
		transformTab.setLayoutData(gd_transformTab);
		addChild(transformTab);
		
		Composite bottom = new Composite(contentEditor, SWT.NONE);
		bottom.setLayout(new GridLayout(3, false));
		bottom.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));
		
		Group grpImg = new Group(bottom, SWT.NONE);
		grpImg.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		GridLayout gl_grpImg = new GridLayout(1, false);
		gl_grpImg.marginWidth = 0;
		gl_grpImg.marginHeight = 0;
		grpImg.setLayout(gl_grpImg);
		grpImg.setText(Vocab.instance.GRAPHICS);
		
		QuadButton btnSelectImage = new QuadButton(grpImg, SWT.NONE);
		btnSelectImage.setLayoutData(new GridData(SWT.LEFT, SWT.TOP, false, false, 1, 1));
		
		LImage image = new LImage(grpImg, SWT.NONE);
		GridData gd_image = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		gd_image.widthHint = 150;
		image.setLayoutData(gd_image);
		image.setImage(SWTResourceManager.getImage(AnimationTab.class, "/javax/swing/plaf/basic/icons/image-delayed.png"));
		
		addQuadButton(btnSelectImage, image, "", "quad");
		
		Group grpAudio = new Group(bottom, SWT.NONE);
		grpAudio.setLayout(new FillLayout(SWT.HORIZONTAL));
		grpAudio.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		grpAudio.setText(Vocab.instance.SOUND);
		
		AudioList lstAudio = new AudioList(grpAudio, SWT.NONE);
		addChild(lstAudio);
		
		Group grpTags = new Group(bottom, SWT.NONE);
		grpTags.setLayout(new FillLayout(SWT.HORIZONTAL));
		grpTags.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		grpTags.setText(Vocab.instance.TAGS);
		
		TagList lstTag = new TagList(grpTags, SWT.NONE);
		addChild(lstTag);
		
	}

	@Override
	protected GObjectTreeSerializer getSerializer() {
		return Project.current.animations;
	}

}
