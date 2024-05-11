package gui.views.fieldTree.subcontent;

import gui.Tooltip;
import gui.Vocab;
import gui.shell.field.TroopSpawnDialog;
import gui.widgets.DirectionCombo;
import gui.widgets.SimpleEditableList;
import lui.base.LFlags;
import lui.base.LPrefs;
import lui.base.event.listener.LControlListener;
import data.field.Party;
import data.field.Party.TroopSpawn;
import lui.container.LContainer;
import lui.container.LFrame;
import lui.container.LPanel;
import lui.dialog.LObjectDialog;
import lui.dialog.LWindow;
import lui.dialog.LWindowFactory;
import lui.gson.GDefaultObjectEditor;
import lui.widget.LCombo;
import lui.widget.LLabel;
import lui.widget.LSpinner;
import lui.widget.LText;

import java.util.function.Consumer;
import java.util.function.Function;

public class PartyEditor extends GDefaultObjectEditor<Party> {

	public Consumer<Party> onChange;
	public Consumer<String> onRename;

	public PartyEditor(LContainer parent) {
		super(parent, false);
	}

	protected void createContent(int style) {
		setGridLayout(2);
		
		// Position

		new LLabel(this, Vocab.instance.POSITION, Tooltip.instance.PARTYPOS);
		LPanel position = new LPanel(this);
		position.setFillLayout(true);
		position.setSpacing(LPrefs.GRIDSPACING);
		position.getCellData().setAlignment(LFlags.LEFT | LFlags.MIDDLE);

		LSpinner spnX = new LSpinner(position);
		spnX.setMinimum(1);
		addControl(spnX, "x");
		
		LSpinner spnY = new LSpinner(position);
		spnY.setMinimum(1);
		addControl(spnY, "y");
		
		LSpinner spnH = new LSpinner(position);
		spnH.setMinimum(1);
		addControl(spnH, "h");

		LControlListener<Integer> redrawListener = event -> {
            if (event == null || event.oldValue == null || onChange == null) return;
            onChange.accept(getObject());
        };

		spnX.addModifyListener(redrawListener);
		spnY.addModifyListener(redrawListener);
		spnH.addModifyListener(redrawListener);
		
		new LLabel(this, Vocab.instance.NAME, Tooltip.instance.NAME);
		
		LText txtName = new LText(this);
		txtName.getCellData().setExpand(true, false);
		txtName.addModifyListener(event -> {
			if (onRename != null)
				onRename.accept(event.newValue);
		});
		addControl(txtName, "name");

		new LLabel(this, Vocab.instance.DIRECTION, Tooltip.instance.PARTYDIR);
		
		LCombo cmbDir = new LCombo(this);
		cmbDir.setIncludeID(false);
		cmbDir.setOptional(false);
		cmbDir.setItems(DirectionCombo.dir4);
		cmbDir.getCellData().setExpand(true, false);
		cmbDir.addModifyListener(redrawListener);
		addControl(cmbDir, "rotation");

		// Members
		
		new LLabel(this, Vocab.instance.PARTYGEN, Tooltip.instance.PARTYGEN);
		
		LCombo cmbGen = new LCombo(this, true);
		cmbGen.getCellData().setExpand(true, false);
		cmbGen.setIncludeID(false);
		cmbGen.setOptional(false);
		cmbGen.setItems(new String[] {
			Vocab.instance.FIELDCHARS,
			Vocab.instance.TROOPUNITS
		});
		addControl(cmbGen, "memberGen");

		LFrame grpTroops = new LFrame(this, Vocab.instance.TROOPS);
		grpTroops.setFillLayout(true);
		grpTroops.setHoverText(Tooltip.instance.PARTYTROOPS);
		grpTroops.getCellData().setExpand(true, true);
		grpTroops.getCellData().setSpread(2, 1);
		SimpleEditableList<TroopSpawn> lstTroops = new SimpleEditableList<>(grpTroops);
		lstTroops.type = TroopSpawn.class;
		lstTroops.setIncludeID(false);
		lstTroops.setShellFactory(new LWindowFactory<>() {
			@Override
			public LObjectDialog<TroopSpawn> createWindow(LWindow parent) {
				return new TroopSpawnDialog(parent);
			}
		});
		addChild(lstTroops, "troopSpawn");

		Function<Integer, Boolean> f = (i) -> i%2==0;
	}

	@Override
	public Class<?> getType() {
		return Party.class;
	}
	
}
