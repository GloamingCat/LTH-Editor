package gui.shell.system;

import gui.Vocab;
import gui.shell.ObjectShell;
import gui.views.database.subcontent.TagList;
import gui.widgets.FileSelector;

import java.io.File;

import lwt.container.LFrame;
import lwt.container.LPanel;
import lwt.container.LSashPanel;
import lwt.dialog.LShell;
import lwt.widget.LCheckBox;

import org.eclipse.swt.SWT;

import data.config.Plugin;
import project.Project;

import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;

public class PluginShell extends ObjectShell<Plugin> {
	
	private FileSelector selFile;
	
	/**
	 * @wbp.parser.constructor
	 */
	public PluginShell(LShell parent) {
		this(parent, true);
	}
	
	public PluginShell(LShell parent, boolean optional) {
		super(parent);
		contentEditor.setLayout(new FillLayout(SWT.HORIZONTAL));
		LSashPanel form = new LSashPanel(contentEditor, true);
		selFile = new FileSelector(form, optional) {
			@Override
			protected boolean isValidFile(File f) {
				return f.getName().endsWith(".lua");
			}
		};
		selFile.setFolder(Project.current.scriptPath());
		
		LPanel composite = new LPanel(form, 1);
		
		LFrame grpParameters = new LFrame(composite, Vocab.instance.PARAM, true, true);
		grpParameters.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		TagList lstParam = new TagList(grpParameters);
		addChild(lstParam, "tags");
		
		LCheckBox btnON = new LCheckBox(composite);
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
