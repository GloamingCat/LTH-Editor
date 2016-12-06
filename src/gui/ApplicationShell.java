package gui;

import gui.views.config.ConfigEditor;
import gui.views.database.DatabaseEditor;
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
		
		MenuItem mntmDatabaseEditor = new MenuItem(menuView, SWT.NONE);
		mntmDatabaseEditor.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				setCurrentView(databaseEditor);
			}
		});
		mntmDatabaseEditor.setText(Vocab.instance.DATABASEEDITOR + "\tF2");
		mntmDatabaseEditor.setAccelerator(SWT.F2);
		
		MenuItem mntmFieldEditor = new MenuItem(menuView, SWT.NONE);
		mntmFieldEditor.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				setCurrentView(fieldTreeEditor);
			}
		});
		mntmFieldEditor.setText(Vocab.instance.FIELDEDITOR + "\tF3");
		mntmFieldEditor.setAccelerator(SWT.F3);
		
		MenuItem mntmConfigEditor = new MenuItem(menuView, SWT.NONE);
		mntmConfigEditor.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				setCurrentView(configEditor);
			}
		});
		mntmConfigEditor.setText(Vocab.instance.CONFIGEDITOR + "\tF4");
		mntmConfigEditor.setAccelerator(SWT.F4);
		
		if (loadDefault()) {
			setCurrentView(fieldTreeEditor);
		}
	}
	
	@Override
	protected LSerializer createProject(String path) {
		return new Project(path);
	}
	
	@Override
	protected void checkSubclass() { }

}
