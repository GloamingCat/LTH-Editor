package gui.views.fieldTree.subcontent;

import gui.Tooltip;
import gui.Vocab;
import gui.shell.field.TroopSpawnShell;
import gui.views.fieldTree.FieldEditor;
import gui.views.fieldTree.FieldSideEditor;
import gui.widgets.SimpleEditableList;

import data.field.Party;
import data.field.Party.TroopSpawn;
import gson.editor.GDefaultObjectEditor;
import lwt.LFlags;
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
		super(parent, false);
		setGridLayout(2);
		
		LPanel position = new LPanel(this);
		position.setGridLayout(4);
		position.setExpand(true, false);
		position.setSpread(2, 1);
		position.setAlignment(LFlags.CENTER);
		
		// Position
		
		new LLabel(position, Vocab.instance.POSITION, Tooltip.instance.PARTYPOS);
		
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
		
		new LLabel(this, Vocab.instance.NAME, Tooltip.instance.NAME);
		
		LText txtName = new LText(this);
		addControl(txtName, "name");
		txtName.addModifyListener(new LControlListener<String>() {
			@Override
			public void onModify(LControlEvent<String> event) {
				FieldSideEditor.instance.updatePartyNames();
			}
		});
		
		new LLabel(this, Vocab.instance.DIRECTION, Tooltip.instance.PARTYDIR);
		
		LCombo cmbDir = new LCombo(this, true);
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
		
		new LLabel(this, Vocab.instance.PARTYGEN, Tooltip.instance.PARTYGEN);
		
		LCombo cmbGen = new LCombo(this, true);
		String[] s = new String[] {
			Vocab.instance.FIELDCHARS,
			Vocab.instance.TROOPUNITS
		};
		cmbGen.setIncludeID(false);
		cmbGen.setOptional(false);
		cmbGen.setItems(s);
		addControl(cmbGen, "memberGen");
		LFrame frame = new LFrame(this, (String) Vocab.instance.TROOPS);
		frame.setFillLayout(true);
		
		LFrame grpTroops = frame;
		grpTroops.setHoverText(Tooltip.instance.PARTYTROOPS);
		grpTroops.setExpand(true, true);
		grpTroops.setSpread(2, 1);
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

	@Override
	public Class<?> getType() {
		return Party.class;
	}
	
}
