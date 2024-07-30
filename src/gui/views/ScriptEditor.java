package gui.views;

import data.subcontent.Script;
import gui.Tooltip;
import gui.Vocab;
import gui.views.database.subcontent.TagList;
import gui.widgets.CheckBoxPanel;
import lui.base.LPrefs;
import lui.container.LContainer;
import lui.container.LFlexPanel;
import lui.container.LFrame;
import lui.container.LViewFolder;
import lui.gson.GDefaultObjectEditor;
import lui.widget.*;
import project.Project;

import java.lang.reflect.Type;

public class ScriptEditor extends GDefaultObjectEditor<Script> {

	private LViewFolder viewFolder;
	private LFileSelector selFile;
	private LNodeSelector<Object> selSheet;

    public static final int OPTIONAL = 1;
	public static final int ONLOAD = 2;
	public static final int ONEXIT = 4;
	public static final int SCOPE = 8;

	private boolean onLoad = false;
	private boolean onExit = false;

	public ScriptEditor(LContainer parent, int style, boolean doubleBuffered) {
		super(parent, style, doubleBuffered);
	}
	
	@Override
	protected void createContent(int style) {
		getCellData().setTargetSize(750, 450);
		setGridLayout(2);
		
		new LLabel(this, Vocab.instance.DESCRIPTION, Tooltip.instance.DESCRIPTION);
		LText txtDescription = new LText(this);
		txtDescription.getCellData().setExpand(true, false);
		addControl(txtDescription, "description");
		
		LFlexPanel form = new LFlexPanel(this, true);
		form.getCellData().setExpand(true, true);
		form.getCellData().setSpread(2, 1);

		// Script

		viewFolder = new LViewFolder(form, false);

		selFile = new LFileSelector(viewFolder, (style & OPTIONAL) > 0);
		selFile.addFileRestriction(f ->f.getName().endsWith(".lua"));
		selFile.setFolder(Project.current.scriptPath());
		selFile.addModifyListener(event -> {
			if (event.newValue != null) {
				Script script = getObject();
				script.name = selFile.getSelectedFile();
				if (script.name == null) // Legacy
					script.name = "";
				selSheet.setValue(null);
			}
		});
		viewFolder.addTab(Vocab.instance.LUAFILE, selFile);

		selSheet = new LNodeSelector<>(viewFolder, style & LNodeSelector.OPTIONAL);
		selSheet.addModifyListener(event -> {
			if (event.newValue != null) {
				Script script = getObject();
				if (event.newValue >= 0)
					script.name = "" + event.newValue;
				else
					script.name = "";
				selFile.setValue(null);
			}
		});
		viewFolder.addTab(Vocab.instance.EVENTSHEET, selSheet);

		// Parameters

		LFrame grpOpts = new LFrame(form, Vocab.instance.OPTIONS);
		grpOpts.setGridLayout(2);
		grpOpts.getCellData().setExpand(true, true);

		if ((style & SCOPE) > 0) {
			new LLabel(grpOpts, Vocab.instance.SCOPE, Tooltip.instance.SCRIPTSCOPE);
			LCombo cmbScope = new LCombo(grpOpts);
			cmbScope.getCellData().setExpand(true, false);
			cmbScope.setItems(new String[]{
					Vocab.instance.OBJECT,
					Vocab.instance.FIELD,
					Vocab.instance.GLOBAL,
					Vocab.instance.CURRENT
			});
		}

		// Triggers

		onLoad = (style & ONLOAD) > 0;
		onExit = (style & ONEXIT) > 0;
		if (onLoad == onExit) {

			new LLabel(grpOpts, Vocab.instance.TRIGGER, Tooltip.instance.SCRIPTTRIGGER);
			CheckBoxPanel trigger = new CheckBoxPanel(grpOpts);

			LCheckBox btnLoad = new LCheckBox(trigger);
			btnLoad.setText(Vocab.instance.ONLOAD);
			btnLoad.setHoverText(Tooltip.instance.ONLOAD);
			addControl(btnLoad, "onLoad");

			LCheckBox btnExit = new LCheckBox(trigger);
			btnExit.setText(Vocab.instance.ONEXIT);
			btnExit.setHoverText(Tooltip.instance.ONEXIT);
			addControl(btnExit, "onExit");

			if (!onLoad) {

				LCheckBox btnCollide = new LCheckBox(trigger);
				btnCollide.setText(Vocab.instance.ONCOLLIDE);
				btnCollide.setHoverText(Tooltip.instance.ONCOLLIDE);
				addControl(btnCollide, "onCollide");

				LCheckBox btnInteract = new LCheckBox(trigger);
				btnInteract.setText(Vocab.instance.ONINTERACT);
				btnInteract.setHoverText(Tooltip.instance.ONINTERACT);
				addControl(btnInteract, "onInteract");

				LCheckBox btnDestroy = new LCheckBox(trigger);
				btnDestroy.setText(Vocab.instance.ONDESTROY);
				btnDestroy.setHoverText(Tooltip.instance.ONDESTROY);
				addControl(btnDestroy, "onDestroy");

			}
		}

		LLabel lblParam = new LLabel(grpOpts, Vocab.instance.PARAM, Tooltip.instance.PARAM);
		lblParam.getCellData().setRequiredSize(LPrefs.LABELWIDTH + 20, LPrefs.WIDGETHEIGHT);
		TagList lstParam = new TagList(grpOpts, Vocab.instance.PARAM);
		lstParam.getCellData().setExpand(true, true);
		addChild(lstParam, "tags");

		new LLabel(grpOpts, Vocab.instance.SYNC, Tooltip.instance.SCRIPTSYNC);
		CheckBoxPanel sync = new CheckBoxPanel(grpOpts);

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

	@Override
	public void onVisible() {
		selSheet.setCollection(Project.current.events.getTree());
		super.onVisible();
	}

    @Override
    public void setObject(Object value) {
		super.setObject(value);
		Script script = (Script) value;
		if (value == null) {
			selSheet.setValue(null);
			selFile.setSelectedFile(null);
		} else {
			script.initialize();
			try {
				int id = Integer.parseInt(script.name);
				selFile.setSelectedFile(null);
				selSheet.setValue(id);
				viewFolder.openTab(1);
			} catch (NumberFormatException e) {
				selSheet.setValue(null);
				selFile.setSelectedFile(script.name);
			}
			if (onLoad) {
				script.onLoad = true;
				script.onExit = onExit;
			} else if (onExit) {
				script.onLoad = false;
				script.onExit = true;
			}
		}
    }

	@Override
	public void refresh() {
		Script object = currentObject;
		currentObject = null;
		setObject(object);
	}

    @Override
    public Type getType() {
        return Script.class;
    }

}
