package gui.shell;

import gui.Vocab;
import gui.views.database.subcontent.TagList;
import gui.widgets.FileSelector;

import java.io.File;

import lwt.widget.LCheckButton;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.widgets.Shell;

import data.subcontent.Script;
import project.Project;

import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.GridData;

public class ScriptShell extends ObjectShell<Script> {
	
	private FileSelector selFile;
	
	public ScriptShell(Shell parent, int style) {
		super(parent);
		contentEditor.setLayout(new FillLayout(SWT.HORIZONTAL));
		SashForm form = new SashForm(contentEditor, SWT.NONE);
		selFile = new FileSelector(form, style) {
			@Override
			protected boolean isValidFile(File f) {
				return f.getName().endsWith(".lua");
			}
		};
		selFile.setFolder(Project.current.scriptPath());
		
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
		
		Composite options = new Composite(composite, SWT.NONE);
		options.setLayout(new GridLayout(3, false));
		options.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		
		LCheckButton btnGlobal = new LCheckButton(options);
		btnGlobal.setText(Vocab.instance.GLOBAL);
		addControl(btnGlobal, "global");
		
		LCheckButton btnWait = new LCheckButton(options);
		btnWait.setText(Vocab.instance.WAIT);
		addControl(btnWait, "wait");
		
		LCheckButton btnBlock = new LCheckButton(options);
		btnBlock.setText(Vocab.instance.BLOCKPLAYER);
		addControl(btnBlock, "block");
		
		LCheckButton btnLoad = new LCheckButton(options);
		btnLoad.setText(Vocab.instance.ONLOAD);
		addControl(btnLoad, "onLoad");
		btnLoad.setEnabled((style & 2) != 0);
	
		LCheckButton btnCollide = new LCheckButton(options);
		btnCollide.setText(Vocab.instance.ONCOLLIDE);
		addControl(btnCollide, "onCollide");
		btnCollide.setEnabled((style & 4) != 0);
		
		LCheckButton btnInteract = new LCheckButton(options);
		btnInteract.setText(Vocab.instance.ONINTERACT);
		addControl(btnInteract, "onInteract");
		btnInteract.setEnabled((style & 8) != 0);
		
		form.setWeights(new int[] {1, 1});
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
