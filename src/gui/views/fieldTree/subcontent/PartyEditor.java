package gui.views.fieldTree.subcontent;

import gui.Vocab;
import gui.views.fieldTree.FieldEditor;
import gui.views.fieldTree.FieldSideEditor;
import gui.widgets.IDList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.GridData;

import project.Project;
import lwt.dataestructure.LDataTree;
import lwt.editor.LObjectEditor;
import lwt.event.LControlEvent;
import lwt.event.listener.LControlListener;
import lwt.widget.LCombo;
import lwt.widget.LLabel;
import lwt.widget.LSpinner;
import lwt.widget.LText;

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
		
		new LLabel(position, Vocab.instance.POSITION);
		
		LSpinner spnX = new LSpinner(position);
		spnX.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		spnX.setMinimum(1);
		addControl(spnX, "x");
		
		LSpinner spnY = new LSpinner(position);
		spnY.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		spnY.setMinimum(1);
		addControl(spnY, "y");
		
		LSpinner spnH = new LSpinner(position);
		spnH.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
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
		txtName.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		addControl(txtName, "name");
		txtName.addModifyListener(new LControlListener<String>() {
			@Override
			public void onModify(LControlEvent<String> event) {
				FieldSideEditor.instance.updatePartyNames();
			}
		});
		
		new LLabel(this, Vocab.instance.DIRECTION);
		
		LCombo cmbDir = new LCombo(this);
		cmbDir.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
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
		cmbGen.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		String[] s = new String[] {Vocab.instance.FIELDCHARS, Vocab.instance.TROOPUNITS};
		cmbGen.setIncludeID(false);
		cmbGen.setOptional(false);
		cmbGen.setItems(s);
		addControl(cmbGen, "memberGen");
		
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
