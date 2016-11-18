package gui.views.database.content;

import lwt.widget.LSpinner;
import gui.Vocab;
import gui.views.database.DatabaseTab;
import gui.views.database.subcontent.RampEditor;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import project.ListSerializer;
import project.Project;


public class RampTab extends DatabaseTab {
	
	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public RampTab(Composite parent, int style) {
		super(parent, style);
		
		Label lblHeight = new Label(grpGeneral, SWT.NONE);
		lblHeight.setText(Vocab.instance.HEIGHT);
		
		LSpinner spnHeight = new LSpinner(grpGeneral, SWT.NONE);
		spnHeight.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		contentEditor.addControl(spnHeight, "height");
		
		RampEditor editor = new RampEditor(contentEditor, SWT.NONE);
		editor.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, true, 1, 1));
		contentEditor.addChild(editor);
		
	}

	@Override
	protected ListSerializer getSerializer() {
		return Project.current.ramps;
	}

}
