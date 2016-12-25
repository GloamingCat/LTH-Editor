package gui.shell;

import gui.Vocab;

import java.io.File;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Shell;

import project.Project;

import data.Script;

public class ScriptShell extends FileShell<Script> {

	private StyledText txtParam;
	
	/**
	 * @wbp.parser.constructor
	 */
	public ScriptShell(Shell parent) {
		this(parent, "");
	}
	
	public ScriptShell(Shell parent, String folder) {
		super(parent, folder);
		
		Group grpParam = new Group(sashForm, SWT.BORDER);
		grpParam.setText(Vocab.instance.PARAM);
		grpParam.setLayout(new FillLayout());
		txtParam = new StyledText(grpParam, SWT.BORDER);
	}
	
	public void open(Script initial) {
		super.open(initial);
		txtParam.setText(initial.param);
		int i = indexOf(initial.path);
		list.select(i);
	}
	
	@Override
	protected Script createResult(Script initial) {
		int i = list.getSelectionIndex();
		if (i >= 0) {
			String newValue = folder + "/" + list.getItem(i);
			if (newValue.equals(initial.path) && txtParam.getText().equals(initial.param)) {
				return null;
			} else {
				Script s = new Script();
				s.path = newValue;
				s.param = txtParam.getText();
				return s;
			}
		} else {
			return null;
		}
	}

	protected boolean isValidFile(File f) {
		return f.getName().endsWith(".lua");
	}
	
	protected String rootPath() {
		return Project.current.scriptPath();
	}

}
