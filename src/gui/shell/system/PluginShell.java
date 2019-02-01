package gui.shell.system;

import gui.Vocab;
import gui.shell.ObjectShell;
import gui.views.database.subcontent.TagList;
import gui.widgets.FileSelector;

import java.io.File;

import lwt.widget.LCheckButton;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.widgets.Shell;

import data.config.Plugin;
import project.Project;

import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.GridData;

public class PluginShell extends ObjectShell<Plugin> {
	
	private FileSelector selFile;
	
	/**
	 * @wbp.parser.constructor
	 */
	public PluginShell(Shell parent) {
		this(parent, "", 1);
	}
	
	public PluginShell(Shell parent, String folder, int optional) {
		super(parent);
		contentEditor.setLayout(new FillLayout(SWT.HORIZONTAL));
		SashForm form = new SashForm(contentEditor, SWT.NONE);
		selFile = new FileSelector(form, optional) {
			@Override
			protected boolean isValidFile(File f) {
				return f.getName().endsWith(".lua");
			}
		};
		selFile.setFolder(Project.current.scriptPath() + folder);
		
		Composite composite = new Composite(form, SWT.NONE);
		GridLayout gl_composite = new GridLayout(1, false);
		gl_composite.marginHeight = 0;
		gl_composite.marginWidth = 0;
		composite.setLayout(gl_composite);
		
		Group grpParameters = new Group(composite, SWT.NONE);
		grpParameters.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		grpParameters.setText(Vocab.instance.PARAM);
		grpParameters.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		TagList lstParam = new TagList(grpParameters, SWT.NONE);
		addChild(lstParam, "tags");
		
		LCheckButton btnON = new LCheckButton(composite, SWT.NONE);
		btnON.setText(Vocab.instance.PLUGINON);
		addControl(btnON, "on");
		
		form.setWeights(new int[] {1, 1});
	}
	
	public void open(Plugin initial) {
		super.open(initial);
		selFile.setSelectedFile(initial.name);
	}
	
	@Override
	protected Plugin createResult(Plugin initial) {
		Plugin script = (Plugin) contentEditor.getObject();
		script.name = selFile.getSelectedFile();
		if (script.name == null)
			script.name = "";
		return super.createResult(initial);
	}
}
