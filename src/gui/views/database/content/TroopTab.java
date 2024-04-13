package gui.views.database.content;

import lui.base.LFlags;
import lui.base.data.LDataList;
import lui.base.data.LPath;
import lui.base.data.LPoint;
import lui.base.event.listener.LCollectionListener;
import lui.base.event.listener.LSelectionListener;
import lui.base.event.LDeleteEvent;
import lui.base.event.LEditEvent;
import lui.base.event.LInsertEvent;
import lui.container.LContainer;
import lui.container.LFrame;
import lui.container.LImage;
import lui.container.LPanel;
import lui.editor.LGridEditor;
import lui.graphics.LColor;
import lui.widget.LCheckBox;
import lui.widget.LLabel;
import lui.widget.LSpinner;
import lui.widget.LText;
import gui.Tooltip;
import gui.Vocab;
import gui.views.database.DatabaseTab;
import gui.views.database.subcontent.DropList;
import gui.views.database.subcontent.UnitEditor;
import gui.widgets.LuaButton;
import gui.widgets.SimpleEditableList;
import data.Animation;
import data.GameCharacter;
import data.Troop;
import data.Troop.Unit;
import data.config.Config;
import data.subcontent.Icon;
import gson.GObjectTreeSerializer;
import project.Project;

public class TroopTab extends DatabaseTab<Troop> {
	
	public static int tWidth = 32;
	public static int tHeight = 48;
	
	private final LGridEditor<LPoint, LPoint> gridEditor;
	private final LDataList<LPoint> points = new LDataList<>();

	/**
	 * @wbp.parser.constructor
	 * @wbp.eval.method.parameter parent new lwt.dialog.LShell(800, 600)
	 */
	public TroopTab(LContainer parent) {
		super(parent);
		
		// Rewards
		
		LLabel lblMoney = new LLabel(grpGeneral, Vocab.instance.MONEY, Tooltip.instance.MONEY);
		LPanel compositeReward = new LPanel(grpGeneral);
		compositeReward.setGridLayout(3);
		compositeReward.getCellData().setExpand(true, false);
		LSpinner spnMoney = new LSpinner(compositeReward);
		spnMoney.getCellData().setExpand(true, false);
		spnMoney.setMaximum(99999999);
		spnMoney.addMenu(lblMoney);
		addControl(spnMoney, "money");
		
		LLabel lblExp = new LLabel(compositeReward, Vocab.instance.EXP, Tooltip.instance.EXP);
		lblExp.getCellData().setMinimumSize(LABELWIDTH, 0);
		LSpinner spnEXP = new LSpinner(compositeReward);
		spnEXP.getCellData().setExpand(true, false);
		spnEXP.setMaximum(99999999);
		spnEXP.addMenu(lblExp);
		addControl(spnEXP, "exp");
		
		// AI
		
		LLabel lblAI = new LLabel(grpGeneral, Vocab.instance.AI, Tooltip.instance.AI);
		LPanel select = new LPanel(grpGeneral);
		select.setGridLayout(2);
		select.getCellData().setExpand(true, false);
		LText txtAI = new LText(select, true);
		txtAI.getCellData().setExpand(true, false);
		LuaButton btnAI = new LuaButton(select, Vocab.instance.TROOPSCRIPTSHELL, true);
		btnAI.setPathWidget(txtAI);
		btnAI.addMenu(lblAI);
		btnAI.addMenu(txtAI);
		addControl(btnAI, "ai");

		// Properties

		LPanel check = new LPanel(grpGeneral);
		check.setGridLayout(2);
		check.getCellData().setExpand(true, false);
		check.getCellData().setAlignment(LFlags.LEFT);
		check.getCellData().setSpread(2, 1);

		LCheckBox btnPersistent = new LCheckBox(check);
		btnPersistent.setText(Vocab.instance.PERSISTENT);
		btnPersistent.setHoverText(Tooltip.instance.PERSISTENT);
		btnPersistent.getCellData().setExpand(true, false);
		addControl(btnPersistent, "persistent");

		// Grid
		
		LFrame grpGrid = new LFrame(left, Vocab.instance.GRID);
		grpGrid.setFillLayout(true);
		grpGrid.setHoverText(Tooltip.instance.TROOPGRID);
		grpGrid.getCellData().setExpand(true, true);
		gridEditor = new UnitGrid(grpGrid);

		// Items
		
		LFrame grpItems = new LFrame(right, Vocab.instance.ITEMS);
		grpItems.setFillLayout(true);
		grpItems.setHoverText(Tooltip.instance.INVENTORY);
		grpItems.getCellData().setExpand(true, true);
		DropList lstItems = new DropList(grpItems);
		lstItems.addMenu(grpItems);
		addChild(lstItems, "items");

		// Units
		
		LFrame grpMembers = new LFrame(contentEditor, Vocab.instance.UNITS);
		grpMembers.setFillLayout(true);
		grpMembers.setSpacing(5);
		grpMembers.setHoverText(Tooltip.instance.UNITS);
		grpMembers.getCellData().setExpand(true, false);
		grpMembers.getCellData().setSpread(2, 1);
		
		SimpleEditableList<Unit> lstMembers = new SimpleEditableList<>(grpMembers);
		lstMembers.getCollectionWidget().setEditEnabled(false);
		lstMembers.setIncludeID(false);
		lstMembers.type = Unit.class;
		lstMembers.addMenu(grpMembers);
		addChild(lstMembers, "members");
		
		UnitEditor unitEditor = new UnitEditor(grpMembers);
		lstMembers.addChild(unitEditor);
		unitEditor.addModifyListener(event -> refreshUnit(event.newValue));
		
		LCollectionListener<Unit> modifyListener = new LCollectionListener<>() {
			public void onEdit(LEditEvent<Unit> e) {
				refreshUnit(e.newData);
			}
			public void onInsert(LInsertEvent<Unit> e) {
				refreshUnit(e.node.data);
			}
			public void onDelete(LDeleteEvent<Unit> e) {
				refreshUnit(e.node.data);
			}
		};
		lstMembers.getCollectionWidget().addEditListener(modifyListener);
		lstMembers.getCollectionWidget().addInsertListener(modifyListener);
		lstMembers.getCollectionWidget().addDeleteListener(modifyListener);
		lstMembers.getCollectionWidget().addMoveListener(modifyListener);
		lstMembers.getCollectionWidget().addSelectionListener(event -> {
            Unit u = (Unit) event.data;
            if (u != null) {
                Config.Troop conf = Project.current.config.getData().troop;
                int i = (u.y - 1) * conf.width + (u.x - 1);
                gridEditor.getCollectionWidget().select(new LPath(i));
            }
        });
		gridEditor.getCollectionWidget().addSelectionListener(event -> {
            Troop troop = contentEditor.getObject();
            if (troop == null)
                return;
            LPoint p = (LPoint) event.data;
            if (p != null) {
                int i = troop.find(p.x, p.y);
                if (i != -1) {
                    lstMembers.getCollectionWidget().select(new LPath(i));
                }
            }
        });
		
		LSelectionListener selectionListener = event -> gridEditor.getCollectionWidget().setDataCollection(points);
		listEditor.getCollectionWidget().addSelectionListener(selectionListener);
		unitEditor.addSelectionListener(selectionListener);
		
	}
	
	@Override
	public void onVisible() {
		Config.Troop conf = Project.current.config.getData().troop;
		gridEditor.getCollectionWidget().setColumns(conf.width);
		points.clear();
		for (int j = 0; j < conf.height; j++) {				
			for (int i = 0; i < conf.width; i++) {
				points.add(new LPoint(i + 1, j + 1));
			}
		}
		super.onVisible();
	}
	
	protected void refreshUnit(LImage img) {
		LPoint p = (LPoint) img.getData();
		Troop troop = contentEditor.getObject();
		if (troop == null) {
			img.setImage((String) null);
			return;
		}
		int i = troop.find(p.x, p.y);
		Unit u = i >= 0 ? troop.members.get(i) : null;
		refreshUnit(img, u);
	}
	
	protected void refreshUnit(Unit u) {
		Config.Troop conf = Project.current.config.getData().troop;
		int i = (u.y - 1) * conf.width + (u.x - 1);
		LImage img = (LImage) gridEditor.getCollectionWidget().getChild(i);
		refreshUnit(img, u);
	}
	
	protected void refreshUnit(LImage img, Unit u) {
		if (u != null) {
			GameCharacter c = (GameCharacter) Project.current.characters.getData().get(u.charID);
			if (c != null) {
				int animID = c.defaultAnimationID();
				Animation anim = (Animation) Project.current.animations.getData().get(animID);
				if (anim != null && anim.cols > 0 && anim.rows > 0) {
					Icon icon = new Icon(animID, anim.getFrame(0), 270 / 45);
					anim.transform.setColorTransform(img, c.transform);
					img.setScale(anim.transform.scaleX * c.transform.scaleX / 10000f, anim.transform.scaleY * c.transform.scaleY / 10000f);
					img.setImage(icon.fullPath(), icon.getRectangle());
					return;
				}
			}
		}
		img.setImage((String) null);
	}
	
	@Override
	protected GObjectTreeSerializer getSerializer() {
		return Project.current.troops;
	}
	
	private class UnitGrid extends LGridEditor<LPoint, LPoint> {
		
		public UnitGrid(LContainer parent) {
			super(parent);
			getCollectionWidget().cellWidth = tWidth;
			getCollectionWidget().cellHeight = tHeight;
			setBackground(new LColor(255, 0, 255).convert());
		}
		
		@Override
		protected LPoint createNewElement() { return null; }
		@Override
		protected LPoint duplicateElement(LPoint original) { return null; }
		@Override
		protected void setImage(LImage img, int i) {
			refreshUnit(img);
		}
		@Override
		protected LDataList<LPoint> getDataCollection() {
			return points;
		}
		@Override
		protected LPoint getEditableData(LPath path) { return null; }
		@Override
		protected void setEditableData(LPath path, LPoint newData) {}
		@Override
		protected String encodeElement(LPoint data) {
			return null;
		}
		@Override
		protected LPoint decodeElement(String str) {
			return null;
		}
		@Override
		public boolean canDecode(String str) {
			return false;
		}
	}
	
}
