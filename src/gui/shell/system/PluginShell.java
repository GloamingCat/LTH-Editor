package gui.shell.system;

import gui.Tooltip;
import gui.Vocab;
import gui.shell.ObjectShell;
import gui.views.database.subcontent.TagList;
import lui.container.LFrame;
import lui.container.LPanel;
import lui.container.LFlexPanel;
import lui.dialog.LWindow;
import lui.widget.LFileSelector;
import lui.widget.LCheckBox;

import data.config.Plugin;
import project.Project;

public class PluginShell extends ObjectShell<Plugin> {
	
	private LFileSelector selFile;
	
	/**
	 * @wbp.parser.constructor
	 */
	public PluginShell(LWindow parent) {
		super(parent, Vocab.instance.PLUGINSHELL);
	}
	
	@Override
	protected void createContent(int style) {
		super.createContent(style);
		contentEditor.setFillLayout(true);
		LFlexPanel form = new LFlexPanel(contentEditor, true);
		selFile = new LFileSelector(form, false);
		selFile.addFileRestriction( (f) -> { return f.getName().endsWith(".lua"); } );
		selFile.setFolder(Project.current.scriptPath());
		
		LPanel composite = new LPanel(form);
		composite.setGridLayout(1);
		LFrame frame = new LFrame(composite, (String) Vocab.instance.PARAM);
		frame.setFillLayout(true);
		
		LFrame grpParameters = frame;
		grpParameters.setHoverText(Tooltip.instance.PARAM);
		grpParameters.getCellData().setExpand(true, true);
		TagList lstParam = new TagList(grpParameters);
		addChild(lstParam, "tags");
		
		LCheckBox btnON = new LCheckBox(composite);
		btnON.setText(Vocab.instance.PLUGINON);
		btnON.setHoverText(Tooltip.instance.PLUGINON);
		addControl(btnON, "on");
		
		form.setWeights(1, 1);
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
