package gui;

import gui.views.config.ConfigEditor;
import gui.views.database.DatabaseEditor;
import gui.views.dialog.DialogTreeEditor;
import gui.views.fieldTree.FieldTreeEditor;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MenuItem;

import project.Project;
import lwt.LDefaultApplicationShell;
import lwt.dataserialization.LSerializer;

import org.eclipse.swt.graphics.Point;

public class ApplicationShell extends LDefaultApplicationShell {
	
	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String args[]) {
		try {
			Display display = Display.getDefault();
			ApplicationShell shell = new ApplicationShell(display);
			shell.open();
			shell.layout();
			while (!shell.isDisposed()) {
				if (!display.readAndDispatch()) {
					display.sleep();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the shell.
	 * @param display
	 */
	public ApplicationShell(Display display) {
		super(display);
		setMinimumSize(new Point(800, 600));
		setText("LTH Editor");
		setSize(450, 300);
		
		final DatabaseEditor databaseEditor = new DatabaseEditor(this, SWT.NONE);
		final ConfigEditor configEditor = new ConfigEditor(this, SWT.NONE);
		final FieldTreeEditor fieldTreeEditor = new FieldTreeEditor(this, SWT.NONE); 
		//final GUITreeEditor guiEditor = new GUITreeEditor(this, SWT.NONE);
		final DialogTreeEditor dialogEditor = new DialogTreeEditor(this, SWT.NONE);
		
		MenuItem mntmFieldEditor = new MenuItem(menuView, SWT.NONE);
		mntmFieldEditor.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				setCurrentView(fieldTreeEditor);
			}
		});
		mntmFieldEditor.setText(Vocab.instance.FIELDEDITOR + "\tF2");
		mntmFieldEditor.setAccelerator(SWT.F2);
		
		MenuItem mntmDatabaseEditor = new MenuItem(menuView, SWT.NONE);
		mntmDatabaseEditor.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				setCurrentView(databaseEditor);
			}
		});
		mntmDatabaseEditor.setText(Vocab.instance.DATABASEEDITOR + "\tF3");
		mntmDatabaseEditor.setAccelerator(SWT.F3);

		/*MenuItem mntmGUIEditor = new MenuItem(menuView, SWT.NONE);
		mntmGUIEditor.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				setCurrentView(guiEditor);
			}
		});
		mntmGUIEditor.setText(Vocab.instance.GUIEDITOR + "\tF4");
		mntmGUIEditor.setAccelerator(SWT.F4);*/
		
		MenuItem mntmDialogEditor = new MenuItem(menuView, SWT.NONE);
		mntmDialogEditor.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				setCurrentView(dialogEditor);
			}
		});
		mntmDialogEditor.setText(Vocab.instance.DIALOGEDITOR + "\tF4");
		mntmDialogEditor.setAccelerator(SWT.F4);

		MenuItem mntmConfigEditor = new MenuItem(menuView, SWT.NONE);
		mntmConfigEditor.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				setCurrentView(configEditor);
			}
		});
		mntmConfigEditor.setText(Vocab.instance.CONFIGEDITOR + "\tF5");
		mntmConfigEditor.setAccelerator(SWT.F5);
		
		if (loadDefault()) {
			setCurrentView(configEditor);
		}
	}
	
	@Override
	protected LSerializer createProject(String path) {
		return new Project(path);
	}

}
