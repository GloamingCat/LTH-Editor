package gui.views.database.content;

import gui.widgets.CheckBoxPanel;
import lui.LovelyTheme;
import lui.base.LPrefs;
import lui.base.data.LDataList;
import lui.base.data.LPath;
import lui.base.data.LPoint;
import lui.base.event.listener.LCollectionListener;
import lui.base.event.LDeleteEvent;
import lui.base.event.LEditEvent;
import lui.base.event.LInsertEvent;
import lui.container.*;
import lui.widget.*;
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
	
	private LGrid<LPoint, LPoint> unitGrid;
	private final LDataList<LPoint> points = new LDataList<>();
	private SimpleEditableList<Unit> lstMembers;

	/**
	 * @wbp.parser.constructor
	 * @wbp.eval.method.parameter parent new lwt.dialog.LShell(800, 600)
	 */
	public TroopTab(LContainer parent) {
		super(parent);
	}

	@Override
	protected void createContent() {

		// Rewards
		
		LLabel lblMoney = new LLabel(contentEditor.grpGeneral, Vocab.instance.MONEY, Tooltip.instance.MONEY);
		LPanel rewards = new LPanel(contentEditor.grpGeneral);
		rewards.setGridLayout(3);
		rewards.getCellData().setExpand(true, false);

		LSpinner spnMoney = new LSpinner(rewards);
		spnMoney.getCellData().setExpand(true, false);
		spnMoney.addMenu(lblMoney);
		addControl(spnMoney, "money");
		
		LLabel lblExp = new LLabel(rewards, Vocab.instance.EXP, Tooltip.instance.EXP);
		lblExp.getCellData().setRequiredSize(LPrefs.LABELWIDTH / 2, 0);

		LSpinner spnEXP = new LSpinner(rewards);
		//spnEXP.getCellData().setTargetSize(LPrefs.BUTTONWIDTH, LPrefs.WIDGETHEIGHT);
		spnEXP.getCellData().setExpand(true, false);
		spnEXP.addMenu(lblExp);
		addControl(spnEXP, "exp");
		
		// AI
		
		LLabel lblAI = new LLabel(contentEditor.grpGeneral, Vocab.instance.AI, Tooltip.instance.AI);
		LPanel select = new LPanel(contentEditor.grpGeneral);
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

		LPanel check = new CheckBoxPanel(contentEditor.grpGeneral);
		check.getCellData().setSpread(2, 1);

		LCheckBox btnPersistent = new LCheckBox(check);
		btnPersistent.setText(Vocab.instance.PERSISTENT);
		btnPersistent.setHoverText(Tooltip.instance.PERSISTENT);
		addControl(btnPersistent, "persistent");

		// Grid
		
		LFrame grpGrid = new LFrame(contentEditor.left, Vocab.instance.GRID);
		grpGrid.setHoverText(Tooltip.instance.TROOPGRID);
		grpGrid.getCellData().setExpand(true, true);
		unitGrid = new UnitGrid(grpGrid);

		// Items
		
		LFrame grpItems = new LFrame(contentEditor.right, Vocab.instance.ITEMS);
		grpItems.setFillLayout(true);
		grpItems.setHoverText(Tooltip.instance.INVENTORY);
		grpItems.getCellData().setExpand(true, true);
		DropList lstItems = new DropList(grpItems);
		lstItems.addMenu(grpItems);
		addChild(lstItems, "items");

		// Units
		
		LFrame grpMembers = new LFrame(contentEditor, Vocab.instance.UNITS);
		grpMembers.setGridLayout(2);
		grpMembers.setEqualCells(true);
		grpMembers.setHoverText(Tooltip.instance.UNITS);
		grpMembers.getCellData().setExpand(true, false);
		grpMembers.getCellData().setSpread(2, 1);

		lstMembers = new SimpleEditableList<>(grpMembers);
		lstMembers.getCollectionWidget().setEditEnabled(false);
		lstMembers.getCellData().setExpand(true, true);
		lstMembers.setIncludeID(false);
		lstMembers.type = Unit.class;
		lstMembers.addMenu(grpMembers);
		addChild(lstMembers, "members");

		UnitEditor unitEditor = new UnitEditor(grpMembers);
		unitEditor.getCellData().setExpand(true, false);
		unitEditor.addModifyListener(event -> {
			refreshUnit(event.oldValue, true);
			refreshUnit(event.newValue, false);
		});
		lstMembers.addChild(unitEditor);

		LCollectionListener<Unit> modifyListener = new LCollectionListener<>() {
			public void onEdit(LEditEvent<Unit> e) {
				refreshUnit(e.newData, false);
			}
			public void onInsert(LInsertEvent<Unit> e) {
				refreshUnit(e.node.data, false);
			}
			public void onDelete(LDeleteEvent<Unit> e) {
				refreshUnit(e.node.data, true);
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
				LPath selection = unitGrid.getSelectedPath();
				int current = selection == null ? -1 : selection.index;
				if (current != i)
					unitGrid.select(new LPath(i));
            }
        });
		unitGrid.addSelectionListener(event -> {
            Troop troop = contentEditor.getObject();
            if (troop == null)
                return;
            LPoint p = (LPoint) event.data;
            if (p != null) {
                int i = troop.find(p.x, p.y);
                if (i != -1) {
					LPath selection = lstMembers.getCollectionWidget().getSelectedPath();
					if (selection == null || selection.index != i)
						lstMembers.getCollectionWidget().select(new LPath(i));
                }
            }
        });

		listEditor.getCollectionWidget().addSelectionListener(e -> refreshAllUnits());
		lstMembers.addModifyListener(e -> refreshAllUnits());
		
	}
	
	@Override
	public void onVisible() {
		Config.Troop conf = Project.current.config.getData().troop;
		points.clear();
		for (int j = 0; j < conf.height; j++) {
			for (int i = 0; i < conf.width; i++)
				points.add(new LPoint(i + 1, j + 1));
		}
		unitGrid.setColumns(conf.width);
		unitGrid.setDataCollection(points);
		super.onVisible();
		refreshAllUnits();
		lstMembers.forceFirstSelection();
	}

	protected void refreshAllUnits() {
		for (int i = 0; i < points.size(); i++) {
			LImage img = unitGrid.getImage(i);
			refreshUnit(img);
		}
	}
	
	protected void refreshUnit(LImage img) {
		LPoint p = (LPoint) img.getData();
		Troop troop = contentEditor.getObject();
		int i = troop == null ? -1 : troop.find(p.x, p.y);
		Unit u = i >= 0 ? troop.members.get(i) : null;
		refreshUnit(img, u);
	}
	
	protected void refreshUnit(Unit u, boolean erase) {
		Config.Troop conf = Project.current.config.getData().troop;
		if (u.x <= 0 || u.y <= 0 || u.x > conf.width || u.y > conf.height)
			return;
		int i = (u.y - 1) * conf.width + (u.x - 1);
		LImage img = (LImage) unitGrid.getChild(i);
		refreshUnit(img, erase ? null : u);
	}
	
	protected void refreshUnit(LImage img, Unit u) {
		if (u != null) {
			GameCharacter c = (GameCharacter) Project.current.characters.getData().get(u.charID);
			if (c != null) {
				int animID = c.defaultAnimationID();
				Animation anim = (Animation) Project.current.animations.getData().get(animID);
				if (anim != null && anim.cols > 0 && anim.rows > 0) {
					Icon icon = new Icon(animID, anim.getFrame(0), 270 / 45);
					img.resetTransform();
					anim.transform.applyTo(img);
					c.transform.applyTo(img);
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
	
	private class UnitGrid extends LGrid<LPoint, LPoint> {
		public UnitGrid(LContainer parent) {
			super(parent);
			setCellSize(tWidth, tHeight);
			setCellColor(LovelyTheme.LIGHT);
		}
		@Override
		protected void setImage(LImage img, int i) {
			refreshUnit(img);
		}
		@Override
		public LPoint toObject(LPath path) {
			return path == null ? null : points.get(path.index);
		}
	}
	
}
