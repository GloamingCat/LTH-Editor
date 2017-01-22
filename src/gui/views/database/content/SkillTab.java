package gui.views.database.content;

import gui.Vocab;
import gui.views.QuadButton;
import gui.views.ScriptButton;
import gui.views.database.DatabaseTab;
import gui.views.database.subcontent.TagList;

import java.util.ArrayList;

import lwt.editor.LComboView;
import lwt.widget.LSpinner;
import lwt.widget.LTextBox;
import lwt.widget.LText;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;

import project.GObjectListSerializer;
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
		
		LTextBox txtDescription = new LTextBox(grpGeneral, SWT.NONE);
		txtDescription.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		addControl(txtDescription, "description");
		
		Label lblIcon = new Label(grpGeneral, SWT.NONE);
		lblIcon.setText(Vocab.instance.ICON);
		
		Composite compositeIcon = new Composite(grpGeneral, SWT.NONE);
		GridLayout gl_compositeIcon = new GridLayout(2, false);
		gl_compositeIcon.marginWidth = 0;
		gl_compositeIcon.marginHeight = 0;
		compositeIcon.setLayout(gl_compositeIcon);
		compositeIcon.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		
		QuadButton btnSelect = new QuadButton(compositeIcon, SWT.NONE);
		
		Label imgIcon = new Label(compositeIcon, SWT.NONE);
		imgIcon.setImage(SWTResourceManager.getImage(SkillTab.class, "/javax/swing/plaf/basic/icons/image-delayed.png"));
		GridData gd_imgIcon = new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1);
		gd_imgIcon.widthHint = 48;
		gd_imgIcon.heightHint = 48;
		imgIcon.setLayoutData(gd_imgIcon);
		
		addQuadButton(btnSelect, imgIcon, "Icon", "icon");
		
		Label lblRadius = new Label(grpGeneral, SWT.NONE);
		lblRadius.setLayoutData(new GridData(SWT.LEFT, SWT.TOP, false, false, 1, 1));
		lblRadius.setText(Vocab.instance.RADIUS);
		
		LSpinner spnRadius = new LSpinner(grpGeneral, SWT.NONE);
		spnRadius.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		addControl(spnRadius, "radius");
		
		Label lblRange = new Label(grpGeneral, SWT.NONE);
		lblRange.setLayoutData(new GridData(SWT.LEFT, SWT.TOP, false, false, 1, 1));
		lblRange.setText(Vocab.instance.RANGE);
		
		LSpinner spnRange = new LSpinner(grpGeneral, SWT.NONE);
		spnRange.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		addControl(spnRange, "range");
		
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
		addControl(txtUserAnimation, "userAnim");
		
		Label lblCastAnimation = new Label(grpAnimations, SWT.NONE);
		lblCastAnimation.setText(Vocab.instance.CAST);
		
		LComboView cmbCastAnimation = new LComboView(grpAnimations, SWT.NONE) {
			public ArrayList<Object> getArray() {
				return Project.current.animBattle.getList();
			}
		};
		cmbCastAnimation.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		addControl(cmbCastAnimation, "castAnimID");
		
		Label lblCenterAnimation = new Label(grpAnimations, SWT.NONE);
		lblCenterAnimation.setText(Vocab.instance.CENTER);
		
		LComboView cmbCenterAnimation = new LComboView(grpAnimations, SWT.NONE) {
			public ArrayList<Object> getArray() {
				return Project.current.animBattle.getList();
			}
		};
		cmbCenterAnimation.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		addControl(cmbCenterAnimation, "centerAnimID");
		
		Label lblIndividualAnimation = new Label(grpAnimations, SWT.NONE);
		lblIndividualAnimation.setText(Vocab.instance.INDIVIDUAL);

		LComboView cmbIndividualAnimation = new LComboView(grpAnimations, SWT.NONE) {
			public ArrayList<Object> getArray() {
				return Project.current.animBattle.getList();
			}
		};
		cmbIndividualAnimation.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		addControl(cmbIndividualAnimation, "individualAnimID");
		
		Group grpOther = new Group(right, SWT.NONE);
		grpOther.setLayout(new GridLayout(2, false));
		grpOther.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		grpOther.setText("Other");
		
		Label lblEnergyCost = new Label(grpOther, SWT.NONE);
		lblEnergyCost.setText(Vocab.instance.EPCOST);
		
		LSpinner spnEnergyCost = new LSpinner(grpOther, SWT.NONE);
		spnEnergyCost.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		addControl(spnEnergyCost, "energyCost");
		
		Label lblTimeCost = new Label(grpOther, SWT.NONE);
		lblTimeCost.setText(Vocab.instance.TIMECOST);
		
		LSpinner spnTimeCost = new LSpinner(grpOther, SWT.NONE);
		spnTimeCost.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		addControl(spnTimeCost, "timeCost");
		
		ArrayList<String> restrictions = new ArrayList<String>();
		restrictions.add(Vocab.instance.ALWAYS);
		restrictions.add(Vocab.instance.BATTLEONLY);
		restrictions.add(Vocab.instance.FIELDONLY);
		
		Label lblRestrictions = new Label(grpOther, SWT.NONE);
		lblRestrictions.setText(Vocab.instance.RESTRICTIONS);
		
		LComboView cmbRestrictions = new LComboView(grpOther, SWT.NONE) {
			public ArrayList<?> getArray() {
				return restrictions;
			}
		};
		cmbRestrictions.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		cmbRestrictions.setIncludeID(false);
		cmbRestrictions.setOptional(false);
		addControl(cmbRestrictions, "restriction");
		
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
		
		ScriptButton btnSelectEffect = new ScriptButton(grpEffect, SWT.NONE);
		
		Label lblParam = new Label(grpEffect, SWT.NONE);
		lblParam.setLayoutData(new GridData(SWT.LEFT, SWT.TOP, false, false, 1, 1));
		lblParam.setText(Vocab.instance.PARAM);
		
		StyledText txtParam = new StyledText(grpEffect, SWT.BORDER | SWT.READ_ONLY);
		txtParam.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));
		
		addScriptButton(btnSelectEffect, txtEffect, txtParam, "skill", "effect");
		
		Group grpTags = new Group(bottom, SWT.NONE);
		grpTags.setLayout(new FillLayout(SWT.HORIZONTAL));
		grpTags.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		grpTags.setText(Vocab.instance.TAGS);
		
		TagList tagEditor = new TagList(grpTags, SWT.NONE);
		addChild(tagEditor);
	}

	@Override
	protected GObjectListSerializer getSerializer() {
		return Project.current.skills;
	}

}
