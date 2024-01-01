package gui.views.database.content;

import gson.project.GObjectTreeSerializer;
import gui.Tooltip;
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
import lwt.container.LImage;
import lwt.container.LPanel;
import lwt.widget.LCheckBox;
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
		
		LLabel lblIcon = new LLabel(grpGeneral, Vocab.instance.ICON, Tooltip.instance.ICON);
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
		btnGraphics.addMenu(lblIcon);
		btnGraphics.addMenu(imgIcon);
		addControl(btnGraphics, "icon");
		
		// Description
		
		LLabel lblDesc = new LLabel(grpGeneral, LFlags.TOP, Vocab.instance.DESCRIPTION,
				Tooltip.instance.DESCRIPTION);
		LTextBox txtDescription = new LTextBox(grpGeneral, 1, 1);
		txtDescription.setMinimumHeight(60);
		txtDescription.addMenu(lblDesc);
		addControl(txtDescription, "description");
		
		// Price
		
		LLabel lblPrice = new LLabel(grpGeneral, Vocab.instance.PRICE, Tooltip.instance.PRICE);
		LPanel price = new LPanel(grpGeneral, 2, false);
		price.setAlignment(LFlags.CENTER);
		LSpinner spnPrice = new LSpinner(price);
		spnPrice.setMinimum(0);
		spnPrice.setMaximum(9999999);
		spnPrice.addMenu(lblPrice);
		addControl(spnPrice, "price");
		
		LCheckBox btnSellable = new LCheckBox(price);
		btnSellable.setText(Vocab.instance.SELLABLE);
		btnSellable.setHoverText(Tooltip.instance.SELLABLE);
		addControl(btnSellable, "sellable");

		// Use
		
		LFrame grpUse = new LFrame(left, Vocab.instance.USE, 3, false);
		grpUse.setHoverText(Tooltip.instance.USE);
		grpUse.setExpand(true, true);
		
		LLabel lblSkill = new LLabel(grpUse, Vocab.instance.ITEMSKILL, Tooltip.instance.ITEMSKILL);
		LText txtSkill = new LText(grpUse, true);		
		btnSkill = new IDButton(grpUse, true);
		btnSkill.setNameWidget(txtSkill);
		btnSkill.addMenu(lblSkill);
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
		
		LLabel lblEffects = new LLabel(grpUse, LFlags.TOP, Vocab.instance.EFFECTS,
				Tooltip.instance.ITEMEFFECTS);
		
		SkillEffectList lstEffects = new SkillEffectList(grpUse);
		lstEffects.setMinimumHeight(60);
		lstEffects.setExpand(true, true);
		lstEffects.setSpread(2, 1);
		lstEffects.addMenu(lblEffects);
		addChild(lstEffects, "effects");
		
		LLabel lblAtt = new LLabel(grpUse, LFlags.TOP, Vocab.instance.ATTRIBUTES,
				Tooltip.instance.ITEMATTRIBUTES);
		AttributeList lstUseAtt = new AttributeList(grpUse);
		lstUseAtt.setMinimumHeight(60);
		lstUseAtt.setExpand(true, true);
		lstUseAtt.setSpread(2, 1);
		lstUseAtt.addMenu(lblAtt);
		addChild(lstUseAtt, "attributes");
		
		// Equip
		
		LFrame grpEquip = new LFrame(right, Vocab.instance.EQUIP, 2, false);
		grpEquip.setHoverText(Tooltip.instance.EQUIP);
		grpEquip.setExpand(false, true);
		
		LLabel lblSlot = new LLabel(grpEquip, Vocab.instance.SLOT, Tooltip.instance.SLOT);
		LText txtSlot = new LText(grpEquip);
		txtSlot.addMenu(lblSlot);
		addControl(txtSlot, "slot");
		
		new LLabel(grpEquip, 1, 1);
		LCheckBox btnAllSlots = new LCheckBox(grpEquip);
		btnAllSlots.setText(Vocab.instance.ALLSLOTS);
		addControl(btnAllSlots, "allSlots");
		
		LLabel lblBlock = new LLabel(grpEquip, LFlags.TOP, Vocab.instance.BLOCKEDSLOTS,
				Tooltip.instance.BLOCKEDSLOTS);
		NameList lstBlocked = new NameList(grpEquip);
		lstBlocked.setAlignment(LFlags.CENTER);
		lstBlocked.setMinimumHeight(48);
		lstBlocked.addMenu(lblBlock);
		addChild(lstBlocked, "blocked");
		
		LLabel lblEquipAtt = new LLabel(grpEquip, LFlags.TOP, Vocab.instance.ATTRIBUTES,
				Tooltip.instance.EQUIPATTRIBUTES);
		AttributeList lstEquipAtt = new AttributeList(grpEquip);
		lstEquipAtt.setExpand(true, true);
		lstEquipAtt.setMinimumHeight(60);
		lstEquipAtt.addMenu(lblEquipAtt);
		addChild(lstEquipAtt, "equipAttributes");
		
		LLabel lblProp = new LLabel(grpEquip, LFlags.TOP, Vocab.instance.PROPERTIES,
				Tooltip.instance.EQUIPPROPERTIES);
		BonusList lstElement = new BonusList(grpEquip);
		lstElement.setExpand(true, true);
		lstElement.setMinimumHeight(60);
		lstElement.addMenu(lblProp);
		addChild(lstElement, "bonuses");
		
		LLabel lblStatus = new LLabel(grpEquip, LFlags.TOP, Vocab.instance.STATUSES,
				Tooltip.instance.EQUIPSTATUSES);
		EquipStatusList lstEquipStatus = new EquipStatusList(grpEquip);
		lstEquipStatus.setExpand(true, true);
		lstEquipStatus.setMinimumHeight(60);
		lstEquipStatus.setIncludeID(false);
		lstEquipStatus.addMenu(lblStatus);
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
