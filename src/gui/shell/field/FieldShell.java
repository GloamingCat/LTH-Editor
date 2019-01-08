package gui.shell.field;

import lwt.widget.LCombo;
import lwt.widget.LImage;
import lwt.widget.LSpinner;
import lwt.widget.LText;
import gui.Vocab;
import gui.shell.ObjectShell;
import gui.widgets.QuadButton;

import org.eclipse.swt.widgets.Shell;

import data.field.Field;

import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Group;

import project.Project;

public class FieldShell extends ObjectShell<Field.Prefs> {

	public FieldShell(Shell parent) {
		super(parent);

		setMinimumSize(300, 400);
		contentEditor.setLayout(new GridLayout(2, false));
		
		Label lblName = new Label(contentEditor, SWT.NONE);
		lblName.setText(Vocab.instance.NAME);
		
		LText text = new LText(contentEditor, SWT.NONE);
		text.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		addControl(text, "name");
		
		Label lblDefaultRegions = new Label(contentEditor, SWT.NONE);
		lblDefaultRegions.setText(Vocab.instance.DEFAULTREGION);

		LCombo cmbRegion = new LCombo(contentEditor);
		cmbRegion.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		cmbRegion.setItems(Project.current.regions.getData());
		addControl(cmbRegion, "defaultRegion");
		
		Label lblPartyCount = new Label(contentEditor, SWT.NONE);
		lblPartyCount.setText(Vocab.instance.PARTYCOUNT);
		
		LSpinner spnParties = new LSpinner(contentEditor);
		spnParties.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		addControl(spnParties, "partyCount");

		Group grpBG = new Group(contentEditor, SWT.NONE);
		grpBG.setLayout(new GridLayout(2, false));
		grpBG.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));
		grpBG.setText(Vocab.instance.BACKGROUND);
		
		QuadButton btnBG = new QuadButton(grpBG, 1);
		btnBG.setLayoutData(new GridData(SWT.LEFT, SWT.TOP, false, false, 1, 1));
		addControl(btnBG, "background");
		
		LImage imgBG = new LImage(grpBG, SWT.NONE);
		imgBG.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		btnBG.setImage(imgBG);
		
		Group grpPL = new Group(contentEditor, SWT.NONE);
		grpPL.setLayout(new GridLayout(2, false));
		grpPL.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));
		grpPL.setText(Vocab.instance.PARALLAX);
		
		QuadButton btnPL = new QuadButton(grpPL, 1);
		btnPL.setLayoutData(new GridData(SWT.LEFT, SWT.TOP, false, false, 1, 1));
		addControl(btnPL, "parallax");
		
		LImage imgPL = new LImage(grpPL, SWT.NONE);
		imgPL.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		btnPL.setImage(imgPL);
		
	}
}
