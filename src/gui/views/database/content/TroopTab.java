package gui.views.database.content;

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
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;

import data.GameCharacter;
import data.Troop;
import data.Troop.Unit;
import data.config.Config;
import project.Project;

import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;

public class TroopTab extends DatabaseTab<Troop> {
	
	public static final int tWidth = 32;
	public static final int tHeight = 48;

	/**
	 * Create the composite.
	 * @param parent
	 */
	public TroopTab(Composite parent) {
		super(parent);
		
		// General
		
		new LLabel(grpGeneral, "");
		LCheckBox btnPersistent = new LCheckBox(grpGeneral, SWT.NONE);
		btnPersistent.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		btnPersistent.setText(Vocab.instance.PERSISTENT);
		addControl(btnPersistent, "persistent");
		
		// Rewards
		
		new LLabel(grpGeneral, Vocab.instance.MONEY);
		
		Composite compositeReward = new Composite(grpGeneral, SWT.NONE);
		GridLayout gl_compositeReward = new GridLayout(3, false);
		gl_compositeReward.marginWidth = 0;
		gl_compositeReward.marginHeight = 0;
		compositeReward.setLayout(gl_compositeReward);
		compositeReward.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));
		
		LSpinner spnMoney = new LSpinner(compositeReward, SWT.NONE);
		spnMoney.setMaximum(99999999);
		spnMoney.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		addControl(spnMoney, "money");
		
		new LLabel(compositeReward, Vocab.instance.EXP);
		
		LSpinner spnEXP = new LSpinner(compositeReward, SWT.NONE);
		spnEXP.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		spnEXP.setMaximum(99999999);
		addControl(spnEXP, "exp");
		
		// AI
		
		new LLabel(grpGeneral, Vocab.instance.AI);
		
		Composite select = new Composite(grpGeneral, SWT.NONE);
		select.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		GridLayout gl_select = new GridLayout(2, false);
		gl_select.marginHeight = 0;
		gl_select.marginWidth = 0;
		select.setLayout(gl_select);
		
		LText txtAI = new LText(select, true);
		txtAI.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		LuaButton btnAI = new LuaButton(select, 1);
		btnAI.setPathWidget(txtAI);
		addControl(btnAI, "ai");
		
		// Grid
		
		Group grpGrid = new Group(left, SWT.NONE);
		grpGrid.setText(Vocab.instance.GRID);
		grpGrid.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		grpGrid.setLayout(new FillLayout());
		
		Composite gridEditor = new Composite(grpGrid, SWT.NONE);
		gridEditor.setLayout(new FillLayout());

		// Items
		
		Group grpItems = new Group(right, SWT.NONE);
		grpItems.setLayout(new FillLayout(SWT.HORIZONTAL));
		grpItems.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		grpItems.setText(Vocab.instance.ITEMS);
		
		DropList lstItems = new DropList(grpItems, SWT.NONE);
		addChild(lstItems, "items");
		
		new LLabel(right, "").setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));

		// Units
		
		Group grpMembers = new Group(contentEditor, SWT.NONE);
		grpMembers.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 2, 1));
		grpMembers.setLayout(new GridLayout(2, false));
		grpMembers.setText(Vocab.instance.UNITS);
		
		SimpleEditableList<Unit> lstMembers = new SimpleEditableList<>(grpMembers, SWT.NONE);
		lstMembers.getCollectionWidget().setEditEnabled(false);
		lstMembers.setIncludeID(false);
		lstMembers.type = Unit.class;
		GridData gd_lstUnits = new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1);
		gd_lstUnits.widthHint = 220;
		lstMembers.setLayoutData(gd_lstUnits);
		addChild(lstMembers, "members");
		
		UnitEditor unitEditor = new UnitEditor(grpMembers, SWT.NONE) {
			public void refresh() {
				gridEditor.redraw();
			}
		};
		unitEditor.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
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
