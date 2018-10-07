package gui.views.database.content;

import lwt.event.*;
import lwt.event.listener.LCollectionListener;
import gson.project.GObjectTreeSerializer;
import gui.Vocab;
import gui.views.database.DatabaseTab;
import gui.views.database.subcontent.UnitList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;

import data.Troop;
import data.Troop.Unit;
import project.Project;

public class TroopTab extends DatabaseTab {
	
	private Composite gridEditor;
	
	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public TroopTab(Composite parent, int style) {
		super(parent, style);
		
		Composite size = new Composite(grpGeneral, SWT.NONE);
		size.setLayout(new GridLayout(4, false));
		
		Group grpUnits = new Group(contentEditor, SWT.NONE);
		grpUnits.setText(Vocab.instance.BATTLERS);
		
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
		
		UnitList lstUnits = new UnitList(grpUnits, SWT.NONE);
		lstUnits.getCollectionWidget().addEditListener(listener);
		lstUnits.getCollectionWidget().addInsertListener(listener);
		lstUnits.getCollectionWidget().addDeleteListener(listener);
		lstUnits.getCollectionWidget().addMoveListener(listener);
		
		gridEditor = new Composite(grpUnits, SWT.NONE);
		gridEditor.addPaintListener(new PaintListener() {
			public void paintControl(PaintEvent arg0) {
				if (contentEditor.getObject() != null) {
					Troop troop = (Troop) contentEditor.getObject();
				}
			}
		});
	}

	@Override
	protected GObjectTreeSerializer getSerializer() {
		return Project.current.troops;
	}

}
