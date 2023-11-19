package gui.shell.field;

import gui.Vocab;
import gui.shell.ObjectShell;
import gui.views.database.subcontent.TagList;
import gui.views.fieldTree.FieldSideEditor;
import lwt.container.LFrame;
import lwt.dialog.LShell;
import lwt.widget.LCheckBox;
import lwt.widget.LLabel;
import lwt.widget.LSpinner;
import lwt.widget.LText;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;

import data.field.Layer.Info;

public class LayerShell extends ObjectShell<Info> {

	public LayerShell(LShell parent) {
		super(parent);
		GridData gridData = (GridData) content.getLayoutData();
		gridData.verticalAlignment = SWT.FILL;
		gridData.grabExcessVerticalSpace = true;

		GridLayout gridLayout = new GridLayout(2, false);
		gridLayout.marginWidth = 0;
		gridLayout.marginHeight = 0;
		contentEditor.setLayout(gridLayout);
		
		LFrame grpGeneral = new LFrame(contentEditor, Vocab.instance.GENERAL, 2, false);
		grpGeneral.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		
		new LLabel(grpGeneral, Vocab.instance.NAME);
		
		LText txtName = new LText(grpGeneral);
		addControl(txtName, "name");
		
		new LLabel(grpGeneral, Vocab.instance.HEIGHT);
		
		LSpinner spnHeight = new LSpinner(grpGeneral);
		spnHeight.setMinimum(1);
		spnHeight.setMaximum(FieldSideEditor.instance.field.prefs.maxHeight);
		addControl(spnHeight, "height");
		
		LCheckBox btnNoAuto = new LCheckBox(grpGeneral, 2);
		btnNoAuto.setText(Vocab.instance.NOAUTO);
		addControl(btnNoAuto, "noAuto");
		
		LFrame grpTags = new LFrame(contentEditor, Vocab.instance.TAGS, true, true);
		grpTags.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		TagList lstTags = new TagList(grpTags);
		addChild(lstTags, "tags");
		
		pack();
	}
	
}
