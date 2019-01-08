package gui;

import gui.views.database.DatabaseEditor;
import gui.views.fieldTree.FieldTreeEditor;
import gui.views.system.SystemEditor;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MenuItem;

import project.Project;
import lwt.LDefaultApplicationShell;
import lwt.dataserialization.LFileManager;
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
			LFileManager.log(e);
			e.printStackTrace();
		}
	}

	/**
	 * Create the shell.
	 * @param display
	 */
	public ApplicationShell(Display display) {
		super(display);
		setSize(new Point(900, 600));
		setText("LTH Editor");
		
		final FieldTreeEditor fieldTreeEditor = new FieldTreeEditor(this, SWT.NONE); 
		
		MenuItem mntmFieldEditor = new MenuItem(menuView, SWT.NONE);
		mntmFieldEditor.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				setCurrentView(fieldTreeEditor);
			}
		});
		mntmFieldEditor.setText(Vocab.instance.FIELDEDITOR + "\tF2");
		mntmFieldEditor.setAccelerator(SWT.F2);
		
		final DatabaseEditor databaseEditor = new DatabaseEditor(this, SWT.NONE);
		MenuItem mntmDatabaseEditor = new MenuItem(menuView, SWT.NONE);
		mntmDatabaseEditor.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				setCurrentView(databaseEditor);
			}
		});
		mntmDatabaseEditor.setText(Vocab.instance.DATABASEEDITOR + "\tF3");
		mntmDatabaseEditor.setAccelerator(SWT.F3);

		final SystemEditor systemEditor = new SystemEditor(this, SWT.NONE);
		MenuItem mntmSystemEditor = new MenuItem(menuView, SWT.NONE);
		mntmSystemEditor.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				setCurrentView(systemEditor);
			}
		});
		mntmSystemEditor.setText(Vocab.instance.SYSTEMEDITOR + "\tF4");
		mntmSystemEditor.setAccelerator(SWT.F4);
		
		applicationName = getText();
		if (loadDefault()) {
			setCurrentView(fieldTreeEditor);
		}
	}
	
	@Override
	protected LSerializer createProject(String path) {
		return new Project(path);
	}

}
