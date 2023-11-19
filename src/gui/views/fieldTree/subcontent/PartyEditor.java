package gui.views.fieldTree.subcontent;

import gui.Vocab;
import gui.shell.field.TroopSpawnShell;
import gui.views.fieldTree.FieldEditor;
import gui.views.fieldTree.FieldSideEditor;
import gui.widgets.SimpleEditableList;

import org.eclipse.swt.SWT;

import data.field.Party;
import data.field.Party.TroopSpawn;
import gson.editor.GDefaultObjectEditor;

import org.eclipse.swt.layout.GridData;

import lwt.container.LContainer;
import lwt.container.LFrame;
import lwt.container.LPanel;
import lwt.dialog.LObjectShell;
import lwt.dialog.LShell;
import lwt.dialog.LShellFactory;
import lwt.event.LControlEvent;
import lwt.event.listener.LControlListener;
import lwt.widget.LCombo;
import lwt.widget.LLabel;
import lwt.widget.LSpinner;
import lwt.widget.LText;

public class PartyEditor extends GDefaultObjectEditor<Party> {

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public PartyEditor(LContainer parent) {
		super(parent, 2, false, false);
		
		LPanel position = new LPanel(this, 4, false);
		position.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		
		// Position
		
		new LLabel(position, Vocab.instance.POSITION);
		
		LSpinner spnX = new LSpinner(position);
		spnX.setMinimum(1);
		addControl(spnX, "x");
		
		LSpinner spnY = new LSpinner(position);
		spnY.setMinimum(1);
		addControl(spnY, "y");
		
		LSpinner spnH = new LSpinner(position);
		spnH.setMinimum(1);
		addControl(spnH, "h");
		
		spnX.addModifyListener(new LControlListener<Integer>() {
			@Override
			public void onModify(LControlEvent<Integer> event) {
				if (event == null || event.oldValue == null) return;
				FieldEditor.instance.canvas.redraw();
			}
		});
		spnY.addModifyListener(new LControlListener<Integer>() {
			@Override
			public void onModify(LControlEvent<Integer> event) {
				if (event == null || event.oldValue == null) return;
				FieldEditor.instance.canvas.redraw();
			}
		});
		spnH.addModifyListener(new LControlListener<Integer>() {
			@Override
			public void onModify(LControlEvent<Integer> event) {
				if (event == null || event.oldValue == null) return;
				FieldEditor.instance.canvas.redraw();
			}
		});
		
		new LLabel(this, Vocab.instance.NAME);
		
		LText txtName = new LText(this);
		addControl(txtName, "name");
		txtName.addModifyListener(new LControlListener<String>() {
			@Override
			public void onModify(LControlEvent<String> event) {
				FieldSideEditor.instance.updatePartyNames();
			}
		});
		
		new LLabel(this, Vocab.instance.DIRECTION);
		
		LCombo cmbDir = new LCombo(this);
		String[] d = new String[] {"0°", "90°", "180°", "270°"};
		cmbDir.setIncludeID(false);
		cmbDir.setOptional(false);
		cmbDir.setItems(d);
		addControl(cmbDir, "rotation");
		cmbDir.addModifyListener(new LControlListener<Integer>() {
			@Override
			public void onModify(LControlEvent<Integer> event) {
				if (event == null || event.oldValue == null) return;
				FieldEditor.instance.canvas.redraw();
			}
		});
		
		// Members
		
		new LLabel(this, Vocab.instance.PARTYGEN);
		
		LCombo cmbGen = new LCombo(this);
		String[] s = new String[] {Vocab.instance.FIELDCHARS, Vocab.instance.TROOPUNITS};
		cmbGen.setIncludeID(false);
		cmbGen.setOptional(false);
		cmbGen.setItems(s);
		addControl(cmbGen, "memberGen");
		
		LFrame grpTroops = new LFrame(this, Vocab.instance.TROOPS, true, true);
		grpTroops.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));
		SimpleEditableList<TroopSpawn> lstTroops = new SimpleEditableList<TroopSpawn>(grpTroops);
		lstTroops.type = TroopSpawn.class;
		lstTroops.setIncludeID(false);
		lstTroops.setShellFactory(new LShellFactory<TroopSpawn>() {
			@Override
			public LObjectShell<TroopSpawn> createShell(LShell parent) {
				return new TroopSpawnShell(parent);
			}
		});
		addChild(lstTroops, "troopSpawn");
		
	}
	
}
