package gui.views.fieldTree;

import gui.Vocab;
import gui.widgets.IDButton;
import gui.widgets.ScriptButton;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import lwt.dataestructure.LDataTree;
import lwt.editor.LObjectEditor;
import lwt.event.LControlEvent;
import lwt.event.listener.LControlListener;
import lwt.widget.LCheckButton;
import lwt.widget.LCombo;
import lwt.widget.LSpinner;
import lwt.widget.LText;

import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.GridData;

import project.Project;

import org.eclipse.swt.widgets.Text;

import data.field.Field;

import org.eclipse.swt.widgets.Group;

public class CharTileEditor extends LObjectEditor {

	private LCombo cmbParty;
	
	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public CharTileEditor(Composite parent, int style) {
		super(parent, style);
		setLayout(new GridLayout(3, false));
		
		// General
		
		Label lblKey = new Label(this, SWT.NONE);
		lblKey.setText(Vocab.instance.KEY);
		
		LText txtKey = new LText(this, SWT.NONE);
		txtKey.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		addControl(txtKey, "key");
		
		Label lblPersistent = new Label(this, SWT.NONE);
		lblPersistent.setText(Vocab.instance.PERSISTENT);
		
		LCheckButton btnPersistent = new LCheckButton(this, SWT.NONE);
		btnPersistent.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1));
		addControl(btnPersistent, "persistent");
		
		// Char
		
		Label lblChar = new Label(this, SWT.NONE);
		lblChar.setText(Vocab.instance.CHARACTER);
		
		Text txtChar = new Text(this, SWT.BORDER | SWT.READ_ONLY);
		txtChar.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		IDButton btnChar = new IDButton(this, SWT.NONE) {
			@Override
			public LDataTree<Object> getDataTree() {
				return Project.current.characters.getTree();
			}
		};
		btnChar.setNameText(txtChar);
		addControl(btnChar, "charID");
		
		Label lblPos = new Label(this, SWT.NONE);
		lblPos.setText(Vocab.instance.POSITION);
		
		Composite position = new Composite(this, SWT.NONE);
		GridLayout gl_position = new GridLayout(3, false);
		gl_position.marginWidth = 0;
		gl_position.marginHeight = 0;
		position.setLayout(gl_position);
		position.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		
		LSpinner spnX = new LSpinner(position);
		spnX.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		addControl(spnX, "x");
		
		LSpinner spnY = new LSpinner(position);
		spnY.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		addControl(spnY, "y");
		
		LSpinner spnH = new LSpinner(position);
		spnH.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		addControl(spnH, "h");
		
		spnX.addModifyListener(new LControlListener<Integer>() {
			@Override
			public void onModify(LControlEvent<Integer> event) {
				if (event == null || event.oldValue == null) return;
				FieldEditor.instance.canvas.updateTileImage(event.oldValue, spnY.getValue());
				FieldEditor.instance.canvas.updateTileImage(event.newValue, spnY.getValue());
			}
		});
		spnY.addModifyListener(new LControlListener<Integer>() {
			@Override
			public void onModify(LControlEvent<Integer> event) {
				if (event == null || event.oldValue == null) return;
				FieldEditor.instance.canvas.updateTileImage(spnX.getValue(), event.oldValue);
				FieldEditor.instance.canvas.updateTileImage(spnX.getValue(), event.newValue);
			}
		});
		spnH.addModifyListener(new LControlListener<Integer>() {
			@Override
			public void onModify(LControlEvent<Integer> event) {
				if (event == null || event.oldValue == null) return;
				FieldEditor.instance.canvas.updateTileImage(spnX.getValue(), spnY.getValue());
			}
		});
		
		Label lblDir = new Label(this, SWT.NONE);
		lblDir.setText(Vocab.instance.DIRECTION);
		
		LCombo cmbDir = new LCombo(this);
		cmbDir.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 2, 1));
		String[] d = new String[] {"0°", "45°", "90°", "135°", 
				"180°", "225°", "270°", "315°"};
		cmbDir.setIncludeID(false);
		cmbDir.setOptional(false);
		cmbDir.setItems(d);
		addControl(cmbDir, "row");
		cmbDir.addModifyListener(new LControlListener<Integer>() {
			@Override
			public void onModify(LControlEvent<Integer> event) {
				if (event == null || event.oldValue == null) return;
				FieldEditor.instance.canvas.updateTileImage(spnX.getValue(), spnY.getValue());
			}
		});
		
		Label lblAnim = new Label(this, SWT.NONE);
		lblAnim.setText(Vocab.instance.ANIMATION);
		
		LText txtAnim = new LText(this);
		txtAnim.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 2, 1));
		addControl(txtAnim, "animation");
		
		btnChar.addModifyListener(new LControlListener<Integer>() {
			@Override
			public void onModify(LControlEvent<Integer> event) {
				if (event == null || event.oldValue == null) return;
				FieldEditor.instance.canvas.updateTileImage(spnX.getValue(), spnY.getValue());
			}
		});
		
		// Battle
		
		Label lblParty = new Label(this, SWT.NONE);
		lblParty.setText(Vocab.instance.PARTY);
		
		cmbParty = new LCombo(this);
		cmbParty.setIncludeID(false);
		cmbParty.setOptional(true);
		cmbParty.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		addControl(cmbParty, "party");
		
		Label lblBattler = new Label(this, SWT.NONE);
		lblBattler.setText(Vocab.instance.CHARBATTLER);
		
		Text txtBattler = new Text(this, SWT.BORDER | SWT.READ_ONLY);
		txtBattler.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		IDButton btnBattler = new IDButton(this, SWT.NONE) {
			@Override
			public LDataTree<Object> getDataTree() {
				return Project.current.battlers.getTree();
			}
		};
		btnBattler.setNameText(txtBattler);
		addControl(btnBattler, "battlerID");
		
		Group grpScripts = new Group(this, SWT.NONE);
		grpScripts.setLayout(new GridLayout(2, false));
		grpScripts.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 3, 1));
		grpScripts.setText(Vocab.instance.SCRIPTS);
		
		Label lblStart = new Label(grpScripts, SWT.NONE);
		lblStart.setText(Vocab.instance.STARTLISTENER);
		
		ScriptButton btnStart = new ScriptButton(grpScripts, SWT.NONE);
		btnStart.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, true, false, 1, 1));
		addControl(btnStart, "startScript");
		
		Label lblCollision = new Label(grpScripts, SWT.NONE);
		lblCollision.setText(Vocab.instance.COLLISIONLISTENER);
		
		ScriptButton btnCollision = new ScriptButton(grpScripts, SWT.NONE);
		btnCollision.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		addControl(btnCollision, "collisionScript");
		
		Label lblInteract = new Label(grpScripts, SWT.NONE);
		lblInteract.setText(Vocab.instance.INTERACTLISTENER);
		
		ScriptButton btnInteract = new ScriptButton(grpScripts, SWT.NONE);
		btnInteract.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		addControl(btnInteract, "interactScript");

	}

	public void setField(Field field) {
		if (currentObject == null)
			return;
		cmbParty.setItems(FieldEditor.instance.canvas.field.createPartyArray());
	}
	
}
