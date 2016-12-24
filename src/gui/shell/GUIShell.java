package gui.shell;

import gui.Vocab;
import gui.views.common.ImageButton;
import gui.views.common.ScriptButton;
import gui.views.database.subcontent.TagList;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import lwt.dataestructure.LDataList;
import lwt.dialog.LObjectShell;

import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;

import data.GUI.Prefs;
import data.Tag;

import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;

public class GUIShell extends LObjectShell<Prefs> {
	
	private Text txtName;
	private Text txtBG;
	
	private Text txtScript;
	
	protected TagList lstTags;
	protected LDataList<Tag> tags;

	public GUIShell(Shell parent) {
		super(parent);
		GridData gridData = (GridData) content.getLayoutData();
		gridData.verticalAlignment = SWT.FILL;
		gridData.grabExcessVerticalSpace = true;
		setMinimumSize(new Point(320, 280));
		setSize(360, 260);
		
		GridLayout gridLayout = new GridLayout(1, false);
		gridLayout.marginWidth = 0;
		gridLayout.marginHeight = 0;
		content.setLayout(gridLayout);
		
		Group grpGeneral = new Group(content, SWT.NONE);
		grpGeneral.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		grpGeneral.setText(Vocab.instance.GENERAL);
		GridLayout gl_grpGeneral = new GridLayout(2, false);
		gl_grpGeneral.marginWidth = 0;
		gl_grpGeneral.marginHeight = 0;
		grpGeneral.setLayout(gl_grpGeneral);
		
		Label lblName = new Label(grpGeneral, SWT.NONE);
		lblName.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		lblName.setText(Vocab.instance.NAME);
		
		txtName = new Text(grpGeneral, SWT.BORDER);
		txtName.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label lblBG = new Label(grpGeneral, SWT.NONE);
		lblBG.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		lblBG.setText(Vocab.instance.BACKGROUND);
		
		Composite compBG = new Composite(grpGeneral, SWT.NONE);
		GridLayout gl_compBG = new GridLayout(2, false);
		gl_compBG.marginWidth = 0;
		gl_compBG.marginHeight = 0;
		compBG.setLayout(gl_compBG);
		compBG.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		txtBG = new Text(compBG, SWT.BORDER | SWT.READ_ONLY);
		txtBG.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		ImageButton btnBG = new ImageButton(compBG, SWT.NONE);
		btnBG.setFolder("GUI");
		btnBG.setText(txtBG);
		
		Label lblScript = new Label(grpGeneral, SWT.NONE);
		lblScript.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, true, 1, 1));
		lblScript.setText(Vocab.instance.SCRIPT);
		
		Composite scriptComp = new Composite(grpGeneral, SWT.NONE);
		scriptComp.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, true, 1, 1));
		GridLayout gl_scriptComp = new GridLayout(2, false);
		gl_scriptComp.marginWidth = 0;
		gl_scriptComp.marginHeight = 0;
		scriptComp.setLayout(gl_scriptComp);
		
		txtScript = new Text(scriptComp, SWT.BORDER | SWT.READ_ONLY);
		txtScript.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		ScriptButton btnScript = new ScriptButton(scriptComp, SWT.NONE);
		btnScript.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		btnScript.setFolder("eventsheet");
		
		btnScript.setPathText(txtScript);
		
		Group grpTags = new Group(content, SWT.NONE);
		grpTags.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 2, 1));
		grpTags.setLayout(new FillLayout(SWT.HORIZONTAL));
		grpTags.setText(Vocab.instance.TAGS);
		
		lstTags = new TagList(grpTags, SWT.NONE) {
			public LDataList<Tag> getDataCollection() {
				return tags;
			}
		};
		
		pack();
	}
	
	public void open(Prefs initial) {
		super.open(initial);
		txtName.setText(initial.name);
		txtBG.setText(initial.background);
		txtScript.setText(initial.script);
		
		tags = new LDataList<Tag>();
		for(Tag tag : initial.tags) {
			tags.add(new Tag(tag));
		}
		lstTags.onVisible();
	}

	@Override
	protected Prefs createResult(Prefs initial) {
		if (txtName.getText().equals(initial.name) && txtBG.getText().equals(initial.background)
				 && tags.equals(initial.tags) && txtScript.getText().equals(initial.script)) {
			return null;
		}
		Prefs p = new Prefs();
		p.name = txtName.getText();
		p.background = txtBG.getText();
		p.tags = tags;
		p.script = txtScript.getText();
		return p;
	}
	
}
