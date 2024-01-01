package gui.shell.field;

import gui.Tooltip;
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

import data.field.Layer.Info;

public class LayerShell extends ObjectShell<Info> {

	public LayerShell(LShell parent) {
		super(parent);

		contentEditor.setGridLayout(2, false);
		
		LFrame grpGeneral = new LFrame(contentEditor, Vocab.instance.GENERAL, 2, false);
		grpGeneral.setHoverText(Tooltip.instance.GENERAL);
		grpGeneral.setExpand(true, true);
		
		new LLabel(grpGeneral, Vocab.instance.NAME, Tooltip.instance.NAME);
		
		LText txtName = new LText(grpGeneral);
		addControl(txtName, "name");
		
		new LLabel(grpGeneral, Vocab.instance.HEIGHT, Tooltip.instance.POSITIONH);
		
		LSpinner spnHeight = new LSpinner(grpGeneral);
		spnHeight.setMinimum(1);
		spnHeight.setMaximum(FieldSideEditor.instance.field.prefs.maxHeight);
		addControl(spnHeight, "height");
		
		LCheckBox btnNoAuto = new LCheckBox(grpGeneral, 2);
		btnNoAuto.setText(Vocab.instance.NOAUTO);
		addControl(btnNoAuto, "noAuto");
		
		LFrame grpTags = new LFrame(contentEditor, Vocab.instance.TAGS, true, true);
		grpTags.setHoverText(Tooltip.instance.TAGS);
		grpTags.setExpand(true, true);
		TagList lstTags = new TagList(grpTags);
		addChild(lstTags, "tags");
		
		pack();
	}
	
}
