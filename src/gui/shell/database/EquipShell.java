package gui.shell.database;

import gui.Vocab;
import gui.shell.ObjectShell;
import gui.widgets.IDButton;

import org.eclipse.swt.widgets.Shell;

import data.Battler.Equip;
import lwt.dataestructure.LDataTree;
import lwt.widget.LCombo;
import lwt.widget.LText;

import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Text;

import project.Project;

public class EquipShell extends ObjectShell<Equip> {

	public EquipShell(Shell parent) {
		super(parent);
		
		setText(Vocab.instance.EQUIP);
		contentEditor.setLayout(new GridLayout(3, false));
		
		Label lblKey = new Label(contentEditor, SWT.NONE);
		lblKey.setText(Vocab.instance.KEY);

		LText txtKey = new LText(contentEditor, SWT.NONE);
		txtKey.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 2, 1));
		addControl(txtKey, "key");
		
		Label lblState = new Label(contentEditor, SWT.NONE);
		lblState.setText(Vocab.instance.STATE);
		
		LCombo cmbState = new LCombo(contentEditor, SWT.READ_ONLY);
		cmbState.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 2, 1));
		cmbState.setIncludeID(false);
		cmbState.setItems(new String[] {
				Vocab.instance.FREE, Vocab.instance.NOTEMPTY,
				Vocab.instance.ALLEQUIPED, Vocab.instance.UNCHANGABLE,
				Vocab.instance.INVISIBLE
		});
		addControl(cmbState, "state");
		
		Label lblItem = new Label(contentEditor, SWT.NONE);
		lblItem.setText(Vocab.instance.EQUIPITEM);
		
		Text txtItem = new Text(contentEditor, SWT.BORDER | SWT.READ_ONLY);
		txtItem.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		IDButton btnItem = new IDButton(contentEditor, 1) {
			public LDataTree<Object> getDataTree() {
				return Project.current.items.getTree();
			}
		};
		btnItem.setNameText(txtItem);
		addControl(btnItem, "id");
		
		pack();
	}
	
}
