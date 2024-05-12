package gui.shell.field;

import gui.views.fieldTree.subcontent.TransitionEditor;
import gui.widgets.CheckBoxPanel;
import lui.container.LFrame;
import lui.container.LImage;
import lui.container.LPanel;
import lui.dialog.LWindow;
import lui.base.event.LEditEvent;
import lui.widget.LCheckBox;
import lui.widget.LCombo;
import lui.widget.LLabel;
import lui.widget.LSpinner;
import lui.widget.LText;
import gui.Tooltip;
import gui.Vocab;
import lui.gson.GObjectDialog;
import gui.views.database.subcontent.TagList;
import gui.views.fieldTree.subcontent.FieldImageList;
import gui.widgets.AudioButton;
import gui.widgets.ScriptButton;
import gui.widgets.SimpleEditableList;
import lui.base.LFlags;
import lui.base.event.listener.LCollectionListener;

import data.Animation;
import data.field.Field;
import data.field.FieldImage;
import data.field.FieldNode;
import data.field.Transition;
import project.Project;

public class FieldPrefDialog extends GObjectDialog<Field.Prefs> {

	protected int id;
	
	public FieldPrefDialog(LWindow parent, FieldNode n) {
		super(parent, 800, 400, Project.current.fieldTree.getData().findNode(n).id, "");
		setTitle(String.format("[%03d] ", id) + n.name);
		setCurrentSize(1000, 600);
	}
	
	@Override
	protected void createContent(int id) {
		super.createContent(0);
		this.id = id;
		contentEditor.setFillLayout(true);

		LPanel left = new LPanel(contentEditor);
		left.setGridLayout(1);

		LFrame grpGeneral = new LFrame(left, Vocab.instance.GENERAL);
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
		txtKey.getCellData().setExpand(true, false);
		addControl(txtKey, "key");
		
		new LLabel(grpGeneral, Vocab.instance.NAME, Tooltip.instance.NAME);
		LText txtName = new LText(grpGeneral);
		txtName.getCellData().setSpread(2, 1);
		txtName.getCellData().setExpand(true, false);
		addControl(txtName, "name");
		
		new LLabel(grpGeneral, Vocab.instance.DEFAULTREGION, Tooltip.instance.DEFAULTREGION);
		LCombo cmbRegion = new LCombo(grpGeneral, true);
		cmbRegion.getCellData().setSpread(2, 1);
		cmbRegion.getCellData().setExpand(true, false);
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
		txtBGM.getCellData().setExpand(true, false);
		txtBGM.getCellData().setAlignment(LFlags.MIDDLE);
		AudioButton btnBGM = new AudioButton(grpGeneral, true);
		btnBGM.setTextWidget(txtBGM);
		addControl(btnBGM, "bgm");

		new LLabel(grpGeneral, Vocab.instance.LOADSCRIPT, Tooltip.instance.LOADSCRIPT);
		LText txtScript = new LText(grpGeneral, true);
		txtScript.getCellData().setExpand(true, false);
		txtScript.getCellData().setAlignment(LFlags.MIDDLE);
		ScriptButton btnScript = new ScriptButton(grpGeneral, 1);
		btnScript.setPathWidget(txtScript);
		addControl(btnScript, "loadScript");

		LPanel check = new CheckBoxPanel(grpGeneral);
		check.getCellData().setSpread(3, 1);

		LCheckBox btnPersistent = new LCheckBox(check);
		btnPersistent.setText(Vocab.instance.PERSISTENT);
		btnPersistent.setHoverText(Tooltip.instance.PERSISTENT);
		addControl(btnPersistent, "persistent");

		// Transitions

		LFrame grpTransitions = new LFrame(left, Vocab.instance.TRANSITIONS);
		grpTransitions.setGridLayout(1);
		grpTransitions.setHoverText(Tooltip.instance.TRANSITIONS);
		grpTransitions.getCellData().setExpand(true, true);

		SimpleEditableList<Transition> lstTransitions = new SimpleEditableList<>(grpTransitions);
		lstTransitions.type = Transition.class;
		lstTransitions.getCollectionWidget().setEditEnabled(false);
		lstTransitions.getCellData().setExpand(true, true);
		addChild(lstTransitions, "transitions");

		TransitionEditor transitionEditor = new TransitionEditor(grpTransitions, id);
		transitionEditor.getCellData().setExpand(true, false);
		lstTransitions.addChild(transitionEditor);

		// Images
		
		LFrame grpImages = new LFrame(contentEditor, Vocab.instance.IMAGES);
		grpImages.setFillLayout(false);
		grpImages.setHoverText(Tooltip.instance.IMAGES);
		
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
		TagList lstTags = new TagList(grpTags);
		addChild(lstTags, "tags");

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
			img.setImage(anim.quad.fullPath(), anim.quad);
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
