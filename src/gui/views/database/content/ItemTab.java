package gui.views.database.content;

import gson.project.GObjectTreeSerializer;
import gui.Vocab;
import gui.views.database.DatabaseTab;
import gui.views.database.subcontent.AttributeList;
import gui.views.database.subcontent.ElementList;
import gui.views.database.subcontent.SkillEffectList;
import gui.views.database.subcontent.EquipStatusList;
import gui.views.database.subcontent.TagList;
import gui.widgets.IDButton;
import gui.widgets.IconButton;
import gui.widgets.NameList;
import lwt.dataestructure.LDataTree;
import lwt.widget.LCheckBox;
import lwt.widget.LImage;
import lwt.widget.LLabel;
import lwt.widget.LSpinner;
import lwt.widget.LText;
import lwt.widget.LTextBox;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;

import project.Project;

import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;

public class ItemTab extends DatabaseTab {

	/**
	 * Create the composite.
	 * @param parent
	 */
	public ItemTab(Composite parent) {
		super(parent);
		
		Composite right = new Composite(contentEditor, SWT.NONE);
		right.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 2));
		
		new LLabel(grpGeneral, Vocab.instance.DESCRIPTION).setLayoutData(new GridData(SWT.LEFT, SWT.FILL, false, false, 1, 1));
		
		LTextBox txtDescription = new LTextBox(grpGeneral);
		GridData gd_txtDescription = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		gd_txtDescription.minimumHeight = 48;
		txtDescription.setLayoutData(gd_txtDescription);
		addControl(txtDescription, "description");
		
		// Icon
		
		new LLabel(grpGeneral, Vocab.instance.ICON);
		
		Composite compositeIcon = new Composite(grpGeneral, SWT.NONE);
		GridLayout gl_compositeIcon = new GridLayout(2, false);
		gl_compositeIcon.marginWidth = 0;
		gl_compositeIcon.marginHeight = 0;
		compositeIcon.setLayout(gl_compositeIcon);
		compositeIcon.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		
		LImage imgIcon = new LImage(compositeIcon, SWT.NONE);
		imgIcon.setImage("/javax/swing/plaf/basic/icons/image-delayed.png");
		GridData gd_imgIcon = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		gd_imgIcon.widthHint = 48;
		gd_imgIcon.heightHint = 48;
		imgIcon.setVerticalAlign(SWT.CENTER);
		imgIcon.setLayoutData(gd_imgIcon);
		
		IconButton btnGraphics = new IconButton(compositeIcon, 0);
		btnGraphics.setImageWidget(imgIcon);
		addControl(btnGraphics, "icon");
		
		// Price
		
		new LLabel(grpGeneral, Vocab.instance.PRICE);
		
		Composite price = new Composite(grpGeneral, SWT.NONE);
		price.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		GridLayout gl_price = new GridLayout(2, false);
		gl_price.marginWidth = 0;
		gl_price.marginHeight = 0;
		price.setLayout(gl_price);
		
		LSpinner spnPrice = new LSpinner(price, SWT.NONE);
		spnPrice.setMinimum(0);
		spnPrice.setMaximum(9999999);
		spnPrice.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		addControl(spnPrice, "price");
		
		LCheckBox btnSellable = new LCheckBox(price, SWT.NONE);
		btnSellable.setText(Vocab.instance.SELLABLE);
		GridLayout gl_right = new GridLayout(1, false);
		gl_right.marginWidth = 0;
		gl_right.marginHeight = 0;
		right.setLayout(gl_right);
		addControl(btnSellable, "sellable");
		
		// Equip
		
		Group grpEquip = new Group(right, SWT.NONE);
		grpEquip.setLayout(new GridLayout(2, false));
		grpEquip.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, true, 1, 1));
		grpEquip.setText(Vocab.instance.EQUIP);
		
		new LLabel(grpEquip, Vocab.instance.SLOT);
		
		LText txtSlot = new LText(grpEquip);
		txtSlot.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		addControl(txtSlot, "slot");
		
		LCheckBox btnAllSlots = new LCheckBox(grpEquip, SWT.NONE);
		btnAllSlots.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1));
		btnAllSlots.setText(Vocab.instance.ALLSLOTS);
		addControl(btnAllSlots, "allSlots");
		
		new LLabel(grpEquip, Vocab.instance.BLOCKEDSLOTS).setLayoutData(new GridData(SWT.LEFT, SWT.TOP, false, false, 1, 1));

		NameList lstBlocked = new NameList(grpEquip, SWT.NONE);
		GridData gd_lstBlocked = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
		gd_lstBlocked.heightHint = 48;
		lstBlocked.setLayoutData(gd_lstBlocked);
		addChild(lstBlocked, "blocked");
		
		TabFolder equipFolder = new TabFolder(grpEquip, SWT.NONE);
		equipFolder.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, true, 2, 1));
		
		TabItem tbtmEquipAtt = new TabItem(equipFolder, SWT.NONE);
		tbtmEquipAtt.setText(Vocab.instance.ATTRIBUTES);
		AttributeList lstEquipAtt = new AttributeList(equipFolder, SWT.NONE);
		tbtmEquipAtt.setControl(lstEquipAtt);
		addChild(lstEquipAtt, "equipAttributes");
		
		TabItem tbtmEquipStatus = new TabItem(equipFolder, SWT.NONE);
		tbtmEquipStatus.setText(Vocab.instance.STATUS);
		EquipStatusList lstEquipStatus = new EquipStatusList(equipFolder, SWT.NONE);
		lstEquipStatus.setIncludeID(false);
		tbtmEquipStatus.setControl(lstEquipStatus);
		addChild(lstEquipStatus, "equipStatus");
		
		TabItem tbtmElement = new TabItem(equipFolder, SWT.NONE);
		tbtmElement.setText(Vocab.instance.ELEMENTS);
		ElementList lstElement = new ElementList(equipFolder, SWT.NONE);
		tbtmElement.setControl(lstElement);
		addChild(lstElement, "elements");
		
		// Use
		
		Group grpUse = new Group(contentEditor, SWT.NONE);
		grpUse.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		grpUse.setText(Vocab.instance.USE);
		grpUse.setLayout(new GridLayout(3, false));
		
		new LLabel(grpUse, Vocab.instance.ITEMSKILL);
	
		LText txtSkill = new LText(grpUse, true);
		txtSkill.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
		
		IDButton btnSkill = new IDButton(grpUse, 1) {
			public LDataTree<Object> getDataTree() {
				return Project.current.skills.getTree();
			}
		};
		btnSkill.setNameWidget(txtSkill);
		addControl(btnSkill, "skillID");
		
		Composite checkButtons = new Composite(grpUse, SWT.NONE);
		checkButtons.setLayout(new FillLayout(SWT.HORIZONTAL));
		checkButtons.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false, 3, 1));
		
		LCheckBox btnConsume = new LCheckBox(checkButtons, SWT.NONE);
		btnConsume.setText(Vocab.instance.CONSUME);
		addControl(btnConsume, "consume");
		
		LCheckBox btnNeedsUser = new LCheckBox(checkButtons, SWT.NONE);
		btnNeedsUser.setText(Vocab.instance.NEEDSUSER);
		addControl(btnNeedsUser, "needsUser");
		
		TabFolder useFolder = new TabFolder(grpUse, SWT.NONE);
		useFolder.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, true, 3, 1));
		
		TabItem tbtmEffects = new TabItem(useFolder, SWT.NONE);
		tbtmEffects.setText(Vocab.instance.EFFECTS);
		SkillEffectList lstEffects = new SkillEffectList(useFolder, SWT.NONE);
		tbtmEffects.setControl(lstEffects);
		addChild(lstEffects, "effects");
		
		TabItem tbtmUseAtt = new TabItem(useFolder, SWT.NONE);
		tbtmUseAtt.setText(Vocab.instance.ATTRIBUTES);
		AttributeList lstUseAtt = new AttributeList(useFolder, SWT.NONE);
		tbtmUseAtt.setControl(lstUseAtt);
		addChild(lstUseAtt, "attributes");
		
		// Tags
		
		Group grpTags = new Group(right, SWT.NONE);
		grpTags.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		grpTags.setLayout(new FillLayout());
		grpTags.setText(Vocab.instance.TAGS);
		
		TagList lstTags = new TagList(grpTags, SWT.NONE);
		addChild(lstTags, "tags");
	}

	@Override
	protected GObjectTreeSerializer getSerializer() {
		return Project.current.items;
	}

}
