package gui.shell.field;

import lwt.container.LContainer;
import lwt.container.LFrame;
import lwt.container.LImage;
import lwt.container.LPanel;
import lwt.dialog.LWindow;
import lwt.gson.GDefaultObjectEditor;
import lbase.event.LEditEvent;
import lwt.widget.LCheckBox;
import lwt.widget.LCombo;
import lwt.widget.LLabel;
import lwt.widget.LSpinner;
import lwt.widget.LText;
import gui.Tooltip;
import gui.Vocab;
import gui.shell.ObjectShell;
import gui.views.database.subcontent.TagList;
import gui.views.fieldTree.subcontent.FieldImageList;
import gui.widgets.AudioButton;
import gui.widgets.PortalButton;
import gui.widgets.PositionButton;
import gui.widgets.ScriptButton;
import gui.widgets.SimpleEditableList;
import lbase.LFlags;
import lbase.event.listener.LCollectionListener;

import java.lang.reflect.Type;

import data.Animation;
import data.field.Field;
import data.field.FieldImage;
import data.field.FieldNode;
import data.field.Transition;
import project.Project;

public class FieldPrefShell extends ObjectShell<Field.Prefs> {

	protected int id;
	
	public FieldPrefShell(LWindow parent, FieldNode n) {
		super(parent,  Project.current.fieldTree.getData().findNode(n).id, "");
		setTitle(String.format("[%03d] ", id) + n.name);
		setMinimumSize(600, 400);
	}
	
	@Override
	protected void createContent(int id) {
		super.createContent(0);
		this.id = id;
		contentEditor.setGridLayout(3);
		
		LFrame grpGeneral = new LFrame(contentEditor, Vocab.instance.GENERAL);
		grpGeneral.setGridLayout(3);
		grpGeneral.setHoverText(Tooltip.instance.GENERAL);
		grpGeneral.getCellData().setAlignment(LFlags.TOP);
		grpGeneral.getCellData().setExpand(true, false);
		
		LPanel key = new LPanel(grpGeneral);
		key.setGridLayout(3);
		key.getCellData().setExpand(true, false);
		key.getCellData().setSpread(3, 1);
		
		LLabel fieldID = new LLabel(key, LFlags.EXPAND, "", Tooltip.instance.ID);
		fieldID.setText("ID: " + id);
		
		new LLabel(key, Vocab.instance.KEY, Tooltip.instance.KEY);
		LText txtKey = new LText(key);
		addControl(txtKey, "key");
		
		new LLabel(grpGeneral, Vocab.instance.NAME, Tooltip.instance.NAME);
		LText txtName = new LText(grpGeneral);
		txtName.getCellData().setSpread(2, 1);
		addControl(txtName, "name");
		
		new LLabel(grpGeneral, 1, 1);
		LCheckBox btnPersistent = new LCheckBox(grpGeneral, 2);
		btnPersistent.setText(Vocab.instance.PERSISTENT);
		btnPersistent.setHoverText(Tooltip.instance.PERSISTENT);
		addControl(btnPersistent, "persistent");
		
		new LLabel(grpGeneral, Vocab.instance.DEFAULTREGION, Tooltip.instance.DEFAULTREGION);
		LCombo cmbRegion = new LCombo(grpGeneral, true);
		cmbRegion.getCellData().setSpread(2, 1);
		cmbRegion.setItems(Project.current.regions.getData());
		addControl(cmbRegion, "defaultRegion");
		
		new LLabel(grpGeneral, Vocab.instance.FIELDMAXHEIGHT, Tooltip.instance.FIELDMAXHEIGHT);
		LSpinner spnHeight = new LSpinner(grpGeneral);
		spnHeight.getCellData().setSpread(2, 1);
		spnHeight.getCellData().setExpand(true, false);
		spnHeight.setMinimum(0);
		spnHeight.setMaximum(99);
		addControl(spnHeight, "maxHeight");
		
		new LLabel(grpGeneral, Vocab.instance.BGM, Tooltip.instance.BGM);
		LText txtBGM = new LText(grpGeneral, true);
		AudioButton btnBGM = new AudioButton(grpGeneral, true);
		btnBGM.setTextWidget(txtBGM);
		addControl(btnBGM, "bgm");
		
		new LLabel(grpGeneral, Vocab.instance.LOADSCRIPT, Tooltip.instance.LOADSCRIPT);
		LText txtScript = new LText(grpGeneral, true);
		
		ScriptButton btnScript = new ScriptButton(grpGeneral, 1);
		btnScript.setPathWidget(txtScript);
		addControl(btnScript, "loadScript");
		
		// Images
		
		LFrame grpImages = new LFrame(contentEditor, Vocab.instance.IMAGES);
		grpImages.setFillLayout(false);
		grpImages.setHoverText(Tooltip.instance.IMAGES);
		grpImages.getCellData().setSpread(1, 2);
		grpImages.getCellData().setExpand(true, true);
		
		FieldImageList lstImages = new FieldImageList(grpImages);
		addChild(lstImages, "images");
		LImage img = new LImage(grpImages);
		lstImages.getCollectionWidget().addSelectionListener(event -> updateImage(img, (FieldImage) event.data));
		lstImages.getCollectionWidget().addEditListener(new LCollectionListener<>() {
			@Override
			public void onEdit(LEditEvent<FieldImage> e) {
				updateImage(img, e.newData);
			}
		});
		
		// Tags
		
		LFrame grpTags = new LFrame(contentEditor, Vocab.instance.TAGS);
		grpTags.setFillLayout(false);
		grpTags.setHoverText(Tooltip.instance.TAGS);
		grpTags.getCellData().setSpread(1, 2);
		grpTags.getCellData().setExpand(true, true);
		TagList lstTags = new TagList(grpTags);
		addChild(lstTags, "tags");
		
		// Transitions
		
		LFrame grpTransitions = new LFrame(contentEditor, Vocab.instance.TRANSITIONS);
		grpTransitions.setGridLayout(1);
		grpTransitions.setHoverText(Tooltip.instance.TRANSITIONS);
		grpTransitions.getCellData().setExpand(true, true);
		
		SimpleEditableList<Transition> lstTransitions = new SimpleEditableList<>(grpTransitions);
		lstTransitions.type = Transition.class;
		lstTransitions.getCollectionWidget().setEditEnabled(false);
		lstTransitions.getCellData().setExpand(true, true);
		addChild(lstTransitions, "transitions");
		
		TransitionEditor transitionEditor = new TransitionEditor(grpTransitions, false);
		transitionEditor.setGridLayout(3);
		transitionEditor.getCellData().setExpand(true, false);
		transitionEditor.getCellData().setAlignment(LFlags.CENTER);
		lstTransitions.addChild(transitionEditor);
		
		// Destination
		
		new LLabel(transitionEditor, Vocab.instance.DESTINATION, Tooltip.instance.DESTINATION);
		LText txtDest = new LText(transitionEditor, true);
		PositionButton btnDest = new PositionButton(transitionEditor);
		btnDest.setTextWidget(txtDest);
		transitionEditor.addControl(btnDest, "destination");
		
		// Origin Tiles
		
		new LLabel(transitionEditor, Vocab.instance.ORIGTILES, Tooltip.instance.ORIGTILES);
		LText txtOrigin = new LText(transitionEditor, true);
		PortalButton btnOrigin = new PortalButton(transitionEditor, id);
		btnOrigin.setTextWidget(txtOrigin);
		transitionEditor.addControl(btnOrigin, "origin");
		
		// Fade
		
		new LLabel(transitionEditor, Vocab.instance.FADEOUT, Tooltip.instance.FADEOUT);
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
	
	private static class TransitionEditor extends GDefaultObjectEditor<Transition> {
		public TransitionEditor(LContainer parent, boolean doubleBuffered) {
			super(parent, doubleBuffered);
		}
		@Override
		public Type getType() {
			return Transition.class;
		}
		
	}
	
}
