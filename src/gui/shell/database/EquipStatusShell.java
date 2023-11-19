package gui.shell.database;

import gui.Vocab;
import gui.shell.ObjectShell;

import data.Item.EquipStatus;
import lwt.dialog.LShell;
import lwt.widget.LCheckBox;
import lwt.widget.LNodeSelector;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;

import project.Project;
import org.eclipse.swt.layout.GridLayout;

public class EquipStatusShell extends ObjectShell<EquipStatus> {

	public EquipStatusShell(LShell parent) {
		super(parent);
		setMinimumSize(300, 250);
		setSize(310, 246);
		contentEditor.setLayout(new GridLayout(1, false));
		
		LNodeSelector<Object> tree = new LNodeSelector<Object>(contentEditor, false);
		tree.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		tree.setCollection(Project.current.status.getTree());
		addControl(tree, "id");
		
		LCheckBox btnBattle = new LCheckBox(contentEditor);
		btnBattle.setText(Vocab.instance.ADDONBATTLE);
		addControl(btnBattle, "battle");
		
		pack();
	}
	
}
