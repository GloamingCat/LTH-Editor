
package gui.shell.field;

import gui.Vocab;
import gui.shell.ObjectShell;

import data.field.Party.TroopSpawn;
import lwt.dialog.LShell;
import lwt.widget.LLabel;
import lwt.widget.LNodeSelector;
import lwt.widget.LSpinner;
import project.Project;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.GridData;

public class TroopSpawnShell extends ObjectShell<TroopSpawn> {

	public TroopSpawnShell(LShell parent) {
		super(parent, 360, 320);
		contentEditor.setLayout(new GridLayout(2, false));
		
		new LLabel(contentEditor, Vocab.instance.MINLEVEL);
		
		LSpinner spnMin = new LSpinner(contentEditor);
		spnMin.setMinimum(0);
		spnMin.setMaximum(100000);
		spnMin.setValue(1);
		addControl(spnMin, "minLevel");
		
		new LLabel(contentEditor, Vocab.instance.MAXLEVEL);
		
		LSpinner spnMax = new LSpinner(contentEditor);
		spnMax.setMinimum(0);
		spnMax.setMaximum(100000);
		spnMax.setValue(100);
		addControl(spnMax, "maxLevel");
		
		LNodeSelector<Object> tree = new LNodeSelector<Object>(contentEditor, false);
		tree.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));
		tree.setCollection(Project.current.troops.getTree());
		addControl(tree, "id");
		
		pack();
	}
	
}
