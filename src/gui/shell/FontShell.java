package gui.shell;

import java.io.File;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Shell;

import project.Project;

import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Spinner;

import data.Config;

import org.eclipse.swt.widgets.Text;

public class FontShell extends FileShell<Config.Font> {

	private Spinner spnSize;
	private Text txtFormat;
	
	public FontShell(Shell parent) {
		super(parent, "");
		
		Composite composite = new Composite(sashForm, SWT.NONE);
		composite.setLayout(new GridLayout(2, false));
		composite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		
		Label lblSize = new Label(composite, SWT.NONE);
		lblSize.setText("Size");
		
		spnSize = new Spinner(composite, SWT.BORDER);
		spnSize.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label lblFormat = new Label(composite, SWT.NONE);
		lblFormat.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblFormat.setText("Format");
		
		txtFormat = new Text(composite, SWT.BORDER);
		txtFormat.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
	}
	
	public void open(Config.Font initial) {
		super.open(initial);
		int i = indexOf(initial.path);
		list.select(i);
		spnSize.setSelection(initial.size);
		txtFormat.setText(initial.format);
	}

	@Override
	protected Config.Font createResult(Config.Font initial) {
		int i = list.getSelectionIndex();
		if (i < 0)
			return null;
		String newPath = folder + "/" + list.getItem(i);
		if (newPath.equals(initial.path) && initial.size == spnSize.getSelection() 
				&& txtFormat.getText().equals(initial.format)) {
			return null;
		} else {
			return new Config.Font(newPath, 
					spnSize.getSelection(), 
					txtFormat.getText());
		}
	}

	protected boolean isValidFile(File file) {
		try {
			Font f = new Font(Display.getCurrent(), file.getPath(), 
					spnSize.getSelection(), SWT.NONE);
			f.dispose();
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	protected String rootPath() {
		return Project.current.fontPath();
	}
	
}
