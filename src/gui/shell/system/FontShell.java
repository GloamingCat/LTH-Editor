package gui.shell.system;

import gui.Vocab;
import gui.shell.FileShell;
import lwt.container.LPanel;
import lwt.dialog.LShell;
import lwt.widget.LLabel;
import lwt.widget.LSpinner;
import lwt.widget.LText;

import java.io.File;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;

import project.Project;

import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Display;

import data.subcontent.FontData;

public class FontShell extends FileShell<FontData> {

	private LSpinner spnSize;
	private LText txtFormat;
	
	public FontShell(LShell parent) {
		super(parent, "", false);
		
		LPanel composite = new LPanel(sashForm, 2, false);
		composite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		
		new LLabel(composite, Vocab.instance.SIZE);
		
		spnSize = new LSpinner(composite);
		
		new LLabel(composite, Vocab.instance.FORMAT);
		//lblFormat.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		
		txtFormat = new LText(composite);
	}
	
	public void open(FontData initial) {
		super.open(initial);
		int i = indexOf(initial.path);
		list.select(i);
		spnSize.setValue(initial.size);
		txtFormat.setValue(initial.format);
	}

	@Override
	protected FontData createResult(FontData initial) {
		int i = list.getSelectionIndex();
		if (i < 0)
			return null;
		return new FontData(folder + "/" + list.getItem(i), 
			spnSize.getValue(), 
			txtFormat.getValue());
	}

	protected boolean isValidFile(File file) {
		try {
			Font f = new Font(Display.getCurrent(), file.getPath(), 
					spnSize.getValue(), SWT.NONE);
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
