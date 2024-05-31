package gui.shell;

import gui.widgets.IconSelector;
import project.Project;
import data.subcontent.Icon;
import gui.Vocab;
import lui.base.data.LDataTree;
import lui.dialog.LObjectDialog;
import lui.dialog.LWindow;

public class IconDialog extends LObjectDialog<Icon> {

	private IconSelector iconSelector;
	
	public static final int OPTIONAL = 1;
	
	/**
	 * @wbp.parser.constructor
	 */
	public IconDialog(LWindow parent, int style) {
		super(parent, 600, 400, style, Vocab.instance.ICONSHELL);
		setCurrentSize(800, 800);
	}
	
	@Override
	protected void createContent(int style) {
		super.createContent(style);
		content.setFillLayout(true);
		iconSelector = new IconSelector(content);
	}
	
	public void open(Icon initial) {
		iconSelector.setCollection(Project.current.animations.getTree());
		iconSelector.setIcon(initial);
		super.open(initial);
	}

	@Override
	protected Icon createResult(Icon initial) {
		Icon icon = new Icon();
		icon.id = iconSelector.getSelectedId();
		icon.col = iconSelector.getSelectedCol();
		icon.row = iconSelector.getSelectedRow();
		return icon;
	}
	
	protected LDataTree<Object> getTree() {
		return Project.current.animations.getTree();
	}
	
}
