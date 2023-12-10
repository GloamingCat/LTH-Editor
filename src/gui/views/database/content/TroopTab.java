package gui.views.database.content;

import lwt.LFlags;
import lwt.container.LContainer;
import lwt.container.LFrame;
import lwt.container.LImage;
import lwt.container.LPanel;
import lwt.dataestructure.LDataList;
import lwt.dataestructure.LPath;
import lwt.editor.LGridEditor;
import lwt.event.*;
import lwt.event.listener.LCollectionListener;
import lwt.event.listener.LControlListener;
import lwt.event.listener.LSelectionListener;
import lwt.graphics.LPoint;
import lwt.widget.LCheckBox;
import lwt.widget.LLabel;
import lwt.widget.LSpinner;
import lwt.widget.LText;
import gson.project.GObjectTreeSerializer;
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
import project.Project;

public class TroopTab extends DatabaseTab<Troop> {
	
	public static int tWidth = 32;
	public static int tHeight = 48;
	
	private LGridEditor<LPoint, LPoint> gridEditor;
	private LDataList<LPoint> points = new LDataList<LPoint>();

	/**
	 * @wbp.parser.constructor
	 * @wbp.eval.method.parameter parent new lwt.dialog.LShell(800, 600)
	 */
	public TroopTab(LContainer parent) {
		super(parent);
		
		// General
		
		new LLabel(grpGeneral, 1, 1);
		LCheckBox btnPersistent = new LCheckBox(grpGeneral);
		btnPersistent.setText(Vocab.instance.PERSISTENT);
		addControl(btnPersistent, "persistent");
		
		// Rewards
		
		new LLabel(grpGeneral, Vocab.instance.MONEY);
		
		LPanel compositeReward = new LPanel(grpGeneral, 3, false);
		compositeReward.setExpand(true, false);
		
		LSpinner spnMoney = new LSpinner(compositeReward);
		spnMoney.setMaximum(99999999);
		addControl(spnMoney, "money");
		
		new LLabel(compositeReward, Vocab.instance.EXP);
		
		LSpinner spnEXP = new LSpinner(compositeReward);
		spnEXP.setMaximum(99999999);
		addControl(spnEXP, "exp");
		
		// AI
		
		new LLabel(grpGeneral, Vocab.instance.AI);
		
		LPanel select = new LPanel(grpGeneral, 2, false);
		select.setAlignment(LFlags.CENTER);
		
		LText txtAI = new LText(select, true);		
		LuaButton btnAI = new LuaButton(select, true);
		btnAI.setPathWidget(txtAI);
		addControl(btnAI, "ai");
		
		// Grid
		
		LFrame grpGrid = new LFrame(left, Vocab.instance.GRID, true, true);
		grpGrid.setExpand(true, true);
		gridEditor = new UnitGrid(grpGrid);
		gridEditor.getCollectionWidget().cellWidth = tWidth;
		gridEditor.getCollectionWidget().cellHeight = tHeight;

		// Items
		
		LFrame grpItems = new LFrame(right, Vocab.instance.ITEMS, true, true);
		grpItems.setExpand(true, true);
		DropList lstItems = new DropList(grpItems);
		addChild(lstItems, "items");

		// Units
		
		LFrame grpMembers = new LFrame(contentEditor, Vocab.instance.UNITS, 2, false);
		grpMembers.setExpand(true, false);
		grpMembers.setSpread(2, 1);
		
		SimpleEditableList<Unit> lstMembers = new SimpleEditableList<>(grpMembers);
		lstMembers.getCollectionWidget().setEditEnabled(false);
		lstMembers.setIncludeID(false);
		lstMembers.type = Unit.class;
		lstMembers.setExpand(true, false);
		lstMembers.setMinimumWidth(220);
		addChild(lstMembers, "members");
		
		UnitEditor unitEditor = new UnitEditor(grpMembers);
		unitEditor.setExpand(true, false);
		lstMembers.addChild(unitEditor);
		
		unitEditor.addModifyListener(new LControlListener<Troop.Unit>() {
			@Override
			public void onModify(LControlEvent<Unit> event) {
				refreshUnit(event.newValue);
			}
		});
		
		LCollectionListener<Unit> modifyListener = new LCollectionListener<Unit>() {
			public void onEdit(LEditEvent<Unit> e) {
				refreshUnit(e.newData);
			}
			public void onInsert(LInsertEvent<Unit> e) {
				refreshUnit(e.node.data);
			}
			public void onDelete(LDeleteEvent<Unit> e) {
				refreshUnit(e.node.data);
			}
			public void onMove(LMoveEvent<Unit> e) {}
		};
		lstMembers.getCollectionWidget().addEditListener(modifyListener);
		lstMembers.getCollectionWidget().addInsertListener(modifyListener);
		lstMembers.getCollectionWidget().addDeleteListener(modifyListener);
		lstMembers.getCollectionWidget().addMoveListener(modifyListener);
		lstMembers.getCollectionWidget().addSelectionListener(new LSelectionListener() {
			public void onSelect(LSelectionEvent event) {
				Unit u = (Unit) event.data;
				if (u != null) {
					Config.Troop conf = Project.current.config.getData().troop;
					int i = (u.y - 1) * conf.width + (u.x - 1);
					gridEditor.getCollectionWidget().select(new LPath(i));
				}
			}
		});
		gridEditor.getCollectionWidget().addSelectionListener(new LSelectionListener() {
			@Override
			public void onSelect(LSelectionEvent event) {
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
			}
		});
		
		LSelectionListener selectionListener = new LSelectionListener() {
			@Override
			public void onSelect(LSelectionEvent event) {
				gridEditor.getCollectionWidget().setDataCollection(points);
			}
		};
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
	
	protected void refreshUnit(LImage img, int i) {
		LPoint p = (LPoint) img.getData();
		Troop troop = contentEditor.getObject();
		if (troop == null) {
			img.setImage((String) null);
			return;
		}
		i = troop.find(p.x, p.y);
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
			int animID = c == null ? -1 : c.defaultAnimationID();
			Animation anim = (Animation) Project.current.animations.getData().get(animID);
			if (anim != null && anim.cols > 0 && anim.rows > 0) {
				Icon icon = new Icon(animID, anim.getFrame(0), 270 / 45);
				anim.transform.setColorTransform(img, c.transform);
				img.setScale(anim.transform.scaleX * c.transform.scaleX / 10000f, anim.transform.scaleY * c.transform.scaleY / 10000f);
				img.setImage(icon.fullPath(), icon.getRectangle());
				return;
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
			// TODO Auto-generated constructor stub
		}
		
		@Override
		protected LPoint createNewElement() { return null; }
		@Override
		protected LPoint duplicateElement(LPoint original) { return null; }
		@Override
		protected void setImage(LImage label, int i) {
			refreshUnit(label, i);
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
			// TODO Auto-generated method stub
			return null;
		}
		@Override
		protected LPoint decodeElement(String str) {
			// TODO Auto-generated method stub
			return null;
		}
		@Override
		public boolean canDecode(String str) {
			// TODO Auto-generated method stub
			return false;
		}
	}
	
}
