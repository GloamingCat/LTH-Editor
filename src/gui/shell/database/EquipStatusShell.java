package gui.shell.database;

import gui.Vocab;
import gui.shell.ObjectShell;

import org.eclipse.swt.widgets.Shell;

import data.Item.EquipStatus;
import lwt.widget.LCheckBox;
import lwt.widget.LNodeSelector;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;

import project.Project;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.graphics.Point;

public class EquipStatusShell extends ObjectShell<EquipStatus> {

	public EquipStatusShell(Shell parent) {
		super(parent);
		setMinimumSize(new Point(300, 250));
		setSize(310, 246);
		contentEditor.setLayout(new GridLayout(1, false));
		
		LNodeSelector<Object> tree = new LNodeSelector<Object>(contentEditor, SWT.NONE);
		tree.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));
		tree.setCollection(Project.current.status.getTree());
		addControl(tree, "id");
		
		LCheckBox btnBattle = new LCheckBox(contentEditor);
		btnBattle.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		btnBattle.setText(Vocab.instance.ADDONBATTLE);
		addControl(btnBattle, "battle");
		
		pack();
	}
	
}
