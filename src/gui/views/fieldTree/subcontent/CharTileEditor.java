package gui.views.fieldTree.subcontent;

import gui.Vocab;
import gui.views.fieldTree.FieldEditor;
import gui.widgets.DirectionCombo;
import gui.widgets.IDButton;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

import lwt.dataestructure.LDataTree;
import lwt.editor.LObjectEditor;
import lwt.event.LControlEvent;
import lwt.event.listener.LControlListener;
import lwt.widget.LCheckBox;
import lwt.widget.LCombo;
import lwt.widget.LLabel;
import lwt.widget.LSpinner;
import lwt.widget.LText;

import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.GridData;

import project.Project;

import data.field.CharTile;
import data.field.Field;

import org.eclipse.swt.widgets.Group;

public class CharTileEditor extends LObjectEditor {

	private LCombo cmbParty;
	private LSpinner spnX;
	private LSpinner spnY;
	private LSpinner spnH;
	
	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public CharTileEditor(Composite parent, int style) {
		super(parent, style);
		setLayout(new GridLayout(4, false));
		
		Composite position = new Composite(this, SWT.NONE);
		GridLayout gl_position = new GridLayout(4, false);
		gl_position.marginWidth = 0;
		gl_position.marginHeight = 0;
		position.setLayout(gl_position);
		position.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 4, 1));
		
		new LLabel(position, Vocab.instance.POSITION);
		
		spnX = new LSpinner(position);
		spnX.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		spnX.setMinimum(1);
		addControl(spnX, "x");
		
		spnY = new LSpinner(position);
		spnY.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		spnY.setMinimum(1);
		addControl(spnY, "y");
		
		spnH = new LSpinner(position);
		spnH.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
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
		
		new LLabel(this, Vocab.instance.KEY);
		
		LText txtKey = new LText(this);
		txtKey.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 3, 1));
		addControl(txtKey, "key");
		
		Composite compOptions = new Composite(this, SWT.NONE);
		compOptions.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 4, 1));
		compOptions.setLayout(new FillLayout());

		LCheckBox btnPersistent = new LCheckBox(compOptions, SWT.NONE);
		btnPersistent.setText(Vocab.instance.PERSISTENT);
		addControl(btnPersistent, "persistent");
		
		LCheckBox btnPassable = new LCheckBox(compOptions, SWT.NONE);
		btnPassable.setText(Vocab.instance.PASSABLE);
		addControl(btnPassable, "passable");
		
		LCheckBox btnVisible = new LCheckBox(compOptions, SWT.NONE);
		btnVisible.setText(Vocab.instance.VISIBLE);
		addControl(btnVisible, "visible");
		
		// Char
		
		new LLabel(this, Vocab.instance.CHARACTER);
		
		LText txtChar = new LText(this, true);
		txtChar.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		
		IDButton btnChar = new IDButton(this, 1) {
			@Override
			public LDataTree<Object> getDataTree() {
				return Project.current.characters.getTree();
			}
		};
		btnChar.setNameWidget(txtChar);
		addControl(btnChar, "charID");
		
		new LLabel(this, Vocab.instance.SPEED);
		
		LSpinner spnSpeed = new LSpinner(this);
		spnSpeed.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 3, 1));
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
		
		new LLabel(this, Vocab.instance.DIRECTION);
		
		DirectionCombo cmbDir = new DirectionCombo(this);
		cmbDir.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		addControl(cmbDir, "direction");
		cmbDir.addModifyListener(listener);
		
		LLabel label = new LLabel(this, Vocab.instance.FRAME);
		label.setLayoutData(new GridData(SWT.LEFT, SWT.BOTTOM, true, false, 1, 1));
		new LLabel(this, "");
		
		new LLabel(this, Vocab.instance.ANIMATION);
		
		LText txtAnim = new LText(this);
		txtAnim.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
		addControl(txtAnim, "animation");
		
		btnChar.addModifyListener(listener);
		
		LSpinner spnFrame = new LSpinner(this);
		GridData gd_spnFrame = new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1);
		gd_spnFrame.widthHint = 84;
		spnFrame.setLayoutData(gd_spnFrame);
		addControl(spnFrame, "frame");
		
		// Battle
		
		new LLabel(this, Vocab.instance.PARTY);
		
		cmbParty = new LCombo(this);
		cmbParty.setIncludeID(false);
		cmbParty.setOptional(true);
		cmbParty.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 3, 1));
		addControl(cmbParty, "party");
		
		new LLabel(this, Vocab.instance.CHARBATTLER);
		
		LText txtBattler = new LText(this, true);
		txtBattler.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		
		IDButton btnBattler = new IDButton(this, SWT.NONE) {
			@Override
			public LDataTree<Object> getDataTree() {
				return Project.current.battlers.getTree();
			}
		};
		btnBattler.setNameWidget(txtBattler);
		addControl(btnBattler, "battlerID");
		
		Group grpScripts = new Group(this, SWT.NONE);
		grpScripts.setLayout(new GridLayout(1, true));
		grpScripts.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, true, 4, 1));
		grpScripts.setText(Vocab.instance.SCRIPTS);
		
		ScriptList lstScripts = new ScriptList(grpScripts, 2 | 4 | 8);
		lstScripts.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		addChild(lstScripts, "scripts");
		
		LCheckBox btnRepeat = new LCheckBox(grpScripts, SWT.NONE);
		btnRepeat.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		btnRepeat.setText(Vocab.instance.REPEATCOLLISIONS);
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
	
}
