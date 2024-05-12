package gui.views.database.content;

import gui.Tooltip;
import gui.Vocab;
import gui.views.database.DatabaseTab;
import gui.views.database.subcontent.AttributeList;
import gui.views.database.subcontent.BonusList;
import gui.views.database.subcontent.SkillEffectList;
import gui.views.database.subcontent.EquipStatusList;
import gui.widgets.CheckBoxPanel;
import gui.widgets.IDButton;
import gui.widgets.IconButton;
import gui.widgets.NameList;
import lui.base.LFlags;
import lui.base.LPrefs;
import lui.container.LContainer;
import lui.container.LFrame;
import lui.container.LImage;
import lui.container.LPanel;
import lui.widget.LCheckBox;
import lui.widget.LLabel;
import lui.widget.LSpinner;
import lui.widget.LText;
import lui.widget.LTextBox;

import project.Project;

import data.Item;
import gson.GObjectTreeSerializer;

public class ItemTab extends DatabaseTab<Item> {

	private IDButton btnSkill;
	
	/**
	 * @wbp.parser.constructor
	 * @wbp.eval.method.parameter parent new lwt.dialog.LShell(800, 600)
	 */
	public ItemTab(LContainer parent) {
		super(parent);
	}

	@Override
	protected void createContent() {
		// Icon
		
		LLabel lblIcon = new LLabel(contentEditor.grpGeneral, Vocab.instance.ICON, Tooltip.instance.ICON);
		LPanel compositeIcon = new LPanel(contentEditor.grpGeneral);
		compositeIcon.setGridLayout(2);
		compositeIcon.getCellData().setExpand(true, false);
		LImage imgIcon = new LImage(compositeIcon);
		imgIcon.getCellData().setExpand(true, true);
		imgIcon.getCellData().setRequiredSize(48,48);
		imgIcon.setAlignment(LFlags.LEFT | LFlags.TOP);
		IconButton btnGraphics = new IconButton(compositeIcon, true);
		btnGraphics.setImageWidget(imgIcon);
		btnGraphics.addMenu(lblIcon);
		btnGraphics.addMenu(imgIcon);
		addControl(btnGraphics, "icon");
		
		// Description
		
		LLabel lblDesc = new LLabel(contentEditor.grpGeneral, LFlags.TOP, Vocab.instance.DESCRIPTION,
				Tooltip.instance.DESCRIPTION);
		LTextBox txtDescription = new LTextBox(contentEditor.grpGeneral);
		txtDescription.getCellData().setExpand(true, true);
		txtDescription.getCellData().setRequiredSize(0, 60);
		txtDescription.addMenu(lblDesc);
		addControl(txtDescription, "description");
		
		// Price
		
		LLabel lblPrice = new LLabel(contentEditor.grpGeneral, Vocab.instance.PRICE, Tooltip.instance.PRICE);
		LPanel price = new LPanel(contentEditor.grpGeneral);
		price.setGridLayout(2);
		price.getCellData().setExpand(true, false);
		LSpinner spnPrice = new LSpinner(price);
		spnPrice.getCellData().setExpand(true, false);
		spnPrice.setMinimum(0);
		spnPrice.setMaximum(9999999);
		spnPrice.addMenu(lblPrice);
		addControl(spnPrice, "price");
		
		LCheckBox btnSellable = new LCheckBox(price);
		btnSellable.setText(Vocab.instance.SELLABLE);
		btnSellable.setHoverText(Tooltip.instance.SELLABLE);
		addControl(btnSellable, "sellable");

		// Use
		
		LFrame grpUse = new LFrame(contentEditor.left, Vocab.instance.USE);
		grpUse.setGridLayout(3);
		grpUse.setHoverText(Tooltip.instance.USE);
		grpUse.getCellData().setExpand(true, true);
		
		LLabel lblSkill = new LLabel(grpUse, Vocab.instance.ITEMSKILL, Tooltip.instance.ITEMSKILL);
		LText txtSkill = new LText(grpUse, true);
		txtSkill.getCellData().setExpand(true, false);
		btnSkill = new IDButton(grpUse, Vocab.instance.SKILLSHELL, true);
		btnSkill.setNameWidget(txtSkill);
		btnSkill.addMenu(lblSkill);
		addControl(btnSkill, "skillID");

		LLabel lblEffects = new LLabel(grpUse, LFlags.TOP, Vocab.instance.EFFECTS,
				Tooltip.instance.ITEMEFFECTS);
		lblEffects.getCellData().setRequiredSize(LPrefs.LABELWIDTH, 0);
		SkillEffectList lstEffects = new SkillEffectList(grpUse);
		lstEffects.getCellData().setRequiredSize(0, 60);
		lstEffects.getCellData().setExpand(true, true);
		lstEffects.getCellData().setSpread(2, 1);
		lstEffects.addMenu(lblEffects);
		addChild(lstEffects, "effects");
		
		LLabel lblAtt = new LLabel(grpUse, LFlags.TOP, Vocab.instance.ATTRIBUTES,
				Tooltip.instance.ITEMATTRIBUTES);
		AttributeList lstUseAtt = new AttributeList(grpUse);
		lstUseAtt.getCellData().setRequiredSize(0, 60);
		lstUseAtt.getCellData().setExpand(true, true);
		lstUseAtt.getCellData().setSpread(2, 1);
		lstUseAtt.addMenu(lblAtt);
		addChild(lstUseAtt, "attributes");

		LPanel checkButtons = new CheckBoxPanel(grpUse);
		checkButtons.getCellData().setSpread(3, 1);

		LCheckBox btnConsume = new LCheckBox(checkButtons);
		btnConsume.setText(Vocab.instance.CONSUME);
		btnConsume.setHoverText(Tooltip.instance.CONSUME);
		addControl(btnConsume, "consume");

		LCheckBox btnNeedsUser = new LCheckBox(checkButtons);
		btnNeedsUser.setText(Vocab.instance.NEEDSUSER);
		btnNeedsUser.setHoverText(Tooltip.instance.NEEDSUSER);
		addControl(btnNeedsUser, "needsUser");

		// Equip

		LFrame grpEquip = new LFrame(contentEditor.right, Vocab.instance.EQUIP);
		grpEquip.setGridLayout(2);
		grpEquip.setHoverText(Tooltip.instance.EQUIP);
		grpEquip.getCellData().setExpand(true, true);

		LLabel lblSlot = new LLabel(grpEquip, Vocab.instance.SLOT, Tooltip.instance.SLOT);
		lblSlot.getCellData().setRequiredSize(LPrefs.LABELWIDTH, 0);
		LText txtSlot = new LText(grpEquip);
		txtSlot.getCellData().setExpand(true, false);
		txtSlot.addMenu(lblSlot);
		addControl(txtSlot, "slot");
		
		new LLabel(grpEquip, 1, 1);
		LCheckBox btnAllSlots = new LCheckBox(grpEquip);
		btnAllSlots.getCellData().setAlignment(LFlags.LEFT);
		btnAllSlots.setText(Vocab.instance.ALLSLOTS);
		btnAllSlots.setHoverText(Tooltip.instance.ALLSLOTS);
		addControl(btnAllSlots, "allSlots");
		
		LLabel lblBlock = new LLabel(grpEquip, LFlags.TOP, Vocab.instance.BLOCKEDSLOTS,
				Tooltip.instance.BLOCKEDSLOTS);
		NameList lstBlocked = new NameList(grpEquip, Vocab.instance.SLOTSHELL);
		lstBlocked.setIncludeID(false);
		lstBlocked.getCellData().setExpand(true, true);
		lstBlocked.getCellData().setRequiredSize(0, 48);
		lstBlocked.addMenu(lblBlock);
		addChild(lstBlocked, "blocked");
		
		LLabel lblEquipAtt = new LLabel(grpEquip, LFlags.TOP, Vocab.instance.ATTRIBUTES,
				Tooltip.instance.EQUIPATTRIBUTES);
		AttributeList lstEquipAtt = new AttributeList(grpEquip);
		lstEquipAtt.getCellData().setExpand(true, true);
		lstEquipAtt.getCellData().setRequiredSize(0, 60);
		lstEquipAtt.addMenu(lblEquipAtt);
		addChild(lstEquipAtt, "equipAttributes");
		
		LLabel lblProp = new LLabel(grpEquip, LFlags.TOP, Vocab.instance.PROPERTIES,
				Tooltip.instance.EQUIPPROPERTIES);
		BonusList lstElement = new BonusList(grpEquip, Vocab.instance.PROPERTYSHELL);
		lstElement.getCellData().setExpand(true, true);
		lstElement.getCellData().setRequiredSize(0, 60);
		lstElement.addMenu(lblProp);
		addChild(lstElement, "bonuses");
		
		LLabel lblStatus = new LLabel(grpEquip, LFlags.TOP, Vocab.instance.STATUSES,
				Tooltip.instance.EQUIPSTATUSES);
		EquipStatusList lstEquipStatus = new EquipStatusList(grpEquip);
		lstEquipStatus.getCellData().setExpand(true, true);
		lstEquipStatus.getCellData().setRequiredSize(0, 60);
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
