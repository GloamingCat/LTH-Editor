package gui.shell.field;

import gui.Tooltip;
import gui.Vocab;
import lui.gson.GObjectDialog;
import gui.views.database.subcontent.TagList;
import lui.base.LFlags;
import lui.container.LFrame;
import lui.container.LPanel;
import lui.dialog.LWindow;
import lui.widget.LCheckBox;
import lui.widget.LLabel;
import lui.widget.LSpinner;
import lui.widget.LText;

import data.field.Layer.Info;

public class LayerDialog extends GObjectDialog<Info> {

	public LayerDialog(LWindow parent, int maxHeight) {
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
		grpGeneral.getCellData().setRequiredSize(150, 0);
		
		new LLabel(grpGeneral, Vocab.instance.NAME, Tooltip.instance.DISPLAYNAME);
		
		LText txtName = new LText(grpGeneral);
		txtName.getCellData().setExpand(true, false);
		addControl(txtName, "name");
		
		new LLabel(grpGeneral, Vocab.instance.HEIGHT, Tooltip.instance.POSITIONH);
		
		LSpinner spnHeight = new LSpinner(grpGeneral);
		spnHeight.getCellData().setExpand(true, false);
		spnHeight.setMinimum(1);
		spnHeight.setMaximum(maxHeight);
		addControl(spnHeight, "height");

		LPanel check = new LPanel(grpGeneral);
		check.getCellData().setSpread(2, 1);
		check.getCellData().setAlignment(LFlags.LEFT);
		check.setSequentialLayout(true);

		LCheckBox btnNoAuto = new LCheckBox(check);
		btnNoAuto.setText(Vocab.instance.NOAUTO);
		btnNoAuto.setHoverText(Tooltip.instance.NOAUTO);
		addControl(btnNoAuto, "noAuto");

		new LLabel(grpGeneral, 2, 1).getCellData().setExpand(false, true);

		LFrame grpTags = new LFrame(contentEditor, Vocab.instance.TAGS);
		grpTags.setFillLayout(true);
		grpTags.setHoverText(Tooltip.instance.TAGS);
		grpTags.getCellData().setExpand(true, true);
		TagList lstTags = new TagList(grpTags);
		addChild(lstTags, "tags");

	}
	
}
