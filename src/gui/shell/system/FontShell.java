package gui.shell.system;

import gui.Tooltip;
import gui.Vocab;
import gui.shell.FileShell;
import lwt.container.LPanel;
import lwt.dialog.LShell;
import lwt.widget.LLabel;
import lwt.widget.LSpinner;
import lwt.widget.LText;

import java.io.File;

import project.Project;

import data.subcontent.FontData;

public class FontShell extends FileShell<FontData> {

	private LSpinner spnSize;
	private LText txtFormat;
	
	public FontShell(LShell parent) {
		super(parent, Vocab.instance.FONTSHELL, 0);
		setFolder("fonts/");
		
		LPanel composite = new LPanel(sashForm);
		composite.setGridLayout(2);
		composite.setExpand(true, true);
		
		LLabel lblLength = new LLabel(composite, Vocab.instance.LENGTH, Tooltip.instance.LENGTH);
		
		spnSize = new LSpinner(composite);
		spnSize.addMenu(lblLength);
		
		LLabel lblFormat =new LLabel(composite, Vocab.instance.FORMAT, Tooltip.instance.FORMAT);
		//lblFormat.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		
		txtFormat = new LText(composite);
		txtFormat.addMenu(lblFormat);
	}
	
	public void open(FontData initial) {
		super.open(initial);
		int i = list.indexOf(initial.path);
		list.setValue(i);
		spnSize.setValue(initial.size);
		txtFormat.setValue(initial.format);
	}

	@Override
	protected FontData createResult(FontData initial) {
		int i = list.getValue();
		if (i < 0)
			return null;
		return new FontData(getSelectedPath(), 
			spnSize.getValue(), 
			txtFormat.getValue());
	}

	protected boolean isValidFile(File file) {
		try {
			//Font f = new Font(Display.getCurrent(), file.getPath(), 
			//		spnSize.getValue(), SWT.NONE);
			//f.dispose();
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	protected String rootPath() {
		return Project.current.fontPath();
	}
	
}
