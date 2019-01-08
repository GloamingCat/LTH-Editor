package gui.shell.field;

import gui.Vocab;
import gui.shell.ObjectShell;
import gui.views.database.subcontent.TagList;

import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Shell;

import lwt.widget.LSpinner;
import lwt.widget.LText;

import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;

import data.field.Layer.Info;

import org.eclipse.swt.layout.FillLayout;

public class LayerShell extends ObjectShell<Info> {

	public LayerShell(Shell parent) {
		super(parent);
		GridData gridData = (GridData) content.getLayoutData();
		gridData.verticalAlignment = SWT.FILL;
		gridData.grabExcessVerticalSpace = true;

		GridLayout gridLayout = new GridLayout(2, false);
		gridLayout.marginWidth = 0;
		gridLayout.marginHeight = 0;
		contentEditor.setLayout(gridLayout);
		
		Group grpGeneral = new Group(contentEditor, SWT.NONE);
		grpGeneral.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		grpGeneral.setText(Vocab.instance.GENERAL);
		GridLayout gl_grpGeneral = new GridLayout(2, false);
		gl_grpGeneral.marginWidth = 0;
		gl_grpGeneral.marginHeight = 0;
		grpGeneral.setLayout(gl_grpGeneral);
		
		Label lblName = new Label(grpGeneral, SWT.NONE);
		lblName.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		lblName.setText(Vocab.instance.NAME);
		
		LText txtName = new LText(grpGeneral, SWT.NONE);
		txtName.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		addControl(txtName, "name");
		
		Label lblHeight = new Label(grpGeneral, SWT.NONE);
		lblHeight.setText(Vocab.instance.HEIGHT);
		lblHeight.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		
		LSpinner spnHeight = new LSpinner(grpGeneral, SWT.CENTER);
		spnHeight.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		addControl(spnHeight, "height");
		
		Group grpTags = new Group(contentEditor, SWT.NONE);
		grpTags.setLayout(new FillLayout(SWT.HORIZONTAL));
		grpTags.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		grpTags.setText(Vocab.instance.TAGS);
		
		TagList lstTags = new TagList(grpTags, SWT.NONE);
		addChild(lstTags, "tags");
		
		pack();
	}
	
}
