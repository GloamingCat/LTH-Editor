package gui.views.database.content;

import lwt.dataestructure.LDataTree;
import lwt.event.*;
import lwt.event.listener.LCollectionListener;
import lwt.event.listener.LSelectionListener;
import lwt.widget.LCheckButton;
import lwt.widget.LSpinner;
import gson.project.GObjectTreeSerializer;
import gui.Vocab;
import gui.views.database.DatabaseTab;
import gui.views.database.subcontent.DropList;
import gui.views.database.subcontent.UnitList;
import gui.widgets.IDButton;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Text;

import data.Animation;
import data.GameCharacter;
import data.Troop;
import data.config.Config;
import data.subcontent.Unit;
import project.Project;

import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.layout.GridLayout;

public class TroopTab extends DatabaseTab {
	
	public static final int tWidth = 32;
	public static final int tHeight = 48;

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public TroopTab(Composite parent, int style) {
		super(parent, style);
		
		Label lblMoney = new Label(grpGeneral, SWT.NONE);
		lblMoney.setText(Vocab.instance.MONEY);
		
		LSpinner spnMoney = new LSpinner(grpGeneral, SWT.NONE);
		spnMoney.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		addControl(spnMoney, "money");
		
		// AI
		
		Label lblAI = new Label(grpGeneral, SWT.NONE);
		lblAI.setText(Vocab.instance.AI);
		
		Composite select = new Composite(grpGeneral, SWT.NONE);
		select.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		GridLayout gl_select = new GridLayout(2, false);
		gl_select.marginHeight = 0;
		gl_select.marginWidth = 0;
		select.setLayout(gl_select);
		
		Text txtAI = new Text(select, SWT.BORDER | SWT.READ_ONLY);
		txtAI.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		IDButton btnAI = new IDButton(select, SWT.NONE) {
			@Override
			public LDataTree<Object> getDataTree() {
				return Project.current.scripts.getTree();
			}
		};
		btnAI.optional = true;
		btnAI.setNameText(txtAI);
		addControl(btnAI, "scriptID");
		
		LCheckButton btnPersistent = new LCheckButton(grpGeneral, SWT.NONE);
		btnPersistent.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		btnPersistent.setText(Vocab.instance.PERSISTENT);
		addControl(btnPersistent, "persistent");
		
		Group grpItems = new Group(contentEditor, SWT.NONE);
		grpItems.setLayout(new FillLayout(SWT.HORIZONTAL));
		grpItems.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
		grpItems.setText(Vocab.instance.ITEMS);
		
		DropList lstItems = new DropList(grpItems, SWT.NONE);
		addChild(lstItems);
		
		Composite gridEditor = new Composite(contentEditor, SWT.NONE);
		GridData gd_gridEditor = new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1);
		gd_gridEditor.widthHint = 241;
		gridEditor.setLayoutData(gd_gridEditor);
		
		Composite units = new Composite(contentEditor, SWT.NONE);
		units.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		units.setLayout(new FillLayout(SWT.VERTICAL));
		
		Group grpCurrent = new Group(units, SWT.NONE);
		grpCurrent.setLayout(new FillLayout(SWT.HORIZONTAL));
		grpCurrent.setText(Vocab.instance.UNITS);
		
		UnitList lstCurrent = new UnitList(grpCurrent, SWT.NONE);
		addChild(lstCurrent, "current");
		
		Group grpBackup = new Group(units, SWT.NONE);
		grpBackup.setLayout(new FillLayout(SWT.HORIZONTAL));
		grpBackup.setText(Vocab.instance.BACKUP);
		
		UnitList lstBackup = new UnitList(grpBackup, SWT.NONE);
		addChild(lstBackup, "backup");
		
		gridEditor.addPaintListener(new PaintListener() {
			public void paintControl(PaintEvent e) {
				Config.Troop conf = Project.current.config.getData().troop;
				for (int i = 0; i < conf.width; i++)
					for (int j = 0; j < conf.height; j++)
						e.gc.drawRectangle(i * tWidth, j * tHeight, tWidth, tHeight);
				if (contentEditor.getObject() != null) {
					Troop troop = (Troop) contentEditor.getObject();
					for (Unit u : troop.current) {
						GameCharacter c = (GameCharacter) Project.current.characters.getData().get(u.charID);
						Animation anim = c == null ? null : c.defaultAnimation();
						Image img = anim == null ? null : anim.getImage();
						if (img == null) continue;
						int w = anim.quad.width / anim.cols;
						int h = anim.quad.height / anim.rows;
						int x = anim.quad.x + w;
						int y = anim.quad.y + 6 * h;
						w = Math.min(tWidth, w);
						h = Math.min(tHeight, h);
						e.gc.drawImage(img, 
								x, y, w, h, tWidth * (u.x - 1), tHeight * (u.y - 1), w, h);
					}
					Unit u = lstCurrent.getCollectionWidget().getSelectedObject();
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
		lstCurrent.getCollectionWidget().addEditListener(listener);
		lstCurrent.getCollectionWidget().addInsertListener(listener);
		lstCurrent.getCollectionWidget().addDeleteListener(listener);
		lstCurrent.getCollectionWidget().addMoveListener(listener);
		lstCurrent.getCollectionWidget().addSelectionListener(new LSelectionListener() {
			public void onSelect(LSelectionEvent event) {
				gridEditor.redraw();
			}
		});
		
		listEditor.getCollectionWidget().addSelectionListener(new LSelectionListener() {
			@Override
			public void onSelect(LSelectionEvent event) {
				gridEditor.redraw();
			}
		});
	}

	@Override
	protected GObjectTreeSerializer getSerializer() {
		return Project.current.troops;
	}
	
}
