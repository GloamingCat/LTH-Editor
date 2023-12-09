package gui.shell.system;

import gui.Vocab;
import gui.shell.ObjectShell;
import gui.views.database.subcontent.TagList;
import gui.widgets.FileSelector;

import lwt.container.LFrame;
import lwt.container.LPanel;
import lwt.container.LSashPanel;
import lwt.dialog.LShell;
import lwt.widget.LCheckBox;

import data.config.Plugin;
import project.Project;

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
		contentEditor.setFillLayout(true);
		LSashPanel form = new LSashPanel(contentEditor, true);
		selFile = new FileSelector(form, optional);
		selFile.addFileRestriction( (f) -> { return f.getName().endsWith(".lua"); } );
		selFile.setFolder(Project.current.scriptPath());
		
		LPanel composite = new LPanel(form, 1);
		
		LFrame grpParameters = new LFrame(composite, Vocab.instance.PARAM, true, true);
		grpParameters.setExpand(true, true);
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
