package gui.shell.field;

import lwt.editor.LObjectEditor;
import lwt.widget.LCombo;
import lwt.widget.LImage;
import lwt.widget.LSpinner;
import lwt.widget.LText;
import gui.Vocab;
import gui.shell.ObjectShell;
import gui.views.fieldTree.FieldSideEditor;
import gui.widgets.AudioButton;
import gui.widgets.PositionButton;
import gui.widgets.QuadButton;
import gui.widgets.ScriptButton;
import gui.widgets.SimpleEditableList;

import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import data.field.Field;
import data.field.Transition;

import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Group;

import project.Project;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.layout.FillLayout;

public class FieldPrefShell extends ObjectShell<Field.Prefs> {

	public FieldPrefShell(Shell parent) {
		super(parent);

		setMinimumSize(600, 400);
		GridLayout gridLayout = new GridLayout(2, true);
		gridLayout.verticalSpacing = 0;
		contentEditor.setLayout(gridLayout);
		
		Group grpGeneral = new Group(contentEditor, SWT.NONE);
		grpGeneral.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, true, 1, 1));
		grpGeneral.setText("General");
		grpGeneral.setLayout(new GridLayout(3, false));
		
		Label lblName = new Label(grpGeneral, SWT.NONE);
		lblName.setText(Vocab.instance.NAME);
		
		LText text = new LText(grpGeneral, SWT.NONE);
		text.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		addControl(text, "name");
		
		Label lblDefaultRegions = new Label(grpGeneral, SWT.NONE);
		lblDefaultRegions.setText(Vocab.instance.DEFAULTREGION);
		
		LCombo cmbRegion = new LCombo(grpGeneral);
		cmbRegion.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		cmbRegion.setItems(Project.current.regions.getData());
		addControl(cmbRegion, "defaultRegion");
		
		Label lblMaxHeight = new Label(grpGeneral, SWT.NONE);
		lblMaxHeight.setText(Vocab.instance.MAXHEIGHT);
		
		LSpinner spnHeight = new LSpinner(grpGeneral, SWT.NONE);
		spnHeight.setMinimum(0);
		spnHeight.setMaximum(99);
		spnHeight.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 2, 1));
		addControl(spnHeight, "maxHeight");
		
		Label lblBGM = new Label(grpGeneral, SWT.NONE);
		lblBGM.setText(Vocab.instance.BGM);
		
		Text txtBGM = new Text(grpGeneral, SWT.BORDER | SWT.READ_ONLY);
		txtBGM.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		
		AudioButton btnBGM = new AudioButton(grpGeneral, 1);
		btnBGM.setText(txtBGM);
		addControl(btnBGM, "bgm");
		
		Label lblScript = new Label(grpGeneral, SWT.NONE);
		lblScript.setText(Vocab.instance.LOADSCRIPT);
		
		Text txtScript = new Text(grpGeneral, SWT.BORDER | SWT.READ_ONLY);
		txtScript.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		ScriptButton btnScript = new ScriptButton(grpGeneral, SWT.NONE);
		btnScript.setPathText(txtScript);
		btnScript.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		addControl(btnScript, "loadScript");
		
		Composite right = new Composite(contentEditor, SWT.NONE);
		right.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, true, 1, 2));
		right.setLayout(new FillLayout(SWT.VERTICAL));
		
		Group grpBG = new Group(right, SWT.NONE);
		grpBG.setLayout(new GridLayout(2, false));
		grpBG.setText(Vocab.instance.BACKGROUND);
		
		QuadButton btnBG = new QuadButton(grpBG, 1);
		btnBG.setLayoutData(new GridData(SWT.LEFT, SWT.TOP, false, false, 1, 1));
		addControl(btnBG, "background");
		
		LImage imgBG = new LImage(grpBG, SWT.NONE);
		imgBG.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		btnBG.setImage(imgBG);
		
		Group grpPL = new Group(right, SWT.NONE);
		grpPL.setLayout(new GridLayout(2, false));
		grpPL.setText(Vocab.instance.PARALLAX);
		
		QuadButton btnPL = new QuadButton(grpPL, 1);
		btnPL.setLayoutData(new GridData(SWT.LEFT, SWT.TOP, false, false, 1, 1));
		addControl(btnPL, "parallax");
		
		LImage imgPL = new LImage(grpPL, SWT.NONE);
		imgPL.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		btnPL.setImage(imgPL);
		
		Group grpTransitions = new Group(contentEditor, SWT.NONE);
		grpTransitions.setLayout(new GridLayout(1, false));
		grpTransitions.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, true, 1, 1));
		grpTransitions.setText(Vocab.instance.TRANSITIONS);
		
		SimpleEditableList<Transition> lstTransitions = new SimpleEditableList<>(grpTransitions, SWT.NONE);
		lstTransitions.type = Transition.class;
		lstTransitions.getCollectionWidget().setEditEnabled(false);
		lstTransitions.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		addChild(lstTransitions, "transitions");
		
		LObjectEditor transitionEditor = new LObjectEditor(grpTransitions, SWT.NONE);
		GridLayout gl_transitionEditor = new GridLayout(3, false);
		gl_transitionEditor.marginWidth = 0;
		gl_transitionEditor.marginHeight = 0;
		transitionEditor.setLayout(gl_transitionEditor);
		transitionEditor.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		lstTransitions.addChild(transitionEditor);
		
		// Destination
		
		Label lblDest = new Label(transitionEditor, SWT.NONE);
		lblDest.setText(Vocab.instance.DESTINATION);
		
		Text txtDest = new Text(transitionEditor, SWT.BORDER | SWT.READ_ONLY);
		txtDest.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		PositionButton btnDest = new PositionButton(transitionEditor);
		btnDest.setText(txtDest);
		transitionEditor.addControl(btnDest, "destination");
		
		// Start
		
		Label lblStart = new Label(transitionEditor, SWT.NONE);
		lblStart.setText(Vocab.instance.ORIGSTART);
		
		Text txtStart = new Text(transitionEditor, SWT.BORDER | SWT.READ_ONLY);
		txtStart.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		PositionButton btnStart = new PositionButton(transitionEditor, FieldSideEditor.instance.field.id);
		btnStart.setText(txtStart);
		transitionEditor.addControl(btnStart, "tl");
		
		// End
		
		Label lblEnd = new Label(transitionEditor, SWT.NONE);
		lblEnd.setText(Vocab.instance.ORIGEND);
		
		Text txtEnd = new Text(transitionEditor, SWT.BORDER | SWT.READ_ONLY);
		txtEnd.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		PositionButton btnEnd = new PositionButton(transitionEditor, FieldSideEditor.instance.field.id);
		btnEnd.setText(txtEnd);
		transitionEditor.addControl(btnEnd, "br");
		
		// Fade
		
		Label lblFade = new Label(transitionEditor, SWT.NONE);
		lblFade.setText(Vocab.instance.FADEOUT);
		
		LSpinner spnFade = new LSpinner(transitionEditor);
		spnFade.setMinimum(-1);
		spnFade.setMaximum(99999);
		spnFade.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		transitionEditor.addControl(spnFade, "fade");
		new Label(transitionEditor, SWT.NONE);
		
		pack();
		
	}
}
