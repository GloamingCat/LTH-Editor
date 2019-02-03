package gui.views.database.content;

import gson.project.GObjectTreeSerializer;
import gui.Vocab;
import gui.views.database.DatabaseTab;
import gui.views.database.subcontent.AttributeList;
import gui.views.database.subcontent.BonusList;
import gui.views.database.subcontent.EffectList;
import gui.views.database.subcontent.EquipStatusList;
import gui.views.database.subcontent.TagList;
import gui.widgets.IDButton;
import gui.widgets.IconButton;
import gui.widgets.NameList;
import lwt.dataestructure.LDataTree;
import lwt.widget.LCheckButton;
import lwt.widget.LImage;
import lwt.widget.LSpinner;
import lwt.widget.LText;
import lwt.widget.LTextBox;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import project.Project;

import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;

public class ItemTab extends DatabaseTab {

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public ItemTab(Composite parent, int style) {
		super(parent, style);
		
		Composite right = new Composite(contentEditor, SWT.NONE);
		right.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 2));
		
		Label lblDescription = new Label(grpGeneral, SWT.NONE);
		lblDescription.setLayoutData(new GridData(SWT.LEFT, SWT.FILL, false, false, 1, 1));
		lblDescription.setText(Vocab.instance.DESCRIPTION);
		
		LTextBox txtDescription = new LTextBox(grpGeneral, SWT.NONE);
		GridData gd_txtDescription = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		gd_txtDescription.minimumHeight = 48;
		txtDescription.setLayoutData(gd_txtDescription);
		addControl(txtDescription, "description");
		
		// Icon
		
		Label lblIcon = new Label(grpGeneral, SWT.NONE);
		lblIcon.setText(Vocab.instance.ICON);
		
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
		btnGraphics.setImage(imgIcon);
		addControl(btnGraphics, "icon");
		
		// Price
		
		Label lblPrice = new Label(grpGeneral, SWT.NONE);
		lblPrice.setText(Vocab.instance.PRICE);
		
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
		
		LCheckButton btnSellable = new LCheckButton(price, SWT.NONE);
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
		
		Label lblSlot = new Label(grpEquip, SWT.NONE);
		lblSlot.setText(Vocab.instance.SLOT);
		
		LText txtSlot = new LText(grpEquip, SWT.NONE);
		txtSlot.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		addControl(txtSlot, "slot");
		
		LCheckButton btnAllSlots = new LCheckButton(grpEquip, SWT.NONE);
		btnAllSlots.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1));
		btnAllSlots.setText(Vocab.instance.ALLSLOTS);
		addControl(btnAllSlots, "allSlots");
		
		Label lblBlocked = new Label(grpEquip, SWT.NONE);
		lblBlocked.setLayoutData(new GridData(SWT.LEFT, SWT.TOP, false, false, 1, 1));
		lblBlocked.setText(Vocab.instance.BLOCKEDSLOTS);

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
		
		TabItem tbtmElementAtk = new TabItem(equipFolder, SWT.NONE);
		tbtmElementAtk.setText(Vocab.instance.ELEMENTATK);
		BonusList lstElementAtk = new BonusList(equipFolder, SWT.NONE) {
			@Override
			protected LDataTree<Object> getDataTree() {
				return Project.current.elements.getList().toTree();
			}
		};
		lstElementAtk.setIncludeID(false);
		tbtmElementAtk.setControl(lstElementAtk);
		addChild(lstElementAtk, "elementAtk");
		
		TabItem tbtmElementDef = new TabItem(equipFolder, SWT.NONE);
		tbtmElementDef.setText(Vocab.instance.ELEMENTDEF);
		BonusList lstElementDef = new BonusList(equipFolder, SWT.NONE) {
			@Override
			protected LDataTree<Object> getDataTree() {
				return Project.current.elements.getList().toTree();
			}
		};
		lstElementDef.setIncludeID(false);
		tbtmElementDef.setControl(lstElementDef);
		addChild(lstElementDef, "elementDef");
		
		// Use
		
		Group grpUse = new Group(contentEditor, SWT.NONE);
		grpUse.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		grpUse.setText(Vocab.instance.USE);
		grpUse.setLayout(new GridLayout(3, false));
		
		Label lblSkill = new Label(grpUse, SWT.NONE);
		lblSkill.setText(Vocab.instance.ITEMSKILL);
	
		Text txtSkill = new Text(grpUse, SWT.BORDER | SWT.READ_ONLY);
		txtSkill.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
		
		IDButton btnSkill = new IDButton(grpUse, 1) {
			public LDataTree<Object> getDataTree() {
				return Project.current.skills.getTree();
			}
		};
		btnSkill.setNameText(txtSkill);
		addControl(btnSkill, "skillID");
		
		LCheckButton btnConsume = new LCheckButton(grpUse, SWT.NONE);
		btnConsume.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false, 3, 1));
		btnConsume.setText(Vocab.instance.CONSUME);
		addControl(btnConsume, "consume");
		
		TabFolder useFolder = new TabFolder(grpUse, SWT.NONE);
		useFolder.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, true, 3, 1));
		
		TabItem tbtmEffects = new TabItem(useFolder, SWT.NONE);
		tbtmEffects.setText(Vocab.instance.EFFECTS);
		EffectList lstEffects = new EffectList(useFolder, SWT.NONE);
		tbtmEffects.setControl(lstEffects);
		addChild(lstEffects, "effects");
		
		TabItem tbtmStatusAdd = new TabItem(useFolder, SWT.NONE);
		tbtmStatusAdd.setText(Vocab.instance.STATUSADD);
		BonusList lstStatusAdd = new BonusList(useFolder, SWT.NONE) {
			protected LDataTree<Object> getDataTree() {
				return Project.current.status.getTree();
			}
		};
		lstStatusAdd.setIncludeID(false);
		tbtmStatusAdd.setControl(lstStatusAdd);
		addChild(lstStatusAdd, "statusAdd");
		
		TabItem tbtmStatusRmv = new TabItem(useFolder, SWT.NONE);
		tbtmStatusRmv.setText(Vocab.instance.STATUSREMOVE);
		BonusList lstStatusRmv = new BonusList(useFolder, SWT.NONE) {
			protected LDataTree<Object> getDataTree() {
				return Project.current.status.getTree();
			}
		};
		lstStatusRmv.setIncludeID(false);
		tbtmStatusRmv.setControl(lstStatusRmv);
		addChild(lstStatusRmv, "statusRemove");
		
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
