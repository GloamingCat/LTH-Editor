package gui.shell;

import gui.Tooltip;
import gui.Vocab;
import gui.views.database.subcontent.TagList;
import lui.base.LFlags;
import lui.container.LFrame;
import lui.container.LPanel;
import lui.container.LFlexPanel;
import lui.dialog.LWindow;
import lui.widget.LFileSelector;
import lui.widget.LCheckBox;
import lui.widget.LLabel;
import lui.widget.LText;

import data.subcontent.Script;
import project.Project;

public class ScriptShell extends ObjectShell<Script> {
	
	private LFileSelector selFile;
	
	public static final int OPTIONAL = 0x1;
	public static final int ONLOAD = 0x01;
	public static final int ONCOLLIDE = 0x001;
	public static final int ONINTERACT = 0x0001;
	
	public ScriptShell(LWindow parent, int style) {
		super(parent, style, Vocab.instance.SCRIPTSHELL);
	}
	
	@Override
	protected void createContent(int style) {
		super.createContent(style);

		contentEditor.setGridLayout(2);
		
		new LLabel(contentEditor, Vocab.instance.DESCRIPTION, Tooltip.instance.DESCRIPTION);
		
		LText txtDescription = new LText(contentEditor);
		addControl(txtDescription, "description");
		
		LFlexPanel form = new LFlexPanel(contentEditor, true);
		form.getCellData().setExpand(true, true);
		form.getCellData().setSpread(2, 1);
		selFile = new LFileSelector(form, (style & OPTIONAL) > 0);
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
		
		LPanel options = new LPanel(composite);
		options.setGridLayout(3);
		options.getCellData().setAlignment(LFlags.CENTER);
		
		LCheckBox btnGlobal = new LCheckBox(options);
		btnGlobal.setText(Vocab.instance.GLOBAL);
		btnGlobal.setHoverText(Tooltip.instance.GLOBAL);
		addControl(btnGlobal, "global");
		
		LCheckBox btnWait = new LCheckBox(options);
		btnWait.setText(Vocab.instance.WAIT);
		btnWait.setHoverText(Tooltip.instance.WAIT);
		addControl(btnWait, "wait");
		
		LCheckBox btnBlock = new LCheckBox(options);
		btnBlock.setText(Vocab.instance.BLOCKPLAYER);
		btnBlock.setHoverText(Tooltip.instance.BLOCKPLAYER);
		addControl(btnBlock, "block");
		
		LCheckBox btnLoad = new LCheckBox(options);
		btnLoad.setText(Vocab.instance.ONLOAD);
		btnLoad.setHoverText(Tooltip.instance.ONLOAD);
		addControl(btnLoad, "onLoad");
		btnLoad.setEnabled((style & ONLOAD) > 0);
	
		LCheckBox btnCollide = new LCheckBox(options);
		btnCollide.setText(Vocab.instance.ONCOLLIDE);
		btnCollide.setHoverText(Tooltip.instance.ONCOLLIDE);
		addControl(btnCollide, "onCollide");
		btnCollide.setEnabled((style & ONCOLLIDE) > 0);
		
		LCheckBox btnInteract = new LCheckBox(options);
		btnInteract.setText(Vocab.instance.ONINTERACT);
		btnInteract.setHoverText(Tooltip.instance.ONINTERACT);
		addControl(btnInteract, "onInteract");
		btnInteract.setEnabled((style & ONINTERACT) > 0);
		
		form.setWeights(1, 2);
	}
	
	public void open(Script initial) {
		super.open(initial);
		selFile.setSelectedFile(initial.name);
	}
	
	@Override
	protected Script createResult(Script initial) {
		Script script = (Script) contentEditor.getObject();
		script.name = selFile.getSelectedFile();
		if (script.name == null)
			script.name = "";
		return super.createResult(initial);
	}
}
