package gui.views.fieldTree.subcontent;

import gui.Tooltip;
import gui.Vocab;
import gui.widgets.CheckBoxPanel;
import gui.widgets.DirectionCombo;
import gui.widgets.IDButton;
import lui.base.LFlags;
import lui.base.LPrefs;
import lui.base.event.listener.LControlListener;
import lui.container.LContainer;
import lui.container.LFrame;
import lui.container.LPanel;
import lui.gson.GDefaultObjectEditor;
import lui.base.data.LDataTree;
import lui.widget.LCheckBox;
import lui.widget.LCombo;
import lui.widget.LLabel;
import lui.widget.LSpinner;
import lui.widget.LText;

import project.Project;

import data.field.CharTile;
import data.field.Field;

import java.util.function.Consumer;

public class CharTileEditor extends GDefaultObjectEditor<CharTile> {

	public record PositionEvent(int x, int y, int h, int newValue) {}
	public Consumer<PositionEvent> onChangeX;
	public Consumer<PositionEvent> onChangeY;
	public Consumer<PositionEvent> onChangeH;
	public Consumer<PositionEvent> onChangeSprite;

	private LCombo cmbParty;
	private LSpinner spnX;
	private LSpinner spnY;
	private LSpinner spnH;

	public CharTileEditor(LContainer parent) {
		super(parent, false);
	}

	protected void createContent(int style) {
		setGridLayout(3);

		// Position

		LLabel lblPos = new LLabel(this, Vocab.instance.POSITION, Tooltip.instance.CHARPOS);
		lblPos.getCellData().setTargetSize(LPrefs.LABELWIDTH, -1);

		LPanel position = new LPanel(this);
		position.setFillLayout(true);
		position.setSpacing(LPrefs.GRIDSPACING, 0);
		position.getCellData().setExpand(true, false);
		position.getCellData().setSpread(2, 1);

		spnX = new LSpinner(position);
		spnX.setMinimum(1);
		addControl(spnX, "x");
		
		spnY = new LSpinner(position);
		spnY.setMinimum(1);
		addControl(spnY, "y");
		
		spnH = new LSpinner(position);
		spnH.setMinimum(1);
		addControl(spnH, "h");
		
		spnX.addModifyListener(event -> {
            if (event == null || event.oldValue == null || onChangeX == null ||
			 spnX.getValue() == null || spnY.getValue() == null || spnH.getValue() == null) return;
			onChangeX.accept(new PositionEvent(
					event.oldValue - 1,
					spnY.getValue() - 1,
					spnH.getValue() - 1,
					event.newValue - 1));
        });
		spnY.addModifyListener(event -> {
            if (event == null || event.oldValue == null || onChangeY == null ||
			 spnX.getValue() == null || spnY.getValue() == null || spnH.getValue() == null) return;
			onChangeY.accept(new PositionEvent(
					spnX.getValue() - 1,
					event.oldValue - 1,
					spnH.getValue() - 1,
					event.newValue - 1));
        });
		spnH.addModifyListener(event -> {
            if (event == null || event.oldValue == null || onChangeH == null ||
			 spnX.getValue() == null || spnY.getValue() == null || spnH.getValue() == null) return;
			onChangeH.accept(new PositionEvent(
					spnX.getValue() - 1,
					spnY.getValue() - 1,
					event.oldValue - 1,
					event.newValue - 1));
        });
		
		// General
		
		new LLabel(this, Vocab.instance.KEY, Tooltip.instance.KEY);
		
		LText txtKey = new LText(this);
		txtKey.getCellData().setSpread(2, 1);
		txtKey.getCellData().setExpand(true, false);
		addControl(txtKey, "key");

		// Char

		LControlListener<Integer> updateCharSprite = event -> {
            if (event == null || event.oldValue == null || onChangeSprite == null) return;
			onChangeSprite.accept(new PositionEvent(
					spnX.getValue() - 1,
					spnY.getValue() - 1,
					spnH.getValue() - 1,
					event.newValue
			));
        };

		new LLabel(this, Vocab.instance.CHARACTER, Tooltip.instance.CHARACTER);
		LText txtChar = new LText(this, true);
		txtChar.getCellData().setExpand(true, false);
		IDButton btnChar = new IDButton(this, Vocab.instance.CHARSHELL, true) {
			@Override
			public LDataTree<Object> getDataTree() {
				return Project.current.characters.getTree();
			}
		};
		btnChar.setNameWidget(txtChar);
		btnChar.addModifyListener(updateCharSprite);
		addControl(btnChar, "charID");
		
		new LLabel(this, Vocab.instance.SPEED, Tooltip.instance.SPEED);
		LSpinner spnSpeed = new LSpinner(this);
		spnSpeed.getCellData().setExpand(true, false);
		new LLabel(this, "%");
		addControl(spnSpeed, "defaultSpeed");
		
		// Animation

		new LLabel(this, Vocab.instance.DIRECTION, Tooltip.instance.CHARDIR);
		LPanel animation = new LPanel(this);
		animation.setGridLayout(2);
		animation.setEqualCells(true);
		animation.getCellData().setSpread(2, 2);
		animation.getCellData().setExpand(true, false);
		new LLabel(this, Vocab.instance.ANIMATION, Tooltip.instance.ANIMATION);

		DirectionCombo cmbDir = new DirectionCombo(animation);
		cmbDir.getCellData().setExpand(true, false);
		cmbDir.addModifyListener(updateCharSprite);
		addControl(cmbDir, "direction");

		LLabel lblFrame = new LLabel(animation, LFlags.BOTTOM | LFlags.LEFT, Vocab.instance.FRAME, Tooltip.instance.FRAME);

		LText txtAnim = new LText(animation);
		txtAnim.getCellData().setExpand(true, false);
		txtAnim.getCellData().setRequiredSize(lblFrame.getTargetSize());
		addControl(txtAnim, "animation");

		LSpinner spnFrame = new LSpinner(animation);
		spnFrame.getCellData().setExpand(false, false);
		spnFrame.getCellData().setAlignment(LFlags.MIDDLE);
		spnFrame.getCellData().setRequiredSize(36, 0);
		addControl(spnFrame, "frame");
		
		// Battle
		
		new LLabel(this, Vocab.instance.PARTY, Tooltip.instance.CHARPARTY);
		cmbParty = new LCombo(this, true);
		cmbParty.getCellData().setSpread(2, 1);
		cmbParty.getCellData().setExpand(true, false);
		cmbParty.setIncludeID(false);
		cmbParty.setOptional(true);
		addControl(cmbParty, "party");
		
		new LLabel(this, Vocab.instance.CHARBATTLER, Tooltip.instance.CHARBATTLER);
		LText txtBattler = new LText(this, true);
		txtBattler.getCellData().setExpand(true, false);
		IDButton btnBattler = new IDButton(this, Vocab.instance.BATTLERSHELL, false) {
			@Override
			public LDataTree<Object> getDataTree() {
				return Project.current.battlers.getTree();
			}
		};
		btnBattler.setNameWidget(txtBattler);
		addControl(btnBattler, "battlerID");

		// Properties

		LPanel compOptions = new CheckBoxPanel(this);
		compOptions.getCellData().setSpread(3, 1);

		LCheckBox btnPersistent = new LCheckBox(compOptions);
		btnPersistent.setText(Vocab.instance.PERSISTENT);
		btnPersistent.setHoverText(Tooltip.instance.CHARPERSISTENT);
		addControl(btnPersistent, "persistent");

		LCheckBox btnPassable = new LCheckBox(compOptions);
		btnPassable.setText(Vocab.instance.PASSABLE);
		btnPassable.setHoverText(Tooltip.instance.CHARPASSABLE);
		addControl(btnPassable, "passable");

		LCheckBox btnVisible = new LCheckBox(compOptions);
		btnVisible.setText(Vocab.instance.VISIBLE);
		btnVisible.setHoverText(Tooltip.instance.CHARVISIBLE);
		addControl(btnVisible, "visible");

		// Scripts

		LFrame grpScripts = new LFrame(this, Vocab.instance.SCRIPTS);
		grpScripts.setGridLayout(1);
		grpScripts.getCellData().setSpread(3, 1);
		grpScripts.getCellData().setExpand(true, true);
		
		ScriptList lstScripts = new ScriptList(grpScripts, 2 | 4 | 8);
		lstScripts.getCellData().setExpand(true, true);
		addChild(lstScripts, "scripts");
		
		LCheckBox btnRepeat = new LCheckBox(grpScripts);
		btnRepeat.setText(Vocab.instance.REPEATCOLLISIONS);
		btnRepeat.setHoverText(Tooltip.instance.REPEATCOLLISIONS);
		btnRepeat.getCellData().setAlignment(LFlags.LEFT);
		addControl(btnRepeat, "repeatCollisions");

	}

	public void setField(Field field) {
		if (field == null)
			return;
		cmbParty.setItems(field.parties);
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
