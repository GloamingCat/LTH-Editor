package gui.shell;

import gui.Tooltip;
import gui.Vocab;
import gui.views.database.subcontent.TagList;
import lui.base.LFlags;
import lui.base.LPrefs;
import lui.container.LFrame;
import lui.container.LPanel;
import lui.container.LFlexPanel;
import lui.dialog.LWindow;
import lui.gson.GObjectDialog;
import lui.widget.LFileSelector;
import lui.widget.LCheckBox;
import lui.widget.LLabel;
import lui.widget.LText;

import data.subcontent.Script;
import project.Project;

public class ScriptDialog extends GObjectDialog<Script> {
	
	private LFileSelector selFile;
	
	public static final int OPTIONAL = 0x1;
	public static final int ONLOAD = 0x01;
	public static final int ONCOLLIDE = 0x001;
	public static final int ONINTERACT = 0x0001;
	
	public ScriptDialog(LWindow parent, int style) {
		super(parent, 750, 450, style, Vocab.instance.SCRIPTSHELL);
	}
	
	@Override
	protected void createContent(int style) {
		super.createContent(style);
		contentEditor.setGridLayout(2);
		
		new LLabel(contentEditor, Vocab.instance.DESCRIPTION, Tooltip.instance.DESCRIPTION);
		LText txtDescription = new LText(contentEditor);
		txtDescription.getCellData().setExpand(true, false);
		addControl(txtDescription, "description");
		
		LFlexPanel form = new LFlexPanel(contentEditor, true);
		form.getCellData().setExpand(true, true);
		form.getCellData().setSpread(2, 1);
		selFile = new LFileSelector(form, (style & OPTIONAL) > 0);
		selFile.addFileRestriction(f ->f.getName().endsWith(".lua"));
		selFile.setFolder(Project.current.scriptPath());

		LFrame grpOpts = new LFrame(form, Vocab.instance.OPTIONS);
		grpOpts.setGridLayout(2);
		grpOpts.getCellData().setExpand(true, true);

		new LLabel(grpOpts, Vocab.instance.SYNC, Tooltip.instance.SCRIPTSYNC);
		LPanel checks = new LPanel(grpOpts);
		checks.getCellData().setSpread(1, 2);
		checks.setGridLayout(3);
		checks.getCellData().setAlignment(LFlags.LEFT);
		new LLabel(grpOpts, Vocab.instance.TRIGGER, Tooltip.instance.SCRIPTTRIGGER);

		LCheckBox btnGlobal = new LCheckBox(checks);
		btnGlobal.getCellData().setAlignment(LFlags.LEFT);
		btnGlobal.setText(Vocab.instance.GLOBAL);
		btnGlobal.setHoverText(Tooltip.instance.GLOBAL);
		addControl(btnGlobal, "global");

		LCheckBox btnWait = new LCheckBox(checks);
		btnWait.getCellData().setAlignment(LFlags.LEFT);
		btnWait.setText(Vocab.instance.WAIT);
		btnWait.setHoverText(Tooltip.instance.WAIT);
		addControl(btnWait, "wait");

		LCheckBox btnBlock = new LCheckBox(checks);
		btnBlock.getCellData().setAlignment(LFlags.LEFT);
		btnBlock.setText(Vocab.instance.BLOCKPLAYER);
		btnBlock.setHoverText(Tooltip.instance.BLOCKPLAYER);
		addControl(btnBlock, "block");

		LCheckBox btnLoad = new LCheckBox(checks);
		btnLoad.getCellData().setAlignment(LFlags.LEFT);
		btnLoad.setText(Vocab.instance.ONLOAD);
		btnLoad.setHoverText(Tooltip.instance.ONLOAD);
		addControl(btnLoad, "onLoad");
		btnLoad.setEnabled((style & ONLOAD) > 0);

		LCheckBox btnCollide = new LCheckBox(checks);
		btnCollide.getCellData().setAlignment(LFlags.LEFT);
		btnCollide.setText(Vocab.instance.ONCOLLIDE);
		btnCollide.setHoverText(Tooltip.instance.ONCOLLIDE);
		addControl(btnCollide, "onCollide");
		btnCollide.setEnabled((style & ONCOLLIDE) > 0);

		LCheckBox btnInteract = new LCheckBox(checks);
		btnInteract.getCellData().setAlignment(LFlags.LEFT);
		btnInteract.setText(Vocab.instance.ONINTERACT);
		btnInteract.setHoverText(Tooltip.instance.ONINTERACT);
		addControl(btnInteract, "onInteract");
		btnInteract.setEnabled((style & ONINTERACT) > 0);

		LLabel lblParam = new LLabel(grpOpts, Vocab.instance.PARAM, Tooltip.instance.PARAM);
		lblParam.getCellData().setRequiredSize(LPrefs.LABELWIDTH + 20, LPrefs.WIDGETHEIGHT);
		TagList lstParam = new TagList(grpOpts, Vocab.instance.PARAM);
		lstParam.getCellData().setExpand(true, true);
		addChild(lstParam, "tags");

		form.setWeights(1, 2);
	}
	
	public void open(Script initial) {
		selFile.setSelectedFile(initial.name);
		super.open(initial);
	}
	
	@Override
	protected Script createResult(Script initial) {
		Script script = contentEditor.getObject();
		script.name = selFile.getSelectedFile();
		if (script.name == null)
			script.name = "";
		return super.createResult(initial);
	}
}
