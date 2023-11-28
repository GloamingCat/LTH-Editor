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
import lwt.LFlags;
import lwt.container.LContainer;
import lwt.container.LFrame;
import lwt.container.LPanel;
import lwt.widget.LCheckBox;
import lwt.widget.LImage;
import lwt.widget.LLabel;
import lwt.widget.LSpinner;
import lwt.widget.LText;
import lwt.widget.LTextBox;

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
		compositeIcon.setAlignment(LFlags.CENTER);
		
		LImage imgIcon = new LImage(compositeIcon);
		imgIcon.setImage("/javax/swing/plaf/basic/icons/image-delayed.png");
		imgIcon.setExpand(true, true);
		imgIcon.setMinimumWidth(48);
		imgIcon.setMinimumHeight(48);
		imgIcon.setAlignment(LFlags.CENTER);
		
		IconButton btnGraphics = new IconButton(compositeIcon, true);
		btnGraphics.setImageWidget(imgIcon);
		addControl(btnGraphics, "icon");
		
		// Description
		
		new LLabel(grpGeneral, LFlags.TOP, Vocab.instance.DESCRIPTION);
		
		LTextBox txtDescription = new LTextBox(grpGeneral, 1, 1);
		txtDescription.setMinimumHeight(60);
		addControl(txtDescription, "description");
		
		// Price
		
		new LLabel(grpGeneral, Vocab.instance.PRICE);
		
		LPanel price = new LPanel(grpGeneral, 2, false);
		price.setAlignment(LFlags.CENTER);
		
		LSpinner spnPrice = new LSpinner(price);
		spnPrice.setMinimum(0);
		spnPrice.setMaximum(9999999);
		addControl(spnPrice, "price");
		
		LCheckBox btnSellable = new LCheckBox(price);
		btnSellable.setText(Vocab.instance.SELLABLE);
		addControl(btnSellable, "sellable");

		// Use
		
		LFrame grpUse = new LFrame(left, Vocab.instance.USE, 3, false);
		grpUse.setExpand(true, true);
		
		new LLabel(grpUse, Vocab.instance.ITEMSKILL);
	
		LText txtSkill = new LText(grpUse, true);		
		btnSkill = new IDButton(grpUse, true);
		btnSkill.setNameWidget(txtSkill);
		addControl(btnSkill, "skillID");
		
		LPanel checkButtons = new LPanel(grpUse, true);
		checkButtons.setAlignment(LFlags.TOP);
		checkButtons.setExpand(true, false);
		checkButtons.setSpread(3, 1);
		
		LCheckBox btnConsume = new LCheckBox(checkButtons);
		btnConsume.setText(Vocab.instance.CONSUME);
		addControl(btnConsume, "consume");
		
		LCheckBox btnNeedsUser = new LCheckBox(checkButtons);
		btnNeedsUser.setText(Vocab.instance.NEEDSUSER);
		addControl(btnNeedsUser, "needsUser");
		
		new LLabel(grpUse, LFlags.TOP, Vocab.instance.EFFECTS);
		
		SkillEffectList lstEffects = new SkillEffectList(grpUse);
		lstEffects.setMinimumHeight(60);
		lstEffects.setExpand(true, true);
		lstEffects.setSpread(2, 1);
		addChild(lstEffects, "effects");
		
		new LLabel(grpUse, LFlags.TOP, Vocab.instance.ATTRIBUTES);
		AttributeList lstUseAtt = new AttributeList(grpUse);
		lstUseAtt.setMinimumHeight(60);
		lstUseAtt.setExpand(true, true);
		lstUseAtt.setSpread(2, 1);
		addChild(lstUseAtt, "attributes");
		
		// Equip
		
		LFrame grpEquip = new LFrame(right, Vocab.instance.EQUIP, 2, false);
		grpEquip.setExpand(false, true);
		
		new LLabel(grpEquip, Vocab.instance.SLOT);
		
		LText txtSlot = new LText(grpEquip);
		addControl(txtSlot, "slot");
		
		new LLabel(grpEquip, 1, 1);
		LCheckBox btnAllSlots = new LCheckBox(grpEquip);
		btnAllSlots.setText(Vocab.instance.ALLSLOTS);
		addControl(btnAllSlots, "allSlots");
		
		new LLabel(grpEquip, LFlags.TOP, Vocab.instance.BLOCKEDSLOTS);

		NameList lstBlocked = new NameList(grpEquip);
		lstBlocked.setAlignment(LFlags.CENTER);
		lstBlocked.setMinimumHeight(48);
		addChild(lstBlocked, "blocked");
		
		new LLabel(grpEquip, LFlags.TOP, Vocab.instance.ATTRIBUTES);
		AttributeList lstEquipAtt = new AttributeList(grpEquip);
		lstEquipAtt.setExpand(true, true);
		lstEquipAtt.setMinimumHeight(60);
		addChild(lstEquipAtt, "equipAttributes");
		
		new LLabel(grpEquip, LFlags.TOP, Vocab.instance.PROPERTIES);
		BonusList lstElement = new BonusList(grpEquip);
		lstElement.setExpand(true, true);
		lstElement.setMinimumHeight(60);
		addChild(lstElement, "bonuses");
		
		new LLabel(grpEquip, LFlags.TOP, Vocab.instance.STATUSADD);
		EquipStatusList lstEquipStatus = new EquipStatusList(grpEquip);
		lstEquipStatus.setExpand(true, true);
		lstEquipStatus.setMinimumHeight(60);
		lstEquipStatus.setIncludeID(false);
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
