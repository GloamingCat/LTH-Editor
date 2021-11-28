package gui.shell.field;

import lwt.editor.LObjectEditor;
import lwt.event.LEditEvent;
import lwt.event.LSelectionEvent;
import lwt.event.listener.LCollectionListener;
import lwt.event.listener.LSelectionListener;
import lwt.widget.LCheckButton;
import lwt.widget.LCombo;
import lwt.widget.LDescriptor;
import lwt.widget.LImage;
import lwt.widget.LLabel;
import lwt.widget.LSpinner;
import lwt.widget.LText;
import gui.Vocab;
import gui.shell.ObjectShell;
import gui.views.fieldTree.FieldSideEditor;
import gui.views.fieldTree.subcontent.FieldImageList;
import gui.widgets.AudioButton;
import gui.widgets.PositionButton;
import gui.widgets.ScriptButton;
import gui.widgets.SimpleEditableList;

import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import data.Animation;
import data.field.Field;
import data.field.FieldImage;
import data.field.Transition;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Group;

import project.Project;

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
		
		new LLabel(grpGeneral, Vocab.instance.NAME);
		
		LText txtName = new LText(grpGeneral, SWT.NONE);
		txtName.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		addControl(txtName, "name");
		
		new LLabel(grpGeneral, Vocab.instance.PERSISTENT);
		
		LCheckButton btnPersistent = new LCheckButton(grpGeneral);
		btnPersistent.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		addControl(btnPersistent, "persistent");
		
		new LLabel(grpGeneral, Vocab.instance.DEFAULTREGION);
		
		LCombo cmbRegion = new LCombo(grpGeneral);
		cmbRegion.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		cmbRegion.setItems(Project.current.regions.getData());
		addControl(cmbRegion, "defaultRegion");
		
		new LLabel(grpGeneral, Vocab.instance.MAXHEIGHT);
		
		LSpinner spnHeight = new LSpinner(grpGeneral, SWT.NONE);
		spnHeight.setMinimum(0);
		spnHeight.setMaximum(99);
		spnHeight.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 2, 1));
		addControl(spnHeight, "maxHeight");
		
		new LLabel(grpGeneral, Vocab.instance.BGM);
		
		LDescriptor txtBGM = new LDescriptor(grpGeneral);
		txtBGM.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		
		AudioButton btnBGM = new AudioButton(grpGeneral, 1);
		btnBGM.setDescriptor(txtBGM);
		addControl(btnBGM, "bgm");
		
		new LLabel(grpGeneral, Vocab.instance.ONLOAD);
		
		Text txtScript = new Text(grpGeneral, SWT.BORDER | SWT.READ_ONLY);
		txtScript.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		ScriptButton btnScript = new ScriptButton(grpGeneral, 3);
		btnScript.setPathText(txtScript);
		btnScript.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		addControl(btnScript, "loadScript");
		
		Group grpImages = new Group(contentEditor, SWT.NONE);
		grpImages.setLayout(new FillLayout(SWT.VERTICAL));
		grpImages.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, true, 1, 2));
		grpImages.setText(Vocab.instance.IMAGES);
		
		FieldImageList lstImages = new FieldImageList(grpImages, SWT.NONE);
		addChild(lstImages, "images");

		LImage img = new LImage(grpImages, SWT.NONE);
		
		lstImages.getCollectionWidget().addSelectionListener(new LSelectionListener() {
			@Override
			public void onSelect(LSelectionEvent event) {
				updateImage(img, (FieldImage) event.data);
			}
		});
		lstImages.getCollectionWidget().addEditListener(new LCollectionListener<FieldImage>() {
			@Override
			public void onEdit(LEditEvent<FieldImage> e) {
				updateImage(img, e.newData);
			}
		});
		
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
		
		new LLabel(transitionEditor, Vocab.instance.DESTINATION);
		
		Text txtDest = new Text(transitionEditor, SWT.BORDER | SWT.READ_ONLY);
		txtDest.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		PositionButton btnDest = new PositionButton(transitionEditor);
		btnDest.setText(txtDest);
		transitionEditor.addControl(btnDest, "destination");
		
		// Start
		
		new LLabel(transitionEditor, Vocab.instance.ORIGSTART);
		
		Text txtStart = new Text(transitionEditor, SWT.BORDER | SWT.READ_ONLY);
		txtStart.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		PositionButton btnStart = new PositionButton(transitionEditor, FieldSideEditor.instance.field.id);
		btnStart.setText(txtStart);
		transitionEditor.addControl(btnStart, "tl");
		
		// End
		
		new LLabel(transitionEditor, Vocab.instance.ORIGEND);
		
		Text txtEnd = new Text(transitionEditor, SWT.BORDER | SWT.READ_ONLY);
		txtEnd.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		PositionButton btnEnd = new PositionButton(transitionEditor, FieldSideEditor.instance.field.id);
		btnEnd.setText(txtEnd);
		transitionEditor.addControl(btnEnd, "br");
		
		// Fade
		
		new LLabel(transitionEditor, Vocab.instance.FADEOUT);
		
		LSpinner spnFade = new LSpinner(transitionEditor);
		spnFade.setMinimum(-1);
		spnFade.setMaximum(99999);
		spnFade.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		transitionEditor.addControl(spnFade, "fade");
		new LLabel(transitionEditor, 1);
		
		pack();
		
	}

	private void updateImage(LImage img, FieldImage p) {
		if (p != null) {
			Object obj = Project.current.animations.getTree().get(p.id);
			if (obj == null) {
				img.setImage((Image) null);
				return;
			}
			Animation anim = (Animation) obj;
			if (anim.quad.path.isEmpty()) {
				img.setImage((Image) null);
				return;
			}
			img.setImage(Project.current.imagePath() + anim.quad.path, anim.quad.getRectangle());
		} else {
			img.setImage((Image) null);
		}
	}
	
}
