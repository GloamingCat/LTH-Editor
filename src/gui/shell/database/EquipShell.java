package gui.shell.database;

import gui.Vocab;
import gui.widgets.IDButton;

import org.eclipse.swt.widgets.Shell;

import data.Battler.Equip;
import lwt.dataestructure.LDataTree;
import lwt.dialog.LObjectShell;

import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Text;

import project.Project;

public class EquipShell extends LObjectShell<Equip> {
	
	private Text txtKey;
	private Text txtItem;
	private Combo cmbState;
	private IDButton btnItem;

	public EquipShell(Shell parent) {
		super(parent);
		
		setText(Vocab.instance.EQUIP);
		content.setLayout(new GridLayout(3, false));
		
		Label lblKey = new Label(content, SWT.NONE);
		lblKey.setText(Vocab.instance.KEY);

		txtKey = new Text(content, SWT.BORDER);
		txtKey.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 2, 1));
		
		Label lblState = new Label(content, SWT.NONE);
		lblState.setText(Vocab.instance.STATE);
		
		cmbState = new Combo(content, SWT.READ_ONLY);
		cmbState.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 2, 1));
		cmbState.setItems(new String[] {
				Vocab.instance.FREE, Vocab.instance.NOTEMPTY,
				Vocab.instance.ALLEQUIPED, Vocab.instance.UNCHANGABLE,
				Vocab.instance.INVISIBLE
		});
		
		Label lblItem = new Label(content, SWT.NONE);
		lblItem.setText(Vocab.instance.EQUIPITEM);
		
		txtItem = new Text(content, SWT.BORDER | SWT.READ_ONLY);
		txtItem.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
				btnItem = new IDButton(content, SWT.NONE) {
					public LDataTree<Object> getDataTree() {
						return Project.current.items.getTree();
					}
				};
				btnItem.setNameText(txtItem);
		
		pack();
	}
	
	public void open(Equip initial) {
		super.open(initial);
		btnItem.setValue(initial.id);
		cmbState.select(initial.state);
		txtKey.setText(initial.key);
	}

	@Override
	protected Equip createResult(Equip initial) {
		Equip eq = new Equip();
		eq.key = txtKey.getText();
		eq.state = cmbState.getSelectionIndex();
		eq.id = btnItem.getValue();
		return eq;
	}
	
}
