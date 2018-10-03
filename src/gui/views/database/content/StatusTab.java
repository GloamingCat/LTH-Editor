package gui.views.database.content;

import gui.Vocab;
import gui.views.database.DatabaseTab;
import gui.views.database.subcontent.BonusList;
import gui.views.database.subcontent.TagList;
import gui.widgets.QuadButton;
import gui.widgets.ScriptButton;

import lwt.dataestructure.LDataTree;
import lwt.widget.LCheckButton;
import lwt.widget.LImage;
import lwt.widget.LSpinner;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import data.config.Config;
import project.GObjectTreeSerializer;
import project.Project;

public class StatusTab extends DatabaseTab {

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public StatusTab(Composite parent, int style) {
		super(parent, style);
		
		contentEditor.setLayout(new GridLayout(2, false));
		
		Label lblIcon = new Label(grpGeneral, SWT.NONE);
		lblIcon.setText(Vocab.instance.ICON);
		
		Composite compositeIcon = new Composite(grpGeneral, SWT.NONE);
		GridLayout gl_compositeIcon = new GridLayout(2, false);
		gl_compositeIcon.marginWidth = 0;
		gl_compositeIcon.marginHeight = 0;
		compositeIcon.setLayout(gl_compositeIcon);
		compositeIcon.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
		
		QuadButton btnSelectIcon = new QuadButton(compositeIcon, SWT.NONE);
		btnSelectIcon.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, true, 1, 1));
		
		LImage imgIcon = new LImage(compositeIcon, SWT.NONE);
		imgIcon.setImage("/javax/swing/plaf/basic/icons/image-delayed.png");
		GridData gd_imgIcon = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		gd_imgIcon.widthHint = 48;
		gd_imgIcon.heightHint = 48;
		imgIcon.setLayoutData(gd_imgIcon);
		imgIcon.setVerticalAlign(SWT.CENTER);
		
		addQuadButton(btnSelectIcon, imgIcon, "Icon", "icon");
		
		Label lblDuration = new Label(grpGeneral, SWT.NONE);
		lblDuration.setText(Vocab.instance.DURATION);
		
		Composite compositeDuration = new Composite(grpGeneral, SWT.NONE);
		GridLayout gl_compositeDuration = new GridLayout(2, false);
		gl_compositeDuration.marginWidth = 0;
		gl_compositeDuration.marginHeight = 0;
		compositeDuration.setLayout(gl_compositeDuration);
		compositeDuration.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		
		LSpinner spnDuration = new LSpinner(compositeDuration, SWT.NONE);
		spnDuration.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		spnDuration.setMinimum(-1);
		addControl(spnDuration, "duration");
		
		Label lblSeconds = new Label(compositeDuration, SWT.NONE);
		lblSeconds.setText(Vocab.instance.SECONDS);
		
		LCheckButton btnRemoveOnDamage = new LCheckButton(grpGeneral, SWT.NONE);
		btnRemoveOnDamage.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1));
		btnRemoveOnDamage.setText(Vocab.instance.REMOVEONDAMAGE);
		addControl(btnRemoveOnDamage, "removeOnDamage");
		
		Label lblAI = new Label(grpGeneral, SWT.NONE);
		lblAI.setText(Vocab.instance.AI);
		
		Composite compositeAI = new Composite(grpGeneral, SWT.NONE);
		GridLayout gl_compositeAI = new GridLayout(2, false);
		gl_compositeAI.marginHeight = 0;
		gl_compositeAI.marginWidth = 0;
		compositeAI.setLayout(gl_compositeAI);
		compositeAI.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		
		Text txtAI = new Text(compositeAI, SWT.BORDER | SWT.READ_ONLY);
		txtAI.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		ScriptButton btnSelectAI = new ScriptButton(compositeAI, SWT.NONE);
		addScriptButton(btnSelectAI, txtAI, "ai", "scriptAI");
		
		Group grpDrain = new Group(contentEditor, SWT.NONE);
		grpDrain.setText(Vocab.instance.DRAIN);
		grpDrain.setLayout(new GridLayout(2, false));
		grpDrain.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
		
		Label lblHpDrain = new Label(grpDrain, SWT.NONE);
		lblHpDrain.setText(Vocab.instance.HPDRAIN);
		
		LSpinner spnHPDrain = new LSpinner(grpDrain, SWT.NONE);
		spnHPDrain.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		addControl(spnHPDrain, "hpDrain");
		
		Label lblMpDrain = new Label(grpDrain, SWT.NONE);
		lblMpDrain.setText(Vocab.instance.MPDRAIN);
		
		LSpinner spnMPDrain = new LSpinner(grpDrain, SWT.NONE);
		spnMPDrain.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		addControl(spnMPDrain, "mpDrain");
		
		LCheckButton btnPercentage = new LCheckButton(grpDrain, SWT.NONE);
		btnPercentage.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1));
		btnPercentage.setText(Vocab.instance.PERCENTAGE);
		addControl(btnPercentage, "percentage");
		
		Label lblFrequence = new Label(grpDrain, SWT.NONE);
		lblFrequence.setText(Vocab.instance.FREQUENCE);
		
		LSpinner spnFrequence = new LSpinner(grpDrain, SWT.NONE);
		spnFrequence.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		addControl(spnFrequence, "frequence");
		
		Composite compositeOther = new Composite(contentEditor, SWT.NONE);
		GridLayout gl_compositeOther = new GridLayout(3, true);
		gl_compositeOther.marginWidth = 0;
		gl_compositeOther.marginHeight = 0;
		compositeOther.setLayout(gl_compositeOther);
		compositeOther.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));
		
		Group grpAttributes = new Group(compositeOther, SWT.NONE);
		grpAttributes.setLayout(new FillLayout(SWT.HORIZONTAL));
		grpAttributes.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		grpAttributes.setText(Vocab.instance.ATTRIBUTES);
		
		BonusList lstAttributes = new BonusList(grpAttributes, SWT.NONE) {
			@Override
			protected LDataTree<?> dataTree() {
				Config conf = (Config) Project.current.config.getData();
				return conf.attributes.toTree();
			}
		};
		lstAttributes.attributeName = "attributes";
		addChild(lstAttributes);

		Group grpElements = new Group(compositeOther, SWT.NONE);
		grpElements.setLayout(new FillLayout(SWT.HORIZONTAL));
		grpElements.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		grpElements.setText(Vocab.instance.ELEMENTS);
		
		BonusList lstElements = new BonusList(grpElements, SWT.NONE) {
			@Override
			protected LDataTree<?> dataTree() {
				Config conf = (Config) Project.current.config.getData();
				return conf.elements.toTree();
			}
		};
		lstElements.attributeName = "elements";
		addChild(lstElements);
		
		Group grpTags = new Group(compositeOther, SWT.NONE);
		grpTags.setLayout(new FillLayout(SWT.HORIZONTAL));
		grpTags.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		grpTags.setText(Vocab.instance.TAGS);
		
		TagList tagEditor = new TagList(grpTags, SWT.NONE);
		addChild(tagEditor);	
	}

	@Override
	protected GObjectTreeSerializer getSerializer() {
		return Project.current.status;
	}

}
