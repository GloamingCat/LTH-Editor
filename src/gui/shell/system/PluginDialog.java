package gui.shell.system;

import gui.Tooltip;
import gui.Vocab;
import lui.gson.GObjectDialog;
import gui.views.database.subcontent.TagList;
import lui.container.LFrame;
import lui.container.LPanel;
import lui.container.LFlexPanel;
import lui.dialog.LWindow;
import lui.widget.LFileSelector;
import lui.widget.LCheckBox;

import data.config.Plugin;
import project.Project;

public class PluginDialog extends GObjectDialog<Plugin> {
	
	private LFileSelector selFile;
	
	/**
	 * @wbp.parser.constructor
	 */
	public PluginDialog(LWindow parent) {
		super(parent, 600, 400, Vocab.instance.PLUGINSHELL);
	}
	
	@Override
	protected void createContent(int style) {
		super.createContent(style);
		contentEditor.setFillLayout(true);
		LFlexPanel form = new LFlexPanel(contentEditor, true);
		selFile = new LFileSelector(form, false);
		selFile.addFileRestriction( (f) -> f.getName().endsWith(".lua") );
		selFile.setFolder(Project.current.scriptPath());
		
		LPanel composite = new LPanel(form);
		composite.setGridLayout(1);
		LFrame grpParameters = new LFrame(composite, Vocab.instance.PARAM);
		grpParameters.setFillLayout(true);
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
		selFile.setSelectedFile(initial.name);
		super.open(initial);
	}
	
	@Override
	protected Plugin createResult(Plugin initial) {
		Plugin script = contentEditor.getObject();
		script.name = selFile.getSelectedFile();
		if (script.name == null)
			script.name = "";
		return super.createResult(initial);
	}
}
