package gui.shell;

import gui.Vocab;
import gui.views.database.subcontent.TagList;
import gui.widgets.FileSelector;

import java.io.File;

import lwt.container.LFrame;
import lwt.container.LPanel;
import lwt.container.LSashPanel;
import lwt.dialog.LShell;
import lwt.widget.LCheckBox;
import lwt.widget.LLabel;
import lwt.widget.LText;

import org.eclipse.swt.SWT;

import data.subcontent.Script;
import project.Project;

import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.GridData;

public class ScriptShell extends ObjectShell<Script> {
	
	private FileSelector selFile;
	
	public static final int OPTIONAL = 0x1;
	public static final int ONLOAD = 0x01;
	public static final int ONCOLLIDE = 0x001;
	public static final int ONINTERACT = 0x0001;
	
	public ScriptShell(LShell parent, int style) {
		super(parent);
		contentEditor.setLayout(new GridLayout(2, false));
		
		new LLabel(contentEditor, Vocab.instance.DESCRIPTION);;
		
		LText txtDescription = new LText(contentEditor);
		addControl(txtDescription, "description");
		
		LSashPanel form = new LSashPanel(contentEditor, true);
		form.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));
		selFile = new FileSelector(form, (style & OPTIONAL) > 0) {
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
		
		LPanel options = new LPanel(composite, 3, false);
		options.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		
		LCheckBox btnGlobal = new LCheckBox(options);
		btnGlobal.setText(Vocab.instance.GLOBAL);
		addControl(btnGlobal, "global");
		
		LCheckBox btnWait = new LCheckBox(options);
		btnWait.setText(Vocab.instance.WAIT);
		addControl(btnWait, "wait");
		
		LCheckBox btnBlock = new LCheckBox(options);
		btnBlock.setText(Vocab.instance.BLOCKPLAYER);
		addControl(btnBlock, "block");
		
		LCheckBox btnLoad = new LCheckBox(options);
		btnLoad.setText(Vocab.instance.ONLOAD);
		addControl(btnLoad, "onLoad");
		btnLoad.setEnabled((style & ONLOAD) > 0);
	
		LCheckBox btnCollide = new LCheckBox(options);
		btnCollide.setText(Vocab.instance.ONCOLLIDE);
		addControl(btnCollide, "onCollide");
		btnCollide.setEnabled((style & ONCOLLIDE) > 0);
		
		LCheckBox btnInteract = new LCheckBox(options);
		btnInteract.setText(Vocab.instance.ONINTERACT);
		addControl(btnInteract, "onInteract");
		btnInteract.setEnabled((style & ONINTERACT) > 0);
		
		form.setWeights(new int[] {1, 2});
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
