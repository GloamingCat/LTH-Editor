package gui.shell.database;

import gui.Tooltip;
import gui.Vocab;
import gui.widgets.IconSelector;
import lui.dialog.LObjectDialog;
import lui.dialog.LWindow;
import lui.widget.LLabel;
import lui.widget.LText;

import data.GameCharacter.Portrait;

import project.Project;

public class PortraitDialog extends LObjectDialog<Portrait> {
	
	private IconSelector iconSelector;
	private LText txtName;
	
	public PortraitDialog(LWindow parent) {
		super(parent, 600, 400, Vocab.instance.PORTRAITSHELL);
	}
	
	@Override
	protected void createContent(int style) {
		super.createContent(style);
		content.setGridLayout(2);
		
		new LLabel(content, Vocab.instance.NAME, Tooltip.instance.KEY);
		txtName = new LText(content);
		txtName.getCellData().setExpand(true, false);

		iconSelector = new IconSelector(content);
		iconSelector.getCellData().setSpread(2, 1);
		iconSelector.getCellData().setExpand(true, true);

	}
	
	public void open(Portrait initial) {
		txtName.setValue(initial.name);
		iconSelector.setCollection(Project.current.animations.getTree());
		iconSelector.setIcon(initial);
		super.open(initial);
	}

	@Override
	protected Portrait createResult(Portrait initial) {
		Portrait icon = new Portrait();
		icon.name = txtName.getValue();
		icon.id = iconSelector.getSelectedId();
		icon.col = iconSelector.getSelectedCol();
		icon.row = iconSelector.getSelectedRow();
		return icon;
	}

}
