package gui.views.database.content;

import lwt.LFlags;
import lwt.container.LContainer;
import lwt.container.LFrame;
import lwt.container.LPanel;
import lwt.event.*;
import lwt.event.listener.LCollectionListener;
import lwt.event.listener.LSelectionListener;
import lwt.widget.LCheckBox;
import lwt.widget.LLabel;
import lwt.widget.LSpinner;
import lwt.widget.LText;
import gson.project.GObjectTreeSerializer;
import gui.Vocab;
import gui.helper.TilePainter;
import gui.views.database.DatabaseTab;
import gui.views.database.subcontent.DropList;
import gui.views.database.subcontent.UnitEditor;
import gui.widgets.LuaButton;
import gui.widgets.SimpleEditableList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;

import data.GameCharacter;
import data.Troop;
import data.Troop.Unit;
import data.config.Config;
import project.Project;

public class TroopTab extends DatabaseTab<Troop> {
	
	public static final int tWidth = 32;
	public static final int tHeight = 48;

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
		LPanel gridEditor = new LPanel(grpGrid, SWT.NONE);

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
		
		gridEditor.addPaintListener(new PaintListener() {
			public void paintControl(PaintEvent e) {
				Config.Troop conf = Project.current.config.getData().troop;
				for (int i = 0; i < conf.width; i++)
					for (int j = 0; j < conf.height; j++)
						e.gc.drawRectangle(i * tWidth, j * tHeight, tWidth, tHeight);
				if (contentEditor.getObject() != null) {
					Troop troop = (Troop) contentEditor.getObject();
					for (Unit u : troop.members) {
						if (u.list != 0)
							continue;
						GameCharacter c = (GameCharacter) Project.current.characters.getData().get(u.charID);
						if (c == null)
							continue;
						int animID = c.defaultAnimationID();
						if (animID == -1)
							continue;
						Image img = TilePainter.getAnimationTile(u.charID, animID, 270, 0, c.transform);
						if (img == null)
							continue;
						Rectangle bounds = img.getBounds();
						int sw = bounds.width;
						int sh = bounds.height;
						sw = Math.min(tWidth, sw);
						sh = Math.min(tHeight, sh);
						int dx = tWidth / 2 - sw / 2;
						int dy = tHeight / 2 - sh / 2;
						e.gc.drawImage(img, 
								0, 0, sw, sh, 
								tWidth * (u.x - 1) + dx, tHeight * (u.y - 1) + dy, sw, sh);
					}
					Unit u = lstMembers.getCollectionWidget().getSelectedObject();
					if (u == null) return;
					e.gc.drawRectangle((u.x - 1) * tWidth + 2, (u.y - 1) * tHeight + 2,
							tWidth - 4, tHeight - 4);
				}
			}
		});
		
		LCollectionListener<Unit> listener = new LCollectionListener<Unit>() {
			public void onEdit(LEditEvent<Unit> e) {
				gridEditor.redraw();
			}
			public void onInsert(LInsertEvent<Unit> e) {
				gridEditor.redraw();
			}
			public void onDelete(LDeleteEvent<Unit> e) {
				gridEditor.redraw();
			}
			public void onMove(LMoveEvent<Unit> e) {
				gridEditor.redraw();
			}
		};
		lstMembers.getCollectionWidget().addEditListener(listener);
		lstMembers.getCollectionWidget().addInsertListener(listener);
		lstMembers.getCollectionWidget().addDeleteListener(listener);
		lstMembers.getCollectionWidget().addMoveListener(listener);
		lstMembers.getCollectionWidget().addSelectionListener(new LSelectionListener() {
			public void onSelect(LSelectionEvent event) {
				gridEditor.redraw();
			}
		});
		
		LSelectionListener listener2 = new LSelectionListener() {
			@Override
			public void onSelect(LSelectionEvent event) {
				gridEditor.redraw();
			}
		};
		
		listEditor.getCollectionWidget().addSelectionListener(listener2);
		unitEditor.addSelectionListener(listener2);
		
	}

	@Override
	protected GObjectTreeSerializer getSerializer() {
		return Project.current.troops;
	}
	
}
