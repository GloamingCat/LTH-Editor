package gui.views.database.content;

import gui.Vocab;
import gui.views.QuadButton;
import gui.views.ScriptButton;
import gui.views.database.DatabaseTab;
import gui.views.database.subcontent.TransformEditor;
import lwt.widget.LCombo;
import lwt.widget.LImage;
import lwt.widget.LSpinner;

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
		grpGeneral.setLayoutData(new GridData(SWT.FILL, SWT.TOP, false, false, 1, 1));
		txtName.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, true, 1, 1));
		
		Group grpAudio = new Group(contentEditor, SWT.NONE);
		grpAudio.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
		grpAudio.setLayout(new GridLayout(1, false));
		grpAudio.setText(Vocab.instance.SOUND);
		
		Label lblColumns = new Label(grpGeneral, SWT.NONE);
		lblColumns.setText(Vocab.instance.COLUMNS);
		
		LSpinner spnCols = new LSpinner(grpGeneral, SWT.NONE);
		spnCols.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		addControl(spnCols, "cols");
		
		Label lblRows = new Label(grpGeneral, SWT.NONE);
		lblRows.setText(Vocab.instance.ROWS);
		
		LSpinner spnRows = new LSpinner(grpGeneral, SWT.NONE);
		spnRows.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		addControl(spnRows, "rows");

		Label lblDuration = new Label(grpGeneral, SWT.NONE);
		lblDuration.setText(Vocab.instance.DURATION);

		LSpinner spnDuration = new LSpinner(grpGeneral, SWT.NONE);
		spnDuration.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		addControl(spnDuration, "duration");

		Label lblLoop = new Label(grpGeneral, SWT.NONE);
		lblLoop.setText(Vocab.instance.LOOP);
		
		LCombo cmbLoop = new LCombo(grpGeneral, SWT.NONE);
		cmbLoop.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		cmbLoop.setIncludeID(false);
		cmbLoop.setOptional(false);
		cmbLoop.setItems(new String[] { Vocab.instance.NOLOOP, Vocab.instance.REPEAT, Vocab.instance.MIRROR });
		addControl(cmbLoop, "loop");
		
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
		
		ScriptButton btnScript = new ScriptButton(script, SWT.NONE);
		btnScript.optional = true;
		btnScript.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		addScriptButton(btnScript, txtScript, "animation", "animation");
		
		TransformEditor transformTab = new TransformEditor(contentEditor, SWT.NONE);
		GridData gd_transformTab = new GridData(SWT.FILL, SWT.FILL, false, true, 1, 1);
		gd_transformTab.widthHint = 250;
		transformTab.setLayoutData(gd_transformTab);
		addChild(transformTab);
		
		Group grpImg = new Group(contentEditor, SWT.NONE);
		grpImg.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		GridLayout gl_grpImg = new GridLayout(1, false);
		gl_grpImg.marginWidth = 0;
		gl_grpImg.marginHeight = 0;
		grpImg.setLayout(gl_grpImg);
		grpImg.setText(Vocab.instance.GRAPHICS);
		
		QuadButton btnSelectImage = new QuadButton(grpImg, SWT.NONE);
		btnSelectImage.setLayoutData(new GridData(SWT.LEFT, SWT.TOP, false, false, 1, 1));
		
		LImage image = new LImage(grpImg, SWT.NONE);
		image.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		image.setImage(SWTResourceManager.getImage(AnimationTab.class, "/javax/swing/plaf/basic/icons/image-delayed.png"));
		
		addQuadButton(btnSelectImage, image, "Animations/", "quad");
		
	}

	@Override
	protected GObjectTreeSerializer getSerializer() {
		return Project.current.animations;
	}

}
