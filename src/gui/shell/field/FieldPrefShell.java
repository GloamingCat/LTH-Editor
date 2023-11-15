package gui.shell.field;

import lwt.event.LEditEvent;
import lwt.event.LSelectionEvent;
import lwt.event.listener.LCollectionListener;
import lwt.event.listener.LSelectionListener;
import lwt.widget.LCheckBox;
import lwt.widget.LCombo;
import lwt.widget.LImage;
import lwt.widget.LLabel;
import lwt.widget.LSpinner;
import lwt.widget.LText;
import gui.Vocab;
import gui.shell.ObjectShell;
import gui.views.database.subcontent.TagList;
import gui.views.fieldTree.FieldSideEditor;
import gui.views.fieldTree.subcontent.FieldImageList;
import gui.widgets.AudioButton;
import gui.widgets.PortalButton;
import gui.widgets.PositionButton;
import gui.widgets.ScriptButton;
import gui.widgets.SimpleEditableList;

import data.Animation;
import data.field.Field;
import data.field.FieldImage;
import data.field.Transition;
import gson.editor.GDefaultObjectEditor;

import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridLayout;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;

import project.Project;

import org.eclipse.swt.layout.FillLayout;

public class FieldPrefShell extends ObjectShell<Field.Prefs> {

	public FieldPrefShell(Shell parent) {
		super(parent);

		setMinimumSize(600, 400);
		GridLayout gridLayout = new GridLayout(3, true);
		gridLayout.verticalSpacing = 0;
		contentEditor.setLayout(gridLayout);
		
		Group grpGeneral = new Group(contentEditor, SWT.NONE);
		grpGeneral.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, true, 1, 1));
		grpGeneral.setText("General");
		grpGeneral.setLayout(new GridLayout(3, false));
		
		new LLabel(grpGeneral, "");
		
		Composite key = new Composite(grpGeneral, SWT.NONE);
		GridLayout gl_key = new GridLayout(2, false);
		gl_key.marginWidth = 0;
		gl_key.marginHeight = 0;
		gl_key.verticalSpacing = 0;
		key.setLayout(gl_key);
		key.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		
		new LLabel(key, Vocab.instance.KEY);
		
		LText txtKey = new LText(key);
		txtKey.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		addControl(txtKey, "key");
		
		new LLabel(grpGeneral, Vocab.instance.NAME);
		
		LText txtName = new LText(grpGeneral);
		txtName.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		addControl(txtName, "name");
		
		new LLabel(grpGeneral, Vocab.instance.PERSISTENT);
		
		LCheckBox btnPersistent = new LCheckBox(grpGeneral);
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
		
		LText txtBGM = new LText(grpGeneral, true);
		txtBGM.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		
		AudioButton btnBGM = new AudioButton(grpGeneral, 1);
		btnBGM.setTextWidget(txtBGM);
		addControl(btnBGM, "bgm");
		
		new LLabel(grpGeneral, Vocab.instance.ONLOAD);
		
		LText txtScript = new LText(grpGeneral, true);
		txtScript.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		ScriptButton btnScript = new ScriptButton(grpGeneral, 1);
		btnScript.setPathWidget(txtScript);
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
		
		Group grpTags = new Group(contentEditor, SWT.NONE);
		grpTags.setLayout(new FillLayout(SWT.VERTICAL));
		grpTags.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, true, 1, 2));
		grpTags.setText(Vocab.instance.TAGS);
		
		TagList lstTags = new TagList(grpTags, SWT.NONE);
		addChild(lstTags, "tags");
		
		Group grpTransitions = new Group(contentEditor, SWT.NONE);
		grpTransitions.setLayout(new GridLayout(1, false));
		grpTransitions.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, true, 1, 1));
		grpTransitions.setText(Vocab.instance.TRANSITIONS);
		
		SimpleEditableList<Transition> lstTransitions = new SimpleEditableList<>(grpTransitions, SWT.NONE);
		lstTransitions.type = Transition.class;
		lstTransitions.getCollectionWidget().setEditEnabled(false);
		lstTransitions.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		addChild(lstTransitions, "transitions");
		
		GDefaultObjectEditor<Transition> transitionEditor = new GDefaultObjectEditor<Transition>(grpTransitions, SWT.NONE);
		GridLayout gl_transitionEditor = new GridLayout(3, false);
		gl_transitionEditor.marginWidth = 0;
		gl_transitionEditor.marginHeight = 0;
		transitionEditor.setLayout(gl_transitionEditor);
		transitionEditor.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		lstTransitions.addChild(transitionEditor);
		
		// Destination
		
		new LLabel(transitionEditor, Vocab.instance.DESTINATION);
		
		LText txtDest = new LText(transitionEditor, true);
		txtDest.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		PositionButton btnDest = new PositionButton(transitionEditor);
		btnDest.setTextWidget(txtDest);
		transitionEditor.addControl(btnDest, "destination");
		
		// Origin Tiles
		
		new LLabel(transitionEditor, Vocab.instance.ORIGTILES);
		
		LText txtOrigin = new LText(transitionEditor, true);
		txtOrigin.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		PortalButton btnOrigin = new PortalButton(transitionEditor, FieldSideEditor.instance.field.id);
		btnOrigin.setTextWidget(txtOrigin);
		transitionEditor.addControl(btnOrigin, "origin");
		
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
	
	public void open(Field.Prefs initial) {
		for (Transition t : initial.transitions) {
			if (t.br != null)
				t.convert();
		}
		super.open(initial);
	}
	
}
