package gui.shell.field;

import gui.Tooltip;
import gui.Vocab;
import gui.shell.ObjectShell;
import gui.views.database.subcontent.TagList;
import lui.base.data.LPoint;
import lui.container.LFrame;
import lui.dialog.LWindow;
import lui.widget.LCheckBox;
import lui.widget.LLabel;
import lui.widget.LSpinner;
import lui.widget.LText;

import data.field.Layer.Info;

public class LayerShell extends ObjectShell<Info> {


	public LayerShell(LWindow parent, int maxHeight) {
		super(parent, maxHeight, Vocab.instance.LAYERSHELL);
	}
	
	@Override
	protected void createContent(int maxHeight) {
		super.createContent(0);

		contentEditor.setGridLayout(2);
		
		LFrame grpGeneral = new LFrame(contentEditor, Vocab.instance.GENERAL);
		grpGeneral.setGridLayout(2);
		grpGeneral.setHoverText(Tooltip.instance.GENERAL);
		grpGeneral.getCellData().setExpand(true, true);
		grpGeneral.getCellData().setMinimumSize(200, 0);
		
		new LLabel(grpGeneral, Vocab.instance.NAME, Tooltip.instance.NAME);
		
		LText txtName = new LText(grpGeneral);
		txtName.getCellData().setExpand(true, false);
		addControl(txtName, "name");
		
		new LLabel(grpGeneral, Vocab.instance.HEIGHT, Tooltip.instance.POSITIONH);
		
		LSpinner spnHeight = new LSpinner(grpGeneral);
		spnHeight.getCellData().setExpand(true, false);
		spnHeight.setMinimum(1);
		spnHeight.setMaximum(maxHeight);
		addControl(spnHeight, "height");
		
		LCheckBox btnNoAuto = new LCheckBox(grpGeneral, 2);
		btnNoAuto.setText(Vocab.instance.NOAUTO);
		addControl(btnNoAuto, "noAuto");

		LFrame grpTags = new LFrame(contentEditor, Vocab.instance.TAGS);;
		grpTags.setFillLayout(true);
		grpTags.setHoverText(Tooltip.instance.TAGS);
		grpTags.getCellData().setExpand(true, true);
		grpTags.getCellData().setMinimumSize(100, 0);
		TagList lstTags = new TagList(grpTags);
		addChild(lstTags, "tags");

		LPoint size = getTargetSize();
		setMinimumSize(size.x, size.y);
	}
	
}
