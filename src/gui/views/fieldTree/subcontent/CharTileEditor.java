package gui.views.fieldTree.subcontent;

import gui.Tooltip;
import gui.Vocab;
import gui.views.fieldTree.FieldEditor;
import gui.widgets.DirectionCombo;
import gui.widgets.IDButton;
import lwt.LFlags;
import lwt.container.LContainer;
import lwt.container.LFrame;
import lwt.container.LPanel;
import lwt.dataestructure.LDataTree;
import lwt.event.LControlEvent;
import lwt.event.listener.LControlListener;
import lwt.widget.LCheckBox;
import lwt.widget.LCombo;
import lwt.widget.LLabel;
import lwt.widget.LSpinner;
import lwt.widget.LText;

import project.Project;

import data.field.CharTile;
import data.field.Field;
import gson.editor.GDefaultObjectEditor;

public class CharTileEditor extends GDefaultObjectEditor<CharTile> {

	private LCombo cmbParty;
	private LSpinner spnX;
	private LSpinner spnY;
	private LSpinner spnH;
	
	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public CharTileEditor(LContainer parent) {
		super(parent, false);
		setGridLayout(4);
		
		LPanel position = new LPanel(this);
		position.setGridLayout(4);
		position.setExpand(true, false);
		position.setSpread(4, 1);
		position.setAlignment(LFlags.CENTER);
		
		new LLabel(position, Vocab.instance.POSITION, Tooltip.instance.CHARPOS);
		
		spnX = new LSpinner(position);
		spnX.setMinimum(1);
		addControl(spnX, "x");
		
		spnY = new LSpinner(position);
		spnY.setMinimum(1);
		addControl(spnY, "y");
		
		spnH = new LSpinner(position);
		spnH.setMinimum(1);
		addControl(spnH, "h");
		
		spnX.addModifyListener(new LControlListener<Integer>() {
			@Override
			public void onModify(LControlEvent<Integer> event) {
				if (event == null || event.oldValue == null) return;
				FieldEditor.instance.canvas.onTileChange(event.oldValue - 1, spnY.getValue() - 1);
				FieldEditor.instance.canvas.onTileChange(event.newValue - 1, spnY.getValue() - 1);
				FieldEditor.instance.canvas.redrawBuffer();
				FieldEditor.instance.canvas.redraw();
			}
		});
		spnY.addModifyListener(new LControlListener<Integer>() {
			@Override
			public void onModify(LControlEvent<Integer> event) {
				if (event == null || event.oldValue == null) return;
				FieldEditor.instance.canvas.onTileChange(spnX.getValue() - 1, event.oldValue - 1);
				FieldEditor.instance.canvas.onTileChange(spnX.getValue() - 1, event.newValue - 1);
				FieldEditor.instance.canvas.redrawBuffer();
				FieldEditor.instance.canvas.redraw();
			}
		});
		spnH.addModifyListener(new LControlListener<Integer>() {
			@Override
			public void onModify(LControlEvent<Integer> event) {
				if (event == null || event.oldValue == null) return;
				FieldEditor.instance.canvas.setHeight(spnH.getValue() - 1);
				FieldEditor.instance.canvas.onTileChange(spnX.getValue() - 1, spnY.getValue() - 1);
				FieldEditor.instance.canvas.redrawBuffer();
				FieldEditor.instance.canvas.redraw();
			}
		});
		
		// General
		
		new LLabel(this, Vocab.instance.KEY, Tooltip.instance.KEY);
		
		LText txtKey = new LText(this, 3);
		addControl(txtKey, "key");
		
		LPanel compOptions = new LPanel(this);
		compOptions.setGridLayout(3);
		compOptions.setSpread(4, 1);
		compOptions.setAlignment(LFlags.CENTER);

		LCheckBox btnPersistent = new LCheckBox(compOptions);
		btnPersistent.setText(Vocab.instance.PERSISTENT);
		btnPersistent.setHoverText(Tooltip.instance.CHARPERSISTENT);
		btnPersistent.setExpand(true, false);
		addControl(btnPersistent, "persistent");
		
		LCheckBox btnPassable = new LCheckBox(compOptions);
		btnPassable.setText(Vocab.instance.PASSABLE);
		btnPassable.setHoverText(Tooltip.instance.CHARPASSABLE);
		btnPassable.setExpand(true, false);
		addControl(btnPassable, "passable");
		
		LCheckBox btnVisible = new LCheckBox(compOptions);
		btnVisible.setText(Vocab.instance.VISIBLE);
		btnVisible.setHoverText(Tooltip.instance.CHARVISIBLE);
		btnVisible.setExpand(true, false);
		addControl(btnVisible, "visible");
		
		// Char
		
		new LLabel(this, Vocab.instance.CHARACTER, Tooltip.instance.CHARACTER);
		LText txtChar = new LText(this, 2, true);
		IDButton btnChar = new IDButton(this, Vocab.instance.CHARSHELL, true) {
			@Override
			public LDataTree<Object> getDataTree() {
				return Project.current.characters.getTree();
			}
		};
		btnChar.setNameWidget(txtChar);
		addControl(btnChar, "charID");
		
		new LLabel(this, Vocab.instance.SPEED, Tooltip.instance.SPEED);
		LSpinner spnSpeed = new LSpinner(this, 3);
		spnSpeed.setMaximum(9999);
		addControl(spnSpeed, "defaultSpeed");
		
		// Animation
		
		LControlListener<Integer> listener = new LControlListener<Integer>() {
			@Override
			public void onModify(LControlEvent<Integer> event) {
				if (event == null || event.oldValue == null) return;
				FieldEditor.instance.canvas.onTileChange(spnX.getValue() - 1, spnY.getValue() - 1);
				FieldEditor.instance.canvas.redrawBuffer();
				FieldEditor.instance.canvas.redraw();
			}
		};
		
		new LLabel(this, Vocab.instance.DIRECTION, Tooltip.instance.CHARDIR);
		DirectionCombo cmbDir = new DirectionCombo(this);
		addControl(cmbDir, "direction");
		cmbDir.addModifyListener(listener);
		
		new LLabel(this, LFlags.BOTTOM, Vocab.instance.FRAME, 2);
		
		new LLabel(this, Vocab.instance.ANIMATION, Tooltip.instance.ANIMATION);
		LText txtAnim = new LText(this);
		addControl(txtAnim, "animation");
		
		btnChar.addModifyListener(listener);
		
		LSpinner spnFrame = new LSpinner(this, 2);
		spnFrame.setMinimumWidth(36);
		addControl(spnFrame, "frame");
		
		// Battle
		
		new LLabel(this, Vocab.instance.PARTY, Tooltip.instance.CHARPARTY);
		
		cmbParty = new LCombo(this, 3, true);
		cmbParty.setIncludeID(false);
		cmbParty.setOptional(true);
		addControl(cmbParty, "party");
		
		new LLabel(this, Vocab.instance.CHARBATTLER, Tooltip.instance.CHARBATTLER);
		
		LText txtBattler = new LText(this, 2, true);		
		IDButton btnBattler = new IDButton(this, Vocab.instance.BATTLERSHELL, false) {
			@Override
			public LDataTree<Object> getDataTree() {
				return Project.current.battlers.getTree();
			}
		};
		btnBattler.setNameWidget(txtBattler);
		addControl(btnBattler, "battlerID");
		
		LFrame grpScripts = new LFrame(this, Vocab.instance.SCRIPTS);
		grpScripts.setGridLayout(1);
		grpScripts.setSpread(4, 1);
		grpScripts.setExpand(false, true);
		
		ScriptList lstScripts = new ScriptList(grpScripts, 2 | 4 | 8);
		lstScripts.setExpand(true, true);
		addChild(lstScripts, "scripts");
		
		LCheckBox btnRepeat = new LCheckBox(grpScripts);
		btnRepeat.setText(Vocab.instance.REPEATCOLLISIONS);
		btnRepeat.setHoverText(Tooltip.instance.REPEATCOLLISIONS);
		addControl(btnRepeat, "repeatCollisions");

	}

	public void setField(Field field) {
		if (field == null)
			return;
		cmbParty.setItems(FieldEditor.instance.canvas.field.parties);
		spnX.setMaximum(field.sizeX);
		spnY.setMaximum(field.sizeY);
	}

	public void setPosition(CharTile tile) {
		spnX.setValue(tile.x);
		spnY.setValue(tile.y);
		spnH.setValue(tile.h);
	}

	@Override
	public Class<?> getType() {
		return CharTile.class;
	}
	
}
