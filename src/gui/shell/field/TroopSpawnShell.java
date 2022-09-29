
package gui.shell.field;

import gui.Vocab;
import gui.shell.ObjectShell;

import org.eclipse.swt.widgets.Shell;

import data.field.Party.TroopSpawn;
import lwt.widget.LLabel;
import lwt.widget.LNodeSelector;
import lwt.widget.LSpinner;
import project.Project;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.GridData;

public class TroopSpawnShell extends ObjectShell<TroopSpawn> {

	public TroopSpawnShell(Shell parent) {
		super(parent);
		setMinimumSize(372, 329);
		contentEditor.setLayout(new GridLayout(2, false));
		
		new LLabel(contentEditor, Vocab.instance.MINLEVEL);
		
		LSpinner spnMin = new LSpinner(contentEditor, SWT.NONE);
		spnMin.setMinimum(0);
		spnMin.setMaximum(100000);
		spnMin.setValue(1);
		spnMin.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		addControl(spnMin, "minLevel");
		
		new LLabel(contentEditor, Vocab.instance.MAXLEVEL);
		
		LSpinner spnMax = new LSpinner(contentEditor, SWT.NONE);
		spnMax.setMinimum(0);
		spnMax.setMaximum(100000);
		spnMax.setValue(100);
		spnMax.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		addControl(spnMax, "maxLevel");
		
		LNodeSelector<Object> tree = new LNodeSelector<Object>(contentEditor, SWT.NONE);
		tree.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));
		tree.setCollection(Project.current.troops.getTree());
		addControl(tree, "id");
		
		pack();
	}
	
}
