package gui.views.fieldTree.subcontent;

import gui.Vocab;
import gui.views.fieldTree.FieldEditor;
import gui.widgets.IDList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.GridData;

import project.Project;
import lwt.dataestructure.LDataTree;
import lwt.editor.LObjectEditor;
import lwt.event.LControlEvent;
import lwt.event.listener.LControlListener;
import lwt.widget.LCombo;
import lwt.widget.LSpinner;
import org.eclipse.swt.layout.FillLayout;

public class PartyEditor extends LObjectEditor {

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public PartyEditor(Composite parent, int style) {
		super(parent, style);
		setLayout(new GridLayout(2, false));
		
		Composite position = new Composite(this, SWT.NONE);
		GridLayout gl_position = new GridLayout(4, false);
		gl_position.marginWidth = 0;
		gl_position.marginHeight = 0;
		position.setLayout(gl_position);
		position.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		
		// Position
		
		Label lblPos = new Label(position, SWT.NONE);
		lblPos.setText(Vocab.instance.POSITION);
		
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
		cmbDir.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		String[] d = new String[] {"0°", "90°", "180°", "270°"};
		cmbDir.setIncludeID(false);
		cmbDir.setOptional(false);
		cmbDir.setItems(d);
		addControl(cmbDir, "direction");
		cmbDir.addModifyListener(new LControlListener<Integer>() {
			@Override
			public void onModify(LControlEvent<Integer> event) {
				//if (event == null || event.oldValue == null) return;
				//FieldEditor.instance.canvas.updateTileImage(spnX.getValue(), spnY.getValue());
			}
		});
		
		// Members
		
		Label lblGen = new Label(this, SWT.NONE);
		lblGen.setText(Vocab.instance.PARTYGEN);
		
		LCombo cmbGen = new LCombo(this);
		cmbGen.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		String[] s = new String[] {Vocab.instance.FIELDCHARS, Vocab.instance.TROOPUNITS};
		cmbGen.setIncludeID(false);
		cmbGen.setOptional(false);
		cmbGen.setItems(s);
		addControl(cmbGen, "memberGen");
		cmbGen.addModifyListener(new LControlListener<Integer>() {
			@Override
			public void onModify(LControlEvent<Integer> event) {
				//if (event == null || event.oldValue == null) return;
				//FieldEditor.instance.canvas.updateTileImage(spnX.getValue(), spnY.getValue());
			}
		});
		
		Group grpTroops = new Group(this, SWT.NONE);
		grpTroops.setLayout(new FillLayout(SWT.HORIZONTAL));
		grpTroops.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));
		grpTroops.setText(Vocab.instance.TROOPS);

		IDList lstTroops = new IDList(grpTroops, SWT.NONE) {
			@Override
			public LDataTree<Object> getDataTree() {
				return Project.current.troops.getTree();
			}
		};
		addChild(lstTroops, "troops");
		
	}
	
}
