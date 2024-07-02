package gui.shell;

import gui.Tooltip;
import gui.Vocab;
import gui.views.database.subcontent.TagList;
import gui.widgets.CheckBoxPanel;
import lui.base.LPrefs;
import lui.container.LFrame;
import lui.container.LFlexPanel;
import lui.container.LViewFolder;
import lui.dialog.LWindow;
import lui.gson.GObjectDialog;
import lui.widget.*;

import data.subcontent.Script;
import project.Project;

public class ScriptDialog extends GObjectDialog<Script> {

	private LViewFolder viewFolder;
	private LFileSelector selFile;
	private LNodeSelector<Object> selSheet;
	
	public static final int OPTIONAL = 1;
	public static final int TRIGGERS = 2;
	
	public ScriptDialog(LWindow parent, int style) {
		super(parent, 750, 450, style, Vocab.instance.SCRIPTSHELL);
	}
	
	@Override
	protected void createContent(int style) {
		super.createContent(0);
		contentEditor.setGridLayout(2);
		
		new LLabel(contentEditor, Vocab.instance.DESCRIPTION, Tooltip.instance.DESCRIPTION);
		LText txtDescription = new LText(contentEditor);
		txtDescription.getCellData().setExpand(true, false);
		addControl(txtDescription, "description");
		
		LFlexPanel form = new LFlexPanel(contentEditor, true);
		form.getCellData().setExpand(true, true);
		form.getCellData().setSpread(2, 1);

		// Script

		viewFolder = new LViewFolder(form, false);

		selFile = new LFileSelector(viewFolder, (style & OPTIONAL) > 0);
		selFile.addFileRestriction(f ->f.getName().endsWith(".lua"));
		selFile.setFolder(Project.current.scriptPath());
		selFile.addModifyListener(event -> {
			if (event.newValue != null)
				selSheet.setValue(null);
		});
		viewFolder.addTab(Vocab.instance.LUAFILE, selFile);

		selSheet = new LNodeSelector<>(viewFolder, style & LNodeSelector.OPTIONAL);
		selSheet.addModifyListener(event -> {
			if (event.newValue != null)
				selFile.setValue(null);
		});
		viewFolder.addTab(Vocab.instance.EVENTSHEET, selSheet);

		// Parameters

		LFrame grpOpts = new LFrame(form, Vocab.instance.OPTIONS);
		grpOpts.setGridLayout(2);
		grpOpts.getCellData().setExpand(true, true);

		if ((style & TRIGGERS) > 0) {
			new LLabel(grpOpts, Vocab.instance.TRIGGER, Tooltip.instance.SCRIPTTRIGGER);
			CheckBoxPanel trigger = new CheckBoxPanel(grpOpts);

			LCheckBox btnLoad = new LCheckBox(trigger);
			btnLoad.setText(Vocab.instance.ONLOAD);
			btnLoad.setHoverText(Tooltip.instance.ONLOAD);
			addControl(btnLoad, "onLoad");

			LCheckBox btnCollide = new LCheckBox(trigger);
			btnCollide.setText(Vocab.instance.ONCOLLIDE);
			btnCollide.setHoverText(Tooltip.instance.ONCOLLIDE);
			addControl(btnCollide, "onCollide");

			LCheckBox btnInteract = new LCheckBox(trigger);
			btnInteract.setText(Vocab.instance.ONINTERACT);
			btnInteract.setHoverText(Tooltip.instance.ONINTERACT);
			addControl(btnInteract, "onInteract");

			LCheckBox btnExit = new LCheckBox(trigger);
			btnExit.setText(Vocab.instance.ONEXIT);
			btnExit.setHoverText(Tooltip.instance.ONEXIT);
			addControl(btnExit, "onExit");
		}

		LLabel lblParam = new LLabel(grpOpts, Vocab.instance.PARAM, Tooltip.instance.PARAM);
		lblParam.getCellData().setRequiredSize(LPrefs.LABELWIDTH + 20, LPrefs.WIDGETHEIGHT);
		TagList lstParam = new TagList(grpOpts, Vocab.instance.PARAM);
		lstParam.getCellData().setExpand(true, true);
		addChild(lstParam, "tags");

		new LLabel(grpOpts, Vocab.instance.SYNC, Tooltip.instance.SCRIPTSYNC);
		CheckBoxPanel sync = new CheckBoxPanel(grpOpts);

		LCheckBox btnGlobal = new LCheckBox(sync);
		btnGlobal.setText(Vocab.instance.GLOBAL);
		btnGlobal.setHoverText(Tooltip.instance.GLOBAL);
		addControl(btnGlobal, "global");

		LCheckBox btnWait = new LCheckBox(sync);
		btnWait.setText(Vocab.instance.WAIT);
		btnWait.setHoverText(Tooltip.instance.WAIT);
		addControl(btnWait, "wait");

		LCheckBox btnBlock = new LCheckBox(sync);
		btnBlock.setText(Vocab.instance.BLOCKPLAYER);
		btnBlock.setHoverText(Tooltip.instance.BLOCKPLAYER);
		addControl(btnBlock, "block");

		form.setWeights(1, 2);
	}
	
	public void open(Script initial) {
		selSheet.setCollection(Project.current.events.getTree());
		try {
			int id = Integer.parseInt(initial.name);
			selFile.setSelectedFile(null);
			selSheet.setValue(id);
			viewFolder.openTab(1);
		} catch (NumberFormatException e) {
			selSheet.setValue(null);
			selFile.setSelectedFile(initial.name);
		}
		super.open(initial);
	}
	
	@Override
	protected Script createResult(Script initial) {
		Script script = contentEditor.getObject();
		script.name = selFile.getSelectedFile();
		if (script.name == null) // Legacy
			script.name = "";
		if (script.name.isEmpty()) {
			Integer id = selSheet.getValue();
			System.out.println(id);
			if (id != null && id >= 0)
				script.name = "" + id;
			else
				script.name = "";
		}
		return super.createResult(initial);
	}

}
