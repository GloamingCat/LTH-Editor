package gui.views.database.content;

import gui.Vocab;
import gui.views.QuadButton;
import gui.views.ScriptButton;
import gui.views.database.DatabaseTab;
import gui.views.database.subcontent.BonusList;
import gui.views.database.subcontent.TagList;

import java.util.ArrayList;

import lwt.editor.LComboView;
import lwt.widget.LCheckButton;
import lwt.widget.LImage;
import lwt.widget.LSpinner;
import lwt.widget.LTextBox;
import lwt.widget.LText;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import data.Config;
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
		SashForm center = new SashForm(contentEditor, SWT.VERTICAL);
		SashForm top = new SashForm(center, SWT.HORIZONTAL);

		grpGeneral.setParent(top);
		contentEditor.setLayout(new FillLayout());

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
		btnSelect.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, true, 1, 1));
		
		LImage imgIcon = new LImage(compositeIcon, SWT.NONE);
		imgIcon.setImage("/javax/swing/plaf/basic/icons/image-delayed.png");
		GridData gd_imgIcon = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		gd_imgIcon.widthHint = 48;
		gd_imgIcon.heightHint = 48;
		imgIcon.setLayoutData(gd_imgIcon);
		imgIcon.setVerticalAlign(SWT.CENTER);
		
		addQuadButton(btnSelect, imgIcon, "Icon", "icon");
		
		Label lblRadius = new Label(grpGeneral, SWT.NONE);
		lblRadius.setText(Vocab.instance.RADIUS);
		
		LSpinner spnRadius = new LSpinner(grpGeneral, SWT.NONE);
		spnRadius.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		addControl(spnRadius, "radius");
		
		Label lblRange = new Label(grpGeneral, SWT.NONE);
		lblRange.setText(Vocab.instance.RANGE);
		
		LSpinner spnRange = new LSpinner(grpGeneral, SWT.NONE);
		spnRange.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		addControl(spnRange, "range");

		Label lblType = new Label(grpGeneral, SWT.NONE);
		lblType.setText(Vocab.instance.TYPE);
		
		ArrayList<String> types = new ArrayList<String>();
		types.add(Vocab.instance.GENERAL);
		types.add(Vocab.instance.ATTACK);
		types.add(Vocab.instance.SUPPORT);
		
		LComboView cmbType = new LComboView(grpGeneral, SWT.NONE) {
			public ArrayList<?> getArray() {
				return types;
			}
		};
		cmbType.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		cmbType.setIncludeID(false);
		cmbType.setOptional(false);
		addControl(cmbType, "type");
		
		Label lblEnergyCost = new Label(grpGeneral, SWT.NONE);
		lblEnergyCost.setText(Vocab.instance.EPCOST);
		
		LSpinner spnEnergyCost = new LSpinner(grpGeneral, SWT.NONE);
		spnEnergyCost.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		addControl(spnEnergyCost, "energyCost");
		
		Label lblTimeCost = new Label(grpGeneral, SWT.NONE);
		lblTimeCost.setText(Vocab.instance.TIMECOST);
		
		LSpinner spnTimeCost = new LSpinner(grpGeneral, SWT.NONE);
		spnTimeCost.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		addControl(spnTimeCost, "timeCost");
		
		ArrayList<String> restrictions = new ArrayList<String>();
		restrictions.add(Vocab.instance.ALWAYS);
		restrictions.add(Vocab.instance.BATTLEONLY);
		restrictions.add(Vocab.instance.FIELDONLY);
		
		Label lblRestrictions = new Label(grpGeneral, SWT.NONE);
		lblRestrictions.setText(Vocab.instance.RESTRICTIONS);
		
		LComboView cmbRestrictions = new LComboView(grpGeneral, SWT.NONE) {
			public ArrayList<?> getArray() {
				return restrictions;
			}
		};
		cmbRestrictions.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		cmbRestrictions.setIncludeID(false);
		cmbRestrictions.setOptional(false);
		addControl(cmbRestrictions, "restriction");
		
		Composite right = new Composite(top, SWT.NONE);
		GridLayout gl_right = new GridLayout(1, false);
		gl_right.verticalSpacing = 0;
		gl_right.marginWidth = 0;
		gl_right.marginHeight = 0;
		right.setLayout(gl_right);
		right.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 2));
		
		Group grpBattleAnim = new Group(right, SWT.NONE);
		grpBattleAnim.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		grpBattleAnim.setText(Vocab.instance.BATTLEANIMATIONS);
		grpBattleAnim.setLayout(new GridLayout(2, false));
		
		Label lblLoadAnimation = new Label(grpBattleAnim, SWT.NONE);
		lblLoadAnimation.setText(Vocab.instance.LOAD);
		
		LComboView cmbLoadAnimation = new LComboView(grpBattleAnim, SWT.NONE) {
			public ArrayList<Object> getArray() {
				return Project.current.animBattle.getList();
			}
		};
		cmbLoadAnimation.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		addControl(cmbLoadAnimation, "loadAnimID");
		
		Label lblCastAnimation = new Label(grpBattleAnim, SWT.NONE);
		lblCastAnimation.setText(Vocab.instance.CAST);
		
		LComboView cmbCastAnimation = new LComboView(grpBattleAnim, SWT.NONE) {
			public ArrayList<Object> getArray() {
				return Project.current.animBattle.getList();
			}
		};
		cmbCastAnimation.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		addControl(cmbCastAnimation, "castAnimID");
		
		Label lblCenterAnimation = new Label(grpBattleAnim, SWT.NONE);
		lblCenterAnimation.setText(Vocab.instance.CENTER);
		
		LComboView cmbCenterAnimation = new LComboView(grpBattleAnim, SWT.NONE) {
			public ArrayList<Object> getArray() {
				return Project.current.animBattle.getList();
			}
		};
		cmbCenterAnimation.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		addControl(cmbCenterAnimation, "centerAnimID");
		
		Label lblIndividualAnimation = new Label(grpBattleAnim, SWT.NONE);
		lblIndividualAnimation.setText(Vocab.instance.INDIVIDUAL);

		LComboView cmbIndividualAnimation = new LComboView(grpBattleAnim, SWT.NONE) {
			public ArrayList<Object> getArray() {
				return Project.current.animBattle.getList();
			}
		};
		cmbIndividualAnimation.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		addControl(cmbIndividualAnimation, "individualAnimID");
		
		LCheckButton btnMirror = new LCheckButton(grpBattleAnim, SWT.NONE);
		btnMirror.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 2, 1));
		btnMirror.setText(Vocab.instance.MIRROR);
		addControl(btnMirror, "mirror");
		
		Group grpUserAnim = new Group(right, SWT.NONE);
		grpUserAnim.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		grpUserAnim.setText(Vocab.instance.USERANIMATIONS);
		grpUserAnim.setLayout(new GridLayout(2, false));
		
		Label lblLoadAnim = new Label(grpUserAnim, SWT.NONE);
		lblLoadAnim.setBounds(0, 0, 55, 15);
		lblLoadAnim.setText(Vocab.instance.LOAD);
		
		LText txtLoadAnim = new LText(grpUserAnim, SWT.NONE);
		txtLoadAnim.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		addControl(txtLoadAnim, "userLoadAnim");
		
		Label lblCastAnim = new Label(grpUserAnim, SWT.NONE);
		lblCastAnim.setBounds(0, 0, 55, 15);
		lblCastAnim.setText(Vocab.instance.CAST);
		
		LText txtCastAnim = new LText(grpUserAnim, SWT.NONE);
		txtCastAnim.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		addControl(txtCastAnim, "userCastAnim");
		
		LCheckButton btnStep = new LCheckButton(grpUserAnim, SWT.NONE);
		btnStep.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 2, 1));
		btnStep.setText(Vocab.instance.STEPONCAST);
		addControl(btnStep, "stepOnCast");
		
		Group grpElements = new Group(right, SWT.NONE);
		grpElements.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		grpElements.setLayout(new FillLayout(SWT.HORIZONTAL));
		grpElements.setText(Vocab.instance.ELEMENTS);
		
		BonusList lstElements = new BonusList(grpElements, SWT.NONE) {
			@Override
			protected ArrayList<?> comboArray() {
				Config conf = (Config) Project.current.config.getData();
				return conf.elements;
			}
		};
		lstElements.attributeName = "elements";
		addChild(lstElements);
		
		SashForm bottom = new SashForm(center, SWT.HORIZONTAL);
		bottom.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		
		Group grpScript = new Group(bottom, SWT.NONE);
		grpScript.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		grpScript.setText(Vocab.instance.SCRIPT);
		GridLayout gl_grpScript = new GridLayout(3, false);
		grpScript.setLayout(gl_grpScript);
		
		Label lblPath = new Label(grpScript, SWT.NONE);
		lblPath.setText(Vocab.instance.PATH);
		
		Text txtPath = new Text(grpScript, SWT.BORDER | SWT.READ_ONLY);
		txtPath.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		ScriptButton btnSelectScript = new ScriptButton(grpScript, SWT.NONE);
		btnSelectScript.optional = true;
		
		Label lblParam = new Label(grpScript, SWT.NONE);
		lblParam.setLayoutData(new GridData(SWT.LEFT, SWT.TOP, false, false, 1, 1));
		lblParam.setText(Vocab.instance.PARAM);
		
		StyledText txtParam = new StyledText(grpScript, SWT.BORDER | SWT.READ_ONLY);
		txtParam.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));
		
		addScriptButton(btnSelectScript, txtPath, txtParam, "skill", "script");
		
		Label lblBasicResult = new Label(grpScript, SWT.NONE);
		lblBasicResult.setText(Vocab.instance.BASICRESULT);
		
		LText txtBasicResult = new LText(grpScript, SWT.NONE);
		txtBasicResult.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		addControl(txtBasicResult, "basicResult");
		
		Label lblSuccessRate = new Label(grpScript, SWT.NONE);
		lblSuccessRate.setText(Vocab.instance.SUCCESSRATE);
		
		LText txtSuccessRate = new LText(grpScript, SWT.NONE);
		txtSuccessRate.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		addControl(txtSuccessRate, "successRate");
		
		Composite bottomR = new Composite(bottom, SWT.NONE);
		bottomR.setLayout(new FillLayout(SWT.VERTICAL));
		bottomR.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		
		Group grpTags = new Group(bottomR, SWT.NONE);
		grpTags.setLayout(new FillLayout(SWT.HORIZONTAL));
		grpTags.setText(Vocab.instance.TAGS);
		
		TagList tagEditor = new TagList(grpTags, SWT.NONE);
		addChild(tagEditor);
		bottom.setWeights(new int[] {2, 1});
		center.setWeights(new int[] {294, 173});
	}

	@Override
	protected GObjectListSerializer getSerializer() {
		return Project.current.skills;
	}

}
