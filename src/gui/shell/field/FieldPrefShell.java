package gui.shell.field;

import lwt.LFlags;
import lwt.container.LFrame;
import lwt.container.LImage;
import lwt.container.LPanel;
import lwt.dialog.LShell;
import lwt.event.LEditEvent;
import lwt.event.LSelectionEvent;
import lwt.event.listener.LCollectionListener;
import lwt.event.listener.LSelectionListener;
import lwt.widget.LCheckBox;
import lwt.widget.LCombo;
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

import project.Project;

public class FieldPrefShell extends ObjectShell<Field.Prefs> {

	public FieldPrefShell(LShell parent) {
		super(parent);

		setMinimumSize(600, 400);
		contentEditor.setGridLayout(3, true);
		
		LFrame grpGeneral = new LFrame(contentEditor, Vocab.instance.GENERAL, 3, false);
		grpGeneral.setAlignment(LFlags.TOP);
		grpGeneral.setExpand(true, false);
		
		new LLabel(grpGeneral, 1, 1);
		LPanel key = new LPanel(grpGeneral, 2, false);
		key.setExpand(true, false);
		key.setSpread(2, 1);
		key.setAlignment(LFlags.CENTER);
		
		new LLabel(key, Vocab.instance.KEY);
		LText txtKey = new LText(key);
		addControl(txtKey, "key");
		
		new LLabel(grpGeneral, Vocab.instance.NAME);
		LText txtName = new LText(grpGeneral, 2);
		addControl(txtName, "name");
		
		new LLabel(grpGeneral, Vocab.instance.PERSISTENT);
		LCheckBox btnPersistent = new LCheckBox(grpGeneral, 2);
		addControl(btnPersistent, "persistent");
		
		new LLabel(grpGeneral, Vocab.instance.DEFAULTREGION);
		LCombo cmbRegion = new LCombo(grpGeneral, 2);
		cmbRegion.setItems(Project.current.regions.getData());
		addControl(cmbRegion, "defaultRegion");
		
		new LLabel(grpGeneral, Vocab.instance.MAXHEIGHT);
		LSpinner spnHeight = new LSpinner(grpGeneral, 2);
		spnHeight.setMinimum(0);
		spnHeight.setMaximum(99);
		addControl(spnHeight, "maxHeight");
		
		new LLabel(grpGeneral, Vocab.instance.BGM);
		LText txtBGM = new LText(grpGeneral, true);
		AudioButton btnBGM = new AudioButton(grpGeneral, true);
		btnBGM.setTextWidget(txtBGM);
		addControl(btnBGM, "bgm");
		
		new LLabel(grpGeneral, Vocab.instance.ONLOAD);
		LText txtScript = new LText(grpGeneral, true);
		
		ScriptButton btnScript = new ScriptButton(grpGeneral, 1);
		btnScript.setPathWidget(txtScript);
		addControl(btnScript, "loadScript");
		
		// Images
		
		LFrame grpImages = new LFrame(contentEditor, Vocab.instance.IMAGES, false, true);
		grpImages.setSpread(1, 2);
		grpImages.setExpand(false, true);
		
		FieldImageList lstImages = new FieldImageList(grpImages);
		addChild(lstImages, "images");
		LImage img = new LImage(grpImages);
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
		
		// Tags
		
		LFrame grpTags = new LFrame(contentEditor, Vocab.instance.TAGS, false, true);
		grpTags.setSpread(1, 2);
		grpTags.setExpand(false, true);
		TagList lstTags = new TagList(grpTags);
		addChild(lstTags, "tags");
		
		// Transitions
		
		LFrame grpTransitions = new LFrame(contentEditor, Vocab.instance.TRANSITIONS, 1, false);
		grpTransitions.setExpand(true, true);
		
		SimpleEditableList<Transition> lstTransitions = new SimpleEditableList<>(grpTransitions);
		lstTransitions.type = Transition.class;
		lstTransitions.getCollectionWidget().setEditEnabled(false);
		lstTransitions.setExpand(true, true);
		addChild(lstTransitions, "transitions");
		
		GDefaultObjectEditor<Transition> transitionEditor = new GDefaultObjectEditor<Transition>(grpTransitions, 3, false, false);
		transitionEditor.setExpand(true, false);
		transitionEditor.setAlignment(LFlags.CENTER);
		lstTransitions.addChild(transitionEditor);
		
		// Destination
		
		new LLabel(transitionEditor, Vocab.instance.DESTINATION);
		LText txtDest = new LText(transitionEditor, true);
		PositionButton btnDest = new PositionButton(transitionEditor);
		btnDest.setTextWidget(txtDest);
		transitionEditor.addControl(btnDest, "destination");
		
		// Origin Tiles
		
		new LLabel(transitionEditor, Vocab.instance.ORIGTILES);
		LText txtOrigin = new LText(transitionEditor, true);
		PortalButton btnOrigin = new PortalButton(transitionEditor, FieldSideEditor.instance.field.id);
		btnOrigin.setTextWidget(txtOrigin);
		transitionEditor.addControl(btnOrigin, "origin");
		
		// Fade
		
		new LLabel(transitionEditor, Vocab.instance.FADEOUT);
		LSpinner spnFade = new LSpinner(transitionEditor);
		spnFade.setMinimum(-1);
		spnFade.setMaximum(99999);
		transitionEditor.addControl(spnFade, "fade");
		new LLabel(transitionEditor, 1, 1);
		
	}

	private void updateImage(LImage img, FieldImage p) {
		if (p != null) {
			Object obj = Project.current.animations.getTree().get(p.id);
			if (obj == null) {
				img.setImage((String) null);
				return;
			}
			Animation anim = (Animation) obj;
			if (anim.quad.path.isEmpty()) {
				img.setImage((String) null);
				return;
			}
			img.setImage(anim.quad.fullPath(), anim.quad.getRect());
		} else {
			img.setImage((String) null);
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
