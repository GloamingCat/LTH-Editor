package gui.views.database.content;

import gson.project.GObjectTreeSerializer;
import gui.Vocab;
import gui.views.database.DatabaseTab;
import gui.views.database.subcontent.AttributeList;
import gui.views.database.subcontent.BonusList;
import gui.views.database.subcontent.SkillEffectList;
import gui.views.database.subcontent.EquipStatusList;
import gui.widgets.IDButton;
import gui.widgets.IconButton;
import gui.widgets.NameList;
import lwt.container.LContainer;
import lwt.container.LFrame;
import lwt.container.LPanel;
import lwt.widget.LCheckBox;
import lwt.widget.LImage;
import lwt.widget.LLabel;
import lwt.widget.LSpinner;
import lwt.widget.LText;
import lwt.widget.LTextBox;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;

import project.Project;

import data.Item;

public class ItemTab extends DatabaseTab<Item> {

	private IDButton btnSkill;
	
	/**
	 * @wbp.parser.constructor
	 * @wbp.eval.method.parameter parent new lwt.dialog.LShell(800, 600)
	 */
	public ItemTab(LContainer parent) {
		super(parent);

		// Icon
		
		new LLabel(grpGeneral, Vocab.instance.ICON);
		
		LPanel compositeIcon = new LPanel(grpGeneral, 2, false);
		compositeIcon.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		
		LImage imgIcon = new LImage(compositeIcon, SWT.NONE);
		imgIcon.setImage("/javax/swing/plaf/basic/icons/image-delayed.png");
		GridData gd_imgIcon = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		gd_imgIcon.widthHint = 48;
		gd_imgIcon.heightHint = 48;
		imgIcon.setVerticalAlign(SWT.CENTER);
		imgIcon.setLayoutData(gd_imgIcon);
		
		IconButton btnGraphics = new IconButton(compositeIcon, true);
		btnGraphics.setImageWidget(imgIcon);
		addControl(btnGraphics, "icon");
		
		// Description
		
		new LLabel(grpGeneral, LLabel.TOP, Vocab.instance.DESCRIPTION);
		
		LTextBox txtDescription = new LTextBox(grpGeneral);
		GridData gd_txtDescription = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		gd_txtDescription.minimumHeight = 60;
		gd_txtDescription.heightHint = 60;
		txtDescription.setLayoutData(gd_txtDescription);
		addControl(txtDescription, "description");
		
		// Price
		
		new LLabel(grpGeneral, Vocab.instance.PRICE);
		
		LPanel price = new LPanel(grpGeneral, 2, false);
		price.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		
		LSpinner spnPrice = new LSpinner(price);
		spnPrice.setMinimum(0);
		spnPrice.setMaximum(9999999);
		addControl(spnPrice, "price");
		
		LCheckBox btnSellable = new LCheckBox(price);
		btnSellable.setText(Vocab.instance.SELLABLE);
		addControl(btnSellable, "sellable");

		// Use
		
		LFrame grpUse = new LFrame(left, Vocab.instance.USE, 3, false);
		grpUse.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		
		new LLabel(grpUse, Vocab.instance.ITEMSKILL);
	
		LText txtSkill = new LText(grpUse, true);		
		btnSkill = new IDButton(grpUse, true);
		btnSkill.setNameWidget(txtSkill);
		addControl(btnSkill, "skillID");
		
		LPanel checkButtons = new LPanel(grpUse, true);
		checkButtons.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false, 3, 1));
		
		LCheckBox btnConsume = new LCheckBox(checkButtons);
		btnConsume.setText(Vocab.instance.CONSUME);
		addControl(btnConsume, "consume");
		
		LCheckBox btnNeedsUser = new LCheckBox(checkButtons);
		btnNeedsUser.setText(Vocab.instance.NEEDSUSER);
		addControl(btnNeedsUser, "needsUser");
		
		new LLabel(grpUse, LLabel.TOP, Vocab.instance.EFFECTS);
		
		SkillEffectList lstEffects = new SkillEffectList(grpUse);
		GridData gd_effect = new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1);
		gd_effect.heightHint = 60;
		gd_effect.minimumHeight = 60;
		lstEffects.setLayoutData(gd_effect);
		addChild(lstEffects, "effects");
		
		new LLabel(grpUse, LLabel.TOP, Vocab.instance.ATTRIBUTES);
		AttributeList lstUseAtt = new AttributeList(grpUse);
		GridData gd_att = new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1);
		gd_att.heightHint = 60;
		gd_att.minimumHeight = 60;
		lstUseAtt.setLayoutData(gd_att);
		addChild(lstUseAtt, "attributes");
		
		// Equip
		
		LFrame grpEquip = new LFrame(right, Vocab.instance.EQUIP, 2, false);
		grpEquip.setLayout(new GridLayout(2, false));
		grpEquip.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, true, 1, 1));
		
		new LLabel(grpEquip, Vocab.instance.SLOT);
		
		LText txtSlot = new LText(grpEquip);
		addControl(txtSlot, "slot");
		
		new LLabel(grpEquip, 1, 1);
		LCheckBox btnAllSlots = new LCheckBox(grpEquip);
		btnAllSlots.setText(Vocab.instance.ALLSLOTS);
		addControl(btnAllSlots, "allSlots");
		
		new LLabel(grpEquip, LLabel.TOP, Vocab.instance.BLOCKEDSLOTS);

		NameList lstBlocked = new NameList(grpEquip);
		GridData gd_lstBlocked = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
		gd_lstBlocked.heightHint = 48;
		lstBlocked.setLayoutData(gd_lstBlocked);
		addChild(lstBlocked, "blocked");
		
		new LLabel(grpEquip, LLabel.TOP, Vocab.instance.ATTRIBUTES);
		AttributeList lstEquipAtt = new AttributeList(grpEquip);
		GridData gd_EquipAtt = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		gd_EquipAtt.heightHint = 60;
		gd_EquipAtt.minimumHeight = 60;
		lstEquipAtt.setLayoutData(gd_EquipAtt);
		addChild(lstEquipAtt, "equipAttributes");
		
		new LLabel(grpEquip, LLabel.TOP, Vocab.instance.PROPERTIES);
		BonusList lstElement = new BonusList(grpEquip);
		GridData gd_equipElements = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		gd_equipElements.heightHint = 60;
		gd_equipElements.minimumHeight = 60;
		lstElement.setLayoutData(gd_equipElements);
		addChild(lstElement, "bonuses");
		
		new LLabel(grpEquip, Vocab.instance.STATUSADD, LLabel.TOP);
		EquipStatusList lstEquipStatus = new EquipStatusList(grpEquip);
		lstEquipStatus.setIncludeID(false);
		GridData gd_status = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		gd_status.heightHint = 60;
		gd_status.minimumHeight = 60;
		lstEquipStatus.setLayoutData(gd_status);
		addChild(lstEquipStatus, "equipStatus");
		
	}
	
	@Override
	public void onVisible() {
		btnSkill.dataTree = Project.current.skills.getTree();
		super.onVisible();
	}

	@Override
	protected GObjectTreeSerializer getSerializer() {
		return Project.current.items;
	}

}
