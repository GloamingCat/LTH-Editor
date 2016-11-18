package gui.views.database.content;

import gui.Vocab;
import gui.shell.ImageShell;
import gui.shell.ScriptShell;
import gui.views.database.DatabaseTab;
import gui.views.database.subcontent.TagEditor;

import java.util.ArrayList;

import lwt.dialog.LObjectShell;
import lwt.dialog.LShellFactory;
import lwt.editor.LComboView;
import lwt.widget.LSpinner;
import lwt.widget.LStringButton;
import lwt.widget.LStyledText;
import lwt.widget.LText;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;

import project.ListSerializer;
import project.Project;

public class SkillTab extends DatabaseTab {

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public SkillTab(Composite parent, int style) {
		super(parent, style);
		
		GridLayout gridLayout = new GridLayout(2, false);
		gridLayout.verticalSpacing = 0;
		contentEditor.setLayout(gridLayout);
		
		Label lblDescription = new Label(grpGeneral, SWT.NONE);
		lblDescription.setLayoutData(new GridData(SWT.LEFT, SWT.FILL, false, false, 1, 1));
		lblDescription.setText(Vocab.instance.DESCRIPTION);
		
		LStyledText txtDescription = new LStyledText(grpGeneral, SWT.NONE);
		txtDescription.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		contentEditor.addControl(txtDescription, "description");
		
		Label lblIcon = new Label(grpGeneral, SWT.NONE);
		lblIcon.setText(Vocab.instance.ICON);
		
		Composite compositeIcon = new Composite(grpGeneral, SWT.NONE);
		GridLayout gl_compositeIcon = new GridLayout(2, false);
		gl_compositeIcon.marginWidth = 0;
		gl_compositeIcon.marginHeight = 0;
		compositeIcon.setLayout(gl_compositeIcon);
		compositeIcon.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		
		LStringButton btnSelect = new LStringButton(compositeIcon, SWT.NONE) {
			@Override
			protected String getImagePath() {
				return Project.current.imagePath();
			}
		};
		
		Label imgIcon = new Label(compositeIcon, SWT.NONE);
		imgIcon.setImage(SWTResourceManager.getImage(SkillTab.class, "/javax/swing/plaf/basic/icons/image-delayed.png"));
		GridData gd_imgIcon = new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1);
		gd_imgIcon.widthHint = 48;
		gd_imgIcon.heightHint = 48;
		imgIcon.setLayoutData(gd_imgIcon);
		
		btnSelect.setLabel(imgIcon);
		btnSelect.setShellFactory(new LShellFactory<String>() {
			@Override
			public LObjectShell<String> createShell(Shell parent) {
				return new ImageShell(parent, "Icon");
			}
		});
		
		contentEditor.addControl(btnSelect, "icon");
		
		Label lblRadius = new Label(grpGeneral, SWT.NONE);
		lblRadius.setLayoutData(new GridData(SWT.LEFT, SWT.TOP, false, false, 1, 1));
		lblRadius.setText(Vocab.instance.RADIUS);
		
		LSpinner spnRadius = new LSpinner(grpGeneral, SWT.NONE);
		spnRadius.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		contentEditor.addControl(spnRadius, "radius");
		
		Label lblRange = new Label(grpGeneral, SWT.NONE);
		lblRange.setLayoutData(new GridData(SWT.LEFT, SWT.TOP, false, false, 1, 1));
		lblRange.setText(Vocab.instance.RANGE);
		
		LSpinner spnRange = new LSpinner(grpGeneral, SWT.NONE);
		spnRange.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		contentEditor.addControl(spnRange, "range");
		
		Composite right = new Composite(contentEditor, SWT.NONE);
		GridLayout gl_right = new GridLayout(1, false);
		gl_right.verticalSpacing = 0;
		gl_right.marginWidth = 0;
		gl_right.marginHeight = 0;
		right.setLayout(gl_right);
		right.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
		
		Group grpAnimations = new Group(right, SWT.NONE);
		grpAnimations.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		grpAnimations.setText(Vocab.instance.ANIMATIONS);
		grpAnimations.setLayout(new GridLayout(2, false));
		
		Label lblUserAnimation = new Label(grpAnimations, SWT.NONE);
		lblUserAnimation.setBounds(0, 0, 55, 15);
		lblUserAnimation.setText(Vocab.instance.USER);
		
		LText txtUserAnimation = new LText(grpAnimations, SWT.NONE);
		txtUserAnimation.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		contentEditor.addControl(txtUserAnimation, "userAnim");
		
		Label lblCastAnimation = new Label(grpAnimations, SWT.NONE);
		lblCastAnimation.setText(Vocab.instance.CAST);
		
		LComboView cmbCastAnimation = new LComboView(grpAnimations, SWT.NONE) {
			public ArrayList<Object> getArray() {
				return Project.current.animBattle.getList();
			}
		};
		cmbCastAnimation.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		contentEditor.addControl(cmbCastAnimation, "castAnimID");
		
		Label lblCenterAnimation = new Label(grpAnimations, SWT.NONE);
		lblCenterAnimation.setText(Vocab.instance.CENTER);
		
		LComboView cmbCenterAnimation = new LComboView(grpAnimations, SWT.NONE) {
			public ArrayList<Object> getArray() {
				return Project.current.animBattle.getList();
			}
		};
		cmbCenterAnimation.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		contentEditor.addControl(cmbCenterAnimation, "centerAnimID");
		
		Label lblIndividualAnimation = new Label(grpAnimations, SWT.NONE);
		lblIndividualAnimation.setText(Vocab.instance.INDIVIDUAL);

		LComboView cmbIndividualAnimation = new LComboView(grpAnimations, SWT.NONE) {
			public ArrayList<Object> getArray() {
				return Project.current.animBattle.getList();
			}
		};
		cmbIndividualAnimation.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		contentEditor.addControl(cmbIndividualAnimation, "individualAnimID");
		
		Group grpOther = new Group(right, SWT.NONE);
		grpOther.setLayout(new GridLayout(2, false));
		grpOther.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		grpOther.setText("Other");
		
		Label lblEnergyCost = new Label(grpOther, SWT.NONE);
		lblEnergyCost.setText(Vocab.instance.EPCOST);
		
		LSpinner spnEnergyCost = new LSpinner(grpOther, SWT.NONE);
		spnEnergyCost.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		contentEditor.addControl(spnEnergyCost, "energyCost");
		
		Label lblTimeCost = new Label(grpOther, SWT.NONE);
		lblTimeCost.setText(Vocab.instance.TIMECOST);
		
		LSpinner spnTimeCost = new LSpinner(grpOther, SWT.NONE);
		spnTimeCost.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		contentEditor.addControl(spnTimeCost, "timeCost");
		
		ArrayList<String> restrictions = new ArrayList<String>();
		restrictions.add(Vocab.instance.ALWAYS);
		restrictions.add(Vocab.instance.BATTLEONLY);
		restrictions.add(Vocab.instance.FIELDONLY);
		
		Label lblRestrictions = new Label(grpOther, SWT.NONE);
		lblRestrictions.setText(Vocab.instance.RESTRICTIONS);
		
		LComboView btnRestrictions = new LComboView(grpOther, SWT.NONE) {
			public ArrayList<?> getArray() {
				return restrictions;
			}
		};
		btnRestrictions.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		contentEditor.addControl(btnRestrictions, "restriction");
		
		Composite bottom = new Composite(contentEditor, SWT.NONE);
		GridLayout gl_bottom = new GridLayout(2, false);
		gl_bottom.verticalSpacing = 0;
		gl_bottom.marginWidth = 0;
		gl_bottom.marginHeight = 0;
		bottom.setLayout(gl_bottom);
		bottom.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, true, 2, 1));
		
		Group grpEffect = new Group(bottom, SWT.NONE);
		grpEffect.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		grpEffect.setText(Vocab.instance.EFFECT);
		GridLayout gl_grpEffect = new GridLayout(3, false);
		grpEffect.setLayout(gl_grpEffect);
		
		Label lblEffect = new Label(grpEffect, SWT.NONE);
		lblEffect.setText(Vocab.instance.PATH);
		
		Text txtEffect = new Text(grpEffect, SWT.BORDER | SWT.READ_ONLY);
		txtEffect.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		LStringButton btnSelectEffect = new LStringButton(grpEffect, SWT.NONE);
		btnSelectEffect.setShellFactory(new LShellFactory<String>() {
			@Override
			public LObjectShell<String> createShell(Shell parent) {
				return new ScriptShell(parent, "skilleffect");
			}
		});
		btnSelectEffect.setText(txtEffect);
		contentEditor.addControl(btnSelectEffect, "effect");
		
		Label lblParam = new Label(grpEffect, SWT.NONE);
		lblParam.setLayoutData(new GridData(SWT.LEFT, SWT.TOP, false, false, 1, 1));
		lblParam.setText(Vocab.instance.PARAM);
		
		LStyledText txtParam = new LStyledText(grpEffect, SWT.NONE);
		txtParam.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));
		contentEditor.addControl(txtParam, "param");
		
		Group grpTags = new Group(bottom, SWT.NONE);
		grpTags.setLayout(new FillLayout(SWT.HORIZONTAL));
		grpTags.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		grpTags.setText(Vocab.instance.TAGS);
		
		TagEditor tagEditor = new TagEditor(grpTags, SWT.NONE);
		contentEditor.addChild(tagEditor);
	}

	@Override
	protected ListSerializer getSerializer() {
		return Project.current.items;
	}

}
