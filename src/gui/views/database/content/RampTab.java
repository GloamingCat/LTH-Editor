package gui.views.database.content;

import lwt.dialog.LObjectShell;
import lwt.dialog.LShellFactory;
import lwt.event.LControlEvent;
import lwt.event.listener.LControlListener;
import lwt.widget.LObjectButton;
import lwt.widget.LSpinner;
import gui.Vocab;
import gui.shell.RampShell;
import gui.views.database.DatabaseTab;
import gui.views.database.subcontent.RampEditor;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

import data.Ramp.PointSet;
import project.GObjectListSerializer;
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
		addControl(spnHeight, "height");
		
		Group grpLines = new Group(contentEditor, SWT.NONE);
		grpLines.setLayout(new GridLayout(1, false));
		grpLines.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, true, 1, 1));
		grpLines.setText(Vocab.instance.LINES);
		
		RampEditor editor = new RampEditor(grpLines, SWT.NONE);
		editor.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		addChild(editor);
		
		LObjectButton<PointSet> btnDefault = new LObjectButton<PointSet>(grpLines, SWT.NONE);
		btnDefault.setShellFactory(new LShellFactory<PointSet>() {
			public LObjectShell<PointSet> createShell(Shell parent) {
				return new RampShell(parent);
			}
		});
		btnDefault.setText(Vocab.instance.CHOOSEDEFAULT);
		addControl(btnDefault, "points");
		
		btnDefault.addModifyListener(new LControlListener<PointSet>() {
			@Override
			public void onModify(LControlEvent<PointSet> event) {
				editor.setObject(contentEditor.getObject());
				editor.resetImage();
			}
		});
	}

	@Override
	protected GObjectListSerializer getSerializer() {
		return Project.current.ramps;
	}

}
