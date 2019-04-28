package gui.views.fieldTree.subcontent;

import gui.shell.field.FieldImageShell;
import gui.widgets.SimpleEditableList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;

import data.field.FieldImage;
import lwt.dialog.LObjectShell;
import lwt.dialog.LShellFactory;

public class FieldImageList extends SimpleEditableList<FieldImage> {
	
	public FieldImageList(Composite parent, int style) {
		super(parent, SWT.NONE);
		type = FieldImage.class;
		setIncludeID(false);
		setShellFactory(new LShellFactory<FieldImage>() {
			@Override
			public LObjectShell<FieldImage> createShell(Shell parent) {
				return new FieldImageShell(parent, style);
			}
		});
	}

}
